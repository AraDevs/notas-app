package aradevs.com.gradecheck.helpers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
/**
 * Created by Ar4 on 25/08/2018.
 */
public class FragmentHelper {

    private FragmentManager fm;
    private FragmentTransaction ft;
    public FragmentHelper(Activity activity){
        fm = activity.getFragmentManager();
    }

    public void  setFragment(int id, Fragment fragment){
        ft = fm.beginTransaction();
        ft.add(id, fragment);
        ft.commit();
    }

    public void  replaceFragment(int id, Fragment fragment){
        ft = fm.beginTransaction();
        ft.add(id, fragment);
        ft.commit();
    }
}
