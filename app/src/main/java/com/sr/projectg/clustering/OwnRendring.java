package com.sr.projectg.clustering;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by sr on 4/1/17.
 */

public class OwnRendring extends DefaultClusterRenderer<MyItem> {

    public OwnRendring(Context context, GoogleMap map,
                       ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }


    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {

        markerOptions.icon(item.getIcon());
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}
