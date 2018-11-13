package aradevs.com.gradecheck.adapters;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import aradevs.com.gradecheck.GradeDetailFragment;
import aradevs.com.gradecheck.HomeActivity;
import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.models.Evaluations;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class AdapterGrades extends RecyclerView.Adapter<AdapterGrades.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Courses> items;


    public AdapterGrades(JSONArray objects) {
        ParseJsonHelper pj = new ParseJsonHelper();
        items = pj.parseJsonRegisteredCourses(objects);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //declaring variables
        final Courses c = items.get(position);
        Double tot = 0.0;
        Double totLab = 0.0;
        Double finalProm = 0.0;

        ArrayList<Double> gradesLab = new ArrayList<>();
        ArrayList<Double> grades = new ArrayList<>();

        //temp Arraylists
        ArrayList<Evaluations> withoutLab = new ArrayList<>();
        ArrayList<Evaluations> withLab = new ArrayList<>();

        for (Evaluations eva : c.getEva()) {
            if (eva.getLaboratory().equals("false")) {
                withoutLab.add(eva);
            } else {
                withLab.add(eva);
            }
        }

        int currentPeriod = 1;
        //retrieving evaluations
        for (int i = 1; i <= 3; i++) {
            Double tempTot = new Evaluations().getProm(withoutLab, i);
            if (withLab.size() == 0) {
                switch (i) {
                    case 1:
                        tempTot = tempTot * 0.30;
                        break;
                    case 2:
                        tempTot = tempTot * 0.35;
                        break;
                    case 3:
                        tempTot = tempTot * 0.35;
                }
            } else {
                switch (i) {
                    case 1:
                        tempTot = (tempTot * 0.6) * 0.30;
                        break;
                    case 2:
                        tempTot = (tempTot * 0.6) * 0.35;
                        break;
                    case 3:
                        tempTot = (tempTot * 0.6) * 0.35;
                }
            }
            grades.add(tempTot);
            if (tempTot == 0.0) {
                currentPeriod = i;
                break;
            }
        }

        for (int i = 1; i <= 3; i++) {
            Double tempTot = new Evaluations().getPromLab(withLab, i);
            switch (i) {
                case 1:
                    tempTot = (tempTot * 0.4) * 0.30;
                    break;
                case 2:
                    tempTot = (tempTot * 0.4) * 0.35;
                    break;
                case 3:
                    tempTot = (tempTot * 0.4) * 0.35;
            }
            gradesLab.add(tempTot);
            if (tempTot == 0.0) {
                //currentPeriod = i;
                break;
            }
        }

        for (double item : grades) {
            tot += item;
        }

        for (double item : gradesLab) {
            totLab += item;
        }

        if (withLab.size() == 0) {
            finalProm = tot;
        } else {
            finalProm = tot + totLab;
        }


        final ArrayList<Double> requiredGrades = new Evaluations().calculateRequired(finalProm, currentPeriod);
        for (double item2 : requiredGrades) {
            Log.e("Requerido", String.format("%.2f", item2));
        }
        Log.e("Final total", String.valueOf(finalProm));

        //tot = c.getEva().getProm();

        //setting values
        holder.name.setText(c.getName());
        holder.id.setText(c.getId());
        holder.grades.setText(String.format("%.2f", finalProm));

        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity activity = (HomeActivity) holder.context;
                FragmentTransaction trans = activity.getFragmentManager().beginTransaction();
                GradeDetailFragment gradeDetailFragment = new GradeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", c.getRegistered_Course());
                bundle.putString("name", c.getName());
                bundle.putSerializable("required", requiredGrades);
                gradeDetailFragment.setArguments(bundle);
                trans.replace(R.id.container, gradeDetailFragment, "Inicio");
                trans.addToBackStack(null);
                trans.commit();
            }
        });
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
        TextView id;
        Context context;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            name = itemView.findViewById(R.id.tvCourseName);
            grades = itemView.findViewById(R.id.tvGrade);
            context = itemView.getContext();
            id = itemView.findViewById(R.id.gradeId);
        }
    }


}