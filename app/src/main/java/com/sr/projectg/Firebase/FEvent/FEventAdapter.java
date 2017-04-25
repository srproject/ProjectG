package com.sr.projectg.Firebase.FEvent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sr.projectg.R;
import com.sr.projectg.activity.AddEventActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sr on 4/19/17.
 */

public class FEventAdapter extends BaseAdapter {

    Context c;
    ArrayList<FireEvent> events;
    LayoutInflater inflater;


    ImageView hprofile,imagetype,imageevent;
    TextView user_name,event_type,des1;

    String username;


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
        event_type=(TextView)convertView.findViewById(R.id.event_type);
        des1=(TextView)convertView.findViewById(R.id.des1);

        final FireEvent e= (FireEvent) this.getItem(position);



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
                event_type.setText(e.getEvent_type());
                des1.setText(e.getEvent_det());






        return convertView;
    }

    private Bitmap bbd(String type){

        Bitmap bb = null;
        int tt=Integer.parseInt(type);

        if(tt==1){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.newbump);

        }
        if(tt==2){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.camt);

        }
        if(tt==3){

            bb= BitmapFactory.decodeResource(c.getResources(),R.mipmap.other);

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


}
