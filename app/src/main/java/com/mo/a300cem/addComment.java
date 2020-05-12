package com.mo.a300cem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addComment extends AppCompatActivity {
    String user, Sid,Tuser, TopicDate, FirstCont, Locate;
    int Topicid;
    private LocationManager locationManager;
    private String commadStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        Intent ReIntent = getIntent();
        Topicid = ReIntent.getIntExtra("Topicid", 0);
        Sid = String.valueOf(Topicid);
        Tuser = ReIntent.getStringExtra("Tuser");
        TopicDate = ReIntent.getStringExtra("date");
        FirstCont = ReIntent.getStringExtra("Cont");
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");
        commadStr = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(addComment.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            return;
        }
        //locationManager.requestLocationUpdates(commadStr, 1000,0,locationListener);
        Location location = locationManager.getLastKnownLocation(commadStr);
        if (location != null){
            getAddress(location.getLatitude(), location.getLongitude());
        } else {
            Toast.makeText(addComment.this,"GPS has problem!",Toast.LENGTH_LONG).show();
            Locate = "unknown";
        }
    }


    @Override
    public void onBackPressed(){
        Intent i = new Intent(addComment.this, ViewCont.class);
        i.putExtra("id", Topicid);
        i.putExtra("FirstCont", FirstCont);
        i.putExtra("date", TopicDate);
        i.putExtra("user", Tuser);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void addC(View view){
        //check GPS permission
        if (ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(addComment.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            return;
        }
        //locationManager.requestLocationUpdates(commadStr, 1000,0,locationListener);
        PostComment();
    }

    public void PostComment(){
        commadStr = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(addComment.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(addComment.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            return;
        }
        //locationManager.requestLocationUpdates(commadStr, 1000,0,locationListener);
        Location location = locationManager.getLastKnownLocation(commadStr);
        if (location != null){
            getAddress(location.getLatitude(), location.getLongitude());
        } else {
            Toast.makeText(addComment.this,"GPS has problem!",Toast.LENGTH_LONG).show();
            Locate = "unknown";
        }
        EditText edtC;
        edtC = findViewById(R.id.editText);
        final String Comment = edtC.getText().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    boolean success = jsonObj.getBoolean("success");
                    if (success) {
                        Toast.makeText(addComment.this,"Post Comment Success!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(addComment.this, ViewCont.class);
                        i.putExtra("id", Topicid);
                        i.putExtra("FirstCont", FirstCont);
                        i.putExtra("date", TopicDate);
                        i.putExtra("user", Tuser);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(addComment.this,"Post Fail",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(addComment.this,"Post Fail",Toast.LENGTH_LONG).show();
                }
            }
        };

        addCommentToServer addCommentToServer = new addCommentToServer(Sid,user,Comment,formattedDate,Locate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(addComment.this);
        queue.add(addCommentToServer);
    }

    /*public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getAddress(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };*/


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001: {
                //PostComment();

            }
        }
    }

    public void getAddress(double lat, double lng) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyAm_iuadBjxo3D6K_yl8YyuCYZ5jq_ZeLg";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrayResult = jsonObject.getJSONArray("results");
                            JSONArray arrComponent = arrayResult.getJSONObject(0).getJSONArray("address_components");

                            for (int i = 0; i < arrComponent.length(); i++) {

                                JSONArray arrType = arrComponent.getJSONObject(i).getJSONArray("types");

                                for (int j = 0; j < arrType.length(); j++) {

                                    if (arrType.getString(j).equals("neighborhood")) {
                                        final String longname = arrComponent.getJSONObject(i).getString("long_name");
                                        //textView.setText(longname);
                                        Locate = longname;
                                    }
                                }
                            }

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
