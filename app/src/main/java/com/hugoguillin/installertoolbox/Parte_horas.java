package com.hugoguillin.installertoolbox;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Parte_horas extends AppCompatActivity {

    public static final int NUEVA_ENTRADA_PARTE_HORAS_REQUEST_CODE = 1;
    public static final int ACTUALIZAR_ENTRADA_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_ACTUALIZAR_ENTRADA = "entrada_a_actualizar";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private static final int NOTIFICATION_ID = 0;
    private static final String CANAL_PARTES_ID = "canal_notificaciones_partes";

    private ParteViewModel parteViewModel;
    private NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_horas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        RecyclerView recyclerView = findViewById(R.id.parte_horas_recyclerview);
        final ParteHorasAdapter adapter = new ParteHorasAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parteViewModel = ViewModelProviders.of(this).get(ParteViewModel.class);
        parteViewModel.getEntradas().observe(this, new Observer<List<Entrada_parte_horas>>() {
            @Override
            public void onChanged(@Nullable List<Entrada_parte_horas> entrada_parte_horas) {
                adapter.setEntradas(entrada_parte_horas);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int posicion = viewHolder.getAdapterPosition();
                Entrada_parte_horas entrada = adapter.getEntradaEnPosicion(posicion);
                parteViewModel.delete(entrada);
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ParteHorasAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                Entrada_parte_horas entrada = adapter.getEntradaEnPosicion(posicion);
                abrirParaActualizar(entrada);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parte_horas.this, Rellenar_parte_horas.class);
                startActivityForResult(intent, NUEVA_ENTRADA_PARTE_HORAS_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crearCanalNotificacion();

        //=======SE CREA EL ALARMMANAGER PARA RECORDAR HACER EL PARTE DE HORAS=========
        Intent notyIntent = new Intent(this, ParteReceiver.class);
        PendingIntent notyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //Se fija la alarma una vez al día a las 18 horas
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        //Se usa el método setInexactRepeating() porque no es necesario que active el teléfono a esa hora exactamente
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, notyPendingIntent);
        //=========================================================
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NUEVA_ENTRADA_PARTE_HORAS_REQUEST_CODE && resultCode == RESULT_OK){
            Entrada_parte_horas entrada_parte_horas = new Entrada_parte_horas(
                    data.getStringExtra(Rellenar_parte_horas.EXTRA_REPLY));
            parteViewModel.insert(entrada_parte_horas);
        }else if(requestCode == ACTUALIZAR_ENTRADA_REQUEST_CODE && resultCode == RESULT_OK){
            String datos_entrada = data.getStringExtra(Rellenar_parte_horas.EXTRA_REPLY);
            int id = data.getIntExtra(Rellenar_parte_horas.EXTRA_REPLY_ID, -1);

            if(id != - 1){
                parteViewModel.update(new Entrada_parte_horas(id, datos_entrada));
            }else{
                Toast.makeText(this, getString(R.string.toast_activity_update_result),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_acitivity_result),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void abrirParaActualizar (Entrada_parte_horas entrada){
        Intent intent = new Intent(this, Rellenar_parte_horas.class);
        intent.putExtra(EXTRA_DATA_ACTUALIZAR_ENTRADA, entrada.getEntrada());
        intent.putExtra(EXTRA_DATA_ID, entrada.getId());
        startActivityForResult(intent, ACTUALIZAR_ENTRADA_REQUEST_CODE);
    }

    public void crearCanalNotificacion(){
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_PARTES_ID,
                    getString(R.string.nombre_canal_parte_horas), NotificationManager.IMPORTANCE_HIGH);
            canal.enableLights(true);
            canal.setLightColor(Color.RED);
            canal.setDescription(getString(R.string.desc_canal_parte_horas));
            manager.createNotificationChannel(canal);
        }
    }
}
