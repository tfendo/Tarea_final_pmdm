package com.hugoguillin.installertoolbox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Entrada_parte_horas.class}, version = 1, exportSchema = false)
public abstract class ParteHorasRoomDatabase extends RoomDatabase {

    public abstract ParteHorasDao dao();
    private static ParteHorasRoomDatabase INSTANCIA;

    public static ParteHorasRoomDatabase getDatabase(final Context context){
        if( INSTANCIA == null){
            synchronized (ParteHorasRoomDatabase.class){
                if(INSTANCIA == null){
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            ParteHorasRoomDatabase.class, "parte_horas_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
