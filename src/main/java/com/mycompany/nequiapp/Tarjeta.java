package com.mycompany.nequiapp;

public class Tarjeta {
    private String tipo;
    private String numero;
    public Tarjeta(String tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }
    public String getNumero() { return numero; }
}
