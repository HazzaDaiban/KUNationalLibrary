package com.example.daiban.kunationallibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    }
}
