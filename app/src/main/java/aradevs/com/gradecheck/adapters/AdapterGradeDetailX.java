package aradevs.com.gradecheck.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.models.Courses;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetailX extends RecyclerView.Adapter<AdapterGradeDetailX.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Courses> items;

    public AdapterGradeDetailX() {

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
        //holder.pb.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        // return items.size();
        return 3;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring variables
        LinearLayout ln;
        TextView eva;
        TextView grade;
        TextView percentage;
        ProgressBar pb;

        ViewHolder(LinearLayout itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            eva = itemView.findViewById(R.id.gradedetailEva);
            grade = itemView.findViewById(R.id.gradedetailGrade);
            percentage = itemView.findViewById(R.id.gradedetailPercentage);
            pb = itemView.findViewById(R.id.gradedetailPbX);
        }
    }
}
