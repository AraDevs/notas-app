package aradevs.com.gradecheck;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import aradevs.com.gradecheck.helpers.SharedHelper;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class HomeActivity extends NavigationLiveo implements OnItemClickListener {

    //Shared preferences helper
    SharedHelper sh;

    @SuppressLint("SetTextI18n")
    @Override
    public void onInt(Bundle savedInstanceState) {
        sh = new SharedHelper(HomeActivity.this);

        // User Information
        this.userName.setText(sh.getUser().getName());
        this.userEmail.setText(sh.getUser().getEmail());
        this.userPhoto.setImageResource(R.drawable.g);
        this.userBackground.setImageResource(R.drawable.background);

        // Creating items navigation
        HelpLiveo mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add("Notas", R.mipmap.ic_grade);
        mHelpLiveo.add("Materias Inscritas", R.mipmap.ic_subjects);
        mHelpLiveo.add("Docentes", R.mipmap.ic_teachers);
        mHelpLiveo.add("Apelacion de notas", R.mipmap.ic_mistake);
        mHelpLiveo.add("Ultima actividad", R.mipmap.ic_recent);


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

        //declaring position listeners for fragments
        switch (position){
            case 0:
                GradesFragment inicio = new GradesFragment();
                trans.replace(R.id.container, inicio, "Inicio");
                break;
        }

        //switching fragment
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
            //clearing shared preferences
            sh.clear();
            //redirect to login
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    };
}
