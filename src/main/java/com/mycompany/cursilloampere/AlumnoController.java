/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.detalle_cuota;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AlumnoController implements Initializable {

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
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
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtPadres;
    @FXML
    private TextField txtRuc;
    @FXML
    private DatePicker txtInscripcion;
    @FXML
    private TableView<Alumno> tablaAlumno;
    @FXML
    private TableColumn<Alumno, Integer> colId;
    @FXML
    private TableColumn<Alumno, Integer> colCi;
    @FXML
    private TableColumn<Alumno, String> colNombre;
    @FXML
    private TableColumn<Alumno, String> colApe;
    @FXML
    private TableColumn<Alumno, Integer> colTel;
    @FXML
    private TableColumn<Alumno, String> colCorreo;
    @FXML
    private TableColumn<Alumno, Integer> colTelpad;
    @FXML
    private TableColumn<Alumno, String> colIns;
    @FXML
    private TableColumn<Alumno, String> colRuc;
    @FXML
    private TextField txtId;
    ObservableList<Alumno> registros;//carga de los clientes en la tabla
    ObservableList<Alumno> registrosFiltrados;
    ObservableList<Curso> listaCurso;
    ObservableList<Alumno> listaAlumno;
    Alumno a = new Alumno();
    boolean modificar = false;
    boolean bandera = false;
    detalle_cuota dc = new detalle_cuota();
    @FXML
    private TextField txtCuotas;
    private TextField txtCurso;
    Curso c = new Curso();
    ObservableList<String> cursoNombres = FXCollections.observableArrayList();
    @FXML
    private Button btnModCurso;
    private FXMLLoader loader;
    @FXML
    private Button btnFactura;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnCurso;
    @FXML
    private TextField txtRucNom;
    @FXML
    private TextField txtRuc1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        txtBuscar.setTooltip(new Tooltip("Buscar"));
        cargarCurso();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtId.setText(String.valueOf(a.Auto_incremento()));
        cargarCurso();
        cmbCurso.setDisable(false);
        txtRucNom.setDisable(false);
        txtCuotas.setDisable(false);
        txtCedula.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtTelefono.setDisable(false);
        txtCorreo.setDisable(false);
        txtPadres.setDisable(false);
        txtInscripcion.setDisable(false);
        txtInscripcion.setValue(LocalDate.now());
        txtRuc.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        txtCedula.requestFocus();
    }

    @FXML
    private void guardar(ActionEvent event) {
        // Validar campos antes de proceder
        if (!validarCampos()) {
            // Recoger los datos del formulario
            int ced;
            int tel;
            int tel_padre;
            try {
                ced = Integer.parseInt(txtCedula.getText());
                tel = Integer.parseInt(txtTelefono.getText());
                tel_padre = Integer.parseInt(txtPadres.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa solo números válidos en los campos correspondientes.");
                return; // Detener la ejecución si hay un error de formato
            }
            String ruc = txtRuc.getText();
            String rucnom = txtRucNom.getText();
            String nom = txtNombre.getText();
            String ape = txtApellido.getText();
            String correo = txtCorreo.getText();
            String fecha = txtInscripcion.getValue().toString();
            // Asignar los valores al objeto Alumno
            a.setCi(ced);
            a.setNombre(nom);
            a.setApellido(ape);
            a.setTel(tel);
            a.setCorreo(correo);
            a.setTelpadres(tel_padre);
            a.setFecha(fecha);
            a.setRuc(ruc);
            a.setRucNom(rucnom);
            if (modificar) {
                int id = Integer.parseInt(txtId.getText());
                a.setId(id);
                if (a.modificar()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Modificación Exitosa", "Datos modificados correctamente.");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron modificar.");
                }
                modificar = false;
            } else {
                if (!validarCedula()) {
                    if (a.insertar()) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Inserción Exitosa", "Datos insertados correctamente.");
                        guardar2(event); // Inserción de detalle_cuota
                        cancelar(event);
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los datos no se pudieron insertar.");
                    }
                }
            }
        }
        // Actualizar la tabla con los datos más recientes
        mostrarDatos();
    }

    private void guardar2(ActionEvent event) {
        if (!validarCuota()) {
            int alumno;
            int grupo;
            int cuotas;
            try {
                alumno = Integer.parseInt(txtId.getText());
                grupo = buscarCurso();
                cuotas = Integer.parseInt(txtCuotas.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa solo números en el campo de cuota correspondiente.");
                return; // Detener la ejecución si hay un error de formato
            }
            dc.setIdAlumno(alumno);
            dc.setIdCurso(grupo);
            dc.setNro_cuota(cuotas);
            if (dc.insertar()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Inserción exitosa", "Curso asignado correctamente en la base de datos");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "No se asigno el curso en la base de datos.");
            }
            mostrarDatos();
        }
    }

    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty()) {
            tablaAlumno.setItems(registros);//si está vacio el campo carga todos los datos
        } else {
            registrosFiltrados.clear();
            for (Alumno registro : registros) {
                if (registro.getNombre().toLowerCase().contains(busqueda.toLowerCase()) || registro.getApellido().toLowerCase().contains(busqueda.toLowerCase()) || String.valueOf(registro.getId()).contains(busqueda)) {
                    registrosFiltrados.add(registro);
                }//fin if
            } //fin for
            tablaAlumno.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        btnEliminar.setDisable(false);
        btnMod.setDisable(false);
        btnNuevo.setDisable(true);
        btnCancelar.setDisable(false);
        Alumno a = tablaAlumno.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(a.getId()));
        txtCedula.setText(String.valueOf(a.getCi()));
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtTelefono.setText(String.valueOf(a.getTel()));
        txtCorreo.setText(a.getCorreo());
        txtPadres.setText(String.valueOf(a.getTelpadres()));
        LocalDate fecha = LocalDate.parse(a.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtInscripcion.setValue(fecha);
        txtRuc.setText(a.getRuc());
        txtRucNom.setText(a.getRucNom());
        btnFactura.setDisable(false);
    }

    @FXML
    private void modificar(ActionEvent event) {
        btnModCurso.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtInscripcion.setDisable(false);
        txtRuc.setDisable(false);
        txtCedula.setDisable(false);
        txtTelefono.setDisable(false);
        txtPadres.setDisable(false);
        txtCorreo.setDisable(false);
        txtRucNom.setDisable(false);
        btnEliminar.setDisable(true);
        btnMod.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        mostrarDatos();
        modificar = true;

    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtCuotas.setDisable(true);
        cmbCurso.setDisable(true);
        cmbCurso.setValue(null);
        txtCuotas.clear();
        btnModCurso.setDisable(true);
        txtId.clear();
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtPadres.clear();
        txtRucNom.clear();
        txtInscripcion.setValue(null);
        txtRuc.clear();
        txtInscripcion.setDisable(true);
        txtRuc.setDisable(true);
        txtCedula.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtTelefono.setDisable(true);
        txtCorreo.setDisable(true);
        txtPadres.setDisable(true);
        txtRucNom.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        //mensaje que va a eliminar un registro
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Aviso de Borrado");
        alerta.setHeaderText(null);
        alerta.setContentText("Desea eliminar el registro seleccionado y sus atributos?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            int cod = Integer.parseInt(txtId.getText());
            a.setId(cod);
            if (a.eliminar()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Operación exitosa", "Registros y atributos borrados correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de datos", "Registros y atributos no borrados");
            }
        }
        mostrarDatos();
        cancelar(event);
    }

    @SuppressWarnings("unchecked")
    public void mostrarDatos() {
        registros = FXCollections.observableArrayList(a.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCi.setCellValueFactory(new PropertyValueFactory<>("ci"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApe.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelpad.setCellValueFactory(new PropertyValueFactory<>("telpadres"));
        colIns.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colRuc.setCellValueFactory(new PropertyValueFactory<>("Ruc"));
        tablaAlumno.setItems(registros);
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

    public void abrirFxml(String fxml, String titulo) {
        try {
            loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private boolean abrirDetalle(ActionEvent event) {
        abrirFxml("detalle_cuota.fxml", "ABM Detalle_cuota");
        Detalle_cuotaController detalle = loader.getController(); // Obtener el controlador después de cargar el FXML
        if (detalle != null) {
            detalle.setAlumno(txtNombre.getText() + " " + txtApellido.getText());
            return true;
        }
        return false;
    }

    private void abrirFactura(ActionEvent event) {
        abrirFxml("factura.fxml", "ABM Factura");
        FacturaController factura = loader.getController(); // Obtener el controlador después de cargar el FXML
        if (factura != null) {
            factura.setAlumno(txtNombre.getText() + " " + txtApellido.getText());
        }
    }

    private void cuotaCurso() {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        for (Curso object : listaCurso) {
            if (object.getNombre().equals(seleccionado)) {
                txtCuotas.setText(String.valueOf(object.getDuracion()));
            }
        }
    }

    public boolean validarCampos() {

        if (esCampoVacio(txtCedula, "El campo de cédula está vacío.")
                || esCampoVacio(txtNombre, "El campo de Nombre está vacío.")
                || esCampoVacio(txtApellido, "El campo de Apellido está vacío.")
                || esCampoVacio(txtTelefono, "El campo de teléfono está vacío.")
                || esCampoVacio(txtCorreo, "El campo de Correo está vacío.")
                || esCampoVacio(txtPadres, "El campo de teléfono de padres está vacío.")) {
            return true;
        }

        if (!txtCorreo.getText().contains("@")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El correo debe contener un '@' válido.");
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

    public boolean validarCuota() {
        if (esCampoVacio(txtCuotas, "El campo de cuotas está vacío.")) {
            return true;
        }
        return false;
    }

    public boolean validarCedula() {
        listaAlumno = FXCollections.observableArrayList(a.consulta());
        for (Alumno object : listaAlumno) {
            if (String.valueOf(object.getCi()).equals(txtCedula.getText())) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ya hay un alumno registrado con esta cédula.");
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
