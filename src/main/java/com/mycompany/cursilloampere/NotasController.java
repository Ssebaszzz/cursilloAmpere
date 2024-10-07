/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.Materia;
import com.mycompany.cursilloampere.modelo.Notas;
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
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class NotasController implements Initializable {

    @FXML
    private TableColumn<Notas, Integer> colNota;
    @FXML
    private TableColumn<Notas, String> colGrupo;
    @FXML
    private TableColumn<Notas, String> colAlumno;
    private TextField txtBuscar;
    @FXML
    private Button btnNuevo;
    private Button btnMod;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<String> cmbAlumno;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private TableView<Notas> tablaNotas;
    @FXML
    private TableColumn<Notas, String> colMateria;

    @FXML
    private ComboBox<String> cmbMateria;
    @FXML
    private TextField txtNota;

    ObservableList<Notas> listaNotas;
    ObservableList<Alumno> listaAlumno;
    ObservableList<Curso> listaCurso;
    ObservableList<Materia> listaMateria;
    //objetos

    Alumno a = new Alumno();
    Curso c = new Curso();
    Materia m = new Materia();
    Notas n = new Notas();
    //bandera
    boolean bandera = false;
    @FXML
    private TableColumn<Notas, String> colFecha;
    @FXML
    private DatePicker txtFecha;
    @FXML
    private TableColumn<Notas, Integer> colId;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }

    public void mostrarDatos() {
        listaNotas = FXCollections.observableArrayList(n.consulta());
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreGrupo"));
        colAlumno.setCellValueFactory(new PropertyValueFactory<>("nombreAlumno"));
        colMateria.setCellValueFactory(new PropertyValueFactory<>("nombreMateria"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota_obtenida"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tablaNotas.setItems(listaNotas);
    }

    private void cargarCurso() {
        cmbCurso.getItems().clear();
        listaCurso = FXCollections.observableArrayList(c.consulta());
        for (Curso objecto : listaCurso) {
            cmbCurso.getItems().add(objecto.getNombre());
        }
    }

    private int buscarCurso() {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return 0;
        }
        for (Curso object : listaCurso) {
            if (object.getNombre().equals(seleccionado)) {
                return object.getId();
            }
        }
        return 0;
    }

    private void cargarAlumno() {
        cmbAlumno.getItems().clear();
        listaAlumno = FXCollections.observableArrayList(a.consulta());
        for (Alumno object : listaAlumno) {
            cmbAlumno.getItems().add(object.getNombre() + " " + object.getApellido());
        }
    }

    private int buscarAlumno() {
        String Alumnos = cmbAlumno.getSelectionModel().getSelectedItem();
        if (Alumnos == null) {
            return 0;
        }
        String nombre = Alumnos.substring(0, Alumnos.indexOf(" "));
        String apellido = Alumnos.substring(Alumnos.indexOf(" ") + 1);
        for (Alumno object : listaAlumno) {
            if (object.getNombre().contains(nombre) && object.getApellido().contains(apellido)) {
                return object.getId();
            }
        }
        return 0;
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        Notas n = tablaNotas.getSelectionModel().getSelectedItem();
        txtNota.setText(String.valueOf(n.getNota_obtenida()));
        LocalDate fecha = LocalDate.parse(n.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtFecha.setValue(fecha);
        txtId.setText(String.valueOf(n.getId()));
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarAlumno();
        cargarCurso();
        cargarMateria();
        cmbAlumno.setValue(n.getNombreAlumno());
        cmbCurso.setValue(n.getNombreGrupo());
        cmbMateria.setValue(n.getNombreMateria());
    }

    @FXML
    private void buscar(KeyEvent event) {
        ObservableList<Notas> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaNotas.setItems(listaNotas);
        } else {
            registrosFiltrados.clear();
            for (Notas registro : listaNotas) {
                if (registro.getNombreAlumno().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreGrupo().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreMateria().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaNotas.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtNota.setDisable(false);
        txtFecha.setDisable(false);
        txtFecha.setValue(LocalDate.now());
        cmbMateria.setDisable(false);
        cmbAlumno.setDisable(false);
        cmbCurso.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarAlumno();
        cargarCurso();
        cargarMateria();
        cmbMateria.setPromptText("Seleccione Materia");
        cmbAlumno.setPromptText("Seleccione Alumno");
        cmbCurso.setPromptText("Seleccione Grupo");
        txtFecha.setPromptText("Fecha de examen");
    }

    private void cargarMateria() {
        cmbMateria.getItems().clear();
        listaMateria = FXCollections.observableArrayList(m.consulta());
        for (Materia objecto : listaMateria) {
            cmbMateria.getItems().add(objecto.getNombre());
        }
    }

    private int buscarMateria() {
        String seleccionado = cmbMateria.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return 0;
        }
        for (Materia object : listaMateria) {
            if (object.getNombre().equals(seleccionado)) {
                return object.getId();
            }
        }
        return 0;
    }

    @FXML
    private void modificar(ActionEvent event) {
        cmbMateria.setDisable(false);
        cmbAlumno.setDisable(false);
        cmbCurso.setDisable(false);
        txtNota.setDisable(false);
        btnMod.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        bandera = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Aviso de Borrado");
        alerta.setHeaderText(null);
        alerta.setContentText("Desea eliminar el registro seleccionado?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            n.setId(Integer.parseInt(txtId.getText()));
            if (n.eliminar()) {
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
        int notas=-1;
        int idAlumno;
        int idCurso;
        int idMateria;
        String fecha;
        try {
            notas = Integer.parseInt(txtNota.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(ERROR,"El sistema comunica: ","Revise los campos numericos");
        }
        idAlumno = buscarAlumno();
        idCurso = buscarCurso();
        idMateria = buscarMateria();
        fecha = txtFecha.getValue().toString();
        n.setIdAlumno(idAlumno);
        n.setIdMateria(idMateria);
        n.setIdCurso(idCurso);
        n.setNota_obtenida(notas);
        n.setFecha(fecha);
        if (bandera) {
            n.setId(Integer.parseInt(txtId.getText()));
            if (n.modificar()) {
                mostrarAlerta(INFORMATION,"El sistema comunica:","Datos modicados correctamente");
                cancelar(event);
            } else {
                mostrarAlerta(INFORMATION,"El sistema comunica:","Datos no modicados");
            }
            bandera = false;
        } else {
            if (n.insertar()) {
                mostrarAlerta(INFORMATION,"El sistema comunica:","Registro insertado correctamente");
                cancelar(event);
            } else {
                mostrarAlerta(INFORMATION,"El sistema comunica:","Registro no modicados");
            }
        }
        mostrarDatos();
    }

    @FXML
    private void cancelar(ActionEvent event
    ) {
        cmbMateria.setDisable(true);
        cmbAlumno.setDisable(true);
        cmbCurso.setDisable(true);
        cmbMateria.setPromptText("Seleccione Materia");
        cmbAlumno.setPromptText("Seleccione Alumno");
        cmbCurso.setPromptText("Seleccione Grupo");
        txtFecha.setValue(null);
        txtFecha.setDisable(true);
        // Limpiar los elementos de los ComboBox
        cmbMateria.getItems().clear();
        cmbAlumno.getItems().clear();
        cmbCurso.getItems().clear();
        txtNota.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnMod.setDisable(true);
        btnNuevo.setDisable(false);
        txtNota.clear();
        txtId.clear();
    }
    public boolean validarCampos() {

        if (esCampoVacio(txtNota, "El campo de pago por hora está vacío.")) {
            return true;
        }
        if(txtFecha.getValue()==null){
            mostrarAlerta(ERROR,"El sistema comunica: ","El campo de fecha esta vacio");
            return true;
        }
        if(cmbAlumno.getValue()==null){
            mostrarAlerta(ERROR,"El sistema comunica: ","El campo de alumno esta vacion");
            return true;
        }if(cmbCurso.getValue()==null){
            mostrarAlerta(ERROR,"El sistema comunica: ","El campo de curso esta vacio");
            return true;
        }if(cmbMateria.getValue()==null){
            mostrarAlerta(ERROR,"El sistema comunica: ","El campo de materia esta vacio");
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
    @FXML
    private void materia(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("materia.fxml","ABM Materia");
        stage.close();
    }
}
