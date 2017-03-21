package com.sr.projectg.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sr.projectg.Database.SQLiteDatabaseHelper;
import com.sr.projectg.R;
import com.sr.projectg.adapter.event.HomeEventSQLiteListAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by sr on 2/22/17.
 */

public class HomeFragment extends Fragment implements KeyListener,View.OnClickListener {

    ImageView hprofile, imageView1;

    SQLiteDatabaseHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    HomeEventSQLiteListAdapter ListAdapter ;


    ArrayList<String> event_type_ArrayList = new ArrayList<String>();
    ArrayList<String> event_des_ArrayList = new ArrayList<String>();
    byte[]  event_snap ;


    ListView LISTVIEW;

    //for fab

    private Boolean isFabOpen = false;
    public FloatingActionButton  fab1, fab2;
    Button homeaddbu;
    public Animation fab_open, fab_close, rotate_forward, rotate_backward;
    View shadowViewh;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.home_event_list_adapter, container, false);

        //((MainActivity) getActivity()).hideFloatingActionButton();


        //fab
       // homeaddbu = (Button) rootView.findViewById(R.id.homeaddbu);
        fab1 = (FloatingActionButton)rootView.findViewById(R.id.fabbump);
        fab2 = (FloatingActionButton)rootView.findViewById(R.id.fabtcam);

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        shadowViewh =(View)rootView.findViewById(R.id.shadowViewh);
     //   homeaddbu.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        // homeaddbu.setAnimation(fab_open);


        LISTVIEW = (ListView) rootView.findViewById(R.id.listev);

        SQLITEHELPER = new SQLiteDatabaseHelper(getContext());


        ShowSQLiteDBdata() ;



        //for profile image
       // hprofile = (ImageView) rootView.findViewById(R.id.hprofile);
       // imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);


        //for nteraction
        final TextView sharen = (TextView) rootView.findViewById(R.id.sharen);
        final TextView likehn = (TextView) rootView.findViewById(R.id.likehn);
/*
        final FloatingActionButton fabhshare1 = (FloatingActionButton) rootView.findViewById(R.id.fabhshare1);
        final FloatingActionButton fabhlike = (FloatingActionButton) rootView.findViewById(R.id.fabhlike);
        final FloatingActionButton fabhdislike = (FloatingActionButton) rootView.findViewById(R.id.fabhdislike);
        final FloatingActionButton fabhcomm = (FloatingActionButton) rootView.findViewById(R.id.fabhcomm);

        fabhcomm.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                startActivity(intent);

            }

        });

        fabhshare1.setOnClickListener(new View.OnClickListener() {
            int sharenn=0;
            @Override
            public void onClick(View v) {
                //update text


                //for share


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                sharenn += 1;
                sharen.setText(String.valueOf(sharenn));



            }

        });

        fabhlike.setOnClickListener(new View.OnClickListener() {
            int liken=0;
            @Override
            public void onClick(View v) {
                //update text

                liken +=1;
                likehn.setText(String.valueOf(liken) );
                //for share






            }

        });

*/


            return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        ShowSQLiteDBdata();
//        loadImageFromStorage();


    }


    //load Image From Storage
    private void loadImageFromStorage() {
        String appname = getString(R.string.app_name);


        try {

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");
            if (f.exists()) {

                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                hprofile.setImageBitmap(b);
            }
            File locaionf = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/location_add.png");
            if (locaionf.exists()) {

                Bitmap b2 = BitmapFactory.decodeStream(new FileInputStream(locaionf));

                imageView1.setImageBitmap(b2);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getInputType() {
        return 0;
    }

    private long lastPressedTime;
    private static final int PERIOD = 2000;


    @Override
    public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Press again to exit.",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;

    }

    @Override
    public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyOther(View view, Editable text, KeyEvent event) {
        return false;
    }

    @Override
    public void clearMetaKeyState(View view, Editable content, int states) {

    }

    private void ShowSQLiteDBdata() {

        new CountDownTimer(1000, 800) {

            public void onTick(long millisUntilFinished) {
             }

            public void onFinish() {


                SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

                cursor = SQLITEDATABASE.rawQuery("SELECT * FROM event ORDER BY event_date_time  DESC ", null);


                event_type_ArrayList.clear();
                event_des_ArrayList.clear();


                if (cursor.moveToFirst()) {
                    do {

                        Log.i("SR", "DATALISTevent");


                        event_type_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteDatabaseHelper.event_type)));

                        event_des_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteDatabaseHelper.event_det)));

                    } while (cursor.moveToNext());
                }

                ListAdapter = new HomeEventSQLiteListAdapter(getContext(),

                        event_type_ArrayList,
                        event_des_ArrayList

                );

                LISTVIEW.setAdapter(ListAdapter);

                cursor.close();

            }
        }.start();




    }

    //setup fab animation

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            /*
            case R.id.homeaddbu:
                animateFAB();
                break;
*/
        }
    }


//This for move fab

    public void animateFAB(){


        if(isFabOpen){

         //   homeaddbu.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

            //for undim screen

            shadowViewh.setVisibility(View.GONE);


        } else {

         //   homeaddbu.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
             isFabOpen = true;
            Log.d("Raj","open");

            //for dim screen

              shadowViewh.setVisibility(View.VISIBLE);


        }
    }

    public void showFloatingActionButton() {
        /*if (homeaddbu.getAnimation() == fab_close) {
            homeaddbu.setAnimation(fab_open);


        }
        */
    }


    public void hideFloatingActionButton() {
     //   homeaddbu.setAnimation(fab_close);
    }


}



