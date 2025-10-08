package com.mycompany.nequiapp;

import java.io.IOException;
import java.util.Scanner;

public class NequiApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static CuentaNequi cuenta;

    public static void main(String[] args) throws Exception {
        System.out.println("=== NequiApp ===");
        // Datos por defecto (puedes cambiarlos en ejecución)
        String nombre = "Luis Arias";
        String cuentaNum = "3001234567";
        Titular titular = new Titular(nombre, "CC-1001");
        Banco nequi = new Banco("Nequi");
        cuenta = new CuentaNequi(cuentaNum, 200000.0, titular, nequi);

        menuLoop();
    }

    private static void menuLoop() throws Exception {
        while (true) {
            System.out.println("\nSeleccione opción:");
            System.out.println("1) Mostrar cuenta");
            System.out.println("2) Transferir (simulado)");
            System.out.println("3) Pagar factura");
            System.out.println("4) Crear suscripción");
            System.out.println("5) Pagar suscripción");
            System.out.println("6) Generar PDF (factura o transferencia)");
            System.out.println("7) Salir");
            System.out.print("> ");
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> cuenta.mostrarInfoCuenta();
                case "2" -> procesoTransferencia();
                case "3" -> procesoPagoFactura();
                case "4" -> crearSuscripcion();
                case "5" -> pagarSuscripcion();
                case "6" -> generarPdf();
                case "7" -> {
                    System.out.println("Saliendo..."); return;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void procesoTransferencia() {
        try {
            System.out.print("Banco destino: ");
            String bancoDest = scanner.nextLine();
            System.out.print("Cuenta destino: ");
            String cuentaDest = scanner.nextLine();
            System.out.print("Monto: ");
            double monto = Double.parseDouble(scanner.nextLine());
            Transferencia t = cuenta.transferir(bancoDest, cuentaDest, monto);
            System.out.println("Transferencia realizada. ID transacción: " + t.getId());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void procesoPagoFactura() {
        try {
            System.out.print("Emisor factura: ");
            String emisor = scanner.nextLine();
            System.out.print("Número factura: ");
            String num = scanner.nextLine();
            System.out.print("Monto: ");
            int monto = Integer.parseInt(scanner.nextLine());
            Factura f = new Factura(emisor, num, monto);
            cuenta.pagarFactura(f);
            System.out.println("Pago realizado. Factura procesada.");
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearSuscripcion() {
        try {
            System.out.print("Nombre suscripción: ");
            String name = scanner.nextLine();
            System.out.print("Costo mensual: ");
            int costo = Integer.parseInt(scanner.nextLine());
            Suscripcion s = new Suscripcion(name, costo);
            cuenta.agregarSuscripcion(s);
            System.out.println("Suscripción creada con ID: " + s.getId());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void pagarSuscripcion() {
        try {
            System.out.print("ID suscripción: ");
            long id = Long.parseLong(scanner.nextLine());
            cuenta.pagarSubcricionById(id);
            System.out.println("Pago de suscripción realizado (si había saldo).");
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void generarPdf() {
        try {
            System.out.print("Tipo (1=Factura, 2=Transferencia): ");
            String tipo = scanner.nextLine();
            if ("1".equals(tipo)) {
                Factura f = cuenta.getUltimaFactura();
                if (f == null) {
                    System.out.println("No hay factura reciente. Paga una factura primero.");
                    return;
                }
                String filename = PdfDesigner.createInvoicePdfFromFactura(f, cuenta, "../facturas");
                System.out.println("PDF generado: facturas/" + filename);
            } else {
                System.out.print("ID transferencia: ");
                long id = Long.parseLong(scanner.nextLine());
                Transferencia t = cuenta.buscarTransferenciaPorId(id);
                if (t == null) {
                    System.out.println("Transferencia no encontrada.");
                    return;
                }
                String filename = PdfDesigner.createInvoicePdfFromTransferencia(t, cuenta, "../facturas");
                System.out.println("PDF generado: facturas/" + filename);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error creando PDF: " + e.getMessage());
        }
    }
}
