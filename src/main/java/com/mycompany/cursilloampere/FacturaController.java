/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.clases.reportes;
import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.Factura;
import com.mycompany.cursilloampere.modelo.detalle_cuota;
import com.mycompany.cursilloampere.modelo.detalle_factura;
import java.io.IOException;
import java.net.URL;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
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
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
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
public class FacturaController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TableView<Factura> tablaFactura;
    @FXML
    private TableColumn<Factura, Integer> colId;
    @FXML
    private TableColumn<Factura, String> colFecha;
    @FXML
    private TableColumn<Factura, String> colConcepto;
    @FXML
    private TableColumn<Factura, Integer> colRuc;
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
    private DatePicker txtFecha;
    ObservableList<Factura> listaFactura;
    ObservableList<detalle_factura> listaDetalleFactura;
    ObservableList<Curso> listaCurso;
    ObservableList<Alumno> listaAlumno;
    ObservableList<detalle_cuota> listaDetalleCuota;
    ObservableList<String> alumnoNombres = FXCollections.observableArrayList();
    detalle_cuota dc = new detalle_cuota();
    Alumno a = new Alumno();
    Curso c = new Curso();
    Factura f = new Factura();
    detalle_factura df = new detalle_factura();
    private final ContextMenu contextMenu = new ContextMenu();
    boolean bandera = false;
    @FXML
    private TableColumn<Factura, String> colAlumno;
    @FXML
    private TextField txtAlumno;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<detalle_factura> tablaFactura2;
    @FXML
    private TableColumn<detalle_factura, Integer> colNroCuotas;
    @FXML
    private TableColumn<detalle_factura, Double> colTotal;
    @FXML
    private TableColumn<detalle_factura, String> colGrupo;
    @FXML
    private Button btnNuevo2;
    @FXML
    private Button btnEliminar2;
    @FXML
    private CheckBox chkmatricula;
    @FXML
    private TextField txtPago;
    @FXML
    private Button btnCancelar2;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private TextField txtCuota;
    @FXML
    private Button btnMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        cargarNombresAlumnos();
        listaDetalleFactura = FXCollections.observableArrayList();
    }

    public void mostrarDatos() {
        listaFactura = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colRuc.setCellValueFactory(new PropertyValueFactory<>("Ruc"));
        colConcepto.setCellValueFactory(new PropertyValueFactory<>("Concepto"));
        colAlumno.setCellValueFactory(new PropertyValueFactory<>("NombreAlumno"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tablaFactura.setItems(listaFactura);
    }

    public void mostrarDatos2() {
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colNroCuotas.setCellValueFactory(new PropertyValueFactory<>("Nro_cuota"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Pago"));
        tablaFactura2.setItems(listaDetalleFactura);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        Factura f = tablaFactura.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(f.getId()));
        LocalDate fecha = LocalDate.parse(f.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtFecha.setValue(fecha);
        txtAlumno.setText(f.getNombreAlumno());
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        listaDetalleFactura = FXCollections.observableArrayList(df.consulta());

    }

    private void mostrarFila2(MouseEvent event) {
        detalle_factura df = tablaFactura2.getSelectionModel().getSelectedItem();
        if (df != null) {
            txtCuota.setText(String.valueOf(df.getNro_cuota()));
            txtPago.setText(String.valueOf(df.getPago()));
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);
            cargarCurso();
            cmbCurso.setValue(df.getNombreCurso());
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtAlumno.setDisable(false);
        txtId.setText(String.valueOf(f.Auto_incremento()));
        txtFecha.setDisable(false);
        txtFecha.setValue(LocalDate.now());
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        btnAgregar.setDisable(false);
    }

    @FXML
    private void Agregar(ActionEvent event) {
        btnNuevo2.setDisable(false);
        cmbCurso.setDisable(false);
        chkmatricula.setDisable(false);
        txtPago.setDisable(false);
        txtCuota.setDisable(false);
        btnCancelar.setDisable(false);
        btnAgregar.setDisable(false);
        cargarCurso();
        cmbCurso.setPromptText("Seleccione Curso");
    }

    @FXML
    private void modificar(ActionEvent event) {
        txtFecha.setDisable(false);
        btnMod.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        bandera = true;
    }

    @FXML
    private void guardar(ActionEvent event) {
        int alumno = buscarAlumno();
        f.setAlumno(alumno);
        String fecha = txtFecha.getValue().toString();
        f.setFecha(fecha);
        f.setConcepto("Pago de cuota");
        if (bandera) {//modificar
            int id = Integer.parseInt(txtId.getText());
            f.setId(id);
            if (f.modificar()) {
                System.out.println("aqui llego");
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Modificado correctamente");
                alerta.show();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Error. Registro no modificado.");
                alerta.show();

            }
            bandera = false;
        } else {
            if (f.insertar()) {//insertado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Insertado correctamente en la base de datos");
                alerta.show();
                int idF = Integer.parseInt(txtId.getText());
                insertarDetalle(idF);
                cancelar(event);
                cancelar2(event);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Error. Registro no insertado.");
                alerta.show();
            }
        }
        mostrarDatos();
    }

    private void insertarDetalle(int idF) {
        df.setFactura(idF);  // Asignamos el ID de factura a la entidad
        for (detalle_factura dtf : listaDetalleFactura) {
            // Asignamos los valores de cada detalle de factura a df
            df.setCurso_id(dtf.getCurso_id());
            df.setPago(dtf.getPago());
            df.setNro_cuota(dtf.getNro_cuota());

            // Intentamos insertar el detalle
            if (df.insertar()) {
                // Si la inserción fue exitosa, mostramos un mensaje de éxito
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Detalles insertados en la base de datos.");
                alerta.show();
            } else {
                // Si la inserción falla, mostramos un mensaje de error y salimos del ciclo
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("El sistema comunica:");
                alerta.setHeaderText(null);
                alerta.setContentText("Error. Registro no insertado para el curso ID: " + dtf.getCurso_id());
                alerta.show();
                break;  // Salimos del ciclo si ocurre un error
            }
        }
    }

    @FXML
    private void Agregaralatabladetalle(ActionEvent event) {
        try {
            String curso = cmbCurso.getSelectionModel().getSelectedItem();
            int idCurso = buscarCurso();
            int nrocuotas = Integer.parseInt(txtCuota.getText());
            double total = Double.parseDouble(txtPago.getText().replace(",", "."));
            int IdFactura = Integer.parseInt(txtId.getText());
            System.out.println("Curso: " + curso + "idCurso" + idCurso + "Cuotas" + nrocuotas + "total" + total + "Factura" + IdFactura);
            detalle_factura dtf = new detalle_factura(IdFactura, idCurso, total, nrocuotas, curso);
            listaDetalleFactura.add(dtf);
            tablaFactura.refresh();
            mostrarDatos2();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El detalle de factura se ha agregado correctamente.");
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al agregar el detalle de factura.");
        }
    }

    @FXML
    private void pagoMensual(KeyEvent event) {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        listaCurso = FXCollections.observableArrayList(new Curso().consulta());
        listaDetalleCuota = FXCollections.observableArrayList(new detalle_cuota().consulta());
        int CuotasDelAlumno = 0;
        int idA = buscarAlumno();
        for (detalle_cuota dc : listaDetalleCuota) {
            if (dc.getGrupo().equals(seleccionado) && dc.getIdAlumno() == idA) {
                CuotasDelAlumno = dc.getNro_cuota();
            }
        }
        int NumeroCuotaPagar = Integer.parseInt(txtCuota.getText());
        double operacion = 0;
        for (Curso curso : listaCurso) {
            if (curso.getNombre().equals(seleccionado)) {
                operacion = curso.getCosto() / CuotasDelAlumno;
                txtPago.setText(String.format("%.3f", operacion * NumeroCuotaPagar));
                break;
            }
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        txtAlumno.setDisable(true);
        cmbCurso.setDisable(true);
        cmbCurso.setValue(null);
        cmbCurso.setPromptText("Seleccione curso");
        txtFecha.setValue(null);
        txtAlumno.clear();
        txtCuota.clear();
        txtCuota.setDisable(true);
        txtId.clear();
        txtFecha.setDisable(true);
        txtFecha.setPromptText("Seleccione la fecha");
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    @FXML
    private void cancelar2(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        cmbCurso.setValue(null);
        txtCuota.clear();
        txtPago.clear();
        btnNuevo2.setDisable(true);
        chkmatricula.setSelected(false);
        chkmatricula.setDisable(true);
        btnEliminar2.setDisable(true);
        btnCancelar2.setDisable(true);
        cmbCurso.setDisable(true);
        txtCuota.setDisable(true);
        txtPago.setDisable(true);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("El sistema comunica:");
        alerta.setHeaderText(null);
        alerta.setContentText("Error. Las Facturas no se pueden dar de baja.");
        alerta.show();
        cancelar(event);
    }

    @FXML
    private void eliminar2(ActionEvent event) {
        detalle_factura df = tablaFactura2.getSelectionModel().getSelectedItem();
        if (df != null) {
            listaDetalleFactura.remove(df);
            tablaFactura.refresh(); // Refresh table after removing the item
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El detalle de factura se ha eliminado correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay un detalle de factura seleccionado para eliminar.");
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

    private void cargarCurso() {
        cmbCurso.getItems().clear();
        listaCurso = FXCollections.observableArrayList(c.consulta());
        List<Integer> cursosAlumnoIds = dc.obtenerCursosDelAlumno(buscarAlumno());

        for (Curso curso : listaCurso) {
            if (cursosAlumnoIds.contains(curso.getId())) {
                cmbCurso.getItems().add(curso.getNombre());
            }
        }
    }

    public void setAlumno(String Alumno) {
        txtAlumno.clear();
        txtAlumno.setText(Alumno);
    }

    @FXML
    public void Alumno(KeyEvent event) {
        String buscarAlumno = txtAlumno.getText();
        contextMenu.getItems().clear();

        if (!buscarAlumno.isEmpty()) {
            for (String nombreAlumno : alumnoNombres) {
                if (nombreAlumno.toLowerCase().contains(buscarAlumno.toLowerCase())) {
                    MenuItem item = new MenuItem(nombreAlumno);
                    item.setOnAction(e -> txtAlumno.setText(nombreAlumno));
                    contextMenu.getItems().add(item);
                }
            }
            if (!contextMenu.getItems().isEmpty()) {
                contextMenu.show(txtAlumno, Side.BOTTOM, 0, 0);
            } else {
                contextMenu.hide();
            }
        } else {
            contextMenu.hide();
        }
    }

    private void cargarNombresAlumnos() {
        listaAlumno = FXCollections.observableArrayList(a.consulta());
        for (Alumno alumno : listaAlumno) {
            alumnoNombres.add(alumno.getNombre() + " " + alumno.getApellido());
        }
    }

    private int buscarAlumno() {
        listaAlumno = FXCollections.observableArrayList(a.consulta());
        String alumnoNombre = txtAlumno.getText();

        if (alumnoNombre.isEmpty()) {
            return 0;
        }

        String[] nombreApellido = alumnoNombre.split(" ");
        if (nombreApellido.length < 2) {
            return 0;
        }

        String nombre = nombreApellido[0];
        String apellido = nombreApellido[1];

        for (Alumno alumno : listaAlumno) {
            if (alumno.getNombre().equals(nombre) && alumno.getApellido().equals(apellido)) {
                return alumno.getId();
            }
        }
        return 0;
    }

    public boolean validarCampos() {
        if (esCampoVacio(txtAlumno, "El campo de Nombre está vacío.")) {
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

    public void imprimir(String Ubicacion, String Titulo, String Cliente, int nfactura, Double iva) {
        reportes r = new reportes();
        r.generarFactura(Ubicacion, Titulo, Cliente, nfactura, iva);
    }

    @FXML
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

    @FXML
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

    @FXML
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

    @FXML
    public void abrirAula(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de Alumnos
            Parent aulaRoot = FXMLLoader.load(getClass().getResource("/com/mycompany/cursilloampere/aula.fxml"));
            Scene aulaScene = new Scene(aulaRoot);

            // Obtener el Stage actual a partir del evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena del Stage
            window.setScene(aulaScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
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

    @FXML
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
