package com.sr.projectg.Fragment;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sr.projectg.R;

/**
 * Created by sr on 2/22/17.
 */

public class NotiFragment extends Fragment {


    ImageView imageNoti;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.noti_layout, container, false);



        //imageNoti=(ImageView) rootView.findViewById(R.id.imageNoti);

        return rootView;





    }

/*
    //load Image From Storage
    private void loadImageFromStorage( )
    {
        String appname =  getString(R.string.app_name);


        try {

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");
            if(f.exists()) {

                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                imageNoti.setImageBitmap(b);
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
*/

    @Override
    public void onResume() {
        super.onResume();

      //  loadImageFromStorage();


    }


    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 11);
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 4);

        }

    }


}
