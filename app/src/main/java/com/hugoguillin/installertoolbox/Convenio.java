package com.hugoguillin.installertoolbox;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class Convenio extends AppCompatActivity {

    private static final String TEXTO = "texto";
    private static final String ACTIVO = "visible";
    private TextView muestra_convenio;
    private Button descarga_convenio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio);
        muestra_convenio = findViewById(R.id.texto_convenio);
        descarga_convenio = findViewById(R.id.boton_convenio);
        if(savedInstanceState != null){
            boolean b = savedInstanceState.getBoolean(ACTIVO);
            if (!b){
                descarga_convenio.setEnabled(false);
                muestra_convenio.setVisibility(View.VISIBLE);
                muestra_convenio.setText(savedInstanceState.getString(TEXTO));
            }
        }
    }

    public void descargarConvenio(View view) {
        boolean visible = (muestra_convenio.getVisibility() == View.VISIBLE);
        muestra_convenio.setText(R.string.festivos);
        if (!visible) {
            muestra_convenio.setVisibility(View.VISIBLE);
        }
        new ConvenioAsyncTask(muestra_convenio).execute();
        descarga_convenio.setEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTO, muestra_convenio.getText().toString());
        outState.putBoolean(ACTIVO, descarga_convenio.isEnabled());
    }

    class ConvenioAsyncTask extends AsyncTask<Void, Void, String> {
        private WeakReference<TextView> muestraConvenio;
        String texto = getText(R.string.texto_convenio).toString();

        ConvenioAsyncTask(TextView tv){
            muestraConvenio = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return texto;
        }

        @Override
        protected void onPostExecute(String s) {
            muestraConvenio.get().setText(s);
        }
    }
}
