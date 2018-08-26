package aradevs.com.gradecheck;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import aradevs.com.gradecheck.helpers.ParseJson;
import aradevs.com.gradecheck.helpers.Server;
import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.singleton.AppSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {
    Context context;
    @BindView(R.id.pbGrades)
    ProgressBar pbGrades;
    Unbinder unbinder;

    public GradesFragment() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String id = "1";

        //setting fragment context
        context = getActivity().getApplicationContext();
        //setting view holder
        final View holder = view;

        //requesting data
        JsonObjectRequest request = new JsonObjectRequest(
                Server.URL + Server.COURSES + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //inflating recycler view
                        ParseJson pJ = new ParseJson();
                        ArrayList<Courses> courses = pJ.parseJson(response);
                        RecyclerView mRecyclerView = holder.findViewById(R.id.gradeRecyclerView);
                        assert mRecyclerView != null;
                        mRecyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(holder.getContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        RecyclerView.Adapter mAdapter = new AdapterGrades(courses);
                        mRecyclerView.setAdapter(mAdapter);
                        pbGrades.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Response: ", error.getMessage());
                        Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_LONG).show();
                    }
                }
        );
        //sending request to singleton
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
