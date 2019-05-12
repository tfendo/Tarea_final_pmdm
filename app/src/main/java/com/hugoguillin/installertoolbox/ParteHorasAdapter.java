package com.hugoguillin.installertoolbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ParteHorasAdapter extends RecyclerView.Adapter<ParteHorasAdapter.ParteViewHolder> {
    private final LayoutInflater inflater;
    private List<Entrada_parte_horas> entradas;
    private static ClickListener clickListener;

    ParteHorasAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ParteHorasAdapter.ParteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.entrada_parte, viewGroup, false);
        return new ParteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParteHorasAdapter.ParteViewHolder parteViewHolder, int i) {
        if (entradas != null){
            Entrada_parte_horas entrada = entradas.get(i);
            parteViewHolder.entradaHolder.setText(entrada.getEntrada());
        } else {
            parteViewHolder.entradaHolder.setText(R.string.no_entradas_viewholder);
        }
    }

    void setEntradas(List<Entrada_parte_horas> parte){
        entradas = parte;
        notifyDataSetChanged();
    }

    public Entrada_parte_horas getEntradaEnPosicion(int posicion){
        return entradas.get(posicion);
    }

    @Override
    public int getItemCount() {
        if (entradas != null){
            return entradas.size();
        }else
            return 0;
    }

    class ParteViewHolder extends RecyclerView.ViewHolder{

        private final TextView entradaHolder;

        public ParteViewHolder(@NonNull View itemView) {
            super(itemView);
            entradaHolder = itemView.findViewById(R.id.muestra_entradas_partes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        ParteHorasAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int posicion);
    }
}
