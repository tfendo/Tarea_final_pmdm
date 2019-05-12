package com.hugoguillin.installertoolbox;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ParteHorasDao {
    @Insert
    void insert(Entrada_parte_horas entrada);

    @Delete
    void delete(Entrada_parte_horas entrada);

    @Update
    void update(Entrada_parte_horas... entrada);

    @Query("SELECT * from parte_horas ORDER BY id")
    LiveData<List<Entrada_parte_horas>> getPartes();

}
