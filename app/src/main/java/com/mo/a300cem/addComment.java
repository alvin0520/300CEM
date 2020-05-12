package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addComment extends AppCompatActivity {
    String user, Sid,Tuser, TopicDate, FirstCont;
    int Topicid;
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
        EditText edtC;
        edtC = findViewById(R.id.editText);
        final String Comment = edtC.getText().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        // Request a string response from the provided URL.
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

        addCommentToServer addCommentToServer = new addCommentToServer(Sid,user,Comment,formattedDate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(addComment.this);
        queue.add(addCommentToServer);


    }

}
