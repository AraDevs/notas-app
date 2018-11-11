package aradevs.com.gradecheck;


import android.app.Fragment;
import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import aradevs.com.gradecheck.adapters.AdapterGrades;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.SharedHelper;
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
        JsonArrayRequest request = new JsonArrayRequest(
                ServerHelper.URL + ServerHelper.COURSES + id + ServerHelper.CURRENT_GRADES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("entra?", "entra");
                        swiperefresh.setRefreshing(false);
                        ProgressBar pb = view.findViewById(R.id.pbGrades);
                        pb.setVisibility(View.GONE);
                        RecyclerView mRecyclerView = view.findViewById(R.id.gradeRecyclerView);
                        assert mRecyclerView != null;
                        mRecyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        RecyclerView.Adapter mAdapter = new AdapterGrades(response);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            if (error.networkResponse.statusCode == 401) {
                                sh.sessionExpired(getActivity());
                            } else {
                                Toast.makeText(context, new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Error de servidor", Toast.LENGTH_SHORT).show();
                        }
                        swiperefresh.setRefreshing(false);
                        pbGrades.setVisibility(View.GONE);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", u.getToken());
                return params;
            }
        };
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
                requestData(view, u.getPersonId());
            }
        });
        sh = new SharedHelper(getActivity());
        u = sh.getUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            //setting fragment context
            context = getActivity().getApplicationContext();
            //setting view holder
            view = getView();
            //requesting grades data
            requestData(view, u.getPersonId());
        } catch (Exception e) {
            Log.e("Skipped request data", "Probably cleaning fragment");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }


}