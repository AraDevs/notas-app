package aradevs.com.gradecheck.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.models.Courses;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class AdapterGrades extends RecyclerView.Adapter<AdapterGrades.ViewHolder> {

    private static final String TAG = "GradesFragment-Adapter";

    private ArrayList<Courses> items;


    public AdapterGrades(ArrayList<Courses> objects) {
        items = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grades, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Courses c = items.get(position);
        holder.name.setText(c.getName());
        ArrayList<Double> grades = new ArrayList<>();
        int p = c.getEva().getEvaluations().size() / 3;
        for (int i = 1; i <= p; i++) {
            grades.add(c.getEva().getProm(i));
        }
        Double tot = 0.0;
        for (double item : grades) {
            tot += item;
        }
        holder.grades.setText(String.format("%.2f",tot));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView ln;
        TextView name;
        TextView grades;

        ViewHolder(CardView itemView) {
            super(itemView);
            ln = itemView;
            name = itemView.findViewById(R.id.tvCourseName);
            grades = itemView.findViewById(R.id.tvGrade);
        }
    }


}