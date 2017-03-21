package com.sr.projectg.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sr on 3/15/17.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME="TheXData.db";

    //EVENT Table

    public static  final String event_TABLE="event";
    public static  final String event_id="event_id";
    public static  final String event_account_id="event_account_id";
    public static  final String event_type="event_type";
     public static  final String event_date_time="event_date_time";
    public static  final String event_latitude="event_latitude";
    public static  final String event_longitude="event_longitude";
    public static  final String event_locnam="event_locnam";
    public static  final String event_det="event_det";
    public static  final String event_photo="event_photo";
    public static  final String event_map_snap="event_map_snap";


    //event_area Table

    public static final String event_area_TABLE="event_area";
    public static  final String event_area_id="event_area_id";
    public static  final String event_area_account_id="event_area_account_id";
    public static  final String event_area_type="event_area_type";
    public static  final String event_area_time="event_area_time";
    public static  final String event_area_date="event_area_date";
    public static  final String event_area_latitude="event_area_latitude";
    public static  final String event_area_longitude="event_area_longitude";
    public static  final String event_area_locnam="event_area_locnam";
    public static  final String event_area_area_det="event_area_det";
    public static  final String event_area_area_photo="event_area_photo";
    public static  final String event_area_map_snap="event_area_map_snap";


    // event_comment TABLE

    public static  final  String event_comment_TABLE="event_comment";
    public static  final String comment_id="comment_id";
    public static  final String event_comment_id="event_comment_id";
    public static  final String event_account_comment_id="event_account_comment_id";
    public static  final String comment_text="comment_text";
    public static  final String event_comment_date="event_comment_date";
    public static  final String event_comment_time="event_comment_time";



    // event_like TABLE

    public static final String event_like_TABLE="event_like";
    public static  final String like_id="like_id";
    public static  final String event_like_id="event_like_id";
    public static  final String event_account_like_id="event_account_like_id";
    public static  final String event_like_date="event_like_date";
    public static  final String event_like_time="event_like_time";



    // event_share TABLE

    public static final String event_share_TABLE="event_share";
    public static  final String share_id="share_id";
    public static  final String event_share_id="event_share_id";
    public static  final String event_account_share_id="event_account_share_id";
    public static  final String event_share_date="event_share_date";
    public static  final String event_share_time="event_share_time";



    // event_ex_photo TABLE

    public static final String event_ex_photo_TABLE="event_ex_photo";
    public static  final String photo_id="photo_id";
    public static  final String event_photo_id="event_photo_id";
    public static  final String event_account_photo_id="event_account_photo_id";
    public static  final String event_photo_date="event_photo_date";
    public static  final String event_photo_time="event_photo_time";
    public static  final String event_photo_data="event_photo_data";




    //ACCOUNT Table

    public static final String account_TABLE="account";
    public static  final String account_id="account_id";
    public static  final String account_type="account_type";
    public static  final String account_email="account_email";
    public static  final String account_phone="account_phone";
    public static  final String account_password="account_password";
    // ACCOUNT permission
    public static  final String permission_inter="permission_inter";
    public static  final String permission_event="permission_event";
    public static  final String permission_comm="permission_comm";
    public static  final String permission_share="permission_share";
    public static  final String permission_like="permission_like";
    public static  final String permission_camera="permission_camera";
    public static  final String permission_profile="permission_profile";
    public static  final String permission_editprofile="permission_editprofile";
    public static  final String permission_map="permission_map";
    public static  final String permission_noti="permission_noti";
    public static  final String permission_showevent="permission_showevent";
    public static  final String permission_make_event_solve="permission_make_event_solve";
    public static  final String permission_block="permission_block";
    public static  final String permission_report="permission_report";
    public static  final String permission_blocku_admin="permission_blocku_admin";
    public static  final String permission_deleteu_admin="permission_deleteu_admin";
    public static  final String permission_photo_hide="permission_photo_hide";
    public static  final String permission_photo_hide_admin="permission_photo_hide_admin";
    public static  final String permission_photo_delete="permission_photo_delete";




    //PROFILE FOR ACCOUNT Table

    public static final String profile_TABLE="profile";
    public static  final String profile_id="profile_id";
    public static  final String profile_account_id="profile_account_id";
    public static  final String profile_name="profile_name";
    public static  final String profile_sex="profile_sex";
    public static  final String profile_age="profile_age";
    public static  final String profile_loc="profile_loc";
    public static  final String profile_edu="profile_edu";
    public static  final String profile_rel="profile_rel";




    //follow FOR ACCOUNT Table

    public static final String follow_TABLE="follow";
    public static  final String follow_id="follow_id";
    public static  final String follow_account_id="follow_account_id";
    public static  final String follower_account_id="follower_account_id";
    public static  final String following_account_id="following_account_id";



    public SQLiteDatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //EVENT Table


         db.execSQL("create table event (event_id VARCHAR PRIMARY KEY, event_account_id VARCHAR " +
                 ",event_type VARCHAR ,event_date_time VARCHAR" +
                 ",event_latitude VARCHAR ,event_longitude VARCHAR" +
                 ",event_locnam VARCHAR ,event_det VARCHAR" +
                 ",event_photo BLOB ,event_map_snap BLOB  )" +
                 " ;  " );


        //event_area Table

        db.execSQL("create table event_area (event_area_id VARCHAR PRIMARY KEY, event_area_account_id VARCHAR " +
                ",event_area_type VARCHAR ,event_area_date VARCHAR" +
                ",event_area_latitude VARCHAR ,event_area_longitude VARCHAR" +
                ",event_area_locnam VARCHAR ,event_area_det VARCHAR" +
                ",event_area_photo BLOB ,event_area_map_snap BLOB   )" +
                " ;  " );


        // event_comment TABLE

        db.execSQL("create table event_comment (comment_id VARCHAR PRIMARY KEY, event_comment_id VARCHAR " +
                ",event_account_comment_id VARCHAR ,comment_text VARCHAR" +
                ",event_comment_date VARCHAR ,event_comment_time VARCHAR)" +
                 " ;  " );


        // event_like TABLE

        db.execSQL("create table event_like (like_id VARCHAR PRIMARY KEY, event_like_id VARCHAR " +
                ",event_account_like_id VARCHAR ,event_like_date VARCHAR" +
                ",event_like_time VARCHAR )" +
                " ;  " );


        // event_share TABLE


        db.execSQL("create table event_share (share_id VARCHAR PRIMARY KEY, event_share_id VARCHAR " +
                ",event_account_share_id VARCHAR ,event_share_date VARCHAR" +
                ",event_share_time VARCHAR )" +
                " ;  " );


        // event_ex_photo TABLE


        db.execSQL("create table event_ex_photo (photo_id VARCHAR PRIMARY KEY, event_photo_id VARCHAR " +
                ",event_account_photo_id VARCHAR ,event_photo_date VARCHAR" +
                ",event_photo_time VARCHAR ,event_photo_data BOLE)" +
                " ;  " );


        //ACCOUNT Table



        db.execSQL("create table account (photo_id VARCHAR PRIMARY KEY, account_id VARCHAR " +
                ",account_type VARCHAR ,account_email VARCHAR" +
                ",account_phone VARCHAR ,account_password VARCHAR" +
                ",permission_inter VARCHAR ,permission_event VARCHAR" +
                ",permission_comm VARCHAR ,permission_share VARCHAR" +
                ",permission_like VARCHAR ,permission_camera VARCHAR" +
                ",permission_profile VARCHAR ,permission_editprofile VARCHAR" +
                ",permission_map VARCHAR ,permission_noti VARCHAR" +
                ",permission_showevent VARCHAR ,permission_make_event_solve VARCHAR" +
                ",permission_block VARCHAR ,permission_report VARCHAR" +
                ",permission_blocku_admin VARCHAR ,permission_deleteu_admin VARCHAR" +
                ",permission_photo_hide VARCHAR ,permission_photo_hide_admin VARCHAR" +
                ",permission_photo_delete VARCHAR)" +
                " ;  " );




        //PROFILE FOR ACCOUNT Table


        db.execSQL("create table profile (profile_id VARCHAR PRIMARY KEY, profile_account_id VARCHAR " +
                ",profile_name VARCHAR ,profile_sex VARCHAR" +
                ",profile_age VARCHAR ,profile_loc VARCHAR" +
                ",profile_edu VARCHAR ,profile_rel VARCHAR)" +
                " ;  " );




        //follow FOR ACCOUNT Table


        db.execSQL("create table follow (follow_id VARCHAR PRIMARY KEY, follow_account_id VARCHAR " +
                ",profile_name VARCHAR ,profile_sex VARCHAR" +
                ",follower_account_id VARCHAR ,following_account_id VARCHAR)" +
                 " ;  " );




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        //EVENT Table

        db.execSQL("create table IF NOT EXISTS event (event_id VARCHAR PRIMARY KEY, event_account_id VARCHAR " +
                ",event_type VARCHAR ,event_date_time VARCHAR" +
                ",event_latitude VARCHAR ,event_longitude VARCHAR" +
                ",event_locnam VARCHAR ,event_det VARCHAR" +
                ",event_photo BLOB ,event_map_snap BLOB  )" +
                " ;  " );

        //event_area Table

        db.execSQL("create table IF NOT EXISTS event_area (event_area_id VARCHAR PRIMARY KEY, event_area_account_id VARCHAR " +
                ",event_area_type VARCHAR ,event_area_date VARCHAR" +
                ",event_area_latitude VARCHAR ,event_area_longitude VARCHAR" +
                ",event_area_locnam VARCHAR ,event_area_det VARCHAR" +
                ",event_area_photo BLOB ,event_area_map_snap BLOB   )" +
                " ;  " );


        // event_comment TABLE

        db.execSQL("create table IF NOT EXISTS event_comment (comment_id VARCHAR PRIMARY KEY, event_comment_id VARCHAR " +
                ",event_account_comment_id VARCHAR ,comment_text VARCHAR" +
                ",event_comment_date VARCHAR ,event_comment_time VARCHAR)" +
                " ;  " );


        // event_like TABLE

        db.execSQL("create table IF NOT EXISTS event_like (like_id VARCHAR PRIMARY KEY, event_like_id VARCHAR " +
                ",event_account_like_id VARCHAR ,event_like_date VARCHAR" +
                ",event_like_time VARCHAR )" +
                " ;  " );


        // event_share TABLE


        db.execSQL("create table IF NOT EXISTS event_share (share_id VARCHAR PRIMARY KEY, event_share_id VARCHAR " +
                ",event_account_share_id VARCHAR ,event_share_date VARCHAR" +
                ",event_share_time VARCHAR )" +
                " ;  " );


        // event_ex_photo TABLE


        db.execSQL("create table IF NOT EXISTS event_ex_photo (photo_id VARCHAR PRIMARY KEY, event_photo_id VARCHAR " +
                ",event_account_photo_id VARCHAR ,event_photo_date VARCHAR" +
                ",event_photo_time VARCHAR ,event_photo_data BOLE)" +
                " ;  " );


        //ACCOUNT Table



        db.execSQL("create table IF NOT EXISTS account (photo_id VARCHAR PRIMARY KEY, account_id VARCHAR " +
                ",account_type VARCHAR ,account_email VARCHAR" +
                ",account_phone VARCHAR ,account_password VARCHAR" +
                ",permission_inter VARCHAR ,permission_event VARCHAR" +
                ",permission_comm VARCHAR ,permission_share VARCHAR" +
                ",permission_like VARCHAR ,permission_camera VARCHAR" +
                ",permission_profile VARCHAR ,permission_editprofile VARCHAR" +
                ",permission_map VARCHAR ,permission_noti VARCHAR" +
                ",permission_showevent VARCHAR ,permission_make_event_solve VARCHAR" +
                ",permission_block VARCHAR ,permission_report VARCHAR" +
                ",permission_blocku_admin VARCHAR ,permission_deleteu_admin VARCHAR" +
                ",permission_photo_hide VARCHAR ,permission_photo_hide_admin VARCHAR" +
                ",permission_photo_delete VARCHAR)" +
                " ;  " );




        //PROFILE FOR ACCOUNT Table


        db.execSQL("create table IF NOT EXISTS profile (profile_id VARCHAR PRIMARY KEY, profile_account_id VARCHAR " +
                ",profile_name VARCHAR ,profile_sex VARCHAR" +
                ",profile_age VARCHAR ,profile_loc VARCHAR" +
                ",profile_edu VARCHAR ,profile_rel VARCHAR)" +
                " ;  " );




        //follow FOR ACCOUNT Table


        db.execSQL("create table IF NOT EXISTS follow (follow_id VARCHAR PRIMARY KEY, follow_account_id VARCHAR " +
                ",follower_account_id VARCHAR ,following_account_id VARCHAR)" +
                " ;  " );


    }



// insert data into table event

    public boolean insertDataforevent (String ievent_id,String ievent_account_id,String ievent_type,String ievent_time,String ievent_date
            ,String ievent_latitude,String ievent_longitude,String ievent_locnam,String ievent_det,byte[] ievent_photo ,byte[] ievent_map_snap) {




        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(event_id,ievent_id);
        contentValues.put(event_account_id,ievent_account_id);
        contentValues.put(event_type, ievent_type);
         contentValues.put(event_date_time, ievent_date+"-"+ievent_time);
        contentValues.put(event_latitude, ievent_latitude);
        contentValues.put(event_longitude, ievent_longitude);
        contentValues.put(event_locnam, ievent_locnam);
        contentValues.put(event_det, ievent_det);
        contentValues.put(event_photo, ievent_photo);
        contentValues.put(event_map_snap,ievent_map_snap);




        //db.execSQL("DELETE FROM event " );



        long result = db.insert(event_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
//insert data into table area event
    public boolean insertDataforEventArea(String ievent_area_id,String ievent_area_account_id,String ievent_area_type,String ievent_area_time,String ievent_area_date
            ,String ievent_area_latitude,String ievent_area_longitude,String ievent_area_locnam,String ievent_area_area_det,String ievent_area_area_photo,String ievent_area_map_snap){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(event_area_id,ievent_area_id);
        contentValues.put(event_area_account_id,ievent_area_account_id);
        contentValues.put(event_area_type,ievent_area_type);
        contentValues.put(event_area_time,ievent_area_time);
        contentValues.put(event_area_date,ievent_area_date);
        contentValues.put(event_area_latitude,ievent_area_latitude);
        contentValues.put(event_area_longitude,ievent_area_longitude);
        contentValues.put(event_area_locnam,ievent_area_locnam);
        contentValues.put(event_area_area_det,ievent_area_area_det);
        contentValues.put(event_area_area_photo,ievent_area_area_photo);
        contentValues.put(event_area_map_snap,ievent_area_map_snap);




        long result = db.insert(event_area_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }




    }
    //insert data into event_comment_TABLE  * M Y *
    public boolean insertDataforComment (String icomment_id,String ievent_comment_id, String ievent_account_comment_id ,
                                         String icomment_text,String ievent_comment_date,String ievent_comment_time){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(comment_id,icomment_id);
        contentValues.put(event_comment_id, ievent_comment_id);
        contentValues.put(event_account_comment_id, ievent_account_comment_id);
        contentValues.put(comment_text,icomment_text );
        contentValues.put(event_comment_date,ievent_comment_date );
        contentValues.put(event_comment_time,ievent_comment_time );

        long result = db.insert(event_comment_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }

    //insert data into event_like_TABLE * M Y *

    public boolean insertDataforLike (String ilike_id, String ievent_like_id, String ievent_account_like_id , String ievent_like_date ,String ievent_like_time ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(like_id,ilike_id);
        contentValues.put(event_like_id, ievent_like_id);
        contentValues.put(event_account_like_id, ievent_account_like_id);
        contentValues.put(event_like_date,ievent_like_date );
        contentValues.put(event_like_time,ievent_like_time );


        long result = db.insert(event_like_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }
//insert data into event_share_table  * M Y *
public boolean insertDataforEventShare (String ishare_id ,String ievent_share_id, String ievent_account_share_id, String ievent_share_date,String ievent_share_time ){

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues contentValues = new ContentValues();
    contentValues.put(share_id,ishare_id);
    contentValues.put(event_share_id,ievent_share_id );
    contentValues.put(event_account_share_id,ievent_account_share_id );
    contentValues.put(event_share_date,ievent_share_date);
    contentValues.put(event_share_time,ievent_share_time);



    long result = db.insert(event_share_TABLE, null, contentValues);
    if (result == -1) {
        return false;
    }
    else {
        return true;
    }


}

//insert data into event shre table * M Y *
public boolean insertDataforEventShareExPhoto (String iphoto_id,String ievent_photo_id, String ievent_account_photo_id , String ievent_photo_date ,
                                               String ievent_photo_time,String ievent_photo_data ){

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues contentValues = new ContentValues();
    contentValues.put(photo_id,iphoto_id);
    contentValues.put(event_photo_id,ievent_photo_id);
    contentValues.put(event_account_photo_id,ievent_account_photo_id);
    contentValues.put(event_photo_date,ievent_photo_date );
    contentValues.put(event_photo_time,ievent_photo_time );
    contentValues.put(event_photo_data,ievent_photo_data);

    long result = db.insert(event_ex_photo_TABLE, null, contentValues);
    if (result == -1) {
        return false;
    }
    else {
        return true;
    }


}

//insert data into account table * M Y *
    public boolean insertDataforAccount(String iaccount_id,String iaccount_type,String iaccount_email,String iaccount_phone, String iaccount_password,
                                        String ipermission_inter,String ipermission_event, String ipermission_comm,String ipermission_share,String ipermission_like,
                                        String ipermission_camera,String ipermission_profile,String ipermission_editprofile,String ipermission_map,String ipermission_noti
                                         ,String ipermission_showevent ,String ipermission_make_event_solve,String ipermission_block,String ipermission_report
                                            ,String ipermission_blocku_admin,String ipermission_deleteu_admin,String ipermission_photo_hide,
                                        String ipermission_photo_hide_admin,String ipermission_photo_delete){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(account_id,iaccount_id);
        contentValues.put(account_type,iaccount_type);
        contentValues.put(account_email,iaccount_email);
        contentValues.put(account_phone,iaccount_phone);
        contentValues.put(account_password,iaccount_password);
        contentValues.put(permission_inter,ipermission_inter);
        contentValues.put(permission_event,ipermission_event);
        contentValues.put(permission_comm,ipermission_comm);
        contentValues.put(permission_share,ipermission_share);
        contentValues.put(permission_like,ipermission_like);
        contentValues.put(permission_camera,ipermission_camera);
        contentValues.put(permission_profile,ipermission_profile);
        contentValues.put(permission_editprofile,ipermission_editprofile);
        contentValues.put(permission_map,ipermission_map);
        contentValues.put(permission_noti,ipermission_noti);
        contentValues.put(permission_showevent,ipermission_showevent);
        contentValues.put(permission_make_event_solve,ipermission_make_event_solve);
        contentValues.put(permission_block,ipermission_block);
        contentValues.put(permission_report,ipermission_report);
        contentValues.put(permission_blocku_admin,ipermission_blocku_admin);
        contentValues.put(permission_deleteu_admin,ipermission_deleteu_admin);
        contentValues.put(permission_photo_hide,ipermission_photo_hide);
        contentValues.put(permission_photo_hide_admin,ipermission_photo_hide_admin);
        contentValues.put(permission_photo_delete,ipermission_photo_delete);




        long result =db.insert(account_TABLE,null,contentValues);
        if(result == -1) {
            return false;
        }
        else {
                return true;
        }
    }
    //insert data into profile table * M Y *
    public boolean insertDataforProfile(String iprofile_id,String iprofile_account_id,String iprofile_name,String iprofile_sex, String iprofile_age,
                                        String iprofile_loc ,String iprofile_edu,String iprofile_rel ){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(profile_id,iprofile_id);
        contentValues.put(profile_account_id,iprofile_account_id);
        contentValues.put(profile_name,iprofile_name);
        contentValues.put(profile_sex,iprofile_sex);
        contentValues.put(profile_age,iprofile_age);
        contentValues.put(profile_loc,iprofile_loc);
        contentValues.put(profile_edu,iprofile_edu);
        contentValues.put(profile_rel,iprofile_rel);

        long result =db.insert(profile_TABLE,null,contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

  //insert data into follow table
    public boolean insertDataforFollow(String ifollow_id,String ifollow_account_id,String ifollower_account_id,String ifollowing_account_id){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(follow_id,ifollow_id);
        contentValues.put(follow_account_id,ifollow_account_id);
        contentValues.put(follower_account_id,ifollower_account_id);
        contentValues.put(following_account_id,ifollowing_account_id);

        long result =db.insert(follow_TABLE, null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            return  true;
        }
    }

    /*function to show the data */
//get data from event
    public Cursor getEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from  event ",null);
        return  res;
    }
    //get data from area
    public Cursor getArea() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from  event_area ",null);
        return  res;
    }
    //get data from comment
    public Cursor getComment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from  event_comment ",null);
        return  res;
    }
    //get data from like
    public Cursor getLike() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from  event_like ",null);
        return  res;
    }
    //get data from share
    public Cursor getShare() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from  event_share",null);
        return  res;
    }
    //get data from ex_photo
    public Cursor getExphoto() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from event_ex_photo  ",null);
        return  res;
    }
    //get data from account
    public Cursor getAccount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from account  ",null);
        return  res;
    }
    //get data from profile
    public Cursor getProfile() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from profile  ",null);
        return  res;
    }
    //get data from follow
    public Cursor getFollow() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from follow  ",null);
        return  res;
    }



    /* function to updata data */

    public boolean updataData(String name,String lon,String lat,String id){
        SQLiteDatabase db = this.getWritableDatabase();


      //  String UpdateRecordQuery = "UPDATE "+event_TABLE+" SET NAME='" + name + "', LON='" + lon + "', LAT='" + lat + "' WHERE ID=" + id + ";";



       // db.execSQL(UpdateRecordQuery);

        return true;
    }




// Update data into table event *SR*

    public boolean UpdateDataforevent (String ievent_id,String ievent_account_id,String ievent_type,String ievent_time,String ievent_date
            ,String ievent_latitude,String ievent_longitude,String ievent_locnam,String ievent_det,String ievent_photo ,String ievent_map_snap) {




        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(event_id,ievent_id);
        contentValues.put(event_account_id,ievent_account_id);
        contentValues.put(event_type, ievent_type);
         contentValues.put(event_date_time, ievent_date+"-"+ievent_time);
        contentValues.put(event_latitude, ievent_latitude);
        contentValues.put(event_longitude, ievent_longitude);
        contentValues.put(event_locnam, ievent_locnam);
        contentValues.put(event_det, ievent_det);
        contentValues.put(event_photo, ievent_photo);
        contentValues.put(event_map_snap,ievent_map_snap);




        //db.execSQL("DELETE FROM event " );


        long result = db.update(event_TABLE, contentValues, "event_id="+ievent_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }


    //Update data into table area event *SR*

    public boolean UpdateDataforEventArea(String ievent_area_id,String ievent_area_account_id,String ievent_area_type,String ievent_area_time,String ievent_area_date
            ,String ievent_area_latitude,String ievent_area_longitude,String ievent_area_locnam,String ievent_area_area_det,String ievent_area_area_photo,String ievent_area_map_snap){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(event_area_id,ievent_area_id);
        contentValues.put(event_area_account_id,ievent_area_account_id);
        contentValues.put(event_area_type,ievent_area_type);
        contentValues.put(event_area_time,ievent_area_time);
        contentValues.put(event_area_date,ievent_area_date);
        contentValues.put(event_area_latitude,ievent_area_latitude);
        contentValues.put(event_area_longitude,ievent_area_longitude);
        contentValues.put(event_area_locnam,ievent_area_locnam);
        contentValues.put(event_area_area_det,ievent_area_area_det);
        contentValues.put(event_area_area_photo,ievent_area_area_photo);
        contentValues.put(event_area_map_snap,ievent_area_map_snap);




        long result = db.update(event_area_TABLE, contentValues, "event_area_id="+ievent_area_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }




    }


    //Update data into event_comment_TABLE  *SR*

    public boolean UpdateDataforComment (String icomment_id,String ievent_comment_id, String ievent_account_comment_id ,
                                         String icomment_text,String ievent_comment_date,String ievent_comment_time){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(comment_id,icomment_id);
        contentValues.put(event_comment_id, ievent_comment_id);
        contentValues.put(event_account_comment_id, ievent_account_comment_id);
        contentValues.put(comment_text,icomment_text );
        contentValues.put(event_comment_date,ievent_comment_date );
        contentValues.put(event_comment_time,ievent_comment_time );

        long result = db.update(event_comment_TABLE, contentValues, "comment_id="+icomment_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }


    //Update data into event_like_TABLE *SR*

    public boolean UpdateDataforLike (String ilike_id, String ievent_like_id, String ievent_account_like_id , String ievent_like_date ,String ievent_like_time ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(like_id,ilike_id);
        contentValues.put(event_like_id, ievent_like_id);
        contentValues.put(event_account_like_id, ievent_account_like_id);
        contentValues.put(event_like_date,ievent_like_date );
        contentValues.put(event_like_time,ievent_like_time );


        long result = db.update(event_like_TABLE, contentValues, "like_id="+ilike_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }


    //Update data into event_share_table  *SR*

    public boolean UpdateDataforEventShare (String ishare_id ,String ievent_share_id, String ievent_account_share_id, String ievent_share_date,String ievent_share_time ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(share_id,ishare_id);
        contentValues.put(event_share_id,ievent_share_id );
        contentValues.put(event_account_share_id,ievent_account_share_id );
        contentValues.put(event_share_date,ievent_share_date);
        contentValues.put(event_share_time,ievent_share_time);



        long result = db.update(event_share_TABLE, contentValues, "share_id="+ishare_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }


    //Update data into event shre table *SR*

    public boolean UpdateDataforEventShareExPhoto (String iphoto_id,String ievent_photo_id, String ievent_account_photo_id , String ievent_photo_date ,
                                                   String ievent_photo_time,String ievent_photo_data ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(photo_id,iphoto_id);
        contentValues.put(event_photo_id,ievent_photo_id);
        contentValues.put(event_account_photo_id,ievent_account_photo_id);
        contentValues.put(event_photo_date,ievent_photo_date );
        contentValues.put(event_photo_time,ievent_photo_time );
        contentValues.put(event_photo_data,ievent_photo_data);

        long result = db.update(event_ex_photo_TABLE, contentValues, "photo_id="+iphoto_id, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }


    //Update data into account table *SR*

    public boolean UpdateDataforAccount(String iaccount_id,String iaccount_type,String iaccount_email,String iaccount_phone, String iaccount_password,
                                        String ipermission_inter,String ipermission_event, String ipermission_comm,String ipermission_share,String ipermission_like,
                                        String ipermission_camera,String ipermission_profile,String ipermission_editprofile,String ipermission_map,String ipermission_noti
            ,String ipermission_showevent ,String ipermission_make_event_solve,String ipermission_block,String ipermission_report
            ,String ipermission_blocku_admin,String ipermission_deleteu_admin,String ipermission_photo_hide,
                                        String ipermission_photo_hide_admin,String ipermission_photo_delete){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(account_id,iaccount_id);
        contentValues.put(account_type,iaccount_type);
        contentValues.put(account_email,iaccount_email);
        contentValues.put(account_phone,iaccount_phone);
        contentValues.put(account_password,iaccount_password);
        contentValues.put(permission_inter,ipermission_inter);
        contentValues.put(permission_event,ipermission_event);
        contentValues.put(permission_comm,ipermission_comm);
        contentValues.put(permission_share,ipermission_share);
        contentValues.put(permission_like,ipermission_like);
        contentValues.put(permission_camera,ipermission_camera);
        contentValues.put(permission_profile,ipermission_profile);
        contentValues.put(permission_editprofile,ipermission_editprofile);
        contentValues.put(permission_map,ipermission_map);
        contentValues.put(permission_noti,ipermission_noti);
        contentValues.put(permission_showevent,ipermission_showevent);
        contentValues.put(permission_make_event_solve,ipermission_make_event_solve);
        contentValues.put(permission_block,ipermission_block);
        contentValues.put(permission_report,ipermission_report);
        contentValues.put(permission_blocku_admin,ipermission_blocku_admin);
        contentValues.put(permission_deleteu_admin,ipermission_deleteu_admin);
        contentValues.put(permission_photo_hide,ipermission_photo_hide);
        contentValues.put(permission_photo_hide_admin,ipermission_photo_hide_admin);
        contentValues.put(permission_photo_delete,ipermission_photo_delete);




        long result = db.update(account_TABLE, contentValues, "account_id="+iaccount_id, null);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //Update data into profile table *SR*

    public boolean UpdateDataforProfile(String iprofile_id,String iprofile_account_id,String iprofile_name,String iprofile_sex, String iprofile_age,
                                        String iprofile_loc ,String iprofile_edu,String iprofile_rel ){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(profile_id,iprofile_id);
        contentValues.put(profile_account_id,iprofile_account_id);
        contentValues.put(profile_name,iprofile_name);
        contentValues.put(profile_sex,iprofile_sex);
        contentValues.put(profile_age,iprofile_age);
        contentValues.put(profile_loc,iprofile_loc);
        contentValues.put(profile_edu,iprofile_edu);
        contentValues.put(profile_rel,iprofile_rel);

        long result = db.update(profile_TABLE, contentValues, "profile_id="+iprofile_id, null);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //Update data into follow table
    public boolean UpdateDataforFollow(String ifollow_id,String ifollow_account_id,String ifollower_account_id,String ifollowing_account_id){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(follow_id,ifollow_id);
        contentValues.put(follow_account_id,ifollow_account_id);
        contentValues.put(follower_account_id,ifollower_account_id);
        contentValues.put(following_account_id,ifollowing_account_id);

        long result = db.update(follow_TABLE, contentValues, "follow_id="+ifollow_id, null);
        if(result == -1){
            return false;
        }
        else {
            return  true;
        }
    }





    /* function to delet Data data */

//delete data from event table
    public boolean deletevent(String id){
        SQLiteDatabase db = this.getWritableDatabase();
       String UpdateRecordQuery = "DELETE FROM "+event_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from area table
    public boolean deletarea(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+event_area_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from comment table
    public boolean deletecomment(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+event_comment_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from like table
    public boolean deletelike(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+event_like_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from share table
    public boolean deleteshare(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+event_share_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from Exphoto table
    public boolean deleteExphoto(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+event_ex_photo_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from account table
    public boolean deleteaccount(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+account_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from profile table
    public boolean deleteprofile(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+profile_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete data from follow table
    public boolean deletefollow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE FROM "+follow_TABLE+" WHERE ID = "+id+";";
        db.execSQL(UpdateRecordQuery);
        return true;
    }

    /* function to delet all Data data */

//delete All data from event table *my*
    public boolean deleteallDataevent( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM "+event_TABLE;
       db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from event area table *my*
    public boolean deleteallDataarea( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM  "+event_area_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from comment table
    public boolean deletealldatacomment ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+event_comment_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from like table
    public boolean deletealldatalike ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+event_like_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from share table
    public boolean deletealldatashare ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+event_share_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from event_ex_photo_TABLE
    public boolean deletealldataExphoto ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+event_ex_photo_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from account TABLE
    public boolean deletealldataaccount ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+account_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from profile TABLE
    public boolean deletealldataorofile ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM "+profile_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }
    //delete All data from follow TABLE
    public boolean deletealldatafollow ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateRecordQuery = "DELETE ALL FROM"+follow_TABLE;
        db.execSQL(UpdateRecordQuery);
        return true;
    }


    public static void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("SR", " testing db path " + databasePath);
        Log.d("SR", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File("/mnt/sdcard/DB_SR");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
