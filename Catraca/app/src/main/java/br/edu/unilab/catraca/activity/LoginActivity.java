package br.edu.unilab.catraca.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.unilab.catraca.R;
import br.edu.unilab.catraca.app.AppServer;
import br.edu.unilab.catraca.helper.SQLiteHandler;
import br.edu.unilab.catraca.helper.SessionManager;
import br.edu.unilab.catraca.resource.Usuario;
import br.edu.unilab.catraca.util.Util;

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    pDialog.setMessage("Autenticando...");
                    showDialog();
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Insira seus dados de acesso!", Toast.LENGTH_LONG).show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        try {
            if (!Util.isInternet(LoginActivity.this)) {
                Toast.makeText(getApplicationContext(), "Sem acesso à Internet!", Toast.LENGTH_LONG).show();
            } else {


                List<Usuario> usuario_login=AppServer.getRecursoUsuarioLogin(email);
                if (!usuario_login.isEmpty()) {
                    JSONObject jObj=new JSONObject(Util.getJsonList(usuario_login));
//                Log.d(TAG,jObj.toString());
//                Log.d(TAG, String.valueOf(jObj.getInt("usua_id")));
//                Log.d(TAG,jObj.getString("usua_nome"));
//                Log.d(TAG,jObj.getString("usua_email"));
//                Log.d(TAG,jObj.getString("usua_login"));
//                Log.d(TAG,jObj.getString("usua_senha"));
//                Log.d(TAG,jObj.getString("usua_nivel"));
//                Log.d(TAG, String.valueOf(jObj.getInt("id_base_externa")));
                    String hash1=Util.getMd5Hash(password);
                    String hash2=jObj.getString("usua_senha");

                    if (!hash1.equals(hash2)) {
                        Toast.makeText(getApplicationContext(), "Usuário o senha inválidos!", Toast.LENGTH_LONG).show();
                        session.setLogin(false);
                    } else {
                        // Create login session
                        session.setLogin(true);
                        // Now store the user in SQLite
                        String uid=String.valueOf(jObj.getInt("usua_id"));
                        String name=jObj.getString("usua_nome");
                        String mail=jObj.getString("usua_email");
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dataHoraAtual=dateFormat.format(new Date());
                        String created_at=dataHoraAtual;
                        // Inserting row in users table
                        db.addUser(name, mail, uid, created_at);
                        // Launch main activity
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    // Error in login. Get the error message
                    Toast.makeText(getApplicationContext(), "Não cadastrado!", Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            // JSON error
            Log.d(TAG, e.getMessage());
            Toast.makeText(getApplicationContext(),"Erro json: " + e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(getApplicationContext(),"Erro App: " + e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
            hideDialog();
        }
    }

//        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Resposta login: " + response.toString());
//
//                hideDialog();
//
//                try {
//                    JSONObject jObj=new JSONObject(response);
//                    boolean error=jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//                        // user successfully logged in
//                        // Create login session
//                        session.setLogin(true);
//
//                        // Now store the user in SQLite
//                        String uid=jObj.getString("uid");
//                        JSONObject user=jObj.getJSONObject("user");
//                        String name=user.getString("name");
//                        String email=user.getString("email");
//                        String created_at=user.getString("created_at");
//
//                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at);
//
//                        // Launch main activity
//                        Intent intent=new Intent(LoginActivity.this,
//                                MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg=jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Erro json: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Erro login: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
//                params.put("password", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}