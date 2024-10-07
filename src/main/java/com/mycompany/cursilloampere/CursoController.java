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
    private void materia(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("materia.fxml","ABM Materia");
       stage.close();
    }
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
    @FXML
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
