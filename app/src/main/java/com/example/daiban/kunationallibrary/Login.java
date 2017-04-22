package com.example.daiban.kunationallibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private Button Register, Sign_in;
    private EditText UserN, PassN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register = (Button) findViewById(R.id.Rb);
        Sign_in = (Button) findViewById(R.id.Sb);
        UserN = (EditText) findViewById(R.id.UserN);
        PassN = (EditText) findViewById(R.id.PassN);

        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin(UserN.getText().toString(),PassN.getText().toString());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDialog();
            }
        });
    }

    private void getLogin(final String user, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://kulibnational.000webhostapp.com/users.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("g", response);
                        if (response != null) {
                            if (Integer.valueOf(response) == 1) {
                                Intent intent = new Intent(Login.this, SearchPage.class);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(Login.this, "Incorrect information", Toast.LENGTH_LONG).show();
                        }
                    }
                },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Check you internet connectivity", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,user);
                params.put(KEY_PASSWORD,password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void registerDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_register, null);
        dialogBuilder.setView(dialogView);
        final EditText NTuser = (EditText) dialogView.findViewById(R.id.NTuser);
        final EditText NTpass = (EditText) dialogView.findViewById(R.id.NTpass);

        dialogBuilder.setTitle("New User");
        dialogBuilder.setMessage("Enter the user details");
        dialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newRegister(NTuser.getText().toString(),NTpass.getText().toString());
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private void newRegister(final String user, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://kulibnational.000webhostapp.com/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,user);
                params.put(KEY_PASSWORD,password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }

