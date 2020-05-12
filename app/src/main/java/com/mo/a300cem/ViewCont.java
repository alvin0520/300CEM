package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ViewCont extends AppCompatActivity {
    String Topicuser, user, TopicDate, FirstCont;
    int Topicid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cont);

        Intent ReIntent = getIntent();
        Topicid = ReIntent.getIntExtra("id", 0);
        Topicuser = ReIntent.getStringExtra("user");
        TopicDate = ReIntent.getStringExtra("date");
        FirstCont = ReIntent.getStringExtra("Cont");
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.isEmpty(user)) {

        } else {
            getMenuInflater().inflate(R.menu.mymenu4, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton4) {
            Intent i = new Intent(ViewCont.this, addComment.class);
            i.putExtra("Topicid", Topicid);
            i.putExtra("Tuser", Topicuser);
            i.putExtra("date", TopicDate);
            i.putExtra("Cont", FirstCont);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
