package com.sr.projectg.Fragment;

import android.Manifest;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sr.projectg.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sr on 2/22/17.
 */

public class CameraFragment extends Fragment implements SurfaceHolder.Callback {


    Camera camera; // camera class variable
    SurfaceView camView; // drawing camera preview using this variable
    SurfaceHolder surfaceHolder; // variable to hold surface for surfaceView which means display
    boolean camCondition = false;  // conditional variable for camera preview checking and set to false
    Button camerabu;    // image capturing button



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_layout, container, false);
        camView = (SurfaceView) rootView.findViewById(R.id.camerapreview);


        // getting access to the surface of surfaceView and return it to surfaceHolder
        surfaceHolder = camView.getHolder();
        // adding call back to this context means MainActivity
        surfaceHolder.addCallback((SurfaceHolder.Callback) this);
        // to set surface type
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);




        camView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Camera.Parameters parameters = camera.getParameters();
                //parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA); //applying effect on cameracamera.setParameters(parameters); // setting camera parameters
                try {
                    camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Camera.Parameters parameters2 = camera.getParameters();
                parameters2.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setDisplayOrientation(270);

                camera.setParameters(parameters2);


                camera.startPreview();  // starting camera preview

                camCondition = true;

            }
        });
/*

        // refering button id
        camerabu = (Button) rootView.findViewById(R.id.camerabu);
        // click event on button
        camerabu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Camera.Parameters parameters = camera.getParameters();
                //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                //camera.setParameters(parameters);
                //camera.startPreview();


                // TODO Auto-generated method stub
                // calling a method of camera class takepicture by passing one picture callback interface parameter
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);

                            camera.takePicture(null, null, mPictureCallback);

                            camera.stopPreview(); // stop preview using stopPreview() method
                            camCondition = false;

                        }
                    }
                });


            }
        });
**/

        return rootView;
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera c) {

            FileOutputStream outStream = null;
            try {

                // Directory and name of the photo. We put system time
                // as a postfix, so all photos will have a unique file name.
                outStream = new FileOutputStream("/sdcard/AndroidCodec_" +
                        System.currentTimeMillis() + ".jpg");
                outStream.write(data);
                outStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }

        }
    };


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        // stop the camera
        if (camCondition) {
            camera.stopPreview(); // stop preview using stopPreview() method
            camCondition = false; // setting camera condition to false means stop
        }
        // condition to check whether your device have camera or not
        if (camera != null) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                //parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA); //applying effect on cameracamera.setParameters(parameters); // setting camera parameters
                camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
                camera.startPreview();  // starting camera preview

                camCondition = true; // setting camera to true which means having camera
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 11);
        }
        camera = Camera.open();   // opening camera
       Camera.Parameters parameters = camera.getParameters();
      camera.setDisplayOrientation(90);// setting camera preview orientation
       parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//or
       // parameters.set("orientation", "portrait");
//or
        // parameters.setRotation(90);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
       // camera.stopPreview();  // stopping camera preview
        //camera.release();       // releasing camera
        //camera = null;          // setting camera to null when left
        //camCondition = false;   // setting camera condition to false also when exit from application
    }


    //load Image From Storage

    //END//load Image From Storage


    @Override
    public void onResume() {

         super.onResume();






    }

    @Override
    public void onPause() {


        super.onPause();
        if (camera != null)
        {
            camera.stopPreview();
            camera.release();
        }


    }

    @Override
    public void onDestroy() {


        super.onDestroy();


    }

    public void closeCamera() {
        Log.w("SR", "OnPause()");

        if (null != camera) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
     }
    public void openCamera() {
        // camera.startPreview();  // starting camera preview
        Log.i("camera","off");

        camera = Camera.open();

        Camera.Parameters parameters = camera.getParameters();
        //parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA); //applying effect on cameracamera.setParameters(parameters); // setting camera parameters
        try {
            camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
        } catch (IOException e) {
            e.printStackTrace();
        }

        Camera.Parameters parameters2 = camera.getParameters();
        parameters2.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setDisplayOrientation(270);

        camera.setParameters(parameters2);


        camera.startPreview();
    }
}
