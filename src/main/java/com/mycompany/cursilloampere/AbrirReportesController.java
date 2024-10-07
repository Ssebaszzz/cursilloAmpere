package com.mycompany.cursilloampere;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class AbrirReportesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    private void cuotas(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("detalle_cuota.fxml","ABM Cuotas");
        stage.close();
    }
    @FXML
    private void facturaReportes(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("detalle_factura.fxml","ABM Reporte Facturas");
       stage.close();
    }
    @FXML
    private void clases(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("detalle_materia_profesor.fxml","ABM Reporte Clases");
       stage.close();
    }

}