package br.edu.unilab.catraca.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.edu.unilab.catraca.R;
import br.edu.unilab.catraca.app.AppServer;
import br.edu.unilab.catraca.helper.SQLiteHandler;
import br.edu.unilab.catraca.helper.SessionManager;
import br.edu.unilab.catraca.resource.Extrato;

/**
 * Created by erivando on 28/11/2016.
 */

public class ExtratoActivity extends Activity {
    private Button btnPrincipal;
    private SQLiteHandler db;
    private SessionManager session;
    private ListView listViewExtrato;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        btnPrincipal=(Button) findViewById(R.id.btnPrinciapl);

        db=new SQLiteHandler(getApplicationContext());
        session=new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> user=db.getUserDetails();
        String login=user.get("login");
        ArrayList<Extrato> extrato_usuario=AppServer.getRecursoExtratoUsuario(login);
        listViewExtrato = (ListView) findViewById(R.id.listView);
        List<String> lista = new ArrayList<String>();
        SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfSaida = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            for (Extrato extrato:extrato_usuario ) {
                data = sdfEntrada.parse(extrato.getData());
                lista.add(extrato.getDescricao() + "  " + sdfSaida.format(data) + "  " + extrato.getValor());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista );
        listViewExtrato.setAdapter(arrayAdapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client=new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExtratoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent=new Intent(ExtratoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}