package com.example.kankamet.asabeqwerty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
    TextView _name, _username, _email,_pass;
    android.support.v7.widget.AppCompatButton _sendRequest;
    public  static boolean usernameControlResponse;
    public static boolean emailControlResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooking the UI views for usage
        _name = (EditText) findViewById(R.id.name);
        _username = (EditText) findViewById(R.id.username);
        _email = (EditText) findViewById(R.id.email);
        _pass = (EditText) findViewById(R.id.pass);
        _sendRequest = (AppCompatButton) findViewById(R.id.send_request);

        _username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                RequestQueue queueUserNameControl = Volley.newRequestQueue(MainActivity.this);
                String urlUserNameControl = "http://emirhanpervanlar.com/project/pets/pets/users_create_control.php";

                StringRequest stringRequestUserNameControl = new StringRequest(Request.Method.POST, urlUserNameControl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String responseUsernameControl) {
                                if(Integer.valueOf(responseUsernameControl.trim())==1){
                                    usernameControlResponse = true;
                                }else{
                                    usernameControlResponse = false;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Connection Error",Toast.LENGTH_LONG).show();
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", "a153dd6s33xv6uy9hgf23b16gh");
                        params.put("username", _username.getText().toString().trim());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queueUserNameControl.add(stringRequestUserNameControl);
            }
        });

        _email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                RequestQueue queueEmailControl = Volley.newRequestQueue(MainActivity.this);
                String urlEmailControl = "http://emirhanpervanlar.com/project/pets/pets/users_create_control.php";

                StringRequest stringRequestEmailControl = new StringRequest(Request.Method.POST, urlEmailControl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String responseEmailControl) {
                                if(Integer.valueOf(responseEmailControl.trim())==1){
                                    emailControlResponse = true;
                                }else{
                                    emailControlResponse = false;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Connection Error",Toast.LENGTH_LONG).show();
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", "a153dd6s33xv6uy9hgf23b16gh");
                        params.put("email", _email.getText().toString().trim());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queueEmailControl.add(stringRequestEmailControl);
            }
        });

        //hooking onclick listener of button
        _sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///*validations -*/

                int errorCount = 1;

                if (!validationCheck.isValidEmail(_email.getText().toString().trim())) {
                    _email.setError("Geçersiz E-Mail");
                    errorCount = 0;
                }else{_email.setError(null);}

                if (!validationCheck.isValidPass(_pass.getText().toString().trim())) {
                    _pass.setError("Şifre 6-16 Karakter Olmalıdır ve En Az Bir Harf ve Bir Rakam İçermelidir");
                    errorCount = 0;
                }else{_pass.setError(null);}

                if (!validationCheck.isValidFullName(_name.getText().toString().trim())) {
                    _name.setError("Full Name 6-30 Karakter Olmalıdır");
                    errorCount = 0;
                }else{_name.setError(null);}

                if (!validationCheck.isAlpha(_name.getText().toString().trim())) {
                    _name.setError("İsminiz Yalnızca Harflerden Oluşmalıdır");
                    errorCount = 0;
                }else{_name.setError(null);}

                if(!validationCheck.isUserNameCorrect((_username.getText().toString().trim()))){
                    _username.setError("Kullanıcı Adı Minimum 5 Karakter Olmalıdır");
                    errorCount=0;
                }else{_username.setError(null);}

                if(usernameControlResponse==false) {
                    _username.setError("Mevcut Kullanıcı Adı");
                    errorCount=0;
                } else {_username.setError(null);}

                if(emailControlResponse==false) {
                    _email.setError("Mevcut E-mail");
                    errorCount=0;
                } else {_email.setError(null);}

                ///*validations +*/

                if (errorCount == 1) {
                    final String sifrem = (Sifrele.md5(_pass.getText().toString()).toString().trim()); //has şifreleme olacak
                    // Instantiate the RequestQueue.
                    RequestQueue queueUserCreate = Volley.newRequestQueue(MainActivity.this);
                    //this is the url where you want to send the request
                    //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
                    String urlUserCreate = "http://emirhanpervanlar.com/project/pets/pets/users_create.php";

                    // Request a string response from the provided URL.
                    StringRequest stringRequestUserCreate = new StringRequest(Request.Method.POST, urlUserCreate,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String responseUserCreate) {
                                    Toast.makeText(MainActivity.this,"Kayıt Başarıyla Tamamlandı",Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Kayıt Başarısız",Toast.LENGTH_LONG).show();
                        }
                    }) {
                        //adding parameters to the request
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("token", "a153dd6s33xv6uy9hgf23b16gh");
                            params.put("name", _name.getText().toString());
                            params.put("username", _username.getText().toString().trim());
                            params.put("email", _email.getText().toString().trim());
                            params.put("pass", sifrem.trim());

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queueUserCreate.add(stringRequestUserCreate);
                }
            }
        });
    }
}
