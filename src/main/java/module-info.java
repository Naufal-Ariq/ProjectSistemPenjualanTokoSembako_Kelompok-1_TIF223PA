module com.mycompany.projectpenjualantokosembako {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens Product to javafx.base;
    opens Admin to javafx.base;
    opens com.mycompany.projectpenjualantokosembako to javafx.fxml;
    exports com.mycompany.projectpenjualantokosembako;
}
