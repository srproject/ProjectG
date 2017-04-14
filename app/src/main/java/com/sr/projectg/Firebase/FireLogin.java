package com.sr.projectg.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sr.projectg.BuildConfig;
import com.sr.projectg.MainActivity;
import com.sr.projectg.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by sr on 4/4/17.
 */

public class FireLogin  extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener  {

    private static final String TAG = "EmailPassword";

    private EditText emailetlog;
    private EditText passwordetlog;
    TextView forget_pass_tv;

    // [START declare_auth]
    private static FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    FireACCOUNT fireACCOUNT;
    FirebaseUser fuser;

    // [END declare_auth]

    // [START declare_auth_listener]
    DatabaseReference db;



    // [END declare_auth_listener]

    // [START Google]

    private static final String TAG2 = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    String id,name,email;
    Object idobj;
    Button email_sign_in_button,email_sign_up_button;
    SignInButton g_sign_in_button;
    // [END Google]

    //{Start Facebook}

    CallbackManager mCallbackManager;
    LoginButton loginButton;

    //{END Facebook}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Views
        emailetlog = (EditText) findViewById(R.id.emailetlog);
        passwordetlog = (EditText) findViewById(R.id.passwordetlog);

        // Buttons
        email_sign_in_button=(Button)findViewById(R.id.email_sign_in_button) ;
        g_sign_in_button=(SignInButton) findViewById(R.id.g_sign_in_button) ;
        email_sign_up_button =(Button) findViewById(R.id.email_sign_up_button) ;
        forget_pass_tv= (TextView) findViewById(R.id.forget_pass_tv);
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);

        // Buttons liss
        email_sign_in_button.setOnClickListener(this);
        g_sign_in_button.setOnClickListener(this);
        email_sign_up_button.setOnClickListener(this);
        forget_pass_tv.setOnClickListener(this);
        loginButton.setOnClickListener(this);




        // [START initialize_auth]
        FirebaseApp.initializeApp(this);



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        fireACCOUNT = new FireACCOUNT();

        // [END initialize_auth]

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END config_signin]
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //{Start Facebook_setup}
        FacebookSdk.sdkInitialize(this);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        mCallbackManager = CallbackManager.Factory.create();
         loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.d("SRFire0", "facebook:onSuccess:" + mAuth.getCurrentUser().getDisplayName());
                       handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {

                        Log.d("SRFire0", "facebook:onCancel");


                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("SRFire0", "facebook:onError", error);

                    }


                });


        //{END Facebook_setup}



        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 if (fuser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:"+"OK" + fuser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                 // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void createAccount(String email, String password) {

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);



        if (!validateForm()) {
            return;
        }

      //  showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful()+"-SR");

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(FireLogin.this, "Failed, Try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (task.isSuccessful()) {
                            Toast.makeText(FireLogin.this, "Welcome",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                         }
                        hideProgress();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public static void signOut() {
        mAuth.signOut();

     }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.email_sign_in_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.email_sign_in_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(FireLogin.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(FireLogin.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailetlog.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailetlog.setError("Required.");
            valid = false;
        } else {
            emailetlog.setError(null);
        }

        String password = passwordetlog.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordetlog.setError("Required.");
            valid = false;
        } else {
            passwordetlog.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_up_button) {
            //createAccount(emailetlog.getText().toString(), passwordetlog.getText().toString());
            Intent intent = new Intent(FireLogin.this,FireRegistration.class);
            startActivity(intent);
        } else if (i == R.id.email_sign_in_button) {
            signIn(emailetlog.getText().toString(), passwordetlog.getText().toString());
        }
        else if (i == R.id.g_sign_in_button) {

            signInG();

        }else if (i == R.id.fb_login_button) {

            if(loginButton.isActivated()){

            }
        }


       //else if (i == R.id.verify_email_button) {
            //sendEmailVerification();
        //}
    }

    public void hideProgress(){




    }
    public void showProgress(){




    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RC_SIGN_IN) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

            id = acid();
            name = fuser.getDisplayName().toString();
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            idobj = id;
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);


                id=acid();
                name=account.getDisplayName().toString();
                email=account.getEmail().toString();
                idobj= id;

                Toast.makeText(FireLogin.this,account.getId().toString()+"-"+account.getDisplayName().toString(),
                        Toast.LENGTH_LONG).show();


            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
              //  updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG2, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
       // showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG2, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.



                        if (!task.isSuccessful()) {
                            Log.w(TAG2, "signInWithCredential", task.getException());
                            Toast.makeText(FireLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (task.isSuccessful()){




                          //  Log.i("SRFire0", "mawgod-"+id);

                            Firebase.setAndroidContext(FireLogin.this);



                            final Firebase ref = new Firebase("https://projectg-70ce9.firebaseio.com/ACCOUNT");
                            Query query = ref.orderByChild("account_email").equalTo(email);

                            query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null && FirebaseAuth.getInstance().getCurrentUser() != null){

                                        Log.i("SRFire0", "mawgod-"+name);


                                    }
                                    else {

                                        fireACCOUNT.setAccount_id(id);
                                        fireACCOUNT.setAccount_name(name);
                                        //fireACCOUNT.setAccount_pass(pass);
                                        fireACCOUNT.setAccount_email(email);
                                        fireACCOUNT.setAccount_type("user");
                                        //fireACCOUNT.setAccount_birthday(task.getResult().getUser().g);/fireACCOUNT.setAccount_photo(task.getResult().getUser().getPhotoUrl().toString());

                                        db.child("ACCOUNT").push().setValue(fireACCOUNT);
                                        Log.i("SRFire0", "Kolo Tamam Ya "+name+" Basha" );



                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });



                            /*
                            db.child("ACCOUNT").addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                      Log.e("SRFire0", "======="+postSnapshot.child("account_id").getValue());
                                      if (postSnapshot.child("account_id").getValue().equals(idobj)){

                                          fireACCOUNT.setAccount_id(id);
                                          fireACCOUNT.setAccount_name(name);
                                          //fireACCOUNT.setAccount_pass(pass);
                                          fireACCOUNT.setAccount_email(email);
                                          fireACCOUNT.setAccount_type("user");
                                          //fireACCOUNT.setAccount_birthday(task.getResult().getUser().g);/fireACCOUNT.setAccount_photo(task.getResult().getUser().getPhotoUrl().toString());

                                          db.child("ACCOUNT").push().setValue(fireACCOUNT);
                                          Log.i("SRFire0", "Kolo Tamam Ya "+name+" Basha" );


                                      }


                                  }


                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError) {

                              }
                          });
                            */

                                    // fireACCOUNT.setAccount_id(task.getResult().getUser().getUid().toString());
                                    //fireACCOUNT.setAccount_name(task.getResult().getUser().getDisplayName().toString());
                                    ////fireACCOUNT.setAccount_pass(pass);
                                    // fireACCOUNT.setAccount_email(task.getResult().getUser().getEmail().toString());
                                    //fireACCOUNT.setAccount_type("user");
                                    //    //fireACCOUNT.setAccount_birthday(task.getResult().getUser().g);
                                    //fireACCOUNT.setAccount_photo(task.getResult().getUser().getPhotoUrl().toString());

//                            db.child("ACCOUNT").push().setValue(fireACCOUNT);


                        }


                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthInvalidUserException) {
                    Log.i("SRFire0", "BoooooooooooooM");
                }

            }
        }) ;
    }
    // [END auth_with_google]


    // [START signin]
    private void signInG() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private boolean checkAccountEmailExistInFirebase(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final boolean[] b = new boolean[1];
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                b[0] = !task.getResult().getProviders().isEmpty();
            }
        });
        return b[0];
    }


    // [END signin]


    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                            String gg=token.getToken().toString();
                                                    Log.i("SRFire0",gg);
                            if(gg==null){
                                Log.i("SRFire0","to null");



                            }

                        if (task.isSuccessful()){

                           // g_sign_in_button.setEnabled(false);

                            Firebase.setAndroidContext(FireLogin.this);



                            final Firebase ref = new Firebase("https://projectg-70ce9.firebaseio.com/ACCOUNT");
                            Query query = ref.orderByChild("account_email").equalTo(email);

                            query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null && FirebaseAuth.getInstance().getCurrentUser() != null){

                                        Log.i("SRFire0", "mawgod-"+name);


                                    }
                                    else {

                                        fireACCOUNT.setAccount_id(id);
                                        fireACCOUNT.setAccount_name(name);
                                        //fireACCOUNT.setAccount_pass(pass);
                                        fireACCOUNT.setAccount_email(email);
                                        fireACCOUNT.setAccount_type("user");
                                        //fireACCOUNT.setAccount_birthday(task.getResult().getUser().g);/fireACCOUNT.setAccount_photo(task.getResult().getUser().getPhotoUrl().toString());

                                        db.child("ACCOUNT").push().setValue(fireACCOUNT);
                                        Log.i("SRFire0", "Kolo Tamam Ya "+name+" Basha" );



                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });



                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.


                        // ...
                    }
                });
    }

    public void updateUIH(){

        emailetlog.setEnabled(false);
        passwordetlog.setEnabled(false);
        email_sign_in_button.setEnabled(false);
        email_sign_up_button.setEnabled(false);
        loginButton.setEnabled(false);
        g_sign_in_button.setEnabled(false);

    }
    public void updateUIS(){

        emailetlog.setEnabled(true);
        passwordetlog.setEnabled(true);
        email_sign_in_button.setEnabled(true);
        email_sign_up_button.setEnabled(true);
        loginButton.setEnabled(true);
        g_sign_in_button.setEnabled(true);

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
