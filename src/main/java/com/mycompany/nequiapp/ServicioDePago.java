package com.mycompany.nequiapp;

public interface ServicioDePago {
    void procesarPago(double monto);
    String getConcepto();
}
