package aradevs.com.gradecheck;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import aradevs.com.gradecheck.adapters.AdapterGradeDetail;
import aradevs.com.gradecheck.helpers.SharedHelper;
import aradevs.com.gradecheck.models.Users;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeDetailFragment extends Fragment {


    @BindView(R.id.gradedetailPb)
    ProgressBar gradedetailPb;
    @BindView(R.id.gradedetailRecyclerView)
    RecyclerView gradedetailRecyclerView;
    //declaring useful variables
    Context context;
    Unbinder unbinder;
    View view;
    SharedHelper sh;
    Users u;

    public GradeDetailFragment() {
        // Required empty public constructor
    }

    //request grades data method
    private void requestData(final View view, String id) {
        /*
        JsonObjectRequest request = new JsonObjectRequest(
                ServerHelper.URL + ServerHelper.COURSES + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                        Log.e("Error Response: ", error.getMessage());
                        Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        swiperefresh.setRefreshing(false);
                        pbGrades.setVisibility(View.GONE);
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(context).addToRequestQueue(request, context);
        */
        gradedetailPb.setVisibility(View.GONE);
        gradedetailRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        gradedetailRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new AdapterGradeDetail();
        gradedetailRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initializing variables
        this.view = view;
        requestData(view, "hola");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
