package dlswp113.com.kwebview.parentclass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.naver.jsqim.unipair.LoginActivity;
import com.naver.jsqim.unipair.R;
import com.naver.jsqim.unipair.UserRepairListActivity;
import com.naver.jsqim.unipair.VendorRepairListActivity;
import com.naver.jsqim.unipair.helper.SQLiteHandler;
import com.naver.jsqim.unipair.helper.SessionManager;

public class DrawerLoggedInActivity extends AppBarLoggedInActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SQLiteHandler db;
    private SessionManager session;
    public String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        /*
        setContentView(R.layout.activity_with_drawer);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new RegisterFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Log.i("DrawerLoggedInActivity", "1");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i("DrawerLoggedInActivity", "2");
        setSupportActionBar(toolbar);
        Log.i("DrawerLoggedInActivity", "3");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */

        uid = super.uid;
    }



    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_repairs) {
            if (getIsVendor() == 0) {
                Intent i = new Intent(this, UserRepairListActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, VendorRepairListActivity.class);
                startActivity(i);
            }
        } else if (id == R.id.logout) {
            logoutUser();
        } else if (id == R.id.dev_contact) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","wjhc@naver.com", null));
            startActivity(emailIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
