package com.hugoguillin.installertoolbox;

public class Acciones {

    private String titulo;
    private String descripcion;

    public Acciones(String titulo, String descripcion){
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
