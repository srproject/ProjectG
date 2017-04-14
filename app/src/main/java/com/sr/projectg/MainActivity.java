package com.sr.projectg;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sr.projectg.Firebase.FireLogin;
import com.sr.projectg.Firebase.FireRegistration;
import com.sr.projectg.Fragment.CameraFragment;
import com.sr.projectg.Fragment.HomeFragment;
import com.sr.projectg.Fragment.MapHomeFragment;
import com.sr.projectg.Fragment.NewProFragment;
import com.sr.projectg.Fragment.NotiFragment;
import com.sr.projectg.activity.AddEventActivity;
import com.sr.projectg.activity.LoginActivity;
import com.sr.projectg.Firebase.FireLogin.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements   NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 11;
    MapHomeFragment map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().show();
/*
//For KeyHash for facebook login setup

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.sr.projectg",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
 */


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {


            // only for JELLY_BEAN and newer versions

            permi2();
        }




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

     //   tabLayout.getTabAt(0).setIcon(R.drawable.camera);
        tabLayout.getTabAt(0).setIcon(R.drawable.map);
        tabLayout.getTabAt(1).setIcon(R.drawable.home);
        tabLayout.getTabAt(2).setIcon(R.drawable.notifi);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();

                if (position==0){

                    getSupportActionBar().setTitle("Map");


                }
                if (position==1){

                    getSupportActionBar().setTitle("TimeLine");


                }
                if (position==2){

                    getSupportActionBar().setTitle("Notifics");


                }
                if (position==3){

                    getSupportActionBar().setTitle("Profile");


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();


                if (position==0){

                    getSupportActionBar().setTitle("Map");


                }
                if (position==1){

                    getSupportActionBar().setTitle("TimeLine");


                }
                if (position==2){

                    getSupportActionBar().setTitle("Notifics");


                }
                if (position==3){

                    getSupportActionBar().setTitle("Profile");


                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                int position = tab.getPosition();




                if (position==0){

                    getSupportActionBar().setTitle("Map");


                }
                if (position==1){

                    getSupportActionBar().setTitle("TimeLine");


                }
                if (position==2){

                    getSupportActionBar().setTitle("Notifics");


                }
                if (position==3){

                    getSupportActionBar().setTitle("Profile");


                }

            }
        });
      //  mViewPager.setCurrentItem(1);



        // Set up the NavigationView with button and header.


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_view_header);
        navigationView.setNavigationItemSelectedListener(this);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        //navigationView.getBackground().setAlpha(190);
      //  drawer.setScrimColor(Color.parseColor("#4B000000"));




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_item_Timeline) {


            // Handle the camera action
        } else if (id == R.id.nav_item_Notification) {


        } else if (id == R.id.nav_item_Profile) {

            Intent intent = new Intent(MainActivity.this, FireRegistration.class);
            startActivity(intent);

        } else if (id == R.id.nav_item_Contacts) {


        } else if (id == R.id.nav_item_abu_event) {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            intent.putExtra("SRtitle", "Bump");
            intent.putExtra("SRCode", "1");
            map.zoom();

            startActivity(intent);
        }

        else if (id == R.id.nav_item_atc_event) {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            intent.putExtra("SRtitle", "Trafic Camera");
            intent.putExtra("SRCode", "2");
            map.zoom();


            startActivity(intent);
        }

        else if (id == R.id.nav_item_aoth_event) {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            intent.putExtra("SRtitle", "Other");
            intent.putExtra("SRCode", "3");
            map.zoom();


            startActivity(intent);
        }
        else if (id == R.id.nav_item_signin) {
            Intent intent = new Intent(MainActivity.this, FireLogin.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_item_signout) {
           // FireLogin fireLogin=new FireLogin();
            //fireLogin.signOut();
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



     }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                      return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:

                    MapHomeFragment tab1=new MapHomeFragment();

                    return  tab1;
                case 1:

                    HomeFragment tab2=new HomeFragment();
                    return  tab2;
                case 2:

                    NotiFragment tab3=new NotiFragment();
                    return  tab3;
                case 3:

                    NewProFragment tab4=new NewProFragment();
                    return  tab4;


                default:

                    return  null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return null;


                case 1:


                    return null;
                case 2:

                    return null;
                case 3:

                    return null;
                case 4:

                    return null;

            }
            return null;
        }
    }

    //method for peremission
    public void permi2() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.LOCATION_HARDWARE,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_MULTIPLE_REQUEST);
        }

    }


}
