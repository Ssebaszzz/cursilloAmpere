/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Aula;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
 * @author sebas
 */
public class AulaController implements Initializable {

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
    private TextField txtSucursal;
    @FXML
    private TableView<Aula> tablaAula;
    @FXML
    private TableColumn<Aula, Integer> colId;
    @FXML
    private TableColumn<Aula, Integer> colSucursal;
    @FXML
    private TableColumn<Aula, String> colDireccion;
    @FXML
    private TextField txtDireccion;
    ObservableList<Aula> registros;//carga de los clientes en la tabla
    ObservableList<Aula> registrosFiltrados;
    ObservableList<Aula> listaAula;
    Aula au = new Aula();
    boolean modificar = false;
    @FXML
    private TextField txtId;

    /**
     * Initializes the controller class.
     */
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
        Aula au = tablaAula.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(au.getNro_aula()));
        txtSucursal.setText(String.valueOf(au.getSucursal()));
        txtDireccion.setText(au.getDireccion());
    }

    @FXML
    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty()) {
            tablaAula.setItems(registros);//si está vacio el campo carga todos los datos
        } else {
            registrosFiltrados.clear();
            for (Aula registro : registros) {
                if (String.valueOf(registro.getNro_aula()).contains(busqueda) || String.valueOf(registro.getSucursal()).contains(busqueda) || registro.getDireccion().contains(busqueda)) {
                    registrosFiltrados.add(registro);
                }//fin if
            } //fin for
            tablaAula.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtDireccion.setDisable(false);
        txtId.setDisable(false);
        txtSucursal.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtId.setDisable(false);
        txtSucursal.setDisable(false);
        txtDireccion.setDisable(false);
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
            au.setNro_aula(cod);
            if (au.eliminar()) {
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
            int sucursal;
            int nro;
            String direccion;
            try {
                nro = Integer.parseInt(txtId.getText());
                sucursal = Integer.parseInt(txtSucursal.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa solo números válidos en los campos correspondientes.");
                return;
            }
            if (!verificarAula(nro,sucursal)) {
                direccion = txtDireccion.getText();
                au.setDireccion(direccion);
                au.setSucursal(sucursal);
                au.setNro_aula(nro);
                if (modificar) {
                    // modificar
                    if (au.modificar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Datos modificados");
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Error, Datos no modificados");
                    }
                    modificar = false;
                } else { // insertar los registros
                    if (au.insertar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Datos insertados correctamente");
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Datos no insertados");
                    }
                }
            } else {
                return;
            }
        }
        mostrarDatos(); // actualiza los registros de la tabla
        cancelar(event); // boton cancelar
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtSucursal.clear();
        txtId.clear();
        txtDireccion.clear();
        txtId.setDisable(true);
        txtDireccion.setDisable(true);
        txtSucursal.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    public void mostrarDatos() {
        registros = FXCollections.observableArrayList(au.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("nro_aula"));
        colSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tablaAula.setItems(registros);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public boolean validarCampos() {
        if (esCampoVacio(txtId, "El campo del numero del aula está vacío.")
                || esCampoVacio(txtDireccion, "El campo de Dirección está vacío.")
                || esCampoVacio(txtSucursal, "El campo de Sucursal está vacío.")) {
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

    private boolean verificarAula(int aula, int sucursal) {
        listaAula = FXCollections.observableArrayList(au.consulta());
        for (Aula object : listaAula) {
            if (String.valueOf(object.getSucursal()).equals(String.valueOf(sucursal)) && String.valueOf(object.getNro_aula()).equals(String.valueOf(aula))) {
                mostrarAlerta(ERROR, "El sistema comunica:", "Error, esta aula ya existe en esta sucursal");
                return true;
            }
        }
        return false;
    }
    @FXML
    public void abrirMenu (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent menuRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/menu.fxml"));
            Scene menuScene = new Scene(menuRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(menuScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirCurso (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent cursoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/curso.fxml"));
            Scene cursoScene = new Scene(cursoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(cursoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirMateria (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent materiaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/materia.fxml"));
            Scene materiaScene = new Scene(materiaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(materiaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirAlumno (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent alumnoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/alumno.fxml"));
            Scene alumnoScene = new Scene(alumnoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(alumnoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirProfesor (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent profesoresRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/profesores.fxml"));
            Scene profesoresScene = new Scene(profesoresRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(profesoresScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void abrirPago (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent pagoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/pago.fxml"));
            Scene pagoScene = new Scene(pagoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(pagoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirrFactura (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent facturaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/factura.fxml"));
            Scene facturaScene = new Scene(facturaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(facturaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirNotas (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent notasRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/notas.fxml"));
            Scene notasScene = new Scene(notasRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(notasScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
