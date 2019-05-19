package com.hugoguillin.installertoolbox;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class Trabajos_pendientes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrabajoAdapter adapter;
    private EditText cliente;
    private EditText trabajo;
    private EventoViewModel eventoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos_pendientes);
        cliente = findViewById(R.id.nombre_cliente);
        trabajo = findViewById(R.id.descripcion_trabajo);

        recyclerView = findViewById(R.id.trabajos_pendientes_recyclerView);
        adapter = new TrabajoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventoViewModel = ViewModelProviders.of(this).get(EventoViewModel.class);
        eventoViewModel.getEventos().observe(this, new Observer<List<Eventos_pendientes>>() {
            @Override
            public void onChanged(@Nullable List<Eventos_pendientes> eventos_pendientes) {
                adapter.setEvento(eventos_pendientes);
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
                Eventos_pendientes  evento = adapter.getEventoEnPosicion(posicion);
                eventoViewModel.deleteEvento(evento);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    public void anhadirEvento(View view) {
        String nombre_cliente, texto_trabajo;
        if(!(cliente.getText().toString().isEmpty())) {
            nombre_cliente = cliente.getText().toString().toUpperCase()+"\n";
            if(!(trabajo.getText().toString().isEmpty())) {
                texto_trabajo = trabajo.getText().toString()+"\n";
                String evento = (nombre_cliente + texto_trabajo);
                eventoViewModel.insert(new Eventos_pendientes(evento));
                cliente.setText("");
                trabajo.setText("");
                cliente.requestFocus();
                InputMethodManager methodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(methodManager != null){
                    methodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            } else{
              Toast.makeText(this, getString(R.string.toast_anhadirEvento1),
                      Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, getString(R.string.toast_anhadirEvento),
                    Toast.LENGTH_LONG).show();
        }
    }
}
