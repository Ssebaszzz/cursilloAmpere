/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.*;
import com.mycompany.cursilloampere.modelo.Usuario;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sebas
 */
public class LOGINController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private Button btnAceptar;
    @FXML
    private PasswordField txtPassword;
    Usuario u = new Usuario();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public boolean validarCampos() {

        if (esCampoVacio(txtUser, "El campo de usuario está vacío.")
                || esCampoVacio(txtPassword, "El campo de contraseña está vacío.")) {
            return false;
        }
        return true;
    }

    private boolean esCampoVacio(TextField campo, String mensajeError) {
        if (campo.getText() == null || campo.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", mensajeError);
            return true;
        }
        return false;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    @FXML
    private void Ingreso(ActionEvent event) {
        if (validarCampos()) {
            if (u.validarLogin(txtUser.getText(), txtPassword.getText())) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                abrirFxml("menu.fxml", "ABM Menu");
                stage.close();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Contraseña o usuario incorrecto");
                return;
            }
            return;
        }
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
}
