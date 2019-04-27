package com.hugoguillin.installertoolbox;

public class Acciones {

    private String titulo;
    private String descripcion;
    private final int imagen;

    public Acciones(String titulo, String descripcion, int img){
        this.titulo = titulo;
        this.descripcion = descripcion;
        imagen = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagen() {
        return imagen;
    }
}
