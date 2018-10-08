package aradevs.com.gradecheck;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import aradevs.com.gradecheck.adapters.AdapterGradeDetail;
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
    Unbinder unbinder;
    View view;
    String courseId;
    @BindView(R.id.gradedetailName)
    TextView gradedetailName;

    public GradeDetailFragment() {
        // Required empty public constructor
    }

    //request grades data method
    private void requestData(final View view, String id) {
        gradedetailPb.setVisibility(View.GONE);
        gradedetailRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        gradedetailRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new AdapterGradeDetail(id);
        gradedetailRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        courseId = bundle.getString("id");
        gradedetailName.setText(bundle.getString("name"));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initializing variables
        this.view = view;
        requestData(view, courseId);
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
