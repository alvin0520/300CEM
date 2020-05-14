package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


public class ViewCont extends AppCompatActivity {
    String Topicuser, user, TopicDate, Cont, Topic;
    int Topicid;
    LinearLayout LL2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cont);

        Intent ReIntent = getIntent();
        Topicid = ReIntent.getIntExtra("id", 0);
        Topicuser = ReIntent.getStringExtra("user");
        TopicDate = ReIntent.getStringExtra("date");
        Cont = ReIntent.getStringExtra("FirstCont");
        Topic = ReIntent.getStringExtra("Topic");
        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("user", "");


        LL2 = findViewById(R.id.LL2);
        LL2.setOrientation(LinearLayout.VERTICAL);
        //add TopicTitle, Topicuser and TopicDate
        TextView TextViewTop = new TextView(this);
        TextViewTop.setText(Topic);
        TextViewTop.setTextSize(23);
        TextViewTop.setTextColor(Color.BLACK);
        LL2.addView(TextViewTop);

        View line = new View(ViewCont.this);
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        line.setBackgroundColor(Color.rgb(250, 250, 250));
        LL2.addView(line);

        TextView TextViewuser = new TextView(this);
        TextViewuser.setText("Post By " +  Topicuser + " at " + TopicDate);
        TextViewuser.setTextColor(Color.BLACK);
        TextViewuser.setTextSize(12);
        LL2.addView(TextViewuser);

        final View line2 = new View(ViewCont.this);
        line2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4));
        line2.setBackgroundColor(Color.rgb(115, 115, 115));
        LL2.addView(line2);

        //add the first comment by the topic user post
        TextView TextViewCont = new TextView(this);
        TextViewCont.setText(Cont);
        TextViewCont.setTextSize(18);
        TextViewCont.setTextColor(Color.BLACK);
        LL2.addView(TextViewCont);

        View line3 = new View(ViewCont.this);
        line3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        line3.setBackgroundColor(Color.rgb(250, 250, 250));
        LL2.addView(line3);

        TextView TextViewComt = new TextView(this);
        String Comt = "▼▼▼ View Comment ▼▼▼";
        TextViewComt.setText(Comt);
        TextViewComt.setTextSize(15);
        TextViewComt.setTextColor(Color.BLACK);
        TextViewComt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LL2.addView(TextViewComt);

        View line4 = new View(ViewCont.this);
        line4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        line4.setBackgroundColor(Color.rgb(250, 250, 250));
        LL2.addView(line4);


        //get all comment by other user by using volley
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    boolean success = jsonObj.getBoolean("success");
                    if (success) {
                        JSONArray jsonArrayTable = jsonObj.getJSONArray("Content");
                        for (int i = 0; i < jsonArrayTable.length(); i++) {
                            //show the comment of other user
                            JSONObject jsonObjRow = jsonArrayTable.getJSONObject(i);
                            String Content = jsonObjRow.getString("content");
                            String Cuser = jsonObjRow.getString("user");
                            String Cdate = jsonObjRow.getString("date");
                            String CLocate = jsonObjRow.getString("location");
                            TextView TextViewuser = new TextView(ViewCont.this);
                            TextViewuser.setText(Cuser + " at " + Cdate + " in " + CLocate);
                            TextViewuser.setTextColor(Color.BLACK);
                            TextViewuser.setTextSize(12);
                            LL2.addView(TextViewuser);

                            TextView txtComment = new TextView(ViewCont.this);
                            txtComment.setText(Content);
                            txtComment.setTextColor(Color.BLACK);
                            LL2.addView(txtComment);

                            View line5 = new View(ViewCont.this);
                            line5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
                            line5.setBackgroundColor(Color.rgb(220, 220, 220));
                            LL2.addView(line5);
                        }
                    } else {
                        Toast.makeText(ViewCont.this,"There is no comment yet~",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(ViewCont.this,"Receive data fail, please check and try again",Toast.LENGTH_LONG).show();
                }
            }
        };

        ViewC ViewC = new ViewC(String.valueOf(Topicid), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewCont.this);
        queue.add(ViewC);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add comment button if the user has logged-in
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
            i.putExtra("Cont", Cont);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
