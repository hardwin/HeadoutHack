package com.mevinto.onroad.onroad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


public class MainActivity extends Activity {

    com.mevinto.onroad.onroad.RoundedImageView image;
    String fbid;
    LinearLayout create_button;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
        image = (com.mevinto.onroad.onroad.RoundedImageView) findViewById(R.id.imageView3);
        try {
            String name = getIntent().getExtras().getString("NAME");
            fbid = getIntent().getExtras().getString("FBID");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("NAME", ""+name);
            editor.putString("FBID", ""+fbid);
            editor.commit();
            ((TextView) findViewById(R.id.textView2)).setText("" + name);
            Log.e("id", "" + fbid);

            new CallApi().execute();

//            ((ImageView)findViewById(R.id.imageView2))
        } catch (Exception e) {
            Log.e("exe", "" + e);
        }
        ((LinearLayout) findViewById(R.id.create_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(i);
            }
        });
//
        ((LinearLayout) findViewById(R.id.search_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });


    }

    class CallApi extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject root = new JSONObject(s);
                JSONObject obj = root.getJSONObject("data");
                String url = obj.optString("url");

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FBIMG", ""+url);
                editor.commit();
                //
                Picasso.with(MainActivity.this)
                        .load(url)
                        .placeholder(R.mipmap.user)   // optional
                        .error(R.mipmap.ic_launcher)      // optional
                        .into(image);
            } catch (Exception e) {
                Log.e("ex", "" + e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String response = GetResponse("http://graph.facebook.com/" + fbid + "/picture?redirect=false");
            return response;
        }
    }

    public String GetResponse(String url) {
        HttpClient Client = new DefaultHttpClient();

        // Create URL string


        //Log.i("httpget", URL);
        String SetServerString = "";
        try {


            // Create Request to server and get response

            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            SetServerString = Client.execute(httpget, responseHandler);


        } catch (Exception e) {
            SetServerString = "err";
        }

        return SetServerString;

    }
}