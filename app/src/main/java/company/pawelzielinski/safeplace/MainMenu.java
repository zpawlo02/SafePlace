package company.pawelzielinski.safeplace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import company.pawelzielinski.safeplace.Fragments.F_ADDPlace;
import company.pawelzielinski.safeplace.Fragments.F_MyPlaces;
import company.pawelzielinski.safeplace.Fragments.F_ShowPlaces;
import company.pawelzielinski.safeplace.Fragments.F_topPlaces;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private  TextView emailView, userUsername;
   boolean doubleBackToExitPressedOnce = false;



    private Button buttonAddPlace, buttonSearchSafeNotSafePlace, buttonTopPlaces, buttonMyPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //SETTING ALL COMPONENTS

        //NAVIGATION VIEW
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TEXTVIEWS
        emailView = (TextView) findViewById(R.id.userEmail);
        userUsername = (TextView) findViewById(R.id.userUsername);


        //BUTTONS
        buttonAddPlace = (Button) findViewById(R.id.buttonAddPlace);
        buttonSearchSafeNotSafePlace = (Button) findViewById(R.id.buttonSearchSafeNotSafePlaces);
        buttonTopPlaces = (Button) findViewById(R.id.buttonTopPlaces);
        buttonMyPlaces = (Button) findViewById(R.id.buttonMyPlaces);

        //LISTENERS

        buttonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new F_ADDPlace();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonSearchSafeNotSafePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new F_ShowPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonTopPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new F_topPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonMyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new F_MyPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        //FIREBASE AUTH
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        emailView.setText(firebaseUser.getEmail());
        userUsername.setText(firebaseUser.getDisplayName());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.RELATIVE_LAYOUT_DIRECTION);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(MainMenu.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/
}
