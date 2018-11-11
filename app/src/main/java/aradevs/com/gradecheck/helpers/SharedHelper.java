package aradevs.com.gradecheck.helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import aradevs.com.gradecheck.LoginActivity;
import aradevs.com.gradecheck.models.Users;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ar4 on 26/08/2018.
 */
public class SharedHelper {
    //declaring useful variables
    private SharedPreferences sp;
    private Gson gson;

    //initializing variables
    public SharedHelper(Activity activity) {
        this.sp = activity.getSharedPreferences("aradevs.com.gradecheck", MODE_PRIVATE);
        this.gson = new Gson();
    }

    //get user obj from shared preferences
    public Users getUser() {
        String json = sp.getString("usuario", "");
        Log.e("json", json);
        return gson.fromJson(json, Users.class);
    }

    //clear shared preferences
    public void clear() {
        //login out and returning to LoginLayout
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    //save user object
    public void saveUser(Users users) {
        //storing user in shared preferences
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("usuario", gson.toJson(users));
        edit.apply();
    }

    public void sessionExpired(Activity a) {
        //clearing shared preferences
        clear();
        //redirect to login
        Toast.makeText(a.getApplicationContext(), "Sesion caducada", Toast.LENGTH_SHORT).show();
        a.startActivity(new Intent(a, LoginActivity.class));
        a.finish();
    }
}
