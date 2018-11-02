package com.android.mobilemarcom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.employee.EmployeeActivity;
import com.android.mobilemarcom.event.EventFragment;
import com.android.mobilemarcom.souvenir.SouvenirFragment;
import com.android.mobilemarcom.souvenirRequest.SouvenirRequestActivity;
import com.android.mobilemarcom.t_souvenir.SouvenirStokFragment;
import com.android.mobilemarcom.unit.UnitFragment;
import com.android.mobilemarcom.user.UserActivity;
import com.android.mobilemarcom.utility.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Context context = this;
    private TextView textView;
    private TextClock textClock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String a = SessionManager.getUsername(context);
        textView = (TextView) findViewById(R.id.tampilUsername);
        textClock = (TextClock) findViewById(R.id.textClock);
        Animation fadeIn  = AnimationUtils.loadAnimation(this, R.anim.fadein);
        textView.setText("Welcome "+a);
        textView.startAnimation(fadeIn);
        toolbar.setTitle("MARCOOM");


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String token = SessionManager.getToken(context);
        int id = SessionManager.getId(context);
//        Toast.makeText(this,""+id,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
            return true;
        }else if(id == R.id.action_logout){
                SessionManager.logoutUser(context);
                Toast.makeText(context,"Berhasil logout",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(context,LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawerEmployee) {
//            setActionBarTitle("menu kiri 1");
            EmployeeActivity employeeActivity = new EmployeeActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, employeeActivity,"menu kiri 1");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();

        } else if (id == R.id.drawerUser) {
            UserActivity userActivity = new UserActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, userActivity,"menu kiri 2");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();

        } else if (id == R.id.drawerSouvenir) {
            SouvenirFragment souvenirActivity= new SouvenirFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, souvenirActivity,"menu kiri 3");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();
        } else if (id == R.id.drawerEventRequest) {

            EventFragment eventFragment = new EventFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, eventFragment,"menu kiri 1");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();

        } else if (id == R.id.drawerSouvenirStock) {
            SouvenirStokFragment souvenirStokFragment = new SouvenirStokFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, souvenirStokFragment,"menu kiri 4");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();
        } else if (id == R.id.drawerSouvenirRequest) {
            Intent intent = new Intent(this,SouvenirRequestActivity.class);
            startActivity(intent);
//            SouvenirRequestActivity souvenirRequestActivity = new SouvenirRequestActivity();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frame_all_menu, souvenirRequestActivity,"menu kiri 6");
//            fragmentTransaction.commit();
        }else if (id == R.id.unit) {
            UnitFragment unitFragment = new UnitFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_all_menu, unitFragment,"menu kiri 4");
            textView.setVisibility(View.GONE);
            textClock.setVisibility(View.GONE);
            fragmentTransaction.commit();

        }
//        else if (id == R.id.list){
//            Intent in = new Intent(this, ListTest.class);
//            startActivity(in);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

}


