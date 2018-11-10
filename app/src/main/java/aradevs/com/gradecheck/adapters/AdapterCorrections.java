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

import aradevs.com.gradecheck.CorrectionDetailFragment;
import aradevs.com.gradecheck.HomeActivity;
import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.models.Corrections;

/**
 * Created by Ar4 on 9/11/2018.
 */
public class AdapterCorrections extends RecyclerView.Adapter<AdapterCorrections.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private ArrayList<Corrections> items;

    public AdapterCorrections(JSONArray items) {
        ParseJsonHelper helper = new ParseJsonHelper();
        this.items = helper.parseJsonCorrections(items);
    }

    @Override
    public AdapterCorrections.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_corrections, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //setting on click listener to the cardview
        holder.description.setText(items.get(position).getDescription());
        holder.owner.setText(items.get(position).getOwner());
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity activity = (HomeActivity) holder.context;
                FragmentTransaction trans = activity.getFragmentManager().beginTransaction();
                CorrectionDetailFragment correctionDetailFragment = new CorrectionDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("correction", items.get(holder.getAdapterPosition()));
                correctionDetailFragment.setArguments(bundle);
                trans.replace(R.id.container, correctionDetailFragment, "Inicio");
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
        TextView description;
        TextView owner;
        Context context;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            description = itemView.findViewById(R.id.correctionDetail);
            owner = itemView.findViewById(R.id.correctionTeacher);
            context = itemView.getContext();
        }
    }
}

