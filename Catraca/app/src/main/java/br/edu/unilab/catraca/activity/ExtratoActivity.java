package br.edu.unilab.catraca.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.edu.unilab.catraca.R;
import br.edu.unilab.catraca.adapter.DivisorItemDecoration;
import br.edu.unilab.catraca.adapter.ExtratoAdapter;
import br.edu.unilab.catraca.app.AppServer;
import br.edu.unilab.catraca.helper.SQLiteHandler;
import br.edu.unilab.catraca.helper.SessionManager;
import br.edu.unilab.catraca.resource.Extrato;

import static java.lang.Float.parseFloat;

/**
 * Created by erivando on 28/11/2016.
 */

public class ExtratoActivity extends Activity {
    private List<Extrato> extratoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExtratoAdapter mAdapter;
    private Button btnPrincipal;
    private SQLiteHandler db;
    private SessionManager session;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_extrato);
        mAdapter = new ExtratoAdapter(extratoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DivisorItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        db=new SQLiteHandler(getApplicationContext());
        session=new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> user=db.getUserDetails();
        String login=user.get("login");

        ArrayList<Extrato> extrato_usuario=AppServer.getRecursoExtratoUsuario(login);

        SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfSaida = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("R$ ##,##0.00");
        Date data = null;
        try {
            for (Extrato extrato:extrato_usuario ) {
                data = sdfEntrada.parse(extrato.getData());
                prepareExtratoData(extrato.getDescricao(), sdfSaida.format(data), df.format(parseFloat(extrato.getValor())));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    private void prepareExtratoData(String descricao, String data, String valor) {
        Extrato extrato = new Extrato(descricao, data, valor);
        extratoList.add(extrato);
        mAdapter.notifyDataSetChanged();
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent=new Intent(ExtratoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}