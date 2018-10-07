package aradevs.com.gradecheck;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectDetailFragment extends Fragment {


    public SubjectDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<PieEntry> entries = new ArrayList<>();
        PieChart pieChart = view.findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);
        entries.add(new PieEntry(10, "Aprobados"));
        entries.add(new PieEntry(5, "Reprobados"));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2);
        dataSet.setColors(getResources().getColor(R.color.green), getResources().getColor(R.color.colorPrimaryDark));
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        Description d = new Description();
        d.setText("");
        pieChart.setDescription(d);
        pieChart.setDrawSliceText(false);
        pieChart.invalidate();
    }
}
