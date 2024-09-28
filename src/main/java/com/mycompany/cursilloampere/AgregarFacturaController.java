package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.modelo.Alumno;
import com.mycompany.cursilloampere.modelo.Curso;
import com.mycompany.cursilloampere.modelo.detalle_cuota;
import com.mycompany.cursilloampere.modelo.detalle_factura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AgregarFacturaController implements Initializable {

    @FXML
    private TableColumn<detalle_factura, String> colGrupo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private TextField txtCuota;
    @FXML
    private TableView<detalle_factura> tablaFactura;
    @FXML
    private TextField txtPago;
    public ObservableList<detalle_factura> listaDetalleFactura;
    private ObservableList<detalle_cuota> listaDetalleCuota;
    private ObservableList<Curso> listaCurso;
    private ObservableList<Alumno> listaAlumno;
    @FXML
    private TableColumn<detalle_factura, Integer> colNroCuotas;
    @FXML
    private TableColumn<detalle_factura, Double> colTotal;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnNuevo;
    @FXML
    public TextField txtAlumno;
    @FXML
    public TextField txtId;
    @FXML
    private CheckBox chkmatricula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaDetalleFactura = FXCollections.observableArrayList();
        mostrarDatos();
    }

    public void mostrarDatos() {
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colNroCuotas.setCellValueFactory(new PropertyValueFactory<>("Nro_cuota"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Pago"));
        tablaFactura.setItems(listaDetalleFactura);
    }


    @FXML
    private void agregar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            String curso = cmbCurso.getSelectionModel().getSelectedItem();
            int idCurso = buscarCurso();
            int nrocuotas = Integer.parseInt(txtCuota.getText());
            double total = Double.parseDouble(txtPago.getText().replace(",", "."));
            int IdFactura = Integer.parseInt(txtId.getText());
            detalle_factura dtf = new detalle_factura(IdFactura, idCurso, total, nrocuotas, curso);
            validarMatricula();
            listaDetalleFactura.add(dtf);
            tablaFactura.refresh();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El detalle de factura se ha agregado correctamente.");
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al agregar el detalle de factura.");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        detalle_factura df = tablaFactura.getSelectionModel().getSelectedItem();
        if (df != null) {
            listaDetalleFactura.remove(df);
            tablaFactura.refresh(); // Refresh table after removing the item
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El detalle de factura se ha eliminado correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay un detalle de factura seleccionado para eliminar.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        limpiarCampos();
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
        listaCurso = FXCollections.observableArrayList(new Curso().consulta());

        if (listaCurso == null || listaCurso.isEmpty()) {
            System.out.println("No se encontraron cursos.");
            return;
        }

        List<Integer> cursosAlumnoIds = new detalle_cuota().obtenerCursosDelAlumno(buscarAlumno());
        if (cursosAlumnoIds == null || cursosAlumnoIds.isEmpty()) {
            System.out.println("El alumno no tiene cursos asociados.");
            return;
        }

        for (Curso curso : listaCurso) {
            if (cursosAlumnoIds.contains(curso.getId())) {
                cmbCurso.getItems().add(curso.getNombre());
            }
        }
    }

    private void limpiarCampos() {
        cmbCurso.setValue(null);
        txtCuota.clear();
        txtPago.clear();
        chkmatricula.setSelected(false);
        chkmatricula.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        cmbCurso.setDisable(true);
        txtCuota.setDisable(true);
        txtPago.setDisable(true);
    }

    public boolean validarCampos() {
        if (esCampoVacio(txtCuota, "El campo de cuotas está vacío.")
                || esCampoVacio(txtPago, "El campo de pago está vacío.")
                || cmbCurso.getSelectionModel().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Por favor, complete todos los campos.");
            return false;
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

    @FXML
    private void pagoMensual(KeyEvent event) {
        String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
        listaCurso = FXCollections.observableArrayList(new Curso().consulta());
        listaDetalleCuota = FXCollections.observableArrayList(new detalle_cuota().consulta());
        int CuotasDelAlumno=0;
        int idA=buscarAlumno();
        for(detalle_cuota dc: listaDetalleCuota){
            if(dc.getGrupo().equals(seleccionado) && dc.getIdAlumno()==idA){
                CuotasDelAlumno=dc.getNro_cuota();
            }
        }
        int NumeroCuotaPagar = Integer.parseInt(txtCuota.getText());
        double operacion=0;
        for (Curso curso : listaCurso) {
            if (curso.getNombre().equals(seleccionado)) {
                operacion = curso.getCosto() / CuotasDelAlumno;
                txtPago.setText(String.format("%.3f", operacion * NumeroCuotaPagar));
                break;
            }
        }
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        cmbCurso.setDisable(false);
        chkmatricula.setDisable(false);
        txtPago.setDisable(false);
        txtCuota.setDisable(false);
        btnCancelar.setDisable(false);
        btnAgregar.setDisable(false);
        cargarCurso();
        cmbCurso.setPromptText("Seleccione Curso");
    }

    private int buscarAlumno() {
        listaAlumno = FXCollections.observableArrayList(new Alumno().consulta());
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

    void validarMatricula() {
        if (chkmatricula.isSelected()) {
            listaCurso = FXCollections.observableArrayList(new Curso().consulta());
            String seleccionado = cmbCurso.getSelectionModel().getSelectedItem();
            double operacion;
            for (Curso curso : listaCurso) {
                if (curso.getNombre().equals(seleccionado)) {
                    operacion = curso.getMatricula();
                    try {
                        int idCurso = buscarCurso();
                        int nrocuotas = 0;
                        double total = operacion;
                        int IdFactura = Integer.parseInt(txtId.getText());
                        detalle_factura dtf = new detalle_factura(IdFactura, idCurso, total, nrocuotas, seleccionado);
                        listaDetalleFactura.add(dtf);
                        tablaFactura.refresh(); // Refresh table after adding the item
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La matricula se ha agregado correctamente.");
                        limpiarCampos();
                    } catch (Exception e) {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al agregar la matricula.");
                    }
                    break;
                }
            }
            return;
        }
    }
}
