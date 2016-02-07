package com.mevinto.onroad.onroad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class LoginActivity extends Activity {


    FacebookCallback callback;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_fb);
        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                String name=profile.getFirstName()+" "+profile.getLastName();
                Uri uri=profile.getProfilePictureUri(30,30);
                String fb_id=profile.getId();
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("NAME",name);
                i.putExtra("FBID",fb_id);
                Log.e("URL image",""+uri);
                startActivity(i);
                finish();
//                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
 }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);

//        ((LinearLayout)findViewById(R.id.facebook_login)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            publics void onClick(View v) {
////                Intent i=new Intent(LoginActivity.this,MainActivity.class);
////                startActivity(i);
////                finish();
//
//
//            }
//        });
//
//        ((LinearLayout)findViewById(R.id.google_login)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            publics void onClick(View v) {
//                Intent i=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }
}
