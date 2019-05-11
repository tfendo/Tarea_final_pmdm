package com.hugoguillin.installertoolbox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Eventos_pendientes.class}, version = 1, exportSchema = false)
public abstract class EventoRoomDatabase extends RoomDatabase {

    public abstract EventoDao eventoDao();
    private static EventoRoomDatabase INSTANCIA;


    public static EventoRoomDatabase getDatabase(final Context context){
        if(INSTANCIA == null){
            synchronized (EventoRoomDatabase.class){
                if(INSTANCIA == null){
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            EventoRoomDatabase.class, "evento_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }

}
