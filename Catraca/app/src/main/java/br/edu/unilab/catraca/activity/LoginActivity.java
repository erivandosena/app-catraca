package br.edu.unilab.catraca.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import static com.android.volley.VolleyLog.TAG;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private ImageView imageViewLogo;
    private EditText inputUsuario;
    private EditText inputSenha;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputUsuario = (EditText) findViewById(R.id.nome_usuario);
        inputSenha = (EditText) findViewById(R.id.senha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        imageViewLogo = (ImageView) findViewById(R.id.imgLogo);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);

        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String login = inputUsuario.getText().toString().trim();
                String password = inputSenha.getText().toString().trim();

                if (!login.isEmpty() && !password.isEmpty()) {
                    pDialog.setMessage("Autenticando...");
                    new DialogoTask(pDialog).onPreExecute();
                    checkLogin(login, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Insira seus dados de acesso!", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void checkLogin(final String nome_login, final String password) {
        try {
            if (!Util.isInternet(LoginActivity.this)) {
                Toast.makeText(getApplicationContext(), "Sem acesso à Internet!", Toast.LENGTH_LONG).show();
            } else {

                List<Usuario> usuario_login=AppServer.getRecursoUsuarioLogin(nome_login);
                if (!usuario_login.isEmpty()) {
                    JSONObject jObj=new JSONObject(Util.getJsonList(usuario_login));
                    String hash1=Util.getMd5Hash(password);
                    String hash2=jObj.getString("usua_senha");

                    if (!hash1.equals(hash2)) {
                        Toast.makeText(getApplicationContext(), "Usuário ou senha não confere!", Toast.LENGTH_LONG).show();
                        session.setLogin(false);
                    } else {

                        session.setLogin(true);

                        String uid=String.valueOf(jObj.getInt("usua_id"));
                        String name=jObj.getString("usua_nome");
                        String mail=jObj.getString("usua_email");
                        String login=jObj.getString("usua_login");
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dataHoraAtual=dateFormat.format(new Date());
                        String created_at=dataHoraAtual;

                        db.addUser(name, mail, login, uid, created_at);

                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não cadastrado!", Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(getApplicationContext(),"Erro json login: " + e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(getApplicationContext(),"Erro geral login: " + e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
            new DialogoTask(pDialog).onPostExecute(null);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        new DialogoTask(pDialog).onPostExecute(null);
    }

}