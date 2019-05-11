package com.hugoguillin.installertoolbox;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EventoRepositorio {

    private EventoDao eventoDao;
    private LiveData<List<Eventos_pendientes>> eventos;

    EventoRepositorio(Application application){
        EventoRoomDatabase db = EventoRoomDatabase.getDatabase(application);
        eventoDao = db.eventoDao();
        eventos = eventoDao.getAllEventos();
    }

    LiveData<List<Eventos_pendientes>> getEventos(){
        return eventos;
    }

    public void insert (Eventos_pendientes eventos_pendientes){
        new insertAsyncTask(eventoDao).execute(eventos_pendientes);
    }

    public void deleteEvento (Eventos_pendientes eventos_pendientes){
        new deleteAsyncTask(eventoDao).execute(eventos_pendientes);
    }

    private static class insertAsyncTask extends AsyncTask<Eventos_pendientes, Void, Void>{

        private EventoDao asyncEvento;

        insertAsyncTask(EventoDao dao){
            asyncEvento = dao;
        }

        @Override
        protected Void doInBackground(Eventos_pendientes... eventos_pendientes) {
            asyncEvento.insert(eventos_pendientes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Eventos_pendientes, Void, Void>{

        private EventoDao eventoDao;

        deleteAsyncTask(EventoDao dao){
            eventoDao = dao;
        }

        @Override
        protected Void doInBackground(Eventos_pendientes... eventos_pendientes) {
            eventoDao.deleteEvento(eventos_pendientes[0]);
            return null;
        }
    }
}
