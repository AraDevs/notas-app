package aradevs.com.gradecheck.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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
import aradevs.com.gradecheck.TeacherDetailFragment;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Teachers;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ar4 on 7/10/2018.
 */
public class AdapterTeachers extends RecyclerView.Adapter<AdapterTeachers.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "TeachersFragment-Adapter";
    private ArrayList<Teachers> items;

    public AdapterTeachers(JSONArray items) {
        ParseJsonHelper helper = new ParseJsonHelper();
        this.items = helper.parseJsonTeachers(items);
    }

    @Override
    public AdapterTeachers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teachers, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Teachers t = items.get(position);
        //setting on click listener to the cardview
        String fullName = t.getUsers().getName() + " " + t.getUsers().getSurname();
        holder.name.setText(fullName);
        holder.id.setText(items.get(position).getId());
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity activity = (HomeActivity) holder.context;
                FragmentTransaction trans = activity.getFragmentManager().beginTransaction();
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring variables
        CardView ln;
        TextView name;
        TextView id;
        CircleImageView image;
        Context context;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            name = itemView.findViewById(R.id.teacherName);
            id = itemView.findViewById(R.id.teacherId);
            image = itemView.findViewById(R.id.teacherImage);
            context = itemView.getContext();
        }
    }
}