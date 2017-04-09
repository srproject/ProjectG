package com.sr.projectg.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sr.projectg.Database.SQLiteDatabaseHelper;
import com.sr.projectg.R;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.maps.android.clustering.ClusterManager;
import com.sr.projectg.clustering.MyItem;
import com.sr.projectg.clustering.OwnRendring;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapHomeFragment extends Fragment implements com.google.android.gms.location.LocationListener, AccessibilityManagerCompat.TouchExplorationStateChangeListener, GoogleMap.OnMarkerClickListener {


    public MapHomeFragment() {
        // Required empty public constructor
    }


    static MapView mMapView;
    private static GoogleMap googleMap;
    private LocationListener listener;
    LatLng ff;
    private LocationManager locationManager;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 14;


    static double lng;
    static double lat;

    String name1, lat1, lng1,det1,code1;

    SQLiteDatabaseHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor,cursor2;

    ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    static ArrayList<Double> latc = new ArrayList<>();
    static ArrayList<Double> longc = new ArrayList<>();
    ArrayList<String> location_name = new ArrayList<String>();
    ArrayList<String> det = new ArrayList<String>();
    ArrayList<String> code = new ArrayList<String>();

    LatLng newLatLng;
    private ProgressBar progressBarmapf;


    static ClusterManager<MyItem> mClusterManager;

     BitmapDescriptor icon;
    String bbs;
    int bbi=0;
     static int count = 0;
    MyItem offsetItem;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        getdataevent();

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.setDrawingCacheEnabled(false);
         progressBarmapf= (ProgressBar) rootView.findViewById(R.id.loadingmpfpb);
        progressBarmapf.setVisibility(View.VISIBLE);


        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.LOCATION_HARDWARE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.LOCATION_HARDWARE)) {

               // mMapView.setVisibility(View.VISIBLE);

            }
            else {

               // mMapView.onStop();
               // mMapView.setVisibility(View.GONE);

            }
        }





        //getdatafromevent();
       // MapsInitializer.initialize(getActivity());

        //startupmap();



       //  mMapView.getMapAsync((OnMapReadyCallback) getContext());


    //    mMapView.onResume(); // needed to get the map to display immediately
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                mClusterManager = new ClusterManager<MyItem>(getActivity(), googleMap);
                mClusterManager.setRenderer(new OwnRendring(getContext(),googleMap,mClusterManager));
                mClusterManager.clearItems();

                // Point the map's listeners at the listeners implemented by the cluster manager.
                googleMap.setOnCameraChangeListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.setTrafficEnabled(true);

                DisplayMetrics displayMetrics = getContext().getResources()
                        .getDisplayMetrics();

                int screenWidthInPix =  displayMetrics.widthPixels;

                int screenheightInPix = displayMetrics.heightPixels;
                Log.i("SR SCrean","w: "+screenWidthInPix+"h:"+screenheightInPix);
                if(screenWidthInPix<=480&&screenheightInPix<=800){

                    googleMap.setPadding(0, 0, 0, 300);

                }
                else {
                    googleMap.setPadding(0, 0, 0, 700);


                }
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                // Enable / Disable Compass icon
                googleMap.getUiSettings().setCompassEnabled(true);
                // Enable / Disable Rotate gesture
                googleMap.getUiSettings().setRotateGesturesEnabled(true);







                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    public void onMapLoaded() {

                      // addcluster();


                        progressBarmapf.setVisibility(View.GONE);




                        LatLng sydney = new LatLng(lat, lng);




                        // addClusterMarkers(mClusterManager);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                        googleMap.animateCamera(zoom);




                    }

                });












                ///LatLng sydney = new LatLng(lat, lng);

//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//                   CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
  //                  googleMap.animateCamera(zoom);



                getlatlong();

//                addClusterMarkers(mClusterManager);


                // For zooming automatically to the location of the marker



            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
         mMapView.onResume();
        Log.i("SR0009","Re");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mMapView.getOverlay().clear();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
      //  mMapView.onStop();
      //  mClusterManager.cluster();

        Log.i("SR0009","Pu");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // mMapView.onStop();
        Log.i("SR0009","Destroy");

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLocationChanged(final Location location) {


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                getlatlong();
                break;
            default:
                break;
        }
    }

    public void getlatlong() {
        // first check for permissions

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.


        //noinspection MissingPermission

        if (isNetworkStatusAvialable(getContext())) {
            //  Toast.makeText(getContext(), "internet avialable", Toast.LENGTH_LONG).show();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, listener);

        } else {
            Toast.makeText(getContext(), "internet is not avialable (For faster connect to internet)", Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0.0f, listener );


        }




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i("SR", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.i("SR", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.
            }
        }
    }


    @Override
    public void onTouchExplorationStateChanged(boolean enabled) {

    }

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

    private void  getdataevent(){


        SQLITEHELPER = new SQLiteDatabaseHelper(getContext());

        SQLITEDATABASE = SQLITEHELPER.getReadableDatabase();




        try {
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM event", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        name1 = cursor.getString(cursor.getColumnIndex("event_type"));
                        //det = cursor.getString(cursor.getColumnIndex("det"));
                        lat1 = cursor.getString(cursor.getColumnIndex("event_latitude"));
                        lng1 = cursor.getString(cursor.getColumnIndex("event_longitude"));
                        det1 = cursor.getString(cursor.getColumnIndex("event_det"));
                        code1 = cursor.getString(cursor.getColumnIndex("event_type_code"));
                        newLatLng = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
                        latLngs.add(newLatLng);
                        latc.add(Double.parseDouble(lat1));
                        longc.add(Double.parseDouble(lng1));

                        location_name.add(name1);
                        det.add(det1);
                        code.add(code1);
                        lat = Double.parseDouble(lat1);
                        lng=Double.parseDouble(lng1);






                        Log.i("SR","SR");

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String name= (String) marker.getId();


        if (name=="")
        {
            //handle click here
        }

        return false;
    }

    public void getmylocation() {
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {

                            ff = new LatLng(location.getLatitude(), location.getLongitude());

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(ff).zoom(16).tilt(20).build();
                           // googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(ff).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_black_48dp)).title("This My Location"));
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 700, null);












                }

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
        getlatlong();
    }

   public static void zoom(){

       count = 0;
   }

    private void startupmap(){

     //   LatLng sydney = new LatLng(26.820553, 30.802498);


      //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(26.820553,30.802498), 9));


    }

    private void addClusterMarkers(ClusterManager<MyItem> mClusterManager) {

        // Set some lat/lng coordinates to start with.
        double latitude = 30.044420;
        double longitude = 31.235712;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            longitude = longitude + offset;
           // MyItem offsetItem = new MyItem(null,latitude, longitude,"SAMEH","SAMEH");

           // mClusterManager.addItem(offsetItem);
         }
    }

    private  BitmapDescriptor bbd(String type){

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


    public void addcluster(){

        Iterator<LatLng> latLng = latLngs.iterator();
        Iterator<String> locationname = location_name.iterator();
        Iterator<String> deti = det.iterator();
        Iterator<String> codei = code.iterator();
        Iterator<Double> latcc = latc.iterator();
        Iterator<Double> longcc = longc.iterator();

         while (latcc.hasNext()) {
            // mClusterManager.clearItems();
            // googleMap.addMarker(new MarkerOptions().position(latLng.next()).snippet(deti.next()).title(locationname.next()));
             bbs=codei.next();



            offsetItem = new MyItem(bbd(bbs), latcc.next(), longcc.next(), locationname.next(), deti.next());



                mClusterManager.addItem(offsetItem);
                mClusterManager.cluster();



            Log.i("SR0000",String.valueOf(count));


            //



        }








    }


    public void getitem(){




        Iterator<LatLng> latLng = latLngs.iterator();
        Iterator<String> locationname = location_name.iterator();
        Iterator<String> deti = det.iterator();
        Iterator<String> codei = code.iterator();
        Iterator<Double> latcc = latc.iterator();
        Iterator<Double> longcc = longc.iterator();


        // mClusterManager.clearItems();



        while (latcc.hasNext()&&deti.hasNext()) {
            // mClusterManager.clearItems();
            // googleMap.addMarker(new MarkerOptions().position(latLng.next()).snippet(deti.next()).title(locationname.next()));

            bbs=codei.next();








            //
            if(!latcc.hasNext()) {

                count++;
            }
            Log.i("SR00000",String.valueOf(count));


        }








    }



}


