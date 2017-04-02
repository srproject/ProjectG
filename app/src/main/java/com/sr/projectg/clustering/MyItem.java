package com.sr.projectg.clustering;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Dimitar Danailov on 6/3/15.
 * email: dimityr.danailov@gmail.com
 *
 * Documentation: https://developers.google.com/maps/documentation/android/utility/marker-clustering
 */
public class MyItem implements ClusterItem {

    private final LatLng mPosition;
    BitmapDescriptor icon;
    String title;
    String snippet;

    public MyItem(BitmapDescriptor ic, Double lat, Double lng, String tit, String sni) {
        mPosition = new LatLng(lat, lng);
        icon = ic;
        title = tit;
        snippet = sni;

    }



    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return title;
    }


    public String getSnippet() {
        return snippet;
    }






    public BitmapDescriptor getIcon() {
        return icon;
    }


    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }
}