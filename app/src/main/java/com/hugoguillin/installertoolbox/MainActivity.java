package com.hugoguillin.installertoolbox;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Acciones> datosAcciones;
    private AccionesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datosAcciones = new ArrayList<>();
        adapter = new AccionesAdapter(this, datosAcciones);
        recyclerView.setAdapter(adapter);
        iniciarDatos();
    }

    private void iniciarDatos(){
        String[] listaAcciones = getResources().getStringArray(R.array.acciones);
        String[] listaDescripciones = getResources().getStringArray(R.array.descripcion_acciones);
        TypedArray recursosImagenes = getResources().obtainTypedArray(R.array.imagenes_acciones);

        datosAcciones.clear();

        for (int i = 0; i<listaAcciones.length; i++){
            datosAcciones.add(new Acciones(listaAcciones[i], listaDescripciones[i],
                    recursosImagenes.getResourceId(i,0)));
        }

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
