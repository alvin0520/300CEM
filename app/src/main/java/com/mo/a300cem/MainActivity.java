package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                } catch (JSONException e) {

                }
            }
        };

        getTopic getTopic = new getTopic(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(getTopic);

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
