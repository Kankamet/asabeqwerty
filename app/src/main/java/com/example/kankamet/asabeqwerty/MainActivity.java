package com.example.kankamet.asabeqwerty;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SignUp Sifrele=new SignUp();
    TextView _name, _username, _email, _response,_pass,_surname;
    android.support.v7.widget.AppCompatButton _sendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooking the UI views for usage
        _name = (EditText) findViewById(R.id.name);
        _username = (EditText) findViewById(R.id.username);
        _email = (EditText) findViewById(R.id.email);
        _pass = (EditText) findViewById(R.id.pass);
        _response = (TextView) findViewById(R.id.response);
        _sendRequest = (AppCompatButton) findViewById(R.id.send_request);


        //hooking onclick listener of button
        _sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad=_name.getText().toString();

                final String sifrem = (Sifrele.md5(_pass.getText().toString()).toString());
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //this is the url where you want to send the request
                //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
                String url = "http://emirhanpervanlar.com/project/pets/pets/rest_test2.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                _response.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _response.setText("MainThat didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", "a153dd6s33xv6uy9hgf23b16gh");
                        params.put("name", _name.getText().toString());
                        params.put("username", _username.getText().toString());
                        params.put("email", _email.getText().toString());
                        params.put("pass", sifrem);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }
}
