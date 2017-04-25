package com.sr.projectg.Firebase.FEvent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.sr.projectg.R;

import java.util.ArrayList;

/**
 * Created by sr on 4/19/17.
 */



public class FEventHolder {

    ArrayList<FireEvent> fireEvents=new ArrayList<>();


    //firebase

    DatabaseReference db;



    public FEventHolder(DatabaseReference db) {
        this.db = db;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        fireEvents.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            FireEvent fireEvent=ds.getValue(FireEvent.class);
            fireEvents.add(fireEvent);
        }
    }

    //RETRIEVE
    public ArrayList<FireEvent> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return fireEvents;
    }






}
