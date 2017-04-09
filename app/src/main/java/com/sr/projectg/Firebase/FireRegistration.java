package com.sr.projectg.Firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sr.projectg.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FireRegistration extends AppCompatActivity {

    FloatingActionButton fabeditphoto;
    ImageView imageregi;
    int CAMERA_PIC_REQUEST = 2;
    int  TAKE_PICTURE=0;
    Camera camera;
    Bitmap bitmap;
    Button editpro_save_button;

    EditText username,userpassword,useremail;
     String name,pass,email,radios,birthdays;
    RadioButton userradio,goverradio;
    DatePicker birthday;



    // [START declare_auth]
    private static FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference db;
    private static final String TAG = "SR-EmailPassword";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance().getReference();



        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        username=(EditText)findViewById(R.id.username);
        userpassword=(EditText)findViewById(R.id.userpassword);
        useremail=(EditText)findViewById(R.id.useremail);
        editpro_save_button=(Button) findViewById(R.id.editpro_save_button);
        userradio=(RadioButton) findViewById(R.id.userradio);
        goverradio=(RadioButton) findViewById(R.id.goverradio);
        birthday=(DatePicker) findViewById(R.id.birthday);




        editpro_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Getting values to store
                name = username.getText().toString().trim();
                pass = userpassword.getText().toString().trim();
                email = useremail.getText().toString().trim();
                createAccount(email, pass);


            }
        });



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

    private void createAccount(final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(FireRegistration.this, "failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Creating Person object
                            FireACCOUNT fireACCOUNT = new FireACCOUNT();
                            if(userradio.isChecked()){

                                radios="user";

                            }
                            if(goverradio.isChecked()){

                                radios="government";


                            }

                            birthdays=birthday.getYear()+"-"+birthday.getMonth()+1+"-"+birthday.getDayOfMonth();

                             //Adding values

                            fireACCOUNT.setAccount_id(mAuth.getCurrentUser().getUid().toString());
                            fireACCOUNT.setAccount_name(name);
                            fireACCOUNT.setAccount_pass(pass);
                            fireACCOUNT.setAccount_email(email);
                            fireACCOUNT.setAccount_type(radios);
                            fireACCOUNT.setAccount_birthday(birthdays);



                            //Storing values to firebase
                            db.child("ACCOUNT").push().setValue(fireACCOUNT);
                            db.addChildEventListener(new ChildEventListener() {
                                public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {

                                    Log.i("SRFire0",name+" Added");
                                }
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                }
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                }
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });


                            Toast.makeText(FireRegistration.this, "Welcome",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        // [START_EXCLUDE]
                        //hideProgress();

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = useremail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            useremail.setError("Required.");
            valid = false;
        } else {
            useremail.setError(null);
        }

        String password = userpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userpassword.setError("Required.");
            valid = false;
        } else {
            userpassword.setError(null);
        }

        return valid;
    }

}