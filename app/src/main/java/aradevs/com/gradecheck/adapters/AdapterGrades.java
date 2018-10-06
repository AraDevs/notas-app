package aradevs.com.gradecheck.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Courses;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class AdapterGrades extends RecyclerView.Adapter<AdapterGrades.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Courses> items;


    public AdapterGrades(JSONObject objects) {
        ParseJsonHelper pj = new ParseJsonHelper();
        items = pj.parseJsonCourses(objects);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grades, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //declaring variables
        Courses c = items.get(position);
        Double tot = 0.0;
        ArrayList<Double> grades = new ArrayList<>();

        /*DEPRECATED
        //retrieving evaluations info
        int p = c.getEva().getEvaluations().size() / 3;
        for (int i = 1; i <= p; i++) {
            grades.add(c.getEva().getProm(i));
        }
        for (double item : grades) {
            tot += item;
        }*/


        tot = c.getEva().getProm();

        //setting values
        holder.name.setText(c.getName());
        holder.grades.setText(String.format("%.2f",tot));

    }

    @Override
    public int getItemCount() {
        return items.size();
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