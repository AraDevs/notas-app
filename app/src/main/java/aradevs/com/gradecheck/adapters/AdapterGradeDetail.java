package aradevs.com.gradecheck.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.models.Courses;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetail extends RecyclerView.Adapter<AdapterGradeDetail.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Courses> items;

    public AdapterGradeDetail() {

    }

    @Override
    public AdapterGradeDetail.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gradedetail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //setting on click listener to the cardview
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout expandableLayout = v.findViewById(R.id.expandableLayout);
                int isExpanded = expandableLayout.getVisibility();
                if (isExpanded == View.GONE) {
                    expandableLayout.setVisibility(View.VISIBLE);
                } else {
                    expandableLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // return items.size();
        return 3;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring variables
        CardView ln;
        TextView name;
        TextView grades;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            name = itemView.findViewById(R.id.tvCourseName);
            grades = itemView.findViewById(R.id.tvGrade);

        }
    }
}
