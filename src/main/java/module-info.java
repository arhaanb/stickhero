module com.arhaanb.stickhero.stickhero {
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires javafx.fxml;

  requires com.almasb.fxgl.all;

  opens com.arhaanb.stickhero.stickhero to javafx.fxml;
  exports com.arhaanb.stickhero.stickhero ;
}
