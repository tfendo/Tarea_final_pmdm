package com.hugoguillin.installertoolbox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "trabajos_pendientes")
public class Eventos_pendientes {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "evento")
    private String evento;

    public Eventos_pendientes(@NonNull String evento){
        this.evento = evento;
    }

    public String getEvento(){
        return this.evento;
    }
}
