package com.sr.projectg.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sr.projectg.Database.SQLiteDatabaseHelper;
import com.sr.projectg.Firebase.FireEvent;
import com.sr.projectg.Fragment.MapHomeFragment;
import com.sr.projectg.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddEventActivity extends AppCompatActivity implements LocationListener, Application.ActivityLifecycleCallbacks ,OnMapReadyCallback {


     FloatingActionButton fabloc,fabsend;
     int dc2;
     Bitmap Pbitmap,mapbitmap;
     EditText datematab,timematab,loc,locname,detailsmatab;
     TextView detailscu;
     ImageView imageaddmatab;
     ImageView samplemap;
     int PLACE_PICKER_REQUEST=1;
     int CAMERA_PIC_REQUEST = 2;
    private static Uri outputFileUri;


    double sr1,sr2;
    StringBuilder text1 ,text2,text3;


    private LocationManager locationManager;
    private LocationListener listener;

    private static boolean isMySomeActivityVisible;

    SQLiteDatabaseHelper myDB;
    MapHomeFragment map;

    static  String title=null;
    ActionBar actionBar;


    public GoogleMap mMap;
    private LatLng srlocation =null;
    Bitmap bitmap;
    LatLng latLng=null;
    GoogleApiClient mGoogleApiClient;

    Bundle bundle;


    // [START declare_auth]
    private static FirebaseAuth mAuth;
    private static FirebaseStorage storage;
    StorageReference eventsr,eventsr2;
    StorageReference storageRef,storageRef2;
    FireEvent fireEvent;
    String id,name  ,email,photourl,mapurl ,evid ;




    // [END declare_auth]

    // [START declare_auth_listener]
    DatabaseReference db;
    private static final String TAG = "SRFire0";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance().getReference();



        mAuth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        // [END initialize_auth]

        // Create a storage reference from our app

            evid = eventid();
            name = mAuth.getCurrentUser().getDisplayName().toString();
            email = mAuth.getCurrentUser().getEmail().toString();

            storageRef = storage.getReference();
            storageRef2 = storage.getReference();
            StorageReference userprofile;
            fireEvent = new FireEvent();
            eventsr = storageRef.child("event/" + evid + ".jpg");
            eventsr2 = storageRef2.child("event/" + evid + "-map" + ".jpg");



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapevent);
        mapFragment.getMapAsync(this);




        makefile();

          actionBar = getSupportActionBar();
        if (actionBar != null) {
            bundle = getIntent().getExtras();

            actionBar.setTitle(bundle.getString("SRtitle"));
            Log.v("SR Set Event Title:", actionBar.getTitle().toString());
        }

        //for control database
        myDB= new SQLiteDatabaseHelper(this);
        map= new MapHomeFragment();


       // myDB.copyDatabase(getApplicationContext(),"TheXData.db");





        detailsmatab=(EditText)findViewById(R.id.detailsmatab) ;
        detailsmatab.requestFocus();


        //for location

        loc=(EditText)findViewById(R.id.locationmatab) ;
        locname=(EditText)findViewById(R.id.locname) ;

        getdatatext();

        loc.setText(text1+","+text2);
        locname.setText(text3);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                loc.setText( location.getLongitude() + "," + location.getLatitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {



            }

            @Override
            public void onProviderEnabled(String s) {


            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        getlatlong();





 //for date and time



        timematab = (EditText) findViewById(R.id.timematab);
        datematab =(EditText) findViewById(R.id.datematab);


        new CountDownTimer(300000000, 1000) {

            public void onTick(long millisUntilFinished) {

                DateFormat tf = new SimpleDateFormat("HH:mm:ss");
                String time = tf.format(Calendar.getInstance().getTime());
                timematab.setText(time);




                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                String date = df.format(Calendar.getInstance().getTime());
                datematab.setText(date);



            }

            public void onFinish() {
             }
        }.start();



        // FindViewById
        imageaddmatab = (ImageView) findViewById(R.id.imageaddmatab);
        //samplemap = (ImageView) findViewById(R.id.samplemap);

        // FindViewById//
        detailscu= (TextView) findViewById(R.id.detailscu);
        detailsmatab.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(detailsmatab.length()<1){


                         detailscu.setText("");

                    }
                    if(detailsmatab.length()>1){


                        dc2=detailsmatab.length();
                        detailscu.setText(Integer.toString(dc2));

                    }
                    if(detailsmatab.length()<29){

                        detailscu.setTextColor(Color.parseColor("#FF0000"));
                    }
                    if (detailsmatab.length()>=30){
                        detailscu.setTextColor(Color.parseColor("#3CDE00"));



                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        fabloc = (FloatingActionButton) findViewById(R.id.fabloc);
        fabsend= (FloatingActionButton) findViewById(R.id.fabsend);

        fabsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }



                if(Pbitmap!=null){

                        if(bitmap!=null) {


                            Bitmap bitmapg = Pbitmap;
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmapg.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

                            Log.e("Original   dimensions", bitmapg.getWidth()+" "+bitmapg.getHeight());
                            Log.e("Compressed dimensions", decoded.getWidth()+" "+decoded.getHeight());

                            byte[] data = baos.toByteArray();


                            Bitmap bitmapmp = bitmap;
                            ByteArrayOutputStream baosmp = new ByteArrayOutputStream();
                            bitmapmp.compress(Bitmap.CompressFormat.JPEG, 100,baosmp );
                            Bitmap decoded2 = BitmapFactory.decodeStream(new ByteArrayInputStream(baosmp.toByteArray()));

                            Log.e("Original   dimensions", bitmapmp.getWidth()+" "+bitmapmp.getHeight());
                            Log.e("Compressed dimensions", decoded2.getWidth()+" "+decoded2.getHeight());

                            byte[] datamp = baosmp.toByteArray();


                            UploadTask uploadTask = eventsr.putBytes(data);
                            final UploadTask uploadTaskmp = eventsr2.putBytes(datamp);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(TAG, name + " photo ERORR");

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    photourl = task.getResult().getDownloadUrl().toString();

                                }
                            }) ;

                                    uploadTaskmp.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                            mapurl = task2.getResult().getDownloadUrl().toString();

                                            Firebase.setAndroidContext(AddEventActivity.this);
                                            final Firebase ref = new Firebase("https://projectg-70ce9.firebaseio.com/ACCOUNT");
                                            Query query = ref.orderByChild("account_email").equalTo(email);
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                                                    for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                        id=postSnapshot.child("account_id").getValue().toString();
                                                    }




                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });



                                            fireEvent.setEvent_account_id(id);
                                            fireEvent.setEvent_id(evid);
                                            fireEvent.setEvent_type(bundle.getString("SRtitle"));
                                            fireEvent.setEvent_type_code(bundle.getString("SRCode"));
                                            fireEvent.setEvent_photo_url(photourl);
                                            fireEvent.setEvent_map_snap_url(mapurl);
                                            fireEvent.setEvent_det(detailsmatab.getText().toString());
                                            fireEvent.setEvent_longitude(text2.toString());
                                            fireEvent.setEvent_latitude(text1.toString());
                                            fireEvent.setEvent_locnam(text3.toString());
                                            fireEvent.setEvent_date_time(datematab.getText().toString()
                                                    + "-" + timematab.getText().toString());


                                            db.child("EVENT").push().setValue(fireEvent);
                                            db.addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                    Toast.makeText(getApplicationContext(),"حبيبي تسلم",Toast.LENGTH_SHORT).show();
                                                    finish();


                                                }

                                                @Override
                                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                }

                                                @Override
                                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                }

                                                @Override
                                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    });



                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Get location",Toast.LENGTH_SHORT).show();


                        }

                }
                else{

                    Toast.makeText(getApplicationContext(),"Add photo",Toast.LENGTH_SHORT).show();


                }






/*
                     boolean inserted =myDB.insertDataforevent(getidevent()
                             ,"ac5454"
                             ,bundle.getString("SRtitle")
                             ,bundle.getString("SRCode")
                            ,timematab.getText().toString()
                            ,datematab.getText().toString()
                            ,text1.toString()
                            ,text2.toString()
                            ,text3.toString()
                            ,detailsmatab.getText().toString()
                            ,getBitmapAsByteArray(mapbitmap)
                             ,getBitmapAsByteArray(mapbitmap)
                             );

                    if( inserted == true){



                        Toast.makeText(getApplicationContext(),"حبيبي تسلم event",Toast.LENGTH_SHORT).show();
                        myDB.copyDatabase(getApplicationContext(),"data.db");

                        map.zoom();
                        finish();




                    }
                    else{
                        Toast.makeText(getApplicationContext()," مش تسلم event",Toast.LENGTH_SHORT).show();
                    }

*/
                }



        });

        fabloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loadingmpe).setVisibility(View.VISIBLE);


                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


                Intent intent;
                try {
                    intent=builder.build(AddEventActivity.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        //ClickListener

        imageaddmatab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                 cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                setResult(RESULT_OK, cameraIntent);

                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);


            }
        });


        //ClickListener
    }


    //load Image From Storage
    private void loadImageFromStorage( )
    {
        String appname =  getString(R.string.app_name);


        try {

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/location_add.png");
            if(f.exists()) {

                  mapbitmap = BitmapFactory.decodeStream(new FileInputStream(f));

              //  samplemap.setImageBitmap(mapbitmap);
            }
            else {
                //Intent locIntent = new Intent(getApplication(),MapActivity.class);
                //startActivity(locIntent);


            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    //END//load Image From Storage




    //scale Down Bitmap

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK){
            if(data!=null) {

                final Place place = PlacePicker.getPlace(this, data);
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();

                String attributions = (String) place.getAttributions();
                if (attributions == null) {
                    attributions = "";
                }


                sr1 = place.getLatLng().latitude;
                sr2 = place.getLatLng().longitude;


                writeToFile(String.valueOf(sr1), "sr1", getApplicationContext());
                writeToFile(String.valueOf(sr2), "sr2", getApplicationContext());
                writeToFile(name + "," + address + "," + attributions, "sr3", getApplicationContext());

                loadlocaiton();
                setupMap();



                 CaptureMapScreen();


                // Intent intent = new Intent(AddEventActivity.this, MapActivity.class);
                // startActivity(intent);
                // finish();


            }

        }
        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_CANCELED){


            findViewById(R.id.loadingmpe).setVisibility(View.GONE);


        }

        if( requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Pbitmap = (Bitmap) data.getExtras().get("data");
            imageaddmatab.setImageBitmap(Pbitmap);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Picture Not taken", Toast.LENGTH_LONG);
        }
    }

    // for re
    // sume activity

    @Override
    protected void onResume() {

        super.onResume();
        getdatatext();
        if( MapActivity.activityPaused()==true){
            loadImageFromStorage();

        }
    }

    private void fillTextView (int id, String text) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(text); // tv is null
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                getlatlong();
                break;
            default:
                break;
        }
    }

    void getlatlong(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 0, 0, listener);

    }

    @Override
    public void onLocationChanged(Location location) {
        loc.setText(  location.getLongitude() + "," + location.getLatitude());


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void writeToFile(String data,String tx,Context context) {
        try {
            String appname = getString(R.string.app_name);

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/");
            if (!folder.exists()) {
                folder.mkdirs();
                Toast.makeText(getApplicationContext(), "Folder Maked", Toast.LENGTH_SHORT).show();

            } else {

                File f2 = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/");

                File file = new File(String.valueOf(f2.getPath()), tx+".txt");
                FileOutputStream stream = new FileOutputStream(file);
                try {
                    stream.write(data.getBytes());
                } finally {
                    stream.close();
                }

            }

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof AddEventActivity) {
            isMySomeActivityVisible = true;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof AddEventActivity) {
            isMySomeActivityVisible = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void getdatatext() {


        String appname = getString(R.string.app_name);

        //  readFileAsString(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/");

        String path1 = Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/";



//Get the text file
        File file1 = new File(path1,"sr1.txt");
        File file2 = new File(path1,"sr2.txt");
        File file3 = new File(path1,"sr3.txt");

//Read text from file
        text1 = new StringBuilder();
        text2 = new StringBuilder();
        text3 = new StringBuilder();

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedReader br3 = new BufferedReader(new FileReader(file3));

            String line1;
            String line2;
            String line3;

            while ((line1 = br1.readLine()) != null) {
                text1.append(line1);
            }
            br1.close();
            while ((line2 = br2.readLine()) != null) {
                text2.append(line2);
            }
            br2.close();
            while ((line3 = br3.readLine()) != null) {
                text3.append(line3);
            }
            br3.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }


        loc.setText(text1+","+text2);
        locname.setText(text3);






    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private String  getidevent(){

        Random r = new Random();
        int randomNo = r.nextInt(1000+1);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mon = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        int hour= c.get(Calendar.HOUR_OF_DAY);
        int Min= c.get(Calendar.MINUTE);
        int sec= c.get(Calendar.SECOND);


        String strI = "ev" + randomNo+day+mon+year+hour+Min+sec;

        return strI;


    }

    public void makefile(){

        String appname =  getString(R.string.app_name);


        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/");
        if (!folder.exists()) {
            folder.mkdirs();
            Log.i("SR","eventFileMaked");
        }


         File folder2 = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/");
        if (!folder.exists()) {
            folder.mkdirs();
            Log.i("SR","mapfoldermaked");

        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        setupMap();
    }



    private void setupMap() {



        listener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 70);
                mMap.animateCamera(cameraUpdate);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (srlocation!=null) {
            Marker SR = mMap.addMarker(new MarkerOptions()
                    .position(srlocation)
                     .icon(bbd(bundle.getString("SRCode"))));


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(srlocation, 17));

        }



        //  mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


    private boolean loadlocaiton(){

        String appname = getString(R.string.app_name);

        //  readFileAsString(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/");

        String path1 = Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/";
        String path2 = Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Text/";


//Get the text file
        File file1 = new File(path1,"sr1.txt");
        File file2 = new File(path2,"sr2.txt");

//Read text from file
        StringBuilder text1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();

        try {

            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));

            String line1;
            String line2;

            while ((line1 = br1.readLine()) != null) {
                text1.append(line1);
                Log.i("sr","done read");

            }
            br1.close();
            while ((line2 = br2.readLine()) != null) {
                text2.append(line2);

            }
            br2.close();

        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            return false;
        }

//Find the view by its id

//Set the text
        // tv.setText(text.toString());

        double contents1 = Double.parseDouble(new String(text1.toString()));
        double contents2 = Double.parseDouble(new String(text2.toString()));


        srlocation=new LatLng(contents1, contents2);
        Log.i("sr","contents1:"+text1.toString()+"-contents2:"+text2.toString());
        if (text1==null){

            return false;
        }

        return true;


    }

    public void CaptureMapScreen() {
        findViewById(R.id.loadingmpe).setVisibility(View.VISIBLE);



        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {

                findViewById(R.id.loadingmpe).setVisibility(View.GONE);

                GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {


                    @Override
                    public void onSnapshotReady(Bitmap snapshot) {
                        // TODO Auto-generated method stub
                        bitmap = snapshot;

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String appname =  getString(R.string.app_name);
                        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/");
                        if (!folder.exists()) {
                            folder.mkdirs();
                            Log.i("SR","mapfoldermaked");
                        } else {
                            File f2 = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/location_add.png");


                            try {
                                f2.createNewFile();
                                FileOutputStream fo = new FileOutputStream(f2);
                                fo.write(bytes.toByteArray());
                                fo.close();



                                Toast.makeText(getApplicationContext(), "Location Update", Toast.LENGTH_SHORT).show();




                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        }

                    }


                };


                mMap.snapshot(callback);


            }
        });




    }




    private BitmapDescriptor bbd(String type){

        BitmapDescriptor bb = null;
        int tt=Integer.parseInt(type);

        if(tt==1){

            bb=BitmapDescriptorFactory.fromResource(R.mipmap.newbump);

        }
        if(tt==2){

            bb=BitmapDescriptorFactory.fromResource(R.mipmap.camt);

        }
        if(tt==3){

            bb=BitmapDescriptorFactory.fromResource(R.mipmap.other);

        }



        return bb;
    }


    @Override
    protected void onStop() {
        super.onStop();

        map.zoom();
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(loc.getText().toString())  ) {
            loc.setError("Get your location");
            valid = false;

        } else {
            loc.setError(null);
        }

         if (TextUtils.isEmpty(detailsmatab.getText().toString()) || detailsmatab.length()<30  ) {

             detailsmatab.setError("Minimum of characters is 30");
            valid = false;

        } else {
             detailsmatab.setError(null);
        }




        return valid;
    }


    private String eventid(){


        Random r = new Random();
        int randomNo = r.nextInt(1000+1);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH)+1;
        int mon = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        int hour= c.get(Calendar.HOUR_OF_DAY);
        int Min= c.get(Calendar.MINUTE);
        int sec= c.get(Calendar.SECOND);


        String evid = "ev" + randomNo+day+mon+year+hour+Min+sec;

        return evid;
    }


}
