package com.sr.projectg.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sr.projectg.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class RegistrationActivity extends AppCompatActivity {

    FloatingActionButton fabeditphoto;
    ImageView imageregi;
    int CAMERA_PIC_REQUEST = 2;
    int  TAKE_PICTURE=0;
    Camera camera;
    Bitmap bitmap;
    Button editpro_save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        imageregi=(ImageView) findViewById(R.id.imageregi);

        imageregi.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
              /*  Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                setResult(RESULT_OK,cameraIntent);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
*/
                galleryAddPic();

            }

        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && data != null)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            try {
                saveImage(bitmap);
                imageregi.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else
        {
            Toast.makeText(getApplicationContext(), "Picture Not taken", Toast.LENGTH_LONG);
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

    private void galleryAddPic() {
        String appname = getString(R.string.app_name);


        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f23 = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");
        Uri contentUri = Uri.fromFile(f23);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}