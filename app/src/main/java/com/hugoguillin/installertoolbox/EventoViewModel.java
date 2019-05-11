package com.hugoguillin.installertoolbox;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class EventoViewModel extends AndroidViewModel {

    private EventoRepositorio repo;
    private LiveData<List<Eventos_pendientes>> eventos;

    public EventoViewModel(@NonNull Application application) {
        super(application);
        repo = new EventoRepositorio(application);
        eventos = repo.getEventos();
    }

    LiveData<List<Eventos_pendientes>> getEventos(){
        return eventos;
    }

    public void  insert(Eventos_pendientes pendientes){
        repo.insert(pendientes);
    }

    public void deleteEvento(Eventos_pendientes pendientes){
        repo.deleteEvento(pendientes);
    }
}
