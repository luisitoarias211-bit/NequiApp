package com.mycompany.nequiapp;

public class Usuario {
    protected String nombre;
    protected String identificacion;
    public Usuario(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Identificacion: " + identificacion);
    }
}
