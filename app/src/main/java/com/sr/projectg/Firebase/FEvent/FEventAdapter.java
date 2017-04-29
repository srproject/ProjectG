package com.sr.projectg.Firebase.FEvent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sr.projectg.Firebase.FireLogin;
import com.sr.projectg.MainActivity;
import com.sr.projectg.R;
import com.sr.projectg.activity.AddEventActivity;
import com.sr.projectg.activity.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sr on 4/19/17.
 */

public class FEventAdapter extends BaseAdapter   {



    Context c;
    ArrayList<FireEvent> events;
    LayoutInflater inflater;


    ImageView hprofile,imagetype,imageevent;
    TextView user_name,event_type,des1,event_solve;

    String username,s,evid;

    FEventHolder helper;



    public FEventAdapter(Context c, ArrayList<FireEvent> events) {
        this.c = c;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){

            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView==null){

            convertView=inflater.inflate(R.layout.home_layout,parent,false);

        }
        //FEventHolder holder=new FEventHolder(convertView);

        hprofile=(ImageView)convertView.findViewById(R.id.hprofile);
        imagetype=(ImageView)convertView.findViewById(R.id.imagetype);
        imageevent=(ImageView)convertView.findViewById(R.id.imageevent);
        user_name =(TextView)convertView.findViewById(R.id.user_name);
        event_solve =(TextView)convertView.findViewById(R.id.event_solve);
        event_type=(TextView)convertView.findViewById(R.id.event_type);
        des1=(TextView)convertView.findViewById(R.id.des1);
        final FireEvent e= (FireEvent) this.getItem(position);
        s=e.getEvent_id();
        evid=e.getEvent_id();



 /*

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                         imageevent.setImageBitmap(doInBackground(e.getEvent_photo_url()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
*/

        Firebase.setAndroidContext(c);
        final Firebase ref = new Firebase("https://projectg-70ce9.firebaseio.com/");
        Query query = ref.child("ACCOUNT").orderByChild("account_id").equalTo(e.getEvent_account_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    username=postSnapshot.child("account_name").getValue().toString();
                    Log.i("SRFire0",username);
                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        //hprofile=null;
                imagetype.setImageBitmap(bbd(e.getEvent_type_code()));

        user_name.setText(username);


                event_type.setText(e.getEvent_type()+" ـ "+e.getEvent_solve());
                des1.setText(e.getEvent_det());


        imagetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireLogin ff=new FireLogin();
                 int gg=Integer.parseInt(ff.perr) ;
                if( gg==1){
                    showPopup(v);
                }



            }
        });




        return convertView;
    }

    private Bitmap bbd(String type){

        Bitmap bb = null;
        if(type!=null) {
            int tt = Integer.parseInt(type);

        if(tt==1){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.newbump);

        }
        if(tt==2){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.camt);

        }
        if(tt==3){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.other);

        }
        else {

        }
        }


        return bb;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    public void showPopup(View v ) {
        PopupMenu popup = new PopupMenu(c, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_ev_home, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_hom_mps:
                        //Or Some other code you want to put here.. This is just an example.
                        Firebase.setAndroidContext(c);
                        final Firebase ref = new Firebase("https://projectg-70ce9.firebaseio.com/");
                        Query query = ref.child("EVENT").orderByChild("event_id").equalTo(evid);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    snapshot.getRef().child("event_solve").setValue("SOLVED");
                                }


                                Toast.makeText(c, "SOLVED", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Toast.makeText(c, "ERROR", Toast.LENGTH_SHORT).show();

                            }
                        });

                        break;

                    case R.id.pop_hom_mpns:
                        //Or Some other code you want to put here.. This is just an example.
                        Firebase.setAndroidContext(c);
                        final Firebase ref2 = new Firebase("https://projectg-70ce9.firebaseio.com/");
                        Query query2= ref2.child("EVENT").orderByChild("event_id").equalTo(evid);
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    snapshot.getRef().child("event_solve").setValue("");
                                }




                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        break;


                    default:
                        break;
                }

                return true;
            }
        });


    }



}
