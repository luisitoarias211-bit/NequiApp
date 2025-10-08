package com.mycompany.nequiapp;

public class Factura implements ServicioDePago {
    private String emisor;
    private String numeroFactura;
    private int monto;

    public Factura(String emisor, String numeroFactura, int monto) {
        this.emisor = emisor;
        this.numeroFactura = numeroFactura;
        this.monto = monto;
    }

    public String getEmisor() { return emisor; }
    public String getNumeroFactura() { return numeroFactura; }
    public int getMonto() { return monto; }

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago de factura #" + numeroFactura + " de " + emisor + " por $" + monto);
    }

    @Override
    public String getConcepto() {
        return "Pago de factura " + emisor;
    }
}
