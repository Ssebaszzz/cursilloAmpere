/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Materia;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alvarito
 */
public class MateriaController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TableView tablaMateria;
    @FXML
    private TableColumn colMateria;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnMod;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    ObservableList<Materia> listaMateria;
    ObservableList<Materia> registros;//carga de los clientes en la tabla
    ObservableList<Materia> registrosFiltrados;
    Materia ma = new Materia();
    boolean modificar = false;
    @FXML
    private TableColumn colid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        btnEliminar.setDisable(false);
        btnMod.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        Materia ma = (Materia) tablaMateria.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(ma.getId()));
        txtNombre.setText(String.valueOf(ma.getNombre()));

    }

    @FXML
    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty()) {
            tablaMateria.setItems(registros);//si está vacio el campo carga todos los datos
        } else {
            registrosFiltrados.clear();
            for (Materia registro : registros) {
                if (registro.getNombre().contains(busqueda) || String.valueOf(registro.getId()).contains(busqueda)) {
                    registrosFiltrados.add(registro);
                }//fin if
            } //fin for
            tablaMateria.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtNombre.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        txtNombre.requestFocus();
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtNombre.setDisable(false);
        btnEliminar.setDisable(true);
        btnMod.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        mostrarDatos();
        modificar = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Aviso de Borrado");
        alerta.setHeaderText(null);
        alerta.setContentText("Desea eliminar el registro seleccionado?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            int cod = Integer.parseInt(txtId.getText());
            ma.setId(cod);
            if (ma.eliminar()) {
                Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
                alerta1.setTitle("El sistema comunica");
                alerta1.setHeaderText(null);
                alerta1.setContentText("Los datos se han eliminado correctamente");
                alerta1.show();
            } else {
                Alert alerta1 = new Alert(Alert.AlertType.ERROR);
                alerta1.setTitle("El sistema comunica");
                alerta1.setHeaderText(null);
                alerta1.setContentText("Error. Datos no borrados");
                alerta1.show();
            }
        }
        mostrarDatos();
        cancelar(event);
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (!validarCampos()) {
            String materia = (txtNombre.getText());
            ma.setNombre(materia);
            if (validacion()) {
                mostrarAlerta(ERROR, "El sistema comunica", "La materia ya se encuentra registrada");
            } else {
                if (modificar) {
                    // modificar
                    ma.setId(Integer.parseInt(txtId.getText()));
                    if (ma.modificar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Modificado correctamente");
                        cancelar(event);
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Error, registro no modificado");
                    }
                    modificar = false;
                } else { // insertar los registros
                    if (ma.insertar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Insertado correctamente");
                        cancelar(event);
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Error, datos no insertados");
                    }
                }
            }
        }
        mostrarDatos(); // actualiza los registros de la tabla
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtNombre.clear();
        txtId.clear();
        txtNombre.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    public void mostrarDatos() {
        registros = FXCollections.observableArrayList(ma.consulta());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMateria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMateria.setItems(registros);
    }

    public boolean validacion() {
        String materia = quitarAcentos(txtNombre.getText().strip().toLowerCase());
        String comparado;
        listaMateria = FXCollections.observableArrayList(ma.consulta());
        for (Materia objeto : listaMateria) {
            comparado = quitarAcentos(objeto.getNombre().strip().toLowerCase());
            if (materia.equals(comparado)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarCampos() {

        if (esCampoVacio(txtNombre, "El campo de pago por hora está vacío.")) {
            return true;
        }
        return false;
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

    public String quitarAcentos(String cadena) {
        String cadenaNormalizada = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        return cadenaNormalizada.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
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
    private void alumno(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("alumno.fxml","ABM Alumnos");
        stage.close();
    }
    @FXML
    private void profesor(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("profesores.fxml","ABM Profesor");
       stage.close();
    }
    
    @FXML
    private void curso(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("curso.fxml","ABM Curso");
       stage.close();
    }
    @FXML
    private void aula(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("aula.fxml","ABM Aula");
       stage.close();
    }
    private void notas(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("Notas.fxml","ABM Notas");
       stage.close();
    }
    @FXML
    private void pagos(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("detalle_pago_profesor.fxml","ABM Pagos🤑");
       stage.close();
    }
    @FXML
    private void factura(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("factura.fxml","ABM Factura");
        stage.close();
    }

    @FXML
    private void reportes(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("abrirReportes.fxml","ABM Reportes");
        stage.close();
    }

    @FXML
    private void menu(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("menu.fxml","ABM Menu");
        stage.close();
    }
}
