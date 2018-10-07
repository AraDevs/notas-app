package aradevs.com.gradecheck.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Evaluations;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetailX extends RecyclerView.Adapter<AdapterGradeDetailX.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private Evaluations items;
    private int period;
    private static int latest = 0;


    public AdapterGradeDetailX(JSONObject items, int period) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {

        for (int i = latest; i < items.getPeriods().size(); i++) {
            if (Integer.parseInt(items.getPeriods().get(i)) == this.period) {
                holder.eva.setText(items.getDescriptions().get(i));
                holder.percentage.setText(items.getPercentage().get(i));
                holder.grade.setText(items.getEvaluations().get(i));
                latest = i + 1;
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        // return items.size();
        int count = 0;
        for (String period : items.getPeriods()) {
            if (Integer.parseInt(period) == this.period) {
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
        Context context;

        ViewHolder(LinearLayout itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            eva = itemView.findViewById(R.id.gradedetailEva);
            grade = itemView.findViewById(R.id.gradedetailGrade);
            percentage = itemView.findViewById(R.id.gradedetailPercentage);
            context = itemView.getContext();
        }
    }
}
