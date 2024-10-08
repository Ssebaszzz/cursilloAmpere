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
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    ObservableList<detalle_factura> listaDetalleFactura2;
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
    private Label lblPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        cargarNombresAlumnos();
        listaDetalleFactura = FXCollections.observableArrayList();
        listaDetalleFactura2 = FXCollections.observableArrayList();
    }

    public void mostrarDatos() {
        listaFactura = FXCollections.observableArrayList(f.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
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
        mostrarFila2(f.getId());
    }

    private void mostrarFila2(int idF) {
        listaDetalleFactura2 = buscarDetallesPorId(idF);
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colNroCuotas.setCellValueFactory(new PropertyValueFactory<>("Nro_cuota"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Pago"));
        tablaFactura2.setItems(listaDetalleFactura2);
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
        if (validarCampos()) {
            if (validarMatricula()) {
                btnNuevo2.setDisable(false);
                cmbCurso.setDisable(false);
                chkmatricula.setDisable(true);
                txtPago.setDisable(false);
                txtCuota.setDisable(false);
                btnCancelar.setDisable(false);
                btnAgregar.setDisable(true);
                btnCancelar2.setDisable(false);
                cargarCurso();
                cmbCurso.setPromptText("Seleccione Curso");
            } else {
                btnCancelar2.setDisable(false);
                btnNuevo2.setDisable(false);
                cmbCurso.setDisable(false);
                cargarCurso();
                cmbCurso.getSelectionModel().selectFirst();
                matricula(buscarCurso());
            }
        }
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
        if (validarCampos()) {
            double pago = Double.parseDouble(lblPago.getText().replace(",", "."));
            int alumno = buscarAlumno();
            f.setAlumno(alumno);
            String fecha = txtFecha.getValue().toString();
            f.setFecha(fecha);
            f.setConcepto(concepto());
            if (bandera) {//modificar
                int id = Integer.parseInt(txtId.getText());
                f.setId(id);
                if (f.modificar()) {
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
                    if (tieneRuc(buscarAlumno())) {
                        Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta1.setTitle("Aviso de impresion");
                        alerta1.setHeaderText(null);
                        alerta1.setContentText("Desea imprimir factura?");
                        Optional<ButtonType> opcion = alerta1.showAndWait();
                        if (opcion.get() == ButtonType.OK) {
                            int nfactura = Integer.parseInt(txtId.getText());
                            String Ubicacion = "/reportes/factura.jasper";
                            String Titulo = "Factura N~" + nfactura;
                            imprimir(Ubicacion, Titulo, nfactura, pago);
                        }
                    }
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
        listaDetalleFactura.clear();
        tablaFactura2.getItems().clear();
    }

    @FXML
    private void Agregaralatabladetalle(ActionEvent event) {
        if (verificarTabla(event)) {
            if (validarCuotas(buscarAlumno(), buscarCurso(), event)) {
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
                    if (f.matriculado(buscarAlumno())) {
                        Agregar(event);
                    }
                    pagototal();
                } catch (Exception e) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al agregar el detalle de factura.");
                }
            }
        }
    }

    @FXML
    private void pagoMensual(ActionEvent event) {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        listaCurso = FXCollections.observableArrayList(c.consulta());
        listaDetalleCuota = FXCollections.observableArrayList(dc.consulta());
        int idA = buscarAlumno();
        int NumeroCuotaPagar = Integer.parseInt(txtCuota.getText());
        double operacion = 0;
        for (Curso curso : listaCurso) {
            if (curso.getNombre().equals(seleccionado)) {
                operacion = curso.getCosto();
                txtPago.setText(String.format("%.3f", operacion * NumeroCuotaPagar));
                break;
            }
        }
    }

    void matricula(int idC) {
        listaCurso = FXCollections.observableArrayList(c.consulta());
        for (Curso c : listaCurso) {
            if (c.getId() == idC) {
                txtPago.setText(String.format("%.3f", c.getMatricula()));
            }
        }
    }

    public ObservableList<detalle_factura> buscarDetallesPorId(int idF) {
        ObservableList<detalle_factura> resultados = FXCollections.observableArrayList();

        for (detalle_factura df : listaDetalleFactura) {
            if (df.getFactura() == idF) {
                resultados.add(df); // Agrega el detalle a la lista de resultados
            }
        }

        return resultados; // Retorna la lista, que puede estar vacía
    }

    @FXML
    private boolean validarMatricula() {
        int alumno = buscarAlumno();
        if (!(f.matriculado(alumno))) {
            mostrarAlerta(INFORMATION, "El sistema comunica: ", "El alumno no se encuentra matriulado");
            chkmatricula.setSelected(true);
            btnAgregar.setDisable(false);
            txtCuota.setDisable(true);
            txtPago.setDisable(false);
            txtCuota.setText("0");
            return false;
        }
        return true;
    }

    boolean validarCuotas(int idA, int idC, ActionEvent event) {
        int cuotasPagadas = 0;
        boolean alumnoEncontrado = false;

        // Obtener la lista de cuotas desde la consulta
        listaDetalleCuota = FXCollections.observableArrayList(dc.consulta());

        // Buscar las cuotas del alumno para el curso dado
        for (detalle_cuota dc : listaDetalleCuota) {
            if (dc.getIdAlumno() == idA && dc.getIdCurso() == idC) {
                cuotasPagadas = dc.getNro_cuota();
                alumnoEncontrado = true;
                break; // Salir del bucle una vez encontrada la coincidencia
            }
        }

        // Si no se encontró el alumno o el curso, mostrar un mensaje de error
        if (!alumnoEncontrado) {
            mostrarAlerta(ERROR, "El sistema comunica", "No se encontraron cuotas para este alumno y curso.");
            return false; // Salir del método
        }

        // Verificar si el valor de txtCuota es un número válido
        int cuotaActual;
        try {
            cuotaActual = Integer.parseInt(txtCuota.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(ERROR, "El sistema comunica", "El valor de la cuota es inválido.");
            return false;
        }

        // Calcular las cuotas faltantes
        int cuotasFaltantes = cuotasPagadas - cuotaActual;

        // Mostrar mensajes basados en la cantidad de cuotas faltantes
        if (cuotasFaltantes == 0) {
            mostrarAlerta(ERROR, "El sistema comunica", "El alumno ya pagó todas las cuotas de este curso.");
            return false;
        } else if (cuotasFaltantes < 0) {
            mostrarAlerta(ERROR, "El sistema comunica", "El alumno tiene " + (-cuotasFaltantes) + " cuota(s) por pagar.");
            txtCuota.setText(String.valueOf(-cuotasFaltantes));
            pagoMensual(event);
            return false;
        }

        return true;
    }

    public String concepto() {
        for (detalle_factura dc : listaDetalleFactura) {
            if (dc.getNro_cuota() == 0) {
                return "Matricula"; // Retorna "Matricula" si se encuentra una cuota de 0
            }
        }
        return "Pago de Cuotas"; // Retorna cadena vacía si no se encontró cuota de 0
    }

    public void pagototal() {
        double total = 0;
        for (detalle_factura df : listaDetalleFactura) {
            total += df.getPago();
        }
        lblPago.setText("" + total);
    }

    public boolean tieneRuc(int idA) {
        listaAlumno = FXCollections.observableArrayList(a.consulta());
        for (Alumno a : listaAlumno) {
            if (a.getId() == idA && a.getRuc() != null) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        btnAgregar.setDisable(true);
        cancelar2(event);
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
        listaDetalleFactura.clear();
        listaDetalleFactura2.clear();
        tablaFactura2.getItems().clear();
    }

    private void limpiarCampos() {
        cmbCurso.setValue(null);
        txtCuota.clear();
        txtPago.clear();
        chkmatricula.setSelected(false);
        chkmatricula.setDisable(true);
        btnEliminar2.setDisable(true);
        btnCancelar2.setDisable(true);
        btnNuevo2.setDisable(true);
        cmbCurso.setDisable(true);
        txtCuota.setDisable(true);
        txtPago.setDisable(true);
        lblPago.setText("");
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

    public boolean validarCampos() {
        if (esCampoVacio(txtAlumno, "El campo de Nombre está vacío.")) {
            return false;
        } else if (txtFecha.getValue() == null) {
            mostrarAlerta(ERROR, "El sistema comunica:", "Campo de fecha vacio");
            return false;
        } else if (buscarAlumno() == 0) {
            mostrarAlerta(ERROR, "El sistema comunica:", "Alumno no encontrado");
            return false;
        }
        return true;
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

    private boolean verificarTabla(ActionEvent event) {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        for (detalle_factura df : listaDetalleFactura) {
            if (df.getNombreCurso().equals(seleccionado)) {
                mostrarAlerta(ERROR, "El sistema comunica: ", "Ya hay un registro con ese curso en la tabla");
                limpiarCampos();
                Agregar(event);
                return false;
            }
        }
        return true;
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

    public void imprimir(String Ubicacion, String Titulo, int nfactura, Double Pago) {
        reportes r = new reportes();
        r.generarFactura(Ubicacion, Titulo, nfactura, Pago);
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

    @FXML
    private void notas(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("Notas.fxml", "ABM Notas");
        stage.close();
    }

    @FXML
    private void pagos(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("detalle_pago_profesor.fxml", "ABM Pagos");
        stage.close();
    }

    private void factura(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("factura.fxml", "ABM Factura");
        stage.close();
    }

    @FXML
    private void reportes(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("reportes.fxml", "ABM Reportes");
        stage.close();
    }

    @FXML
    private void menu(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        abrirFxml("menu.fxml", "ABM Menu");
        stage.close();
    }

}
