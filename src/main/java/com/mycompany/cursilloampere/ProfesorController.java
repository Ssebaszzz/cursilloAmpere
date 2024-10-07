/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Profesor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alvarito
 */
public class ProfesorController implements Initializable {

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
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
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TableView tablaProfesor;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colCi;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApe;
    @FXML
    private TableColumn colTel;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TextField txtId;
    ObservableList<Profesor> registros;//carga de los clientes en la tabla
    ObservableList<Profesor> registrosFiltrados;
    ObservableList<Profesor> listaProfesor;
    Profesor p = new Profesor();
    boolean modificar = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        txtBuscar.setTooltip(new Tooltip("Buscar"));
    }

    @FXML
    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty()) {
            tablaProfesor.setItems(registros);//si está vacio el campo carga todos los datos
        } else {
            registrosFiltrados.clear();
            for (Profesor registro : registros) {
                if (registro.getNombre().toLowerCase().contains(busqueda.toLowerCase()) || registro.getApellido().toLowerCase().contains(busqueda.toLowerCase()) || String.valueOf(registro.getId()).contains(busqueda)) {
                    registrosFiltrados.add(registro);
                }//fin if
            } //fin for
            tablaProfesor.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtCedula.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtTelefono.setDisable(false);
        txtCorreo.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        txtCedula.requestFocus();
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtCedula.setDisable(false);
        txtTelefono.setDisable(false);
        txtCorreo.setDisable(false);
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
            p.setId(cod);
            if (p.eliminar()) {
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
            int ced;
            int tel;
            String nom;
            String ape;
            String correo;
            try {
                ced = Integer.parseInt(txtCedula.getText());
                tel = Integer.parseInt(txtTelefono.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Revise los campos numericos");
                return;
            }
            nom = txtNombre.getText();
            ape = txtApellido.getText();
            correo = txtCorreo.getText();
            p.setCi(ced);
            p.setNombre(nom);
            p.setApellido(ape);
            p.setTel(tel);
            p.setCorreo(correo);
            if (modificar) {
                int id = Integer.parseInt(txtId.getText());// modificar
                p.setId(id);
                if (p.modificar()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Modificación Exitosa", "Datos modificados correctamente.");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron modificar.");
                }
                modificar = false;
            } else { // insertar los registros
                if (!validarCedula()) {
                    if (p.insertar()) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Inserción Exitosa", "Datos insertados correctamente.");
                        cancelar(event);
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron insertar.");
                    }
                }
            }
        }
        mostrarDatos();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtId.clear();
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtCedula.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtTelefono.setDisable(true);
        txtCorreo.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        btnEliminar.setDisable(false);
        btnMod.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        Profesor p = (Profesor) tablaProfesor.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(p.getId()));
        txtCedula.setText(String.valueOf(p.getCi()));
        txtNombre.setText(p.getNombre());
        txtApellido.setText(p.getApellido());
        txtTelefono.setText(String.valueOf(p.getTel()));
        txtCorreo.setText(p.getCorreo());

    }

    public void mostrarDatos() {
        registros = FXCollections.observableArrayList(p.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCi.setCellValueFactory(new PropertyValueFactory<>("ci"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApe.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tablaProfesor.setItems(registros);
    }

    public boolean validarCampos() {

        if (esCampoVacio(txtCedula, "El campo de cédula está vacío.")
                || esCampoVacio(txtNombre, "El campo de Nombre está vacío.")
                || esCampoVacio(txtApellido, "El campo de Apellido está vacío.")
                || esCampoVacio(txtTelefono, "El campo de teléfono está vacío.")
                || esCampoVacio(txtCorreo, "El campo de Correo está vacío.")) {
            return true;
        }

        if (!txtCorreo.getText().contains("@")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El correo debe contener un '@' válido.");
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

    public boolean validarCedula() {
        listaProfesor = FXCollections.observableArrayList(p.consulta());
        for (Profesor object : listaProfesor) {
            if (String.valueOf(object.getCi()).equals(txtCedula.getText())) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ya hay un profesor registrado con esta cédula.");
                return true;
            }
        }
        return false;
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
    @FXML
    private void materia(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("materia.fxml","ABM Materia");
        stage.close();
    }

    @FXML
    private void notas(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("notas.fxml","ABM NotasZ");
        stage.close();
    }
}
