package aradevs.com.gradecheck.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import aradevs.com.gradecheck.HomeActivity;
import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.SubjectDetailFragment;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Courses;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Courses> items;

    public AdapterSubject(JSONArray items) {
        ParseJsonHelper helper = new ParseJsonHelper();
        this.items = helper.parseJsonCourses(items);
    }

    @Override
    public AdapterSubject.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subjects, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //setting on click listener to the cardview
        holder.name.setText(items.get(position).getName());
        holder.id.setText(items.get(position).getId());
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity activity = (HomeActivity) holder.context;
                FragmentTransaction trans = activity.getFragmentManager().beginTransaction();
                SubjectDetailFragment subjectDetailFragment = new SubjectDetailFragment();
                trans.replace(R.id.container, subjectDetailFragment, "Inicio");
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
        TextView id;
        Context context;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            name = itemView.findViewById(R.id.subjectName);
            id = itemView.findViewById(R.id.subjectId);
            context = itemView.getContext();
        }
    }
}

