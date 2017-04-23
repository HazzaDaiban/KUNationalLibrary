package com.example.daiban.kunationallibrary;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchPage extends AppCompatActivity {

    private TextView STitle;
    private EditText Btitle, Bauthor, keywords;
    private Button search;
    private String mobilenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        STitle = (TextView) findViewById(R.id.STitle);
        Btitle = (EditText) findViewById(R.id.Btitle);
        Bauthor = (EditText) findViewById(R.id.Bauthor);
        keywords = (EditText) findViewById(R.id.Keywords);
        search = (Button) findViewById(R.id.search);
        mobRequest();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(Btitle.getText().toString(), Bauthor.getText().toString(), keywords.getText().toString());
            }
        });
    }

    private void getInfo(final String title, final String author, final String keywwords) {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "https://kulibnational.000webhostapp.com/items.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SearchPage.this, response, Toast.LENGTH_LONG).show();
                        if (response.equals("Item is available")) {
                            Intent intent = new Intent(SearchPage.this, com.example.daiban.kunationallibrary.View.class);
                            intent.putExtra("title", title);
                            intent.putExtra("author", author);
                            intent.putExtra("keywords", keywwords);
                            intent.putExtra("available", "yes");
                            startActivity(intent);
                        }
                        else {
                            ShowDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchPage.this, "Check you internet connectivity", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Title", title);
                params.put("Author", author);
                params.put("Keywords", keywwords);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    // Action Bar
    @Override
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
            Intent intent = new Intent(SearchPage.this, Profile.class);
            intent.putExtra("User", getIntent().getStringExtra("Username"));
            intent.putExtra("Pass", getIntent().getStringExtra("Password"));
            intent.putExtra("Mob", mobilenum);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
// Dialog
    public void ShowDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Item is not found, Would you like to request it ? ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(SearchPage.this, request.class);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    private void mobRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://kulibnational.000webhostapp.com/mobreq.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mobilenum=response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchPage.this, "error fetching mobile number", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user",getIntent().getStringExtra("Username"));
                params.put("pass",getIntent().getStringExtra("Password"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}