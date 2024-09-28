module com.mycompany.cursilloampere {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jasperreports;
    opens com.mycompany.cursilloampere to javafx.fxml;
    exports com.mycompany.cursilloampere;
    exports com.mycompany.cursilloampere.clases;
    exports com.mycompany.cursilloampere.modelo;
}
