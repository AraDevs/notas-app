package aradevs.com.gradecheck.adapters;

import android.content.Context;
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
 * Created by Ar4 on 7/10/2018.
 */
public class AdapterTeacherSubjects extends RecyclerView.Adapter<AdapterTeacherSubjects.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "TeachersFragment-Adapter";
    private ArrayList<Courses> items;

    public AdapterTeacherSubjects(JSONObject items) {
        ParseJsonHelper helper = new ParseJsonHelper();
        this.items = helper.parseJsonTeacherCourses(items);
    }

    @Override
    public AdapterTeacherSubjects.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teachersubject, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterTeacherSubjects.ViewHolder holder, int position) {

        final Courses c = items.get(position);
        holder.ln.setText(c.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring variables
        TextView ln;
        Context context;

        ViewHolder(TextView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            context = itemView.getContext();
        }
    }
}