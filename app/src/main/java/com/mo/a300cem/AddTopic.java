package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTopic extends AppCompatActivity {
    EditText edtTitle, edtCont;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        //get user name
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");
        edtTitle = findViewById(R.id.edtTitle);
        edtCont = findViewById(R.id.edtCont);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String strTitle, strCont;
        if (id == R.id.mybutton2) {
            //get topic title and content
            strTitle = edtTitle.getText().toString();
            strCont = edtCont.getText().toString();
            //get date now
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        //check if the insert data is success
                        boolean success = jsonObj.getBoolean("success");
                        if (success) {
                            //success and return
                            Toast.makeText(AddTopic.this,"Post success!",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddTopic.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(AddTopic.this,"Post Fail!Try again!",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddTopic.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {

                    }
                }
            };
            //POST detail information and insert data to db
            InsertTopic InsertTopic = new InsertTopic(user,formattedDate,strTitle,strCont, responseListener);
            RequestQueue queue = Volley.newRequestQueue(AddTopic.this);
            queue.add(InsertTopic);
        }

        return super.onOptionsItemSelected(item);
    }
}
