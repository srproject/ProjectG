package com.sr.projectg.Firebase;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sr.projectg.R;
import com.sr.projectg.activity.EditprofileActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FireRegistration extends AppCompatActivity {

    FloatingActionButton fabeditphoto;
    ImageView imageregi;
    int CAMERA_PIC_REQUEST = 2;
    private static int RESULT_LOAD_IMAGE = 1;

    Bitmap bitmapsr;
    Button editpro_save_button;

    EditText username,userpassword,useremail;
    String name,pass,email,radios,birthdays,getDownloadUrls;

    String permission_inter,permission_event,permission_comm,permission_share,
            permission_like,permission_camera,permission_profile,permission_editprofile,
            permission_map,permission_noti,permission_showevent,permission_make_event_solve
    ,permission_block,permission_report,permission_blocku_admin,permission_deleteu_admin
    ,permission_photo_hide,permission_photo_hide_admin,permission_photo_delete;
    RadioButton userradio,goverradio;
    DatePicker birthday;



    // [START declare_auth]
    private static FirebaseAuth mAuth;
    private static FirebaseStorage storage;
    StorageReference userprofile;
    StorageReference storageRef;
    FireACCOUNT fireACCOUNT;

    // [END declare_auth]

    // [START declare_auth_listener]
     DatabaseReference db;
    private static final String TAG = "SRFire0";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance().getReference();



        mAuth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        // [END initialize_auth]

        // Create a storage reference from our app
          storageRef = storage.getReference();


// Create a reference to 'images/mountains.jpg'
        //StorageReference mountainImagesRef = storageRef.child("images/profile.jpg");

// While the file names are the same, the references point to different files
       // mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        //mountainsRef.getPath().equals(mountainImagesRef.getPath());


        username=(EditText)findViewById(R.id.username);
        userpassword=(EditText)findViewById(R.id.userpassword);
        useremail=(EditText)findViewById(R.id.useremail);
        editpro_save_button=(Button) findViewById(R.id.editpro_save_button);
        userradio=(RadioButton) findViewById(R.id.userradio);
        goverradio=(RadioButton) findViewById(R.id.goverradio);
        birthday=(DatePicker) findViewById(R.id.birthday);

        userradio.setChecked(true);


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
                selectImage();

            }

        });



        useremail.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus) {
                    if (!validateForm()) {
                        return;
                    }
                }

            }
        });




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
                FireRegistration.this);



        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                     Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    setResult(RESULT_OK,cameraIntent);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);


                } else if (items[item].equals("Choose from Library")) {
                     Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                } else if (items[item].equals("Cancel")) {
                     dialog.dismiss();
                }
            }
        });
        builder.show();
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
                             fireACCOUNT = new FireACCOUNT();
                            // Create a reference to "userprofile.jpg"
                            userprofile = storageRef.child("userprofile/"+email+".jpg");

                            if(userradio.isChecked()){

                                radios="user";
                                         permission_inter="1";
                                         permission_event="1";
                                         permission_comm="1";
                                         permission_share="1";
                                         permission_like="1";
                                         permission_camera="1";
                                         permission_profile="1";
                                         permission_editprofile="1";
                                         permission_map="1";
                                         permission_noti ="1";
                                         permission_showevent ="1";
                                         permission_make_event_solve="0";
                                         permission_block="1";
                                         permission_report ="0";
                                         permission_blocku_admin="0";
                                         permission_deleteu_admin="0";
                                         permission_photo_hide ="1";
                                         permission_photo_hide_admin ="0";
                                         permission_photo_delete="0";

                            }
                            if(goverradio.isChecked()){

                                radios="government";
                                permission_inter="0";
                                permission_event="0";
                                permission_comm="1";
                                permission_share="0";
                                permission_like="0";
                                permission_camera="0";
                                permission_profile="1";
                                permission_editprofile="1";
                                permission_map="1";
                                permission_noti ="1";
                                permission_showevent ="1";
                                permission_make_event_solve="1";
                                permission_block="0";
                                permission_report ="1";
                                permission_blocku_admin="0";
                                permission_deleteu_admin="0";
                                permission_photo_hide ="0";
                                permission_photo_hide_admin ="0";
                                permission_photo_delete="0";


                            }

                            birthdays=birthday.getYear()+"-"+birthday.getMonth()+1+"-"+birthday.getDayOfMonth();




                        if (bitmapsr!=null) {

                            Bitmap bitmap = bitmapsr;
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = userprofile.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads

                                    Log.i("SRFire0", name + " photo ERORR");

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Uri uploadsession = taskSnapshot.getDownloadUrl();
                                    Log.i("SRFire0", uploadsession.toString() + " photo Added");
                                    getDownloadUrls = uploadsession.toString();


                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Uri uploadsession2 =   task.getResult().getDownloadUrl();

                                    Log.i("SRFire0", uploadsession2.toString() + " photo Added2");
                                    getDownloadUrls = uploadsession2.toString();

                                    //Adding values

                                    fireACCOUNT.setAccount_id(acid());
                                    fireACCOUNT.setAccount_name(name);
                                    fireACCOUNT.setAccount_pass(pass);
                                    fireACCOUNT.setAccount_email(email);
                                    fireACCOUNT.setAccount_type(radios);
                                    fireACCOUNT.setAccount_birthday(birthdays);

                                    fireACCOUNT.setPermission_inter(permission_inter);
                                    fireACCOUNT.setPermission_event(permission_event);
                                    fireACCOUNT.setPermission_comm(permission_comm);
                                    fireACCOUNT.setPermission_share(permission_share);
                                    fireACCOUNT.setPermission_like(permission_like);
                                    fireACCOUNT.setPermission_profile(permission_profile);
                                    fireACCOUNT.setPermission_editprofile(permission_editprofile);
                                    fireACCOUNT.setPermission_map(permission_map);
                                    fireACCOUNT.setPermission_noti(permission_noti);
                                    fireACCOUNT.setPermission_showevent(permission_showevent);
                                    fireACCOUNT.setPermission_make_event_solve(permission_make_event_solve);
                                    fireACCOUNT.setPermission_block(permission_block);
                                    fireACCOUNT.setPermission_report(permission_report);
                                    fireACCOUNT.setPermission_blocku_admin(permission_blocku_admin);
                                    fireACCOUNT.setPermission_deleteu_admin(permission_deleteu_admin);
                                    fireACCOUNT.setPermission_photo_hide(permission_photo_hide);
                                    fireACCOUNT.setPermission_photo_hide_admin(permission_photo_hide_admin);
                                    fireACCOUNT.setPermission_photo_delete(permission_photo_delete);



                                    fireACCOUNT.setAccount_photo(getDownloadUrls);

                                    db.child("ACCOUNT").push().setValue(fireACCOUNT);

                                }
                            });

                        }
                        else {


                            //Adding values

                            fireACCOUNT.setAccount_id(acid());
                            fireACCOUNT.setAccount_name(name);
                            fireACCOUNT.setAccount_pass(pass);
                            fireACCOUNT.setAccount_email(email);
                            fireACCOUNT.setAccount_type(radios);
                            fireACCOUNT.setAccount_birthday(birthdays);

                            fireACCOUNT.setPermission_inter(permission_inter);
                            fireACCOUNT.setPermission_event(permission_event);
                            fireACCOUNT.setPermission_comm(permission_comm);
                            fireACCOUNT.setPermission_share(permission_share);
                            fireACCOUNT.setPermission_like(permission_like);
                            fireACCOUNT.setPermission_profile(permission_profile);
                            fireACCOUNT.setPermission_editprofile(permission_editprofile);
                            fireACCOUNT.setPermission_map(permission_map);
                            fireACCOUNT.setPermission_noti(permission_noti);
                            fireACCOUNT.setPermission_showevent(permission_showevent);
                            fireACCOUNT.setPermission_make_event_solve(permission_make_event_solve);
                            fireACCOUNT.setPermission_block(permission_block);
                            fireACCOUNT.setPermission_report(permission_report);
                            fireACCOUNT.setPermission_blocku_admin(permission_blocku_admin);
                            fireACCOUNT.setPermission_deleteu_admin(permission_deleteu_admin);
                            fireACCOUNT.setPermission_photo_hide(permission_photo_hide);
                            fireACCOUNT.setPermission_photo_hide_admin(permission_photo_hide_admin);
                            fireACCOUNT.setPermission_photo_delete(permission_photo_delete);


                            fireACCOUNT.setAccount_photo(getDownloadUrls);

                            db.child("ACCOUNT").push().setValue(fireACCOUNT);


                            Toast.makeText(FireRegistration.this, "Please add photo of your profile",
                                    Toast.LENGTH_SHORT).show();

                        }



                            //Storing values to firebase
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
                            //finish();

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


        if (TextUtils.isEmpty(useremail.getText().toString()) ||isEmailValid(useremail.getText().toString())==false ) {
            useremail.setError("Enter email correctly.");
             valid = false;

        } else {
            useremail.setError(null);
        }


        String usernames = username.getText().toString();
        if (TextUtils.isEmpty(usernames) ) {

            username.setError("Enter user name");
            valid = false;

        } else {
            username.setError(null);
        }

        String password = userpassword.getText().toString();
        if (TextUtils.isEmpty(password) || userpassword.length()<6 ) {

            userpassword.setError("Enter at least 6 letters or numbers.");
             valid = false;
        } else {
            userpassword.setError(null);
        }



        return valid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





        if( requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && data != null)
        {
            bitmapsr = (Bitmap) data.getExtras().get("data");

            imageregi.setImageBitmap(bitmapsr);




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


            bitmapsr = null;
            try {
                bitmapsr = getBitmapFromUri(selectedImage);
                imageregi.setImageBitmap(bitmapsr);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }

    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private String acid(){


        Random r = new Random();
        int randomNo = r.nextInt(1000+1);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH)+1;
        int mon = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        int hour= c.get(Calendar.HOUR_OF_DAY);
        int Min= c.get(Calendar.MINUTE);
        int sec= c.get(Calendar.SECOND);


        String evid = "ac" + randomNo+day+mon+year+hour+Min+sec;

        return evid;
    }



}
