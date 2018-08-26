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


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {
    Context context;
    ProgressBar pb;

    public GradesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pb = getActivity().findViewById(R.id.pbGrades);
        context = getActivity().getApplicationContext();
        final View holder = view;


        JsonObjectRequest request = new JsonObjectRequest(
                Server.URL + Server.COURSES + "1",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ParseJson pJ = new ParseJson();
                        ArrayList<Courses> courses = pJ.parseJson(response);
                        RecyclerView mRecyclerView = holder.findViewById(R.id.gradeRecyclerView);
                        assert mRecyclerView != null;
                        mRecyclerView.setHasFixedSize(true);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(holder.getContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        RecyclerView.Adapter mAdapter = new AdapterGrades(courses);
                        mRecyclerView.setAdapter(mAdapter);
                        pb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Response: ", error.getMessage());
                        Toast.makeText(context, "Vaya.. la cagamos", Toast.LENGTH_LONG).show();
                    }
                }
        );
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }
}
