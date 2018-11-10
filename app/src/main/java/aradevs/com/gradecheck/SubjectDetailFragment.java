package aradevs.com.gradecheck;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.models.Success;
import aradevs.com.gradecheck.models.Teachers;
import aradevs.com.gradecheck.singleton.AppSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectDetailFragment extends Fragment {


    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.subjectDetailAproved)
    TextView subjectDetailAproved;
    @BindView(R.id.subjectDetailReprobate)
    TextView subjectDetailReprobate;
    Unbinder unbinder;
    Success s;
    String courseId;
    @BindView(R.id.subjectDetailTeacher)
    TextView subjectDetailTeacher;

    public SubjectDetailFragment() {
        // Required empty public constructor
    }

    //request grades data method
    private void requestData(final View view, String id) {
        JsonObjectRequest request = new JsonObjectRequest(
                ServerHelper.URL + ServerHelper.SUCCESS_RATIO + id,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(JSONObject response) {
                        ParseJsonHelper ph = new ParseJsonHelper();
                        s = ph.parseJsonSuccess(response);
                        if (!s.getPassed().equals("0") || !s.getFailed().equals("0")) {

                            //chart
                            Log.e("aprobados", s.getPassed());
                            List<PieEntry> entries = new ArrayList<>();
                            chart.setUsePercentValues(true);
                            entries.add(new PieEntry(Integer.parseInt(s.getPassed()), "Aprobados"));
                            entries.add(new PieEntry(Integer.parseInt(s.getFailed()), "Reprobados"));
                            PieDataSet dataSet = new PieDataSet(entries, "");
                            dataSet.setSliceSpace(2);
                            dataSet.setColors(getResources().getColor(R.color.green), getResources().getColor(R.color.colorPrimaryDark));
                            PieData pieData = new PieData(dataSet);
                            chart.setData(pieData);
                            Description d = new Description();
                            d.setText("");
                            chart.setDescription(d);
                            chart.setDrawSliceText(false);
                            chart.invalidate();

                            //percentage
                            Double tot = Double.parseDouble(s.getPassed()) + Double.parseDouble(s.getFailed());
                            Double success = (Double.parseDouble(s.getPassed()) / tot) * 100;
                            Double failed = (Double.parseDouble(s.getFailed()) / tot) * 100;
                            String totSuccess = String.format("%.2f", success) + "%";
                            String totFailed = String.format("%.2f", failed) + "%";
                            subjectDetailAproved.setText(totSuccess);
                            subjectDetailReprobate.setText(totFailed);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request, getActivity().getApplicationContext());
    }

    //request grades data method
    private void requestTeacherData(final View view, String id) {
        JsonObjectRequest request = new JsonObjectRequest(
                ServerHelper.URL + ServerHelper.COURSE_TEACHER + id + ServerHelper.CURRENT_TEACHERS,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(JSONObject response) {
                        ParseJsonHelper ph = new ParseJsonHelper();
                        final Teachers t = ph.parseJsonSingleTeacher(response);
                        String fullName = t.getUsers().getName() + " " + t.getUsers().getSurname();
                        subjectDetailTeacher.setText(fullName);
                        subjectDetailTeacher.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction trans = getActivity().getFragmentManager().beginTransaction();
                                TeacherDetailFragment teacherDetailFragment = new TeacherDetailFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("teacher", t);
                                teacherDetailFragment.setArguments(bundle);
                                trans.replace(R.id.container, teacherDetailFragment, "Inicio");
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, getResources().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request, getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        courseId = bundle.getString("id");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData(getView(), courseId);
        requestTeacherData(getView(), courseId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
}
