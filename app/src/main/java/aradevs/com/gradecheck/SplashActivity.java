package aradevs.com.gradecheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import aradevs.com.gradecheck.helpers.SharedHelper;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Validate if there's an existing session
        SharedHelper sh = new SharedHelper(SplashActivity.this);

        //if session exist, redirect to home activity
        if (sh.getUser() == null) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
        finish();
    }
}
