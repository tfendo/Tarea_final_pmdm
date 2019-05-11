package com.hugoguillin.installertoolbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TrabajoAdapter extends RecyclerView.Adapter<TrabajoAdapter.TrabajoHolder> {

    private List<Eventos_pendientes> lista;
    private LayoutInflater inflater;

    public TrabajoAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TrabajoAdapter.TrabajoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = inflater.inflate(R.layout.trabajo, viewGroup, false);
        return new TrabajoHolder(item, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TrabajoAdapter.TrabajoHolder trabajoHolder, int i) {
        Eventos_pendientes actual = lista.get(i);
        trabajoHolder.trabajoView.setText(actual.getEvento());
    }

    void setEvento(List<Eventos_pendientes> eventos){
        lista = eventos;
        notifyDataSetChanged();
    }

    public Eventos_pendientes getEventoEnPosicion(int posicion){
        return lista.get(posicion);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        else
            return 0;
    }

    class TrabajoHolder extends RecyclerView.ViewHolder{
        public final TextView trabajoView;
        final TrabajoAdapter adapter;


        public TrabajoHolder(@NonNull View itemView, TrabajoAdapter ta) {
            super(itemView);
            trabajoView = itemView.findViewById(R.id.trabajo);
            adapter = ta;
        }
    }
}
