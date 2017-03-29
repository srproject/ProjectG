package com.sr.projectg.adapter.event;

/**
 * Created by sr on 3/12/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sr.projectg.Database.SQLiteDatabaseHelper;
import com.sr.projectg.R;

import java.util.ArrayList;

public class HomeEventSQLiteListAdapter extends BaseAdapter {

      ArrayList<String> arevent_type;
     ArrayList<String> arevent_des ;
    byte[]  arevent_snap ;
    ArrayList<Bitmap> dsd = new ArrayList<Bitmap>();


    private Context context;



    SQLiteDatabaseHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    byte[]  event_snap;



    public HomeEventSQLiteListAdapter(
            Context context2,
             ArrayList<String> arevent_type,
             ArrayList<String> subject

    )
    {

        this.context = context2;
         this.arevent_type = arevent_type;
         this.arevent_des = subject ;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arevent_type.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.home_layout, null);

            holder = new Holder();

             holder.event_type = (TextView) child.findViewById(R.id.event_type);
            holder.event_des = (TextView) child.findViewById(R.id.des1);
            holder.ev_snap = (ImageView) child.findViewById(R.id.imageViewsnap);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
         holder.event_type.setText(arevent_type.get(position));
        holder.event_des.setText(arevent_des.get(position));
      getChamp_splash();
       holder.ev_snap.setImageBitmap(dsd.get(position));









        return child;
    }

    public class Holder {
         TextView event_type;
        TextView event_des;
        ImageView ev_snap;

     }



    public static Bitmap getImage(byte[] image, int i, int length) {
        return BitmapFactory.decodeByteArray(image, i, image.length);
    }


    public void getChamp_splash() {

        Bitmap bitmap = null;
         SQLITEHELPER = new SQLiteDatabaseHelper(context);
        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        SQLITEDATABASE.beginTransaction();
        String sqlTables = "event";

        String selectQuery = "SELECT * FROM "+ sqlTables;




        Cursor cursor = SQLITEDATABASE.rawQuery(selectQuery, null);


        try {
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM event", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        arevent_snap = cursor.getBlob(cursor.getColumnIndex("event_map_snap"));
                        bitmap = getImage(arevent_snap,0,arevent_snap.length);


                        dsd.add(bitmap);





                        Log.i("SR","snap");

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }
        SQLITEDATABASE.setTransactionSuccessful();
        SQLITEDATABASE.endTransaction();

    }


}