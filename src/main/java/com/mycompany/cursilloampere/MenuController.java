/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    
    
    @FXML
    private void alumno(ActionEvent event) {
        abrirFxml("alumno.fxml","ABM Alumnos");
        
    }
    public void abrirFxml(String fxml, String titulo){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource(fxml));
            Parent root=loader.load();
            Stage stage=new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void aula(ActionEvent event) {
        abrirFxml("aula.fxml","ABM Aula");
    }

    @FXML
    private void curso(ActionEvent event) {
        abrirFxml("curso.fxml","ABM Curso");
    }

    @FXML
    private void profesor(ActionEvent event) {
        abrirFxml("profesores.fxml","ABM Profesor");
    }


    @FXML
    private void factura(ActionEvent event) {
        abrirFxml("factura.fxml","ABM Factura");
    }

    @FXML
    private void materia(ActionEvent event) {
        abrirFxml("materia.fxml","ABM Materia");
    }

    @FXML
    private void detalle_materia_profesor(ActionEvent event) {
        abrirFxml("detalle_materia_profesor.fxml","ABM Detalle_materia_profesor");
    }

    @FXML
    private void detalle_factura(ActionEvent event) {
        abrirFxml("detalle_factura.fxml","ABM Detalle_factura");
    }

    @FXML
    private void notas(ActionEvent event) {
        abrirFxml("notas.fxml","ABM Notas");
    }

    private void asistencia(ActionEvent event) {
        abrirFxml("Asistencia.fxml","ABM Asistencia");
    }

    @FXML
    private void detalle_pago_profesor(ActionEvent event) {
        abrirFxml("detalle_pago_profesor.fxml","ABM Detalle_pago_profesor");
    }
}