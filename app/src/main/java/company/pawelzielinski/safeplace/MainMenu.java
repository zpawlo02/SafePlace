package company.pawelzielinski.safeplace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import company.pawelzielinski.safeplace.Fragments.ADDPlace;
import company.pawelzielinski.safeplace.Fragments.EditUsername;
import company.pawelzielinski.safeplace.Fragments.Info_how_works;
import company.pawelzielinski.safeplace.Fragments.MyPlaces;
import company.pawelzielinski.safeplace.Fragments.ShowPlaces;
import company.pawelzielinski.safeplace.Fragments.topPlaces;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private  TextView emailView, userUsername;
   private FirebaseAuth auth;
   private FirebaseUser firebaseUser;
   private  static long back_pressed;
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
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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


        //FIREBASE AUTH
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        emailView.setText(firebaseUser.getEmail());
        if(firebaseUser.getDisplayName() != null){
            userUsername.setText(firebaseUser.getDisplayName());
        }


        //LISTENERS

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUsername.setText(firebaseUser.getDisplayName());
            }
        });

        /*userUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                int counter = 0;
                String usernameS = firebaseUser.getDisplayName();

                for(int i = 0; i < usernameS.length(); i++){
                    if(usernameS.charAt(i) == ' '){
                        counter++;
                    }
                }

                if(counter != 0 ){
                    FragmentManager fm = getSupportFragmentManager();
                    Fragment add = new EditUsername();
                    fm.beginTransaction().add(R.id.drawer_layout,add).addToBackStack(null).commit();
                }

            }
        });*/

        buttonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new ADDPlace();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonSearchSafeNotSafePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new ShowPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonTopPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new topPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });

        buttonMyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment add = new MyPlaces();
                fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
            }
        });



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
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(back_pressed + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
            }else {
                Toast.makeText(getApplicationContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();

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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_donate) {

            Uri uri = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GR7NAZY78NYDW&source=url");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }else if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(MainMenu.this, LoginActivity.class));
        }else if (id == R.id.nav_info){
            FragmentManager fm = getSupportFragmentManager();
            Fragment add = new Info_how_works();
            fm.beginTransaction().replace(R.id.drawer_layout, add).addToBackStack(null).commit();
        }else if(id == R.id.nav_report){
            Intent intent=new Intent(Intent.ACTION_SEND);
            String[] recipients={"saffeplace@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Report");
            intent.putExtra(Intent.EXTRA_TEXT,"");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/
}
