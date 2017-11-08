package com.example.kankamet.asabeqwerty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
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

    Validation validationCheck=new Validation();
    encryption Sifrele=new encryption();
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

        /* BU KOD DENENECEK - */
        _username.addTextChangedListener(new TextWatcher() {

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "http://emirhanpervanlar.com/project/pets/pets/rest_test2.php";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Kullanıcı Adını Gönder Varsa Hata Ver..

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
                        _response.setText("That didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", "a153dd6s33xv6uy9hgf23b16gh");
                        params.put("username", _username.getText().toString());
                        //params.put("email", _email.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                if(_response.getText().toString()=="false")
                {
                    _username.setError("Kullanıcı Adı Alınmış");
                    //
                }
            }
        });

        /* BU KOD DENENECEK + */

        //hooking onclick listener of button
        _sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*validations -*/
                int errorCount=1;
                if (!validationCheck.isValidEmail(_email.getText().toString())) {_email.setError("Geçersiz E-Mail");errorCount=0;}
                if (!validationCheck.isValidPass(_pass.getText().toString())) {_pass.setError("Şifre 6-16 Karakter Olmalıdır ve En Az Bir Harf ve Bir Rakam İçermelidir");errorCount=0;}
                if (!validationCheck.isValidFullName(_name.getText().toString())) {_name.setError("Full Name 6-30 Karakter Olmalıdır");errorCount=0;}
                if(!validationCheck.isAlpha(_name.getText().toString())) {_name.setError("İsminiz Yalnızca Harflerden Oluşmalıdır");errorCount=0;}
                /*validations +*/

                if(errorCount==1) {
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
                        _response.setText("That didn't work!");
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
                if(_response.toString()=="Veri Eklendi") {
                    Intent i = new Intent(getBaseContext(), WelcomeActivity.class);
                    startActivity(i);
                    finish();
                    }
                }
            }
        });
    }
}
