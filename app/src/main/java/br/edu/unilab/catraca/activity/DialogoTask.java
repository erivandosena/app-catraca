package br.edu.unilab.catraca.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by erivando on 24/01/2017.
 */

public class DialogoTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;

    public DialogoTask(ProgressDialog progress) {
        this.progress = progress;
    }

    public void onPreExecute() {
        if (!progress.isShowing())
            progress.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    public void onPostExecute(Void unused) {
        if (progress.isShowing())
            progress.dismiss();
    }
}
