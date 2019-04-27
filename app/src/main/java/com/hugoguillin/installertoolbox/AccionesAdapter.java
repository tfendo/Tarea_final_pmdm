package com.hugoguillin.installertoolbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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


    /**
     * Clase interna que representa cada una de las acciones en la pantalla principal
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView vTitulo;
        private TextView vDescripcion;
        private ImageView vImagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vTitulo = itemView.findViewById(R.id.titulo);
            vDescripcion = itemView.findViewById(R.id.descripcion);
            vImagen = itemView.findViewById(R.id.imagenAccion);

            itemView.setOnClickListener(this);
        }

        void enlazar(Acciones accion){
            vTitulo.setText(accion.getTitulo());
            vDescripcion.setText(accion.getDescripcion());
            Glide.with(context).load(accion.getImagen()).into(vImagen);
        }

        @Override
        public void onClick(View v) {
            Acciones accion = datosAcciones.get(getAdapterPosition());
            String titulo_accion = accion.getTitulo();
            Intent intent = new Intent();

            switch (titulo_accion){
                case "Parte de horas":
                    intent = new Intent(context, Parte_horas.class);
                    break;

                case "Trabajos pendientes":
                    intent = new Intent(context, Trabajos_pendientes.class);
                    break;

                case "Calendario laboral":
                    intent = new Intent(context, Calendario.class);
                    break;

                case "Información técnica":
                    intent = new Intent(context, Info_tecnica.class);
                    break;

                case "Convenio":
                    intent = new Intent(context, Convenio.class);
                    break;
            }

            context.startActivity(intent);

        }
    }
}
