package com.hugoguillin.installertoolbox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "parte_horas")
public class Entrada_parte_horas {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "entrada")
    private String entrada;

    public Entrada_parte_horas(@NonNull String entrada){
        this.entrada = entrada;
    }

    @Ignore
    public Entrada_parte_horas(int id, @NonNull String entrada){
        this.id = id;
        this.entrada = entrada;
    }

    public String getEntrada(){
        return this.entrada;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
}
