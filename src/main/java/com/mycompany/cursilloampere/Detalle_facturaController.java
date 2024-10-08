/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.Factura;
import com.mycompany.cursilloampere.modelo.detalle_factura;
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
public class Detalle_facturaController implements Initializable {

    private TableColumn<detalle_factura, String> colGrupo;
    private TableColumn<detalle_factura, String> colAlumno;
    private TableColumn<detalle_factura, Integer> colCuota;
    private TextField txtBuscar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnMod;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    private Button btnCancelar;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private TextField txtCuota;
    private TableView<detalle_factura> tablaFactura;
    private TableColumn<detalle_factura, Integer> colFactura;
    @FXML
    private ComboBox<String> cmbFactura;
    private TextField txtPago;
    ObservableList<detalle_factura> listaDetalleFactura;
    ObservableList<Factura> listaFactura;
    ObservableList<Curso> listaCurso;
    int Cactual;
    Factura f = new Factura();
    Curso c = new Curso();
    detalle_factura df = new detalle_factura();
    boolean bandera = false;
    int Factual;
    private TableColumn<detalle_factura, Double> colMonto;
    @FXML
    private TextField Pago;
    @FXML
    private Button bntCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }    
    public void mostrarDatos() {
        listaDetalleFactura = FXCollections.observableArrayList(df.consulta());
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colAlumno.setCellValueFactory(new PropertyValueFactory<>("nombreAlumno"));
        colCuota.setCellValueFactory(new PropertyValueFactory<>("Nro_cuota"));
        colFactura.setCellValueFactory(new PropertyValueFactory<>("Factura"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("Pago"));
        tablaFactura.setItems(listaDetalleFactura);
    }
    @FXML
    private void mostrarFila(MouseEvent event) {
        detalle_factura df = tablaFactura.getSelectionModel().getSelectedItem();
        txtCuota.setText(String.valueOf(df.getNro_cuota()));
        txtPago.setText(String.valueOf(df.getPago()));
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);

        cargarFactura();
        cargarCurso();
        cmbFactura.setValue(String.valueOf(df.getFactura()));
        cmbCurso.setValue(df.getNombreCurso());
    }

    @FXML
    private void buscar(KeyEvent event) {
        ObservableList<detalle_factura> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaFactura.setItems(listaDetalleFactura);
        } else {
            registrosFiltrados.clear();
            for (detalle_factura registro : listaDetalleFactura) {
                if (registro.getNombreAlumno().toLowerCase().contains(buscar.toLowerCase()) || registro.getNombreCurso().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaFactura.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        cmbFactura.setDisable(false);
        cmbCurso.setDisable(false);
        txtPago.setDisable(false);
        txtCuota.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarFactura();
        cargarCurso();
        cmbFactura.setPromptText("Seleccione factura");
        cmbCurso.setPromptText("Seleccione Curso");
    }

    @FXML
    private void modificar(ActionEvent event) {
        cmbFactura.setDisable(false);
        cmbCurso.setDisable(false);
        txtPago.setDisable(false);
        txtCuota.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        Cactual=buscarCurso();
        Factual=buscarFactura();
        bandera = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("El sistema comunica:");
        alerta.setHeaderText(null);
        alerta.setContentText("Error. Los detalles de Facturas no se pueden dar de baja.");
        alerta.show();
        cancelar(event);
    }

    @FXML
    private void guardar(ActionEvent event) {
        int Cuota;
        double monto;
        try{
            Cuota = Integer.parseInt(txtCuota.getText());
            monto= Double.parseDouble(txtPago.getText());
        }catch(NumberFormatException e){
            mostrarAlerta(ERROR,"El sistema comunica: ", "Revise los campos numericos");
            return;
        }
        int factura = buscarFactura();
        int curso = buscarCurso();
        df.setFactura(factura);
        df.setCurso_id(curso);
        df.setNro_cuota(Cuota);
        df.setPago(monto);
        if (bandera) {
            df.setFactual(Factual);
            df.setCactual(Cactual);
            if (df.modificar()) {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Datos modificados exitosamente");
                cancelar(event);
            } else {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Datos no modificados");
            }
            bandera = false;
        } else {
            if (df.insertar()) {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Insertados correctamente en la base de datos");
                cancelar(event);
            } else {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Registro no insertados");
            }
        }
        mostrarDatos();
    }

    private void cancelar(ActionEvent event) {
        cmbFactura.setPromptText("Seleccione factura");
        cmbCurso.setPromptText("Seleccione curso");
        cmbFactura.getItems().clear();
        cmbCurso.getItems().clear();
        txtCuota.clear();
        txtPago.clear();
        txtCuota.setDisable(true);
        txtPago.setDisable(true);
        cmbCurso.setDisable(true);
        cmbFactura.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
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
    private void cargarCurso() {
        cmbCurso.getItems().clear();
        listaCurso = FXCollections.observableArrayList(c.consulta());
        for (Curso objecto : listaCurso) {
            cmbCurso.getItems().add(objecto.getNombre());
        }
    }
    private void cargarFactura() {
        cmbFactura.getItems().clear();
        listaFactura = FXCollections.observableArrayList(f.consulta());
        for (Factura object : listaFactura) {
            cmbFactura.getItems().add(String.valueOf(object.getId()));
        }
    }
    private int buscarFactura() {
        String seleccionado = cmbFactura.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return 0;
        }
        for (Factura object : listaFactura) {
            if ((String.valueOf(object.getId())).equals(seleccionado)) {
                return object.getId();
            }
        }
        return 0;
    }
    public boolean validarCampos() {

        if (esCampoVacio(txtCuota, "El campo de cédula está vacío.")
                || esCampoVacio(txtPago, "El campo de Nombre está vacío.")) {
            return true;
        }

        if (cmbCurso.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campo de seleccion de curso vacio");
            return true;
        }
        if (cmbFactura.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campo de seleccion de factura es vacio");
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

    @FXML
    private void alumno(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("alumno.fxml", "ABM Alumnos");
        stage.close();
    }

    @FXML
    private void profesor(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("profesores.fxml", "ABM Profesor");
        stage.close();
    }

    @FXML
    private void materia(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("materia.fxml", "ABM Materia");
        stage.close();
    }

    @FXML
    private void curso(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("curso.fxml", "ABM Curso");
        stage.close();
    }

    @FXML
    private void aula(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("aula.fxml", "ABM Aula");
        stage.close();
    }

    private void notas(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("Notas.fxml", "ABM Notas");
        stage.close();
    }

    @FXML
    private void pagos(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("detalle_pago_profesor.fxml", "ABM Pagos🤑");
        stage.close();
    }

    @FXML
    private void factura(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("factura.fxml", "ABM Factura");
        stage.close();
    }

    @FXML
    private void reportes(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("abrirReportes.fxml", "ABM Reportes");
        stage.close();
    }

    @FXML
    private void menu(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("menu.fxml", "ABM Menu");
        stage.close();
    }

}
