module com.mycompany.projectpenjualantokosembako {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.projectpenjualantokosembako to javafx.fxml;
    exports com.mycompany.projectpenjualantokosembako;
}
