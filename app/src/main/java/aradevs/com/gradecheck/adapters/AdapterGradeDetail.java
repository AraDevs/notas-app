package aradevs.com.gradecheck.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.singleton.AppSingleton;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetail extends RecyclerView.Adapter<AdapterGradeDetail.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    String id;

    public AdapterGradeDetail(String id) {
        this.id = id;
    }

    //request grades data method
    private void requestData(final AdapterGradeDetail.ViewHolder holder, String id, final int period) {
        JsonObjectRequest request = new JsonObjectRequest(
                ServerHelper.URL + ServerHelper.COURSE_EVALUATIONS + id + ServerHelper.GRADES,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        holder.recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(holder.recyclerView.getContext());
                        holder.recyclerView.setLayoutManager(mLayoutManager);
                        RecyclerView.Adapter mAdapter = new AdapterGradeDetailX(response, period);
                        holder.recyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(holder.context, "Hey", Toast.LENGTH_SHORT).show();
                        holder.pb.setVisibility(View.GONE);
                    }
                }
        );
        //send request to queue
        AppSingleton.getInstance(holder.context).addToRequestQueue(request, holder.context);
    }

    @Override
    public AdapterGradeDetail.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //declaring view holder
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gradedetail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String title = "Periodo " + (holder.getAdapterPosition() + 1);
        holder.period.setText(title);
        //setting on click listener to the cardview
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.pb.setVisibility(View.VISIBLE);
                LinearLayout expandableLayout = v.findViewById(R.id.expandableLayout);

                int isExpanded = expandableLayout.getVisibility();
                if (isExpanded == View.GONE) {
                    expandableLayout.setVisibility(View.VISIBLE);
                    requestData(holder, id, (holder.getAdapterPosition() + 1));
                } else {
                    expandableLayout.setVisibility(View.GONE);
                }

                holder.pb.setVisibility(View.GONE);
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
        TextView period;
        ProgressBar pb;
        RecyclerView recyclerView;
        Context context;

        ViewHolder(CardView itemView) {
            super(itemView);

            //binding UI
            ln = itemView;
            period = itemView.findViewById(R.id.gradedetailPeriod);
            pb = itemView.findViewById(R.id.gradedetailPbX);
            recyclerView = itemView.findViewById(R.id.gradedetailRecyclerViewX);
            context = itemView.getContext();
        }
    }
}
