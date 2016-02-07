package com.mevinto.onroad.onroad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class CreateActivity extends Activity {
    com.mevinto.onroad.onroad.RoundedImageView image;
    String fbid;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        new CallApi().execute();
        image = (com.mevinto.onroad.onroad.RoundedImageView) findViewById(R.id.imageView3);
        sharedpreferences = getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
    }


    class CallApi extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                //
                Picasso.with(CreateActivity.this)
                        .load(sharedpreferences.getString("FBIMG",""))
                        .placeholder(R.mipmap.user)   // optional
                        .error(R.mipmap.ic_launcher)      // optional
                        .into(image);

            ((TextView) findViewById(R.id.textView2)).setText("" + sharedpreferences.getString("NAME",""));

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
