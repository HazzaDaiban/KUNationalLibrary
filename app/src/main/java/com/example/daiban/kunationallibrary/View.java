package com.example.daiban.kunationallibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class View extends AppCompatActivity {
private TextView Btitle, BAuthor, Availability, branch;
private Button Borrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Borrow = (Button) findViewById(R.id.button);
        Btitle = (TextView) findViewById(R.id.Btitle);
        branch = (TextView) findViewById(R.id.branch);
        BAuthor = (TextView) findViewById(R.id.BAuthor);
        Availability = (TextView) findViewById(R.id.Availability);
        Btitle.setText("Title: " + getIntent().getStringExtra("title") );
        BAuthor.setText("Author: " + getIntent().getStringExtra("author") );
        Availability.setText("Available: " + getIntent().getStringExtra("available") );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://kulibnational.000webhostapp.com/returnbr.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        branch.setText("Branch: " + response );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Title",getIntent().getStringExtra("title"));
                params.put("Author",getIntent().getStringExtra("author"));
                params.put("Keywords",getIntent().getStringExtra("keywords"));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Borrow.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(View.this,"Item has been booked please proceed to the branch",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Action bar icon
    public boolean onCreateOptionsMenu(Menu menu) {
        // add profile icon to the action bar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    // Handle action bar item clicks here.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            Intent intent = new Intent(View.this,Profile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
