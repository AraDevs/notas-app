package aradevs.com.gradecheck.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import aradevs.com.gradecheck.R;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.SharedHelper;
import aradevs.com.gradecheck.models.Users;
import aradevs.com.gradecheck.singleton.AppSingleton;

/**
 * Created by Ar4 on 6/10/2018.
 */
public class AdapterGradeDetail extends RecyclerView.Adapter<AdapterGradeDetail.ViewHolder> {

    //declaring global useful variables
    private static final String TAG = "GradesFragment-Adapter";
    private String id;
    private ArrayList<Double> required;
    private SharedHelper sh;
    private Users u;
    private Activity activity;

    public AdapterGradeDetail(String id, ArrayList<Double> required, Activity a) {
        this.required = required;
        this.id = id;
        this.activity = a;
        sh = new SharedHelper(a);
        u = sh.getUser();

    }

    //request grades data method
    private void requestData(final AdapterGradeDetail.ViewHolder holder, String id, final String period) {
        JsonArrayRequest request = new JsonArrayRequest(
                ServerHelper.URL + ServerHelper.COURSE_EVALUATIONS + id + ServerHelper.GRADES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                        try {
                            if (error.networkResponse.statusCode == 401) {
                                sh.sessionExpired(activity);
                            } else {
                                Toast.makeText(activity.getApplicationContext(), new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), "Error de servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", u.getToken());
                return params;
            }
        };
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
        String req = "Nota requerida: " + String.format("%.2f", required.get(position));
        if (position == 2 && required.get(position) > 10) {
            req = "Lo sentimos, esta materia esta reprobada";
        }
        holder.period.setText(title);
        holder.required.setText(req);
        //setting on click listener to the cardview
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout expandableLayout = v.findViewById(R.id.expandableLayout);

                int isExpanded = expandableLayout.getVisibility();
                if (isExpanded == View.GONE) {
                    expandableLayout.setVisibility(View.VISIBLE);
                    requestData(holder, id, String.valueOf(holder.getAdapterPosition() + 1));
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
        TextView period;
        RecyclerView recyclerView;
        Context context;
        TextView required;

        ViewHolder(CardView itemView) {
            super(itemView);
            //binding UI
            ln = itemView;
            period = itemView.findViewById(R.id.gradedetailPeriod);
            recyclerView = itemView.findViewById(R.id.gradedetailRecyclerViewX);
            context = itemView.getContext();
            required = itemView.findViewById(R.id.gradedetailRequired);
        }
    }
}
