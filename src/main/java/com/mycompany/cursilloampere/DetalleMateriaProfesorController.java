/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.Materia;
import com.mycompany.cursilloampere.modelo.Profesor;
import com.mycompany.cursilloampere.modelo.detalle_materia_profesor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class DetalleMateriaProfesorController implements Initializable {

    @FXML
    private TableColumn<detalle_materia_profesor, String> colGrupo;
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
    private ComboBox<String> cmbCurso;
    @FXML
    private TableView<detalle_materia_profesor> tablaClases;
    @FXML
    private TableColumn<detalle_materia_profesor, String> colProfesor;
    @FXML
    private TableColumn<detalle_materia_profesor, String> colMateria;
    @FXML
    private TableColumn<detalle_materia_profesor, Integer> colDuracion;
    @FXML
    private ComboBox<String> cmbProfesor;
    @FXML
    private TextField txtDuracion;
    @FXML
    private ComboBox<String> cmbMateria;
    ObservableList<detalle_materia_profesor> listaDetalle_materia_profesor;
    ObservableList<Profesor> listaProfesor;
    ObservableList<Curso> listaCurso;
    ObservableList<Materia> listaMateria;
    detalle_materia_profesor dmp = new detalle_materia_profesor();
    Profesor p = new Profesor();
    Curso c = new Curso();
    Materia m = new Materia();
    boolean bandera = false;
    @FXML
    private TableColumn<detalle_materia_profesor, String> colFecha;
    @FXML
    private TableColumn<detalle_materia_profesor, Integer> colId;
    @FXML
    private TextField txtId;
    @FXML
    private DatePicker txtFecha;
    @FXML
    private Spinner<Integer> spHrEn;
    @FXML
    private Spinner<Integer> spMinEn;
    ;
    @FXML
    private Spinner<Integer> spHrSal;
    @FXML
    private Spinner<Integer> spMinSal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spHrEn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spMinEn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spHrSal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spMinSal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        mostrarDatos();
    }

    public void mostrarDatos() {
        listaDetalle_materia_profesor = FXCollections.observableArrayList(dmp.consulta());
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colProfesor.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
        colMateria.setCellValueFactory(new PropertyValueFactory<>("nombreMateria"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("Duracion"));
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tablaClases.setItems(listaDetalle_materia_profesor);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        detalle_materia_profesor dmp = tablaClases.getSelectionModel().getSelectedItem();
        txtDuracion.setText(String.valueOf(dmp.getDuracion()));
        LocalDate fecha = LocalDate.parse(dmp.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtId.setText(String.valueOf(dmp.getId()));
        // Asignar valores de entrada
        String HrEn = dmp.getHr_entrada();
        spHrEn.getValueFactory().setValue(Integer.parseInt(HrEn.substring(0, HrEn.indexOf(":"))));
        spMinEn.getValueFactory().setValue(Integer.parseInt(HrEn.substring(HrEn.indexOf(":") + 1, HrEn.indexOf(":") + 3)));

        // Asignar valores de salida
        String HrSal = dmp.getHr_salida();
        spHrSal.getValueFactory().setValue(Integer.parseInt(HrSal.substring(0, HrSal.indexOf(":"))));
        spMinSal.getValueFactory().setValue(Integer.parseInt(HrSal.substring(HrSal.indexOf(":") + 1, HrSal.indexOf(":") + 3)));
        txtFecha.setValue(fecha);
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarProfesor();
        cargarCurso();
        cargarMateria();
        cmbProfesor.setValue(dmp.getNombreProfesor());
        cmbCurso.setValue(dmp.getNombreCurso());
        cmbMateria.setValue(dmp.getNombreMateria());
    }

    @FXML
    private void buscar(KeyEvent event) {
        ObservableList<detalle_materia_profesor> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaClases.setItems(listaDetalle_materia_profesor);
        } else {
            registrosFiltrados.clear();
            for (detalle_materia_profesor registro : listaDetalle_materia_profesor) {
                if (registro.getNombreProfesor().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreCurso().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreMateria().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaClases.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtFecha.setDisable(false);
        txtFecha.setValue(LocalDate.now());
        spHrEn.setDisable(false);
        spHrSal.setDisable(false);
        spMinEn.setDisable(false);
        spMinSal.setDisable(false);
        cmbMateria.setDisable(false);
        cmbProfesor.setDisable(false);
        cmbCurso.setDisable(false);
        txtDuracion.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarProfesor();
        cargarCurso();
        cargarMateria();
        cmbMateria.setPromptText("Seleccione Materia");
        cmbProfesor.setPromptText("Seleccione Profesor");
        cmbCurso.setPromptText("Seleccione Grupo");
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtFecha.setDisable(false);
        spHrEn.setDisable(false);
        spHrSal.setDisable(false);
        spMinEn.setDisable(false);
        spMinSal.setDisable(false);
        cmbMateria.setDisable(false);
        cmbProfesor.setDisable(false);
        cmbCurso.setDisable(false);
        txtDuracion.setDisable(false);
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
            dmp.setId(Integer.parseInt(txtId.getText()));
            if (dmp.eliminar()) {
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
            int horas;
            try {
                horas = Integer.parseInt(txtDuracion.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(ERROR, "El sistema comunica:", "Asegurese de completar correctamente los campos numericos");
                return;
            }
            int idProfesor = buscarProfesor();
            int idCurso = buscarCurso();
            int idMateria = buscarMateria();
            String entrada = String.format("%02d:%02d:00", spHrEn.getValue(), spMinEn.getValue());
            String salida = String.format("%02d:%02d:00", spHrSal.getValue(), spMinSal.getValue());
            String fecha = txtFecha.getValue().toString();
            if (!validarHorario(entrada, salida)) {
                dmp.setFecha(fecha);
                dmp.setHr_salida(salida);
                dmp.setHr_entrada(entrada);
                dmp.setIdProfesor(idProfesor);
                dmp.setIdMateria(idMateria);
                dmp.setIdCurso(idCurso);
                dmp.setDuracion(horas);
                if (bandera) {
                    dmp.setId(Integer.parseInt(txtId.getText()));
                    if (dmp.modificar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Modificado correctamente");
                        cancelar(event);
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Error, registro no modificado");
                    }
                    bandera = false;
                } else {
                    if (dmp.insertar()) {
                        mostrarAlerta(INFORMATION, "El sistema comunica:", "Insertado correctamente");
                        cancelar(event);
                    } else {
                        mostrarAlerta(ERROR, "El sistema comunica:", "Error, datos no insertados");
                    }
                }
                mostrarDatos();
            }
        }

    }

    @FXML
    private void cancelar(ActionEvent event) {
        spHrEn.setDisable(true);
        spHrSal.setDisable(true);
        spMinEn.setDisable(true);
        spMinSal.setDisable(true);
        txtFecha.setValue(null);
        spHrEn.getValueFactory().setValue(0);
        spMinEn.getValueFactory().setValue(0);
        spHrSal.getValueFactory().setValue(0);
        spMinSal.getValueFactory().setValue(0);
        spHrEn.getEditor().clear();
        spMinEn.getEditor().clear();
        spHrSal.getEditor().clear();
        spMinSal.getEditor().clear();
        txtId.clear();
        txtFecha.setDisable(true);
        cmbMateria.setDisable(true);
        cmbProfesor.setDisable(true);
        cmbCurso.setDisable(true);
        cmbMateria.setPromptText("Seleccione Materia");
        cmbProfesor.setPromptText("Seleccione Profesor");
        cmbCurso.setPromptText("Seleccione Grupo");
        // Limpiar los elementos de los ComboBox
        cmbMateria.getItems().clear();
        cmbProfesor.getItems().clear();
        cmbCurso.getItems().clear();
        txtDuracion.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnMod.setDisable(true);
        btnNuevo.setDisable(false);
        txtDuracion.clear();
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

    private void cargarProfesor() {
        cmbProfesor.getItems().clear();
        listaProfesor = FXCollections.observableArrayList(p.consulta());
        for (Profesor object : listaProfesor) {
            cmbProfesor.getItems().add(object.getNombre() + " " + object.getApellido());
        }
    }

    private int buscarProfesor() {
        String Profesores = cmbProfesor.getSelectionModel().getSelectedItem();
        if (Profesores == null) {
            return 0;
        }
        String nombre = Profesores.substring(0, Profesores.indexOf(" "));
        String apellido = Profesores.substring(Profesores.indexOf(" ") + 1);
        for (Profesor object : listaProfesor) {
            if (object.getNombre().contains(nombre) && object.getApellido().contains(apellido)) {
                return object.getId();
            }
        }
        return 0;
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

    public boolean validarCampos() {

        if (esCampoVacio(txtDuracion, "El campo de duracion está vacío.")) {
            return true;
        }
        if (txtFecha.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Fecha no seleccionada");
            return true;
        }
        if ((spHrEn.getValue() == null && spMinEn.getValue() == null) || (spHrSal.getValue() == null && spMinSal.getValue() == null)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos de horario vacios");
            return true;
        }
        if (cmbProfesor.getValue() == null || cmbCurso.getValue() == null || cmbMateria.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos de seleccion vacios");
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

    public boolean validarHorario(String entrada, String salida) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Convertir las cadenas de texto en objetos LocalTime
            LocalTime horaEntrada = LocalTime.parse(entrada, formatter);
            LocalTime horaSalida = LocalTime.parse(salida, formatter);

            // Validar si la salida es mayor o igual a la entrada
            if (horaSalida.isBefore(horaEntrada)) {
                mostrarAlerta(ERROR, "El sistema comunica:", "La hora de salida no puede ser menor que la hora de entrada.");
                return true;
            }
            if(entrada.equals(salida)){
                mostrarAlerta(ERROR, "El sistema comunica:", "La hora de entrada y salida no pueden ser iguales.");
                return true;
            }
            return false;  // No hay error
        } catch (DateTimeParseException e) {
            // Si ocurre un error de formato, muestra un mensaje
            mostrarAlerta(ERROR, "El sistema comunica:", "Formato de hora no válido. Debe ser HH:mm:ss.");
            return true;
        }
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
    private void notas(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("Notas.fxml","ABM Notas");
       stage.close();
    }
    private void pagos(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       abrirFxml("detalle_pago_profesor.fxml","ABM Pagos🤑");
       stage.close();
    }
    private void factura(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("factura.fxml","ABM Factura");
        stage.close();
    }

    @FXML
    private void reportes(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("reportes.fxml","ABM Reportes");
        stage.close();
    }

    @FXML
    private void menu(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("menu.fxml","ABM Menu");
        stage.close();
    }
}
