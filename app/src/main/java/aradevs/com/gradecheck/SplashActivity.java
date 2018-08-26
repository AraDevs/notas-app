package aradevs.com.gradecheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
