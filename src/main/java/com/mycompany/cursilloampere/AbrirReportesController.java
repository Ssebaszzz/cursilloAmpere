package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.clases.reportes;
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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AbrirReportesController implements Initializable {

    @FXML
    private Button btnReporteClases;
    @FXML
    private Button btnReporteNotas;
    reportes r = new reportes();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void abrirFxml(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void generarReporteClases(ActionEvent event) {
        String Ubicacion = "/reportes/ListaCurso.jasper";
        String Titulo ="Lista de cursos";
        r.generarClases(Ubicacion, Titulo);
    }
    @FXML
    private void generarReporteNotas(ActionEvent event) {
        String Ubicacion = "/reportes/Notas.jasper";
        String Titulo ="Lista de Notas";
        r.generarNotas(Ubicacion, Titulo);
    }

    @FXML
    private void volver(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("menu.fxml", "ABM Menu");
        stage.close();
    }

    

}