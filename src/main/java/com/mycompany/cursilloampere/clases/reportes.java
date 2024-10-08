/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cursilloampere.clases;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author sebas
 */
public class reportes extends conexion {

    public reportes() {
    }

    public void generarReporte(String ubicacion, String titulo) {

        try {
            // Ruta al archivo .jasper
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            // Agrega parámetros según sea necesario

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarFactura(String ubicacion, String titulo, int nroFactura, double totalPago) {
        try {
            // Ruta al archivo .jasper (compilado)
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Obtener el directorio "Documentos" del usuario
            String userHome = System.getProperty("user.home");
            String documentsPath = userHome + "\\Documents"; // En Windows

            // Subcarpeta dentro de "Documentos"
            String subCarpeta = "ReporteFacturas";

            // Crear la ruta completa de la subcarpeta
            String rutaSubCarpeta = documentsPath + "\\" + subCarpeta;

            // Crear la subcarpeta si no existe
            File carpeta = new File(rutaSubCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // Crea la subcarpeta si no existe
            }
            String nombrePdf = "Factura" + nroFactura + ".pdf";
            // Nombre del archivo PDF

            // Crear la ruta completa del archivo PDF dentro de la subcarpeta
            String rutaPdf = rutaSubCarpeta + "\\" + nombrePdf;
            

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Nrofactura", nroFactura);
            parameters.put("TotalPago", totalPago);

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPdf);

            // Verificar si el archivo PDF se ha creado
            File pdfFile = new File(rutaPdf);
            if (pdfFile.exists()) {
                System.out.println("Archivo PDF generado exitosamente: " + pdfFile.getAbsolutePath());
            } else {
                System.out.println("Error al generar el archivo PDF.");
            }

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }
    }

    public void generarClases(String ubicacion, String titulo) {
        try {
            // Ruta al archivo .jasper (compilado)
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Obtener el directorio "Documentos" del usuario
            String userHome = System.getProperty("user.home");
            String documentsPath = userHome + "\\Documents"; // En Windows

            // Subcarpeta dentro de "Documentos"
            String subCarpeta = "ReporteClases";

            // Crear la ruta completa de la subcarpeta
            String rutaSubCarpeta = documentsPath + "\\" + subCarpeta;

            // Crear la subcarpeta si no existe
            File carpeta = new File(rutaSubCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // Crea la subcarpeta si no existe
            }

            // Nombre del archivo PDF
            String nombrePdf = "Reporte de clase" + ".pdf";

            // Crear la ruta completa del archivo PDF dentro de la subcarpeta
            String rutaPdf = rutaSubCarpeta + "\\" + nombrePdf;

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPdf);

            // Verificar si el archivo PDF se ha creado
            File pdfFile = new File(rutaPdf);
            if (pdfFile.exists()) {
                System.out.println("Archivo PDF generado exitosamente: " + pdfFile.getAbsolutePath());
            } else {
                System.out.println("Error al generar el archivo PDF.");
            }

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }
    }

    public void generarNotas(String ubicacion, String titulo) {
        try {
            // Ruta al archivo .jasper (compilado)
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Obtener el directorio "Documentos" del usuario
            String userHome = System.getProperty("user.home");
            String documentsPath = userHome + "\\Documents"; // En Windows

            // Subcarpeta dentro de "Documentos"
            String subCarpeta = "ReporteNotas";

            // Crear la ruta completa de la subcarpeta
            String rutaSubCarpeta = documentsPath + "\\" + subCarpeta;

            // Crear la subcarpeta si no existe
            File carpeta = new File(rutaSubCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // Crea la subcarpeta si no existe
            }

            // Nombre del archivo PDF
            String nombrePdf = "Reporte de Notas" + ".pdf";

            // Crear la ruta completa del archivo PDF dentro de la subcarpeta
            String rutaPdf = rutaSubCarpeta + "\\" + nombrePdf;

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPdf);

            // Verificar si el archivo PDF se ha creado
            File pdfFile = new File(rutaPdf);
            if (pdfFile.exists()) {
                System.out.println("Archivo PDF generado exitosamente: " + pdfFile.getAbsolutePath());
            } else {
                System.out.println("Error al generar el archivo PDF.");
            }

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }
    }
}
