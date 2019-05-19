package com.hugoguillin.installertoolbox;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.ArraySet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Acciones> datosAcciones;
    private AccionesAdapter adapter;
    private SharedPreferences editor;
    private String sharedPrefFile = "com.hugoguillin.installertoolbox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int columnas = getResources().getInteger(R.integer.columnas);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnas));
        datosAcciones = new ArrayList<>();
        adapter = new AccionesAdapter(this, datosAcciones);
        recyclerView.setAdapter(adapter);
        editor = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        iniciarDatos();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, 0
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1)
            {
                int desde = viewHolder.getAdapterPosition();
                int hacia = viewHolder1.getAdapterPosition();
                Collections.swap(datosAcciones, desde, hacia);
                adapter.notifyItemMoved(desde, hacia);

                //Guardo la posición de los items cambiados para tenerlo en cuenta al volver a recrear
                //la pantalla de inicio
                SharedPreferences.Editor prefEditor = editor.edit();
                prefEditor.putInt("desde", desde);
                prefEditor.putInt("hacia", hacia);
                prefEditor.apply();

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        });
        helper.attachToRecyclerView(recyclerView);

        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_headers, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void iniciarDatos(){
        String[] listaAcciones = getResources().getStringArray(R.array.acciones);
        String[] listaDescripciones = getResources().getStringArray(R.array.descripcion_acciones);
        TypedArray recursosImagenes = getResources().obtainTypedArray(R.array.imagenes_acciones);


        datosAcciones.clear();

        for (int i = 0; i<listaAcciones.length; i++){
            datosAcciones.add(new Acciones(listaAcciones[i], listaDescripciones[i],
                        recursosImagenes.getResourceId(i, 0)));
        }

        //Recojo la posición de los items cambiados de sitio, para guardar la disposición creada
        //por el usuario, aunque solo vale para un cambio de posición a la vez
        int desde = editor.getInt("desde", 0);
        int hacia = editor.getInt("hacia", 0);
        Collections.swap(datosAcciones, desde, hacia);

        recursosImagenes.recycle();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
