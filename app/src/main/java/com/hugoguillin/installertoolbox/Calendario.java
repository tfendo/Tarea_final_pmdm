package com.hugoguillin.installertoolbox;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class Calendario extends AppCompatActivity {

    static final String FUENTE_DATOS = "https://www.dropbox.com/s/yp373fi7zkklvmm/Calendario_laboral_2019.pdf?dl=1";
    static final String CANAL_CALENDARIO_ID = "canal_notificacion_calendario";
    private static final int NOTIFICACION_ID = 0;

    private NotificationManager manager;
    private static final String VISIBLE = "textViewVisible";
    private static String TEXTO = "textoActual";
    private TextView muestra_festivos;
    private long descargaID;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(descargaID == id){
                Snackbar.make(muestra_festivos, R.string.toast_calendario_descargado, Snackbar.LENGTH_LONG)
                        .setAction("ABRIR", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                muestra_festivos.setText(getString(R.string.textView_calendario_descargado));
                NotificationCompat.Builder builder = getNotificationBuilder();
                manager.notify(NOTIFICACION_ID, builder.build());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        muestra_festivos = findViewById(R.id.festivos);

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if(savedInstanceState != null){
            boolean b = savedInstanceState.getBoolean(VISIBLE);
            if (b) {
                muestra_festivos.setVisibility(View.VISIBLE);
            }else{
                muestra_festivos.setVisibility(View.INVISIBLE);
            }
            muestra_festivos.setText(savedInstanceState.getString(TEXTO));
        }
        crearCanalNotificacion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void consulta_festivos(View view) {
        boolean visible = (muestra_festivos.getVisibility() == View.VISIBLE);
        muestra_festivos.setText(R.string.festivos);
        if (!visible) {
            muestra_festivos.setVisibility(View.VISIBLE);
        }
        descargarCalendario();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTO, muestra_festivos.getText().toString());
        boolean b = muestra_festivos.getVisibility() == View.VISIBLE;
        outState.putBoolean(VISIBLE, b);
    }

    private void descargarCalendario(){
        File file = new File(getExternalFilesDir(null), "Calendario");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(FUENTE_DATOS))
                    .setTitle(getString(R.string.titulo_notify_descarga_calendario))
                    .setDescription(getString(R.string.estado_notify_descarga_calendario))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        descargaID = manager.enqueue(request);
        }
    }

    public void crearCanalNotificacion(){
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_CALENDARIO_ID,
                    "Notificación de Calendario", NotificationManager.IMPORTANCE_HIGH);
            canal.enableLights(true);
            canal.setLightColor(Color.BLUE);
            canal.setDescription("Notificaciones de Calendario");
            manager.createNotificationChannel(canal);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent intent = new Intent(this, Calendario.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICACION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL_CALENDARIO_ID)
                .setContentTitle("Calendario descargado")
                .setContentText("Ya puedes ver los días festivos de este año")
                .setSmallIcon(R.drawable.ic_installertb)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }

}
