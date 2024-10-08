package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.detalle_cuota;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Detalle_cuotaController implements Initializable {

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
    private TableColumn<detalle_cuota, String> colGrupo;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private TableColumn<detalle_cuota, String> colAlumno;
    ObservableList<detalle_cuota> listaDetalleCuota;
    ObservableList<Alumno> listaAlumno;
    ObservableList<Curso> listaCurso;
    int Aactual;
    int Cactual;
    Alumno a = new Alumno();
    Curso c = new Curso();
    detalle_cuota dc = new detalle_cuota();
    boolean bandera = false;
    @FXML
    private TableView<detalle_cuota> tablaCuota;
    @FXML
    private TableColumn<detalle_cuota, Integer> colCuota;
    @FXML
    private TextField txtCuota;
    @FXML
    private TextField txtAlumno;
    private final ContextMenu contextMenu = new ContextMenu();
    ObservableList<String> alumnoNombres = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
        cargarNombresAlumnos();
        cmbCurso.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                cuotasPredeterminadas(); // Llamar a la función de cuotas predeterminadas
            }
        });
    }

    public void mostrarDatos() {
        listaDetalleCuota = FXCollections.observableArrayList(dc.consulta());
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        colAlumno.setCellValueFactory(new PropertyValueFactory<>("Alumno"));
        colCuota.setCellValueFactory(new PropertyValueFactory<>("nro_cuota"));
        tablaCuota.setItems(listaDetalleCuota);
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        detalle_cuota dc = tablaCuota.getSelectionModel().getSelectedItem();
        txtCuota.setText(String.valueOf(dc.getNro_cuota()));
        btnMod.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        txtAlumno.setText(dc.getAlumno());
        cargarCurso();
        cmbCurso.setValue(dc.getGrupo());
    }

    private void buscar(KeyEvent event) {
        ObservableList<detalle_cuota> registrosFiltrados = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tablaCuota.setItems(listaDetalleCuota);
        } else {
            registrosFiltrados.clear();
            for (detalle_cuota registro : listaDetalleCuota) {
                if (registro.getAlumno().toLowerCase().contains(buscar.toLowerCase()) || registro.getGrupo().toLowerCase().contains(buscar.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaCuota.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtAlumno.setDisable(false);
        cmbCurso.setDisable(false);
        txtCuota.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarCurso();
        cmbCurso.setPromptText("Seleccione Curso");
    }

    @FXML
    private void modificar(ActionEvent event) {
        detalle_cuota dc = tablaCuota.getSelectionModel().getSelectedItem();
        cmbCurso.setDisable(false);
        txtAlumno.setDisable(false);
        txtCuota.setDisable(false);
        btnMod.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(false);
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
        cargarCurso();
        cmbCurso.setValue(dc.getGrupo());
        txtCuota.setText(String.valueOf(dc.getNro_cuota()));
        Aactual = buscarAlumno();
        Cactual = buscarCurso();

    }

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Aviso de Borrado");
        alerta.setHeaderText(null);
        alerta.setContentText("Desea eliminar el registro seleccionado?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            int alumno = buscarAlumno();
            int curso = buscarCurso();
            dc.setIdAlumno(alumno);
            dc.setIdCurso(curso);
            if (dc.eliminar()) {
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
            int alumno;
            int curso;
            int Cuota;
            try {
                Cuota = Integer.parseInt(txtCuota.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa solo números válidos en los campos correspondientes.");
                return;
            }
            alumno = buscarAlumno();
            curso = buscarCurso();
            dc.setIdAlumno(alumno);
            dc.setIdCurso(curso);
            dc.setNro_cuota(Cuota);
            if (bandera) {
                dc.setIdAlumnoActual(Aactual);
                dc.setIdCursoActual(Cactual);
                if (dc.modificar()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "El sistema comunica:", "Modificado correctamente");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica:", "Error. Registro no modificado.");
                }
                bandera = false;
            } else {
                if (dc.insertar()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "El sistema comunica:", "Insertado correctamente en la base de datos");
                    cancelar(event);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "El sistema comunica:", "Error. Registro no insertado.");
                }
            }
        }
        mostrarDatos();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtAlumno.setDisable(true);
        cmbCurso.setValue(null);
        cmbCurso.setPromptText("Seleccione curso");
        txtAlumno.clear();
        txtCuota.clear();
        txtCuota.setDisable(true);
        cmbCurso.setDisable(true);
        btnMod.setDisable(true);
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    private void cargarCurso() {
        cmbCurso.getItems().clear();
        // Obtener todos los cursos
        listaCurso = FXCollections.observableArrayList(c.consulta());
        // Obtener cursos en los que el alumno está inscrito
        int a = buscarAlumno();
        if (a == 0) {
            return;
        }
        List<Integer> cursosAlumnoIds = dc.obtenerCursosDelAlumno(buscarAlumno());
        for (Curso curso : listaCurso) {
            if (!cursosAlumnoIds.contains(curso.getId())) {
                cmbCurso.getItems().add(curso.getNombre());
            }
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

    public void setAlumno(String Alumno) {
        txtAlumno.clear();
        txtAlumno.setText(Alumno);
    }

    public void Alumno(KeyEvent event) {
        String buscarAlumno = txtAlumno.getText();
        contextMenu.getItems().clear();

        if (!buscarAlumno.isEmpty()) {
            for (String nombreAlumno : alumnoNombres) {
                if (nombreAlumno.toLowerCase().contains(buscarAlumno.toLowerCase())) {
                    MenuItem item = new MenuItem(nombreAlumno);
                    item.setOnAction(e -> {
                        txtAlumno.setText(nombreAlumno);
                        // Llamar a cargarCurso() cuando se selecciona un alumno
                        cargarCurso();
                    });
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

    private void cuotasPredeterminadas() {
        String Curso = cmbCurso.getSelectionModel().getSelectedItem();
        listaCurso = FXCollections.observableArrayList(c.consulta());
        if (Curso == null) {
            return;
        }
        for (Curso object : listaCurso) {
            if (Curso.equals(object.getNombre())) {
                txtCuota.setText(String.valueOf(object.getDuracion()));
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public boolean validarCampos() {
        if (esCampoVacio(txtAlumno, "El campo de nombre está vacío.")
                || esCampoVacio(txtCuota, "El campo de cuotas está vacío.")) {
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
    private void alumnos(MouseEvent event) {
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
