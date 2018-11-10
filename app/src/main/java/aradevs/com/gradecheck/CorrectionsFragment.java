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
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import aradevs.com.gradecheck.adapters.AdapterCorrections;
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
public class CorrectionsFragment extends Fragment {

    Context context;
    Users u;
    SharedHelper sh;
    @BindView(R.id.correctionsPb)
    ProgressBar correctionsPb;
    @BindView(R.id.correctionsRecyclerView)
    RecyclerView correctionsRecyclerView;
    Unbinder unbinder;

    public CorrectionsFragment() {
        // Required empty public constructor
    }

    //request grades data method
    private void requestData(final View view, String id) {
        JsonArrayRequest request = new JsonArrayRequest(
                ServerHelper.URL + ServerHelper.CORRECTIONS_BY_STUDENT + id + "/course/owner",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        correctionsPb.setVisibility(View.GONE);
                        correctionsRecyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
                        correctionsRecyclerView.setLayoutManager(mLayoutManager);
                        RecyclerView.Adapter mAdapter = new AdapterCorrections(response);
                        correctionsRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        correctionsPb.setVisibility(View.GONE);
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(context).addToRequestQueue(request, context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sh = new SharedHelper(getActivity());
        u = sh.getUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity().getApplicationContext();
        //requesting grades data
        try {
            requestData(getView(), u.getPersonId());
        } catch (Exception e) {
            Log.e("Skipped request data", "Probably cleaning fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_corrections, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
