package com.hugoguillin.installertoolbox;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ParteViewModel extends AndroidViewModel {

    private ParteHorasRepository repo;
    private LiveData<List<Entrada_parte_horas>> entradas;

    public ParteViewModel(@NonNull Application application) {
        super(application);
        repo = new ParteHorasRepository(application);
        entradas = repo.getEntradas();
    }

    LiveData<List<Entrada_parte_horas>> getEntradas(){
        return entradas;
    }

    public void insert(Entrada_parte_horas entrada){
        repo.insert(entrada);
    }

    public void delete (Entrada_parte_horas entrada){
        repo.delete(entrada);
    }

    public void update (Entrada_parte_horas entrada){
        repo.update(entrada);
    }
}
