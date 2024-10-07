/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.Materia;
import com.mycompany.cursilloampere.modelo.Profesor;
import com.mycompany.cursilloampere.modelo.detalle_materia_profesor;
import com.mycompany.cursilloampere.modelo.detalle_pago_profesor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * @author sebas
 */
public class DetallePagoProfesorController implements Initializable {

    @FXML
    private TableView<detalle_pago_profesor> tablaPagos;
    @FXML
    private TableColumn<detalle_pago_profesor, Double> colPagoHora;
    @FXML
    private TableColumn<detalle_pago_profesor, Double> colPagoTotal;
    @FXML
    private TableColumn<detalle_pago_profesor, String> colGrupo;
    @FXML
    private TableColumn<detalle_pago_profesor, String> colMateria;
    @FXML
    private TableColumn<detalle_pago_profesor, String> colProfesor;
    @FXML
    private TableColumn<detalle_pago_profesor, String> colFecha;
    @FXML
    private TableColumn<detalle_pago_profesor, String> colFechaPago;
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
    private ComboBox<String> cmbClase;
    @FXML
    private TextField txtPagoHora;
    @FXML
    private DatePicker txtFecha;
    /**
     * Initializes the controller class.
     */
    ObservableList<detalle_pago_profesor> listaDetalle_pago_profesor;
    ObservableList<detalle_materia_profesor> listaClase;
    ObservableList<Profesor> listaProfesor;
    ObservableList<Curso> listaCurso;
    ObservableList<Materia> listaMateria;
    detalle_pago_profesor dpp = new detalle_pago_profesor();
    detalle_materia_profesor dmp = new detalle_materia_profesor();
    Profesor p = new Profesor();
    Curso c = new Curso();
    Materia m = new Materia();
    boolean bandera = false;
    @FXML
    private TextField txtPagoTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }

    public void mostrarDatos() {
        listaDetalle_pago_profesor = FXCollections.observableArrayList(dpp.consulta());
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("NombreCurso"));
        colProfesor.setCellValueFactory(new PropertyValueFactory<>("NombreProfesor"));
        colMateria.setCellValueFactory(new PropertyValueFactory<>("NombreMateria"));
        colFechaPago.setCellValueFactory(new PropertyValueFactory<>("FechaPago"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("FechaClase"));
        colPagoHora.setCellValueFactory(new PropertyValueFactory<>("PagoHora"));
        colPagoTotal.setCellValueFactory(new PropertyValueFactory<>("PagoTotal"));
        tablaPagos.setItems(listaDetalle_pago_profesor);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        detalle_pago_profesor dpp = tablaPagos.getSelectionModel().getSelectedItem();
        txtPagoHora.setText(String.valueOf(dpp.getPagoHora()));
        txtPagoTotal.setText(String.valueOf(dpp.getPagoTotal()));
        txtFecha.setValue(LocalDate.parse(dpp.getFechaPago(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        cargarClase();
        cmbClase.setValue(dpp.getNombreProfesor() + " " + dpp.getNombreCurso() + " " + dpp.getNombreMateria() + " " + dpp.getFechaClase());
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
    }

    @FXML
    private void buscar(KeyEvent event) {
        ObservableList<detalle_pago_profesor> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaPagos.setItems(listaDetalle_pago_profesor);
        } else {
            registrosFiltrados.clear();
            for (detalle_pago_profesor registro : listaDetalle_pago_profesor) {
                if (registro.getNombreProfesor().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreCurso().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreMateria().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaPagos.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtPagoHora.setDisable(false);
        txtFecha.setDisable(false);
        txtFecha.setValue(LocalDate.now());
        cmbClase.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        cargarClase();
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtPagoHora.setDisable(false);
        txtFecha.setDisable(false);
        cmbClase.setDisable(false);
        btnMod.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        bandera = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (!validarCampos()) {
            double pagoHora;
            double pagoTotal;
            try{
                pagoHora=Double.parseDouble(txtPagoHora.getText().replace(",","."));
                pagoTotal=Double.parseDouble(txtPagoTotal.getText().replace(",","."));
            }catch(NumberFormatException e){
                mostrarAlerta(ERROR, "El sistema comunica:", "Asegurese de completar correctamente los campos numericos");
                return;
            }
            dpp.setPagoHora(pagoHora);
            dpp.setPagoTotal(pagoTotal);
            dpp.setFechaPago(txtFecha.getValue().toString());
            dpp.setIdClase(buscarClase());
            if (bandera) {
                if (dpp.modificar()) {
                    mostrarAlerta(INFORMATION, "El sistema comunica:", "Modificado correctamente");
                    cancelar(event);
                } else {
                    mostrarAlerta(ERROR, "El sistema comunica:", "Error, registro no modificado");
                }
                bandera = false;
            } else {
                if (dpp.insertar()) {
                    mostrarAlerta(INFORMATION, "El sistema comunica:", "Insertado correctamente");
                    cancelar(event);
                } else {
                    mostrarAlerta(ERROR, "El sistema comunica:", "Error, datos no insertados");
                }
            }
        }
        mostrarDatos();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtPagoHora.clear();
        txtPagoTotal.clear();
        txtFecha.setValue(null);
        cmbClase.setValue(null);
        txtPagoHora.setDisable(true);
        txtFecha.setDisable(true);
        cmbClase.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnMod.setDisable(true);
        btnNuevo.setDisable(false);
    }

    private void cargarClase() {
        cmbClase.getItems().clear();
        listaDetalle_pago_profesor = FXCollections.observableArrayList(dpp.consulta());
        listaClase = FXCollections.observableArrayList(dmp.consulta());

        for (detalle_materia_profesor objeto : listaClase) {
            boolean clasepagada = false;
            for (detalle_pago_profesor object : listaDetalle_pago_profesor) {
                if (object.getIdClase() == objeto.getId()) {
                    clasepagada = true;
                    break;
                }
            }
            if (!clasepagada) {
                cmbClase.getItems().add(objeto.getNombreProfesor() + " " + objeto.getNombreCurso() + " " + objeto.getNombreMateria() + " " + objeto.getFecha());
            }
        }
    }

    private int buscarClase() {
        String seleccionado = cmbClase.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return 0;
        }
        for (detalle_materia_profesor objeto : listaClase) {
            if ((objeto.getNombreProfesor() + " " + objeto.getNombreCurso() + " " + objeto.getNombreMateria() + " " + objeto.getFecha()).equals(seleccionado)) {
                return objeto.getId();
            }
        }
        return 0;
    }

    @FXML
    private int calcularPagos() {
        double horastrabajadas = 0;
        int minutostrabajados = 0;
        int entrada = 0;
        int salida = 0;
        int minsal = 0;
        int minen = 0;
        String Id = String.valueOf(buscarClase());
        if (Id == null || txtPagoHora.getText() == null) {
            return 0;
        }
        listaClase = FXCollections.observableArrayList(dmp.consulta());
        for (detalle_materia_profesor objeto : listaClase) {
            if (String.valueOf(objeto.getId()).equals(Id)) {
                entrada = Integer.parseInt(objeto.getHr_entrada().substring(0, objeto.getHr_entrada().indexOf(":")));
                salida = Integer.parseInt(objeto.getHr_salida().substring(0, objeto.getHr_salida().indexOf(":")));
                minsal = Integer.parseInt(objeto.getHr_salida().substring(objeto.getHr_salida().indexOf(":") + 1, objeto.getHr_salida().indexOf(":") + 3));
                minen = Integer.parseInt(objeto.getHr_entrada().substring(objeto.getHr_entrada().indexOf(":") + 1, objeto.getHr_entrada().indexOf(":") + 3));
                minutostrabajados = (salida - entrada) * 60 + (minsal - minen);
                horastrabajadas = minutostrabajados / 60.0;
                txtPagoTotal.setText(String.valueOf(horastrabajadas * (Double.parseDouble(txtPagoHora.getText()))));
                return 0;
            }
        }
        return 0;
    }

    public boolean validarCampos() {

        if (esCampoVacio(txtPagoHora, "El campo de pago por hora está vacío.")) {
            return true;
        }
        if (txtFecha.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Fecha no seleccionada");
            return true;
        }
        if (cmbClase.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campo de seleccion de clase vacio");
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

    public void abrirMenu(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent menuRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/menu.fxml"));
            Scene menuScene = new Scene(menuRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(menuScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirCurso(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent cursoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/curso.fxml"));
            Scene cursoScene = new Scene(cursoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(cursoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirMateria(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent materiaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/materia.fxml"));
            Scene materiaScene = new Scene(materiaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(materiaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirAlumno(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent alumnoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/alumno.fxml"));
            Scene alumnoScene = new Scene(alumnoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(alumnoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirProfesor(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent profesoresRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/profesores.fxml"));
            Scene profesoresScene = new Scene(profesoresRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(profesoresScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirPago(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent pagoRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/pago.fxml"));
            Scene pagoScene = new Scene(pagoRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(pagoScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirrFactura(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent facturaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/factura.fxml"));
            Scene facturaScene = new Scene(facturaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(facturaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirNotas(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent notasRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/notas.fxml"));
            Scene notasScene = new Scene(notasRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(notasScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
