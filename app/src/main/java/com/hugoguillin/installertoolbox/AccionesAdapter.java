package com.hugoguillin.installertoolbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AccionesAdapter extends RecyclerView.Adapter<AccionesAdapter.ViewHolder> {

    private ArrayList<Acciones> datosAcciones;
    private Context context;

    public AccionesAdapter(Context context, ArrayList<Acciones> datos){
        datosAcciones = datos;
        this.context = context;
    }

    @NonNull
    @Override
    public AccionesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.items_principales, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccionesAdapter.ViewHolder viewHolder, int i) {

        Acciones accion = datosAcciones.get(i);
        viewHolder.enlazar(accion);
    }

    @Override
    public int getItemCount() {
        return datosAcciones.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView vTitulo;
        private TextView vDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vTitulo = itemView.findViewById(R.id.titulo);
            vDescripcion = itemView.findViewById(R.id.descripcion);
        }

        void enlazar(Acciones accion){
            vTitulo.setText(accion.getTitulo());
            vDescripcion.setText(accion.getDescripcion());
        }
    }
}
