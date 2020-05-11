package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ViewCont extends AppCompatActivity {
    String Topicuser, user;
    int Topicid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cont);

        Intent ReIntent = getIntent();
        Topicid = ReIntent.getIntExtra("id", 0);
        Topicuser = ReIntent.getStringExtra("user");
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu4, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton4) {
            Intent i = new Intent(ViewCont.this, addComment.class);
            i.putExtra("Topicid", Topicid);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
