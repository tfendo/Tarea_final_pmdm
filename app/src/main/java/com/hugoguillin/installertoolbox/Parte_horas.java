package com.hugoguillin.installertoolbox;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class Parte_horas extends AppCompatActivity {

    public static final int NUEVA_ENTRADA_PARTE_HORAS_REQUEST_CODE = 1;
    public static final int ACTUALIZAR_ENTRADA_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_ACTUALIZAR_ENTRADA = "entrada_a_actualizar";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private ParteViewModel parteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_horas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
