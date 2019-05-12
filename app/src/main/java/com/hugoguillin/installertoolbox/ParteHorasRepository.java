package com.hugoguillin.installertoolbox;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ParteHorasRepository {

    private ParteHorasDao dao;
    private LiveData<List<Entrada_parte_horas>> entradas;

    ParteHorasRepository(Application application){
        ParteHorasRoomDatabase db = ParteHorasRoomDatabase.getDatabase(application);
        dao = db.dao();
        entradas = dao.getPartes();
    }

    LiveData<List<Entrada_parte_horas>> getEntradas(){
        return entradas;
    }

    public void insert (Entrada_parte_horas entrada){
        new insertAsyncTask(dao).execute(entrada);
    }

    public void delete (Entrada_parte_horas entrada){
        new deleteAsyncTask(dao).execute(entrada);
    }

    public void update(Entrada_parte_horas entrada){
        new updateAsyncTask(dao).execute(entrada);
    }

    private static class insertAsyncTask extends AsyncTask<Entrada_parte_horas, Void, Void>{

        private ParteHorasDao asyncDao;

        insertAsyncTask(ParteHorasDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Entrada_parte_horas... entrada_parte_horas) {
            asyncDao.insert(entrada_parte_horas[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Entrada_parte_horas, Void, Void>{

        private ParteHorasDao asyncDao;

        deleteAsyncTask(ParteHorasDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Entrada_parte_horas... entrada_parte_horas) {
            asyncDao.delete(entrada_parte_horas[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Entrada_parte_horas, Void, Void>{

        private ParteHorasDao asyncDao;

        updateAsyncTask(ParteHorasDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Entrada_parte_horas... entrada_parte_horas) {
            asyncDao.update(entrada_parte_horas[0]);
            return null;
        }
    }
}
