package aradevs.com.gradecheck;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import aradevs.com.gradecheck.adapters.AdapterGrades;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.SharedHelper;
import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.models.Users;
import aradevs.com.gradecheck.singleton.AppSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {

    //declaring useful variables
    Context context;
    @BindView(R.id.pbGrades)
    ProgressBar pbGrades;
    Unbinder unbinder;
    View view;
    SwipeRefreshLayout swiperefresh;
    SharedHelper sh;
    Users u;

    public GradesFragment() {
    }

    //request grades data method
    private void requestData(final View view, String id) {
        JsonObjectRequest request = new JsonObjectRequest(
                ServerHelper.URL + ServerHelper.COURSES + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //execute an async task to serialize JSON
                        JsonAsync ja = new JsonAsync(response, view);
                        ja.execute();
                        swiperefresh.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Response: ", error.getMessage());
                        Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_LONG).show();
                        swiperefresh.setRefreshing(false);
                        pbGrades.setVisibility(View.GONE);
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(context).addToRequestQueue(request, context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initializing variables
        this.view = view;
        swiperefresh = view.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(view, u.getId());
            }
        });
        sh = new SharedHelper(getActivity());
        u = sh.getUser();
    }

    @Override
    public void onResume() {
        super.onResume();

        //setting fragment context
        context = getActivity().getApplicationContext();
        //setting view holder
        view = getView();

        //requesting grades data
        requestData(view, u.getId());
        Log.e("Memory", String.valueOf(Runtime.getRuntime().totalMemory()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

//async task to serialize json
class JsonAsync extends AsyncTask<Void, Void, ArrayList<Courses>> {
    @SuppressLint("StaticFieldLeak")
    private View view;
    private JSONObject json;

    JsonAsync(JSONObject j, View view) {
        this.view = view;
        this.json = j;
    }

    @Override
    protected ArrayList<Courses> doInBackground(Void... pParams) {
        ParseJsonHelper pj = new ParseJsonHelper();
        return pj.parseJsonCourses(json);
    }

    @Override
    protected void onPostExecute(ArrayList<Courses> courses) {
        super.onPostExecute(courses);
        ProgressBar pb = view.findViewById(R.id.pbGrades);
        pb.setVisibility(View.GONE);
        RecyclerView mRecyclerView = view.findViewById(R.id.gradeRecyclerView);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new AdapterGrades(courses);
        mRecyclerView.setAdapter(mAdapter);

        Log.e("Available", String.valueOf(Runtime.getRuntime().freeMemory()));
    }
}