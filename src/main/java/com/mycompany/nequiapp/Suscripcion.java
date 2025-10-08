package com.mycompany.nequiapp;

import java.util.concurrent.atomic.AtomicLong;

public class Suscripcion {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private String nombre;
    private int costoMensual;

    public Suscripcion(String nombre, int costoMensual) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.costoMensual = costoMensual;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCostoMensual() { return costoMensual; }
}
