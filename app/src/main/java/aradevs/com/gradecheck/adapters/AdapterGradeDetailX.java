package aradevs.com.gradecheck.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import aradevs.com.gradecheck.AppealFragment;
import aradevs.com.gradecheck.HomeActivity;
import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Evaluations;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetailX extends RecyclerView.Adapter<AdapterGradeDetailX.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Evaluations> items;
    private String period;
    private int latest = 0;

    public AdapterGradeDetailX(JSONArray items, String period) {
        ParseJsonHelper pj = new ParseJsonHelper();
        this.items = pj.parseJsonCourseEvaluations(items);
        this.period = period;
    }

    @Override
    public AdapterGradeDetailX.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gradedetailx, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Double total = 0.0;
        int laboratoryCount = 0;
        for (int i = latest; i < items.size(); i++) {
            if (items.get(i).getLaboratory().equals("true")) {
                laboratoryCount++;
            }
        }
        for (int i = latest; i < items.size(); i++) {
            if (items.get(i).getPeriods().equals(this.period)) {
                if (items.get(i).getLaboratory().equals("true")) {
                    holder.lab.setVisibility(View.VISIBLE);
                }
                total = (Double.parseDouble(items.get(i).getEvaluations()) * (Double.parseDouble(items.get(i).getPercentage()) / 100));
                if (laboratoryCount > 0) {
                    if (items.get(i).getLaboratory().equals("true")) {
                        holder.lab.setVisibility(View.VISIBLE);
                        total = total * 0.4;
                    } else {
                        total = total * 0.6;
                    }
                }
                holder.eva.setText(items.get(i).getDescriptions());
                holder.percentage.setText(items.get(i).getPercentage().trim());
                holder.grade.setText(items.get(i).getEvaluations().trim());
                holder.total.setText(String.format("%.2f", total));
                final int finalI = i;
                holder.correction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HomeActivity activity = (HomeActivity) holder.context;
                        FragmentTransaction trans = activity.getFragmentManager().beginTransaction();
                        AppealFragment appealFragment = new AppealFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(finalI).getGradeId());
                        appealFragment.setArguments(bundle);
                        trans.replace(R.id.container, appealFragment, "Inicio");
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                });
                latest = i + 1;
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        for (Evaluations period : items) {
            if (period.getPeriods().equals(this.period)) {
                count++;
            }
        }
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring variables
        LinearLayout ln;
        TextView eva;
        TextView grade;
        TextView percentage;
        TextView lab;
        TextView total;
        Button correction;
        Context context;

        ViewHolder(LinearLayout itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            eva = itemView.findViewById(R.id.gradedetailEva);
            grade = itemView.findViewById(R.id.gradedetailGrade);
            percentage = itemView.findViewById(R.id.gradedetailPercentage);
            correction = itemView.findViewById(R.id.gradeDetailCorrection);
            lab = itemView.findViewById(R.id.gradeDetailLab);
            total = itemView.findViewById(R.id.gradeDetailTotal);
            context = itemView.getContext();
        }
    }
}
