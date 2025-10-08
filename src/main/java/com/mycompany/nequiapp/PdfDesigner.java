package com.mycompany.nequiapp;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfDesigner {

    // Color morado Nequi
    private static final Color NEQUI_PURPLE = new Color(0x7F, 0x00, 0xFF);

    private static String fechaHoy() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    public static String createInvoicePdfFromTransferencia(Transferencia t, CuentaNequi cuenta, String folder) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();
        String filename = String.format("Transferencia_Nequi_%s_%d.pdf", fechaHoy(), t.getId());
        File out = new File(dir, filename);

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);
            PDRectangle media = page.getMediaBox();
            float margin = 50;
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Header rectangle
                cs.setNonStrokingColor(NEQUI_PURPLE);
                cs.addRect(0, media.getHeight() - 100, media.getWidth(), 100);
                cs.fill();

                // Logo (simple circle) and title
                cs.setNonStrokingColor(Color.WHITE);
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                cs.newLineAtOffset(70, media.getHeight() - 60);
                cs.showText("NequiApp");
                cs.endText();

                // Cliente info box
                float infoY = media.getHeight() - 130;
                cs.setNonStrokingColor(Color.LIGHT_GRAY);
                cs.addRect(margin, infoY - 80, media.getWidth() - 2*margin, 70);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(Color.BLACK);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.newLineAtOffset(margin + 10, infoY - 20);
                cs.showText("Cliente: " + cuenta.getTitular().nombre);
                cs.newLineAtOffset(0, -15);
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.showText("Cuenta: " + cuenta.getNumeroTelefono());
                cs.endText();

                // Details box
                float detailsY = infoY - 110;
                cs.setNonStrokingColor(Color.WHITE);
                cs.addRect(margin, detailsY, media.getWidth() - 2*margin, 120);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(Color.BLACK);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 13);
                cs.newLineAtOffset(margin + 10, detailsY + 90);
                cs.showText("Detalle de la transferencia");
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(0, -20);
                cs.showText("ID Transacción: " + t.getId());
                cs.newLineAtOffset(0, -15);
                cs.showText("Banco destino: " + t.getBancoDestino());
                cs.newLineAtOffset(0, -15);
                cs.showText("Cuenta destino: " + t.getCuentaDestino());
                cs.newLineAtOffset(0, -15);
                cs.showText("Fecha: " + t.getFecha().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                cs.endText();

                // Total box
                float totalY = detailsY - 40;
                cs.setNonStrokingColor(NEQUI_PURPLE);
                cs.addRect(margin, totalY, media.getWidth() - 2*margin, 40);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(Color.WHITE);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(margin + 10, totalY + 12);
                cs.showText("TOTAL: $" + String.format("%.2f", t.getMonto()));
                cs.endText();

                // Footer
                cs.beginText();
                cs.setNonStrokingColor(Color.DARK_GRAY);
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                cs.newLineAtOffset(margin, 30);
                cs.showText("Fecha emisión: " + fechaHoy() + "    Gracias por usar NequiApp");
                cs.endText();
            }
            doc.save(out);
        }
        return filename;
    }

    public static String createInvoicePdfFromFactura(Factura f, CuentaNequi cuenta, String folder) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();
        String filename = String.format("Factura_Nequi_%s_%s.pdf", fechaHoy(), f.getNumeroFactura());
        File out = new File(dir, filename);

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);
            PDRectangle media = page.getMediaBox();
            float margin = 50;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Header
                cs.setNonStrokingColor(NEQUI_PURPLE);
                cs.addRect(0, media.getHeight() - 100, media.getWidth(), 100);
                cs.fill();

                cs.setNonStrokingColor(java.awt.Color.WHITE);
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                cs.newLineAtOffset(70, media.getHeight() - 60);
                cs.showText("NequiApp");
                cs.endText();

                // Cliente box
                float infoY = media.getHeight() - 130;
                cs.setNonStrokingColor(java.awt.Color.LIGHT_GRAY);
                cs.addRect(margin, infoY - 80, media.getWidth() - 2*margin, 70);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(java.awt.Color.BLACK);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.newLineAtOffset(margin + 10, infoY - 20);
                cs.showText("Cliente: " + cuenta.getTitular().nombre);
                cs.newLineAtOffset(0, -15);
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.showText("Cuenta: " + cuenta.getNumeroTelefono());
                cs.endText();

                // Details
                float detailsY = infoY - 110;
                cs.setNonStrokingColor(java.awt.Color.WHITE);
                cs.addRect(margin, detailsY, media.getWidth() - 2*margin, 120);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(java.awt.Color.BLACK);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 13);
                cs.newLineAtOffset(margin + 10, detailsY + 90);
                cs.showText("Detalle del pago");
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(0, -20);
                cs.showText("Número factura: " + f.getNumeroFactura());
                cs.newLineAtOffset(0, -15);
                cs.showText("Emisor: " + f.getEmisor());
                cs.newLineAtOffset(0, -15);
                cs.showText("Concepto: " + f.getConcepto());
                cs.newLineAtOffset(0, -15);
                cs.showText("Fecha: " + fechaHoy());
                cs.endText();

                // Total box
                float totalY = detailsY - 40;
                cs.setNonStrokingColor(NEQUI_PURPLE);
                cs.addRect(margin, totalY, media.getWidth() - 2*margin, 40);
                cs.fill();

                cs.beginText();
                cs.setNonStrokingColor(java.awt.Color.WHITE);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(margin + 10, totalY + 12);
                cs.showText("TOTAL: $" + String.format("%.2f", (double) f.getMonto()));
                cs.endText();

                // Footer
                cs.beginText();
                cs.setNonStrokingColor(java.awt.Color.DARK_GRAY);
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                cs.newLineAtOffset(margin, 30);
                cs.showText("Fecha emisión: " + fechaHoy() + "    Gracias por usar NequiApp");
                cs.endText();
            }
            doc.save(out);
        }
        return filename;
    }
}
