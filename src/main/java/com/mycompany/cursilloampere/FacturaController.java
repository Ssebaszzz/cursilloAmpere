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
    private DatePicker txtFecha;
    ObservableList<Factura> listaFactura;
    ObservableList<detalle_factura> listaDetalleFactura;
    ObservableList<Curso> listaCurso;
    ObservableList<Alumno> listaAlumno;
    ObservableList<detalle_cuota> listaDetalleCuota;
    Alumno a = new Alumno();
    Curso c = new Curso();
    detalle_cuota dc = new detalle_cuota();
    Factura f = new Factura();
    detalle_factura df = new detalle_factura();
    boolean bandera = false;
    private FXMLLoader loader;
    @FXML
    private TableColumn<Factura, String> colAlumno;
    private TextField txtMonto;
    private TextField txtCuota;
    private ComboBox<String> cmbCurso;
    @FXML
    private TextField txtAlumno;
    private final ContextMenu contextMenu = new ContextMenu();
    ObservableList<String> alumnoNombres = FXCollections.observableArrayList();
    String CuotaTotal;
    private ComboBox<String> cmbConcepto;
    private TextField txtNRuc;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnAgregar;
    private AgregarFacturaController af = new AgregarFacturaController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        cargarNombresAlumnos();
        txtAlumno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) { // Asegúrate de cargar conceptos cada vez que cambie el alumno
            } else {
                cmbConcepto.getItems().clear(); // Limpia los conceptos si no hay alumno
            }
        });
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
    
    private void buscar(KeyEvent event) {
        ObservableList<Factura> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaFactura.setItems(listaFactura);
        } else {
            registrosFiltrados.clear();
            for (Factura registro : listaFactura) {
                if (registro.getConcepto().toLowerCase().contains(buscar.toLowerCase()) || registro.getFecha().toLowerCase().contains(buscar.toLowerCase()) || String.valueOf(registro.getRuc()).toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                } // fin if
            } // fin for
            tablaFactura.setItems(registrosFiltrados);
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
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("El sistema comunica:");
        alerta.setHeaderText(null);
        alerta.setContentText("Error. Las Facturas no se pueden dar de baja.");
        alerta.show();
        cancelar(event);
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        int alumno = buscarAlumno();
        f.setAlumno(alumno);
        String fecha = txtFecha.getValue().toString();
        f.setFecha(fecha);
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
                guardar2(event);
                Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
                alerta1.setTitle("Aviso de Impresion");
                alerta1.setHeaderText(null);
                alerta1.setContentText("Desea factura?");
                Optional<ButtonType> opcion = alerta1.showAndWait();
                if (opcion.get() == ButtonType.OK) {
                    int nfactura = Integer.parseInt(txtId.getText());
                    String cliente = txtNRuc.getText();
                    Double iva = Double.parseDouble(txtMonto.getText().replace(",", ".")) / 11;
                    String Ubicacion = "/reportes/factura.jasper";
                    String Titulo = "Factura N~" + String.valueOf(nfactura);
                    imprimir(Ubicacion, Titulo, cliente, nfactura, iva);
                }
                cancelar(event);
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
    
    @FXML
    private void cancelar(ActionEvent event) {
        txtAlumno.setDisable(true);
        cmbCurso.setDisable(true);
        cmbCurso.setValue(null);
        cmbCurso.setPromptText("Seleccione curso");
        cmbConcepto.setDisable(true);
        cmbConcepto.setValue(null);
        cmbConcepto.setPromptText("Seleccione Concepto");
        txtFecha.setValue(null);
        txtMonto.clear();
        txtAlumno.clear();
        txtMonto.setDisable(true);
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
    
    private void guardar2(ActionEvent event) {
        int factura = Integer.parseInt(txtId.getText());
        int curso = buscarCurso();
        int Cuota = Integer.parseInt(txtCuota.getText());
        double monto = Double.parseDouble((txtMonto.getText().replace(",", ".")));
        df.setFactura(factura);
        df.setCurso_id(curso);
        df.setNro_cuota(Cuota);
        df.setPago(monto);
        if (df.insertar()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("El sistema comunica:");
            alerta.setHeaderText(null);
            alerta.setContentText("Detalles insetados en la base de datos");
            alerta.show();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("El sistema comunica:");
            alerta.setHeaderText(null);
            alerta.setContentText("Error. Registro no insertado.");
            alerta.show();
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
    
    public void abrirFxml(String fxml, String titulo) {
        try {
            loader = new FXMLLoader(getClass().getResource(fxml)); // Inicializar loader aquí
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void pagoMensual(ActionEvent event) {
//        encontrarCuotas(event);
//        String tipoConcepto = buscarConcepto();
//        listaDetalleCuota = FXCollections.observableArrayList(dc.consulta());
//        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
//        String alumno = txtAlumno.getText();
//        int cuotas = 1;
//        String monto = "";
//        double operacion;
//        if (tipoConcepto.contains("Pago de Cuota")) {
//            for (detalle_cuota object : listaDetalleCuota) {
//                if (object.getAlumno().equals(alumno) && object.getGrupo().equals(seleccionado)) {
//                    cuotas = object.getNro_cuota();
//                    break;
//                }
//            }
//            for (Curso object : listaCurso) {
//                if (object.getNombre().equals(seleccionado)) {
//                    operacion = object.getCosto() / cuotas;
//                    monto = String.format("%.3f", operacion);
//                    txtMonto.setText(monto);
//                    break;
//                }
//            }
//        } else {
//            for (Curso object : listaCurso) {
//                if (object.getNombre().equals(seleccionado)) {
//                    operacion = object.getMatricula();
//                    monto = String.format("%.3f", operacion);
//                    txtMonto.setText(monto);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void cargarConceptos() {
//        cmbConcepto.getItems().clear();
//        ObservableList<String> conceptos = FXCollections.observableArrayList();
//
//        String alumnoNombre = txtAlumno.getText();
//        if (alumnoNombre.isEmpty()) {
//            return;
//        }
//
//        boolean matriculado = false;
//        listaDetalleFactura = FXCollections.observableArrayList(df.consulta());
//
//        for (detalle_factura object : listaDetalleFactura) {
//            if (alumnoNombre.equals(object.getNombreAlumno()) && object.getNro_cuota() == 0) {
//                matriculado = true;
//                break;
//            }
//        }
//
//        if (matriculado) {
//            conceptos.add("Pago de Cuota");
//        } else {
//            conceptos.addAll("Matrícula", "Pago de Cuota");
//        }
//
//        cmbConcepto.setItems(conceptos);
//    }
//
//    public String buscarConcepto() {
//        String seleccionado = cmbConcepto.getSelectionModel().getSelectedItem();
//        String curso = cmbCurso.getSelectionModel().getSelectedItem();
//        String cuotas = txtCuota.getText();
//        System.out.println(seleccionado);
//        if (seleccionado == null) {
//            return "";
//        }
//        if (seleccionado.equals("Matrícula")) {
//            return "Matricula Cursillo AMPERE";
//        } else {
//            return "Pago de Cuota, Cursillo: " + curso + " Cuota " + cuotas + " de " + CuotaTotal;
//        }
//    }
//
//    public void encontrarCuotas(ActionEvent event) {
//        String concepto = buscarConcepto();
//        if (concepto.contains("Pago de Cuota")) {
//            int cuotas = 0;
//            boolean cuotaexonerada = false;
//            String Alumno = txtAlumno.getText();
//            String Curso = cmbCurso.getSelectionModel().getSelectedItem();
//            listaDetalleFactura = FXCollections.observableArrayList(df.consulta());
//            listaDetalleCuota = FXCollections.observableArrayList(dc.consulta());
//            if (Curso == null || Curso.isEmpty()) {
//                return;
//            }
//            for (detalle_factura object : listaDetalleFactura) {
//                if (Alumno.equals(object.getNombreAlumno()) && Curso.equals(object.getNombreCurso()) && object.getNro_cuota() != 0) {
//                    cuotas++;
//                }
//            }
//            cuotas++;
//            for (detalle_cuota objeto : listaDetalleCuota) {
//                if (Alumno.equals(objeto.getAlumno()) && Curso.equals(objeto.getGrupo())) {
//                    CuotaTotal = String.valueOf(objeto.getNro_cuota());
//                    if (objeto.getNro_cuota() < cuotas) {
//                        cuotaexonerada = true;
//                    }
//                }
//            }
//            if (cuotaexonerada) {
//                Alert alerta = new Alert(Alert.AlertType.WARNING);
//                alerta.setTitle("El sistema comunica:");
//                alerta.setHeaderText(null);
//                alerta.setContentText("Error. El alumno ya ha pagado todas sus cuotas!.");
//                alerta.show();
//                cancelar(event);
//            } else {
//                txtCuota.setText(String.valueOf(cuotas));
//            }
//        } else {
//            txtCuota.setText("0");
//        }
//    }
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
    
    @FXML
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
    
    @FXML
    private boolean Agregar(ActionEvent event) throws IOException {
        abrirFxml("AgregarFactura.fxml", "ABM AGREGAR");
        AgregarFacturaController af = loader.getController(); // Obtener el controlador después de cargar el FXML
        if (af != null) {
            af.txtAlumno.setText(txtAlumno.getText());
            af.txtId.setText(txtId.getText());
            return true;
        }
        return false;
    }
}