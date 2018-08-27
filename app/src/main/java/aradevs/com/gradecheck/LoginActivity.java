package aradevs.com.gradecheck;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aradevs.com.gradecheck.helpers.ParseJsonHelper;
import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.SharedHelper;
import aradevs.com.gradecheck.models.Users;
import aradevs.com.gradecheck.singleton.AppSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    SharedPreferences sp;
    SharedHelper sh;
    @BindView(R.id.etLoginUsername)
    EditText etLoginUsername;
    @BindView(R.id.etLoginPassword)
    EditText etLoginPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    //method to request user data
    private void requestData(final String username, final String pass) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                ServerHelper.URL + ServerHelper.USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //declaring useful variables
                            JSONObject obj = new JSONObject(response);
                            ParseJsonHelper p = new ParseJsonHelper();

                            //saving object in shared preferences
                            Users tempU = p.parseJsonUsers(obj);
                            sh.saveUser(tempU);

                            //go to next activity
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 401) {
                    Toast.makeText(getApplicationContext(), "Usuario o clave incorrecto", Toast.LENGTH_LONG).show();
                }
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        }) {
            //setting URL encode e.e
            @Override
            public String getBodyContentType() {
                Map<String, String> pars = new HashMap<>();
                pars.put("Content-Type", "application/x-www-form-urlencoded");
                //return pars;
                return "application/x-www-form-urlencoded";
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("pass", pass);
                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjRequest, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("aradevs.com.gradecheck", MODE_PRIVATE);
        sh = new SharedHelper(LoginActivity.this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        //go to Home activity
        /*
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();*/
        requestData(etLoginUsername.getText().toString(), etLoginPassword.getText().toString());
    }
}
