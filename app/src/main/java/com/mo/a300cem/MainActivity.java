package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get user if logged-in
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");


    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu, menu);
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
