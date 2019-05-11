package com.hugoguillin.installertoolbox;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Eventos_pendientes evento);

    @Delete
    void deleteEvento(Eventos_pendientes evento);

    @Query("SELECT * from trabajos_pendientes ORDER BY evento ASC")
    LiveData<List<Eventos_pendientes>> getAllEventos();
}
