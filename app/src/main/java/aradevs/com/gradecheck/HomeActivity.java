package aradevs.com.gradecheck;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class HomeActivity extends NavigationLiveo implements OnItemClickListener {

    @Override
    public void onInt(Bundle savedInstanceState) {
// User Information
        this.userName.setText("Ar4 Mendez");
        this.userEmail.setText("ooskarargueta08@gmail.com");
        this.userPhoto.setImageResource(R.drawable.g);
        this.userBackground.setImageResource(R.drawable.background);

        // Creating items navigation
        HelpLiveo mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add("Notas", R.mipmap.ic_grade);

        //with(this, Navigation.THEME_DARK). add theme dark
        //with(this, Navigation.THEME_LIGHT). add theme light

        with(this) // default theme is dark
                .startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .footerItem("Cerrar Sesion", R.mipmap.ic_logout)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();



    }

    @Override
    public void onItemClick(int position) {
        FragmentTransaction trans = HomeActivity.this.getFragmentManager().beginTransaction();

        switch (position){
            case 0:
                Grades inicio = new Grades();
                trans.replace(R.id.container, inicio, "Inicio");
                break;
        }

        trans.commit();
    }
    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };
    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };
    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    };
}
