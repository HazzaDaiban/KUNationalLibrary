package com.example.daiban.kunationallibrary;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        STitle = (TextView) findViewById(R.id.STitle);
        Btitle = (EditText) findViewById(R.id.Btitle);
        Bauthor = (EditText) findViewById(R.id.Bauthor);
        keywords = (EditText) findViewById(R.id.Keywords);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(Btitle.getText().toString(),Bauthor.getText().toString(),keywords.getText().toString());
            }
        });
    }
    private void getInfo(final String title, final String author, final String keywwords){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://kulibnational.000webhostapp.com/items.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SearchPage.this, response, Toast.LENGTH_LONG).show();
                        if(response.equals("Item is available")){
                            Intent intent = new Intent(SearchPage.this, com.example.daiban.kunationallibrary.View.class);
                            intent.putExtra("title", title);
                            intent.putExtra("author", author);
                            intent.putExtra("keywords", keywwords);
                            intent.putExtra("available", "yes");
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchPage.this, "Check you internet connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Title",title);
                params.put("Author",author);
                params.put("Keywords",keywwords);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
