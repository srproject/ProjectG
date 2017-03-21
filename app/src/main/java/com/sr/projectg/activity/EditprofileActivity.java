package com.sr.projectg.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sr.projectg.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditprofileActivity extends Activity {

    FloatingActionButton fabeditimageadd;
    ImageView imageVieweditpro;
    int CAMERA_PIC_REQUEST = 2;
    int  TAKE_PICTURE=0;
    Camera camera;
    Bitmap bitmap;
    Button editpro_save_button;
    private static int RESULT_LOAD_IMAGE = 1;
   static final int REQUEST_VIDEO_CAPTURE = 1;
    int PLACE_PICKER_REQUEST=1;

    int select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_layout);

        editpro_save_button=(Button)findViewById(R.id.editpro_save_button);
        imageVieweditpro=(ImageView) findViewById(R.id.imageVieweditpro);
        fabeditimageadd = (FloatingActionButton) findViewById(R.id.fabeditimageadd);





            /*

        fabeditphoto.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)

            {

           Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                setResult(RESULT_OK,cameraIntent);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                //
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);

                //selectImage();
            }

        });


                    */

    }



    private void selectImage() {
         final CharSequence[] items = { "Take Photo", "Choose from Library"};

        TextView title = new TextView(getApplicationContext());
        title.setText("Add Photo");
         title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditprofileActivity.this);



        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    select=1;
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    setResult(RESULT_OK,cameraIntent);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);


                } else if (items[item].equals("Choose from Library")) {
                    select=2;
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                } else if (items[item].equals("Cancel")) {
                    select=0;
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }




    public boolean hascam(){

        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





            if( requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && data != null)
            {
                bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    saveImage(bitmap);
                    imageVieweditpro.setImageBitmap(bitmap);
                    Log.i("SR","m1");

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Picture Not taken", Toast.LENGTH_LONG);
            }


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)

        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};


            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Log.i("SR", "m2f");


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                saveImage(bmp);
                imageVieweditpro.setImageBitmap(bmp);
                Log.i("SR", "m2");

            } catch (IOException e) {
                e.printStackTrace();
        }


        }

           }

    //check folder
    public void checkfolder(){
        String appname = getString(R.string.app_name);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/");

        if (!folder.exists()) {
            folder.mkdirs();

        }
    }



    //saveImage

    public void saveImage(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
        String appname = getString(R.string.app_name);
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/");
        if (!folder.exists()) {
            folder.mkdirs();
            Toast.makeText(getApplicationContext(), "Folder Maked", Toast.LENGTH_SHORT).show();

        } else {
            File f2 = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");


            f2.createNewFile();
            FileOutputStream fo = new FileOutputStream(f2);
            fo.write(bytes.toByteArray());
            fo.close();
            Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();


        }
    }
    //END//saveImage


    //load Image From Storage
    private void loadImageFromStorage( )
    {
        String appname =  getString(R.string.app_name);


        try {

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");
            if(f.exists()) {

                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                imageVieweditpro.setImageBitmap(b);
            }
            else {



            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    //END//load Image From Storage


    @Override
    public void onResume() {
        super.onResume();

        loadImageFromStorage();


    }

    public void fabeditonclick(View v) {


/*
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        setResult(RESULT_OK,cameraIntent);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        //
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
*/
        selectImage();

//button Clicked
    }

    

 }

