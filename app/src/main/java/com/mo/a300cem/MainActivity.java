package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    String user;
    LinearLayout LL1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get user if logged-in
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");
        LL1 = findViewById(R.id.LL1);
        if (!TextUtils.isEmpty(user)) {
            //set the properties for button
            Button addTopic = new Button(this);
            addTopic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            addTopic.setText("Start a discussion");
            addTopic.setId(0);
            addTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, AddTopic.class);
                    startActivity(i);
                }
            });
            //add button to the layout
            LL1.addView(addTopic);
        }
        TextView Topic = new TextView(this);
        Topic.setText("▼▼▼ All Topics ▼▼▼");
        Topic.setTextSize(25);
        Topic.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LL1.addView(Topic);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.226/getTopic.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArrayTable = jsonObj.getJSONArray("topic");

                    for (int i = 0; i < jsonArrayTable.length(); i++) {
                        //add a divided line
                        View line = new View(MainActivity.this);
                        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                        line.setBackgroundColor(Color.BLACK);
                        LL1.addView(line);
                        //create button with topic title
                        Button btn = new Button(MainActivity.this);
                        btn.setBackgroundColor(Color.WHITE);
                        btn.setTextColor(Color.BLACK);
                        JSONObject jsonObjRow = jsonArrayTable.getJSONObject(i);
                        final String Cont = jsonObjRow.getString("content");
                        final String date = jsonObjRow.getString("date");
                        final String user = jsonObjRow.getString("user");
                        btn.setText(jsonObjRow.getString("name"));
                        btn.setId(jsonObjRow.getInt("id"));
                        btn.setTextSize(14);
                        final int id_ = btn.getId();
                        LL1.addView(btn);
                        btn = ((Button) findViewById(id_));
                        btn.setHeight(22);
                        //add onclicklistener for each button
                        btn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {

                                Intent i = new Intent(MainActivity.this, ViewCont.class);
                                i.putExtra("id", id_);
                                i.putExtra("FirstCont", Cont);
                                i.putExtra("date", date);
                                i.putExtra("user", user);
                                startActivity(i);

                            }
                        });
                    }
                    View line = new View(MainActivity.this);
                    line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                    line.setBackgroundColor(Color.BLACK);
                    LL1.addView(line);
                } catch (JSONException e) {
                    TextView tV = new TextView(MainActivity.this);
                    tV.setText(e.toString());
                    LL1.addView(tV);
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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.isEmpty(user)) {
            getMenuInflater().inflate(R.menu.mymenu, menu);
        } else {
            getMenuInflater().inflate(R.menu.mymenu3, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            //if no logged-in then login, otherwise logout the user
            if (TextUtils.isEmpty(user)) {
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                finish();
            } else {
                SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                pref.edit()
                        .putString("user", "")
                        .commit();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
