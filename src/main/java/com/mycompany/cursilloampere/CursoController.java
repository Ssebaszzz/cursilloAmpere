/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Aula;
import com.mycompany.cursilloampere.modelo.Curso;
import java.io.IOException;
import java.net.URL;
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

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class CursoController implements Initializable {

    @FXML
    private TextField txtNombre;
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
    private TextField txtId;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TableView<Curso> tablaCurso;
    @FXML
    private TableColumn<Curso, Integer> colId;
    @FXML
    private TableColumn<Curso, String> colNom;
    @FXML
    private TableColumn<Curso, Double> colMatricula;
    @FXML
    private TableColumn<Curso, Double> colCosto;
    @FXML
    private TableColumn<Curso, Integer> colDuracion;
    @FXML
    private TableColumn<Curso, Integer> colAula;
    @FXML
    private TextField txtCosto;
    @FXML
    private TextField txtDuracion;
    @FXML
    private ComboBox<String> cmbAula;
    ObservableList<Curso> listaCurso;
    ObservableList<Aula> listaAula;
    //objetos
    Curso c = new Curso();
    Aula au = new Aula();
    //bandera
    boolean bandera = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }

    public void mostrarDatos() {
        listaCurso = FXCollections.observableArrayList(c.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("Duracion"));
        colAula.setCellValueFactory(new PropertyValueFactory<>("Aula"));
        tablaCurso.setItems(listaCurso);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        Curso c = tablaCurso.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(c.getId()));
        txtNombre.setText(c.getNombre());
        txtMatricula.setText(String.valueOf(c.getMatricula()));
        txtCosto.setText(String.valueOf(c.getCosto()));
        txtDuracion.setText(String.valueOf(c.getDuracion()));
        cmbAula.setValue(String.valueOf(c.getAula()));
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
    }

    @FXML
    private void buscar(KeyEvent event) {
        ObservableList<Curso> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaCurso.setItems(listaCurso);
        } else {
            registrosFiltrados.clear();
            for (Curso registro : listaCurso) {
                if (registro.getNombre().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                } // fin if
            } // fin for
            tablaCurso.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtNombre.setDisable(false);
        txtMatricula.setDisable(false);
        txtCosto.setDisable(false);
        txtDuracion.setDisable(false);
        cmbAula.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        //se carga los prompt
        cmbAula.setPromptText("Seleccione Aula");
        cargarAula();
    }

    @FXML
    private void modificar(ActionEvent event) {
        cmbAula.setDisable(false);
        txtNombre.setDisable(false);
        txtMatricula.setDisable(false);
        txtCosto.setDisable(false);
        txtDuracion.setDisable(false);
        cmbAula.setDisable(false);
        // habilitar botones
        btnMod.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarAula();
        bandera = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("El sistema comunica:");
        alerta.setHeaderText(null);
        alerta.setContentText("Error. Los cursos no se pueden dar de baja.");
        alerta.show();
        cancelar(event);
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (!validarCampos()) {
            Double Costo;
            Double Matricula;
            int Duracion = Integer.parseInt(txtDuracion.getText());
            try {
                
                Matricula = Double.parseDouble(txtMatricula.getText().replace(",","."));
                Costo = Double.parseDouble(txtCosto.getText().replace(",","."));
                
            }catch(NumberFormatException e){
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa solo números válidos en los campos correspondientes.");
                return;
            }
            String Nombre = txtNombre.getText();
            int aula = buscarAula();
            c.setAula(aula);
            c.setDuracion(Duracion);
            c.setCosto(Costo);
            c.setMatricula(Matricula);
            c.setNombre(Nombre);
            if (bandera) {//modificar
                c.setId(Integer.parseInt(txtId.getText()));//enviamos el codigo para modificar
                if (c.modificar()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Modificación Exitosa", "Datos modificados correctamente.");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron modificar.");

                }
                bandera = false;
            } else {
                if (c.insertar()) {//insertado
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Inserción Exitosa", "Datos insertados correctamente.");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron insertar.");
                }
            }
        }
        mostrarDatos();

    }

    @FXML
    private void cancelar(ActionEvent event) {
        cmbAula.setValue(null);
        cmbAula.setDisable(true);
        txtNombre.setDisable(true);
        txtMatricula.setDisable(true);
        txtCosto.setDisable(true);
        txtDuracion.setDisable(true);
        cmbAula.setPromptText("Selecione Aula");
        txtNombre.clear();
        txtMatricula.clear();
        txtCosto.clear();
        txtDuracion.clear();
        txtId.clear();
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    private void cargarAula() {
        cmbAula.getItems().clear();
        listaAula = FXCollections.observableArrayList(au.consulta());
        listaCurso = FXCollections.observableArrayList(c.consulta());
        for (Aula aula : listaAula) {
            boolean aulaLibre = true;

            for (Curso curso : listaCurso) {
                if (curso.getAula() == aula.getNro_aula()) {
                    aulaLibre = false;
                    break;
                }
            }
            if (aulaLibre) {
                cmbAula.getItems().add(String.valueOf(aula.getNro_aula()));
            }
        }
    }

    private int buscarAula() {
        for (Aula object : listaAula) {
            if (String.valueOf(object.getNro_aula()).contains(cmbAula.getSelectionModel().getSelectedItem())) {
                return object.getNro_aula();
            }
        }
        return 0;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public boolean validarCampos() {
        if (esCampoVacio(txtMatricula, "El campo de matricula está vacío.")
                || esCampoVacio(txtNombre, "El campo de Nombre está vacío.")
                || esCampoVacio(txtDuracion, "El campo de Duracion está vacío.")
                || esCampoVacio(txtCosto, "El campo de teléfono está vacío.")) {
            return true;
        }
        String Aula = cmbAula.getSelectionModel().getSelectedItem();
        if (Aula == null) {
            mostrarAlerta(ERROR, "El sistema comunica: ", "No ha seleccionado un aula");
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
    public void abrirAula (ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent aulaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/aula.fxml"));
            Scene aulaScene = new Scene(aulaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(aulaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    @FXML
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
