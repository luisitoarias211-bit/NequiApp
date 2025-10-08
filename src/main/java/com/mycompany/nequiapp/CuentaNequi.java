package com.mycompany.nequiapp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CuentaNequi {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private final String numeroTelefono;
    private double saldo;
    private final Titular titular;
    private final Tarjeta tarjeta;
    private final Banco bancoAsociado;
    private final Map<Long, Transferencia> transferencias = new LinkedHashMap<>();
    private final Map<Long, Suscripcion> suscripciones = new LinkedHashMap<>();
    private Factura ultimaFactura;

    public CuentaNequi(String numeroTelefono, double saldoInicial, Titular titular, Banco bancoAsociado) {
        this.id = COUNTER.getAndIncrement();
        this.numeroTelefono = numeroTelefono;
        this.saldo = saldoInicial;
        this.titular = titular;
        this.bancoAsociado = bancoAsociado;
        this.tarjeta = new Tarjeta("débito", "0000-0000-0000-0000");
    }

    public long getId() { return id; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public double getSaldo() { return saldo; }
    public Titular getTitular() { return titular; }

    public void mostrarInfoCuenta() {
        System.out.println("Cuenta ID: " + id);
        System.out.println("Titular: " + titular.nombre);
        System.out.println("Teléfono: " + numeroTelefono);
        System.out.println("Banco asociado: " + bancoAsociado.getNombre());
        System.out.println("Saldo: $" + saldo);
    }

    public Transferencia transferir(String bancoDestino, String cuentaDestino, double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (saldo < monto) throw new IllegalArgumentException("Fondos insuficientes");
        saldo -= monto;
        Transferencia t = new Transferencia(bancoDestino, cuentaDestino, monto, this);
        transferencias.put(t.getId(), t);
        return t;
    }

    public void pagarFactura(Factura f) {
        if (saldo >= f.getMonto()) {
            saldo -= f.getMonto();
            ultimaFactura = f;
            System.out.println("Pagando factura de " + f.getEmisor() + " por: $ " + f.getMonto());
            f.procesarPago(f.getMonto());
        } else {
            System.out.println("Error: Saldo insuficiente");
        }
    }

    public void agregarSuscripcion(Suscripcion s) {
        suscripciones.put(s.getId(), s);
    }

    public void pagarSubcricionById(long id) {
        Suscripcion s = suscripciones.get(id);
        if (s == null) {
            System.out.println("Suscripción no encontrada");
            return;
        }
        if (saldo >= s.getCostoMensual()) {
            saldo -= s.getCostoMensual();
            System.out.println("Suscripción " + s.getNombre() + " pagada por: $" + s.getCostoMensual());
        } else {
            System.out.println("Saldo insuficiente");
        }
    }

    public Factura getUltimaFactura() { return ultimaFactura; }
    public Transferencia buscarTransferenciaPorId(long id) { return transferencias.get(id); }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }
}
