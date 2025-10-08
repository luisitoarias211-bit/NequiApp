package com.mycompany.nequiapp;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Transferencia {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private String bancoDestino;
    private String cuentaDestino;
    private double monto;
    private LocalDateTime fecha;
    private CuentaNequi cuentaOrigen;

    public Transferencia(String bancoDestino, String cuentaDestino, double monto, CuentaNequi origen) {
        this.id = COUNTER.getAndIncrement();
        this.bancoDestino = bancoDestino;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.cuentaOrigen = origen;
    }

    public long getId() { return id; }
    public String getBancoDestino() { return bancoDestino; }
    public String getCuentaDestino() { return cuentaDestino; }
    public double getMonto() { return monto; }
    public java.time.LocalDateTime getFecha() { return fecha; }
    public CuentaNequi getCuentaOrigen() { return cuentaOrigen; }
}
