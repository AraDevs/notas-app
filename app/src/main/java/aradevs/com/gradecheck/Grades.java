package aradevs.com.gradecheck;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aradevs.com.gradecheck.adapters.AdapterGrades;
import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.models.Evaluations;


/**
 * A simple {@link Fragment} subclass.
 */
public class Grades extends Fragment {

    public Grades() {
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
        ArrayList<Evaluations> evaluations = new ArrayList<>();
        Evaluations e = new Evaluations("1","10","9.5","10");
        evaluations.add(e);
        Courses c = new Courses("1","Mate 3",evaluations);

        ArrayList<Courses> courses = new ArrayList<>();
        for(int i=0;i<30;i++){
            courses.add(c);
        }

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.gradeRecyclerView);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new AdapterGrades(courses);
        mRecyclerView.setAdapter(mAdapter);
    }
}
