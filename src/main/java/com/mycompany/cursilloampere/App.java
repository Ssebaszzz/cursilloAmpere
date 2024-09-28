package com.mycompany.cursilloampere;

import com.mycompany.cursilloampere.clases.conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        conexion c = new conexion();
        if (c.getCon() != null) {
            scene = new Scene(loadFXML("menu"), 640, 480);
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.show();
        }
        else{
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de conexión");
            alerta.setHeaderText("");
            alerta.setContentText("Por favor, revise la conexión a la base de datos :)");
            alerta.show();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}