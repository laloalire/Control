package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {
    static int contador = 0;

    @Override
    public void start(Stage primaryStage) {
        ArduinoManager.iniciarArduino();
        primaryStage.setTitle("Hello World");
        AnchorPane anchor = new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(1366, 768);
        HBox topima = new HBox();
        HBox centrin = new HBox();
        BorderPane pane = new BorderPane();
        pane.getStylesheets().add("thisnuts.css");
        ImageView doc = new ImageView("/Imagenes/docente.png");
        ImageView al = new ImageView("Imagenes/alumno.png");
        ImageView sep = new ImageView("/Imagenes/header.png");
        ImageView tec = new ImageView("/Imagenes/tec.png");
        ImageView estado = new ImageView("Imagenes/estado.png");
        sep.setPreserveRatio(true);
        Button docente = new Button();
        Button alumno = new Button();


        docente.setOnAction(event -> {
            String numEmp = new Teclado(false).mostrarTeclado(primaryStage);

            if (numEmp.equals("$")) {
                return;
            }
            try {
                if (numEmp.matches("[0-9]+")) {
                    MysqlConnector sql = new MysqlConnector();
                    Map<String, String> materias = sql.pedirMaterias(numEmp);
                    sql.shutdown();
                    if (materias.size() == 0) {
                        new Alert(Alert.AlertType.INFORMATION, "Número de empleado no válido").show();
                    } else {
                        new Materias(materias, numEmp).start(primaryStage);
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Error: Sólo está permitido introducir números.").show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        alumno.setOnAction(event -> {
            String numCont = new Teclado(true).mostrarTeclado(primaryStage);


            if (numCont.equals("$")) {
                return;
            }
            try {
                if (numCont.matches("([0-9]{2}(CG)[0-9]{4})")) {
                    MysqlConnector sql = new MysqlConnector();
                    ArrayList<Map<String, String>> clases = sql.revisarAlumno(numCont);
                    if (clases == null) {
                        new Alert(Alert.AlertType.ERROR, "Número de control no válido").show();
                    } else if (clases.size() == 0) {
                        new Alert(Alert.AlertType.INFORMATION, "No hay ninguna clase en curso").show();
                    } else {
                        new Alumno(clases, numCont).start(primaryStage);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Comprueba tu información").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        docente.prefHeightProperty().bind(pane.heightProperty().divide(2));
        docente.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        alumno.prefHeightProperty().bind(pane.heightProperty().divide(2));
        alumno.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        anchor.getChildren().add(pane);
        anchor.getChildren().add(topin);
        pane.setTop(topin);
        topin.getChildren().add(topima);

        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        pane.setStyle("-fx-background-color: #745e8e;");
        alumno.setGraphic(al);
        docente.setGraphic(doc);

        //sep.setFitWidth(0.1);
        //sep.setFitWidth(0.1);
        ImageView imgAdmin = new ImageView("/Imagenes/adm.png");
        Button admin = new Button();
        admin.prefHeightProperty().bind(pane.heightProperty().divide(100));
        admin.prefWidthProperty().bind(pane.widthProperty().divide(10.1));
        admin.setGraphic(imgAdmin);
        admin.getStyleClass().add("Admin");
        admin.setOnAction(Event -> {
            if (contador == 4) {

                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setContentText("Autenticación: ");
                textInputDialog.getDialogPane().setHeaderText("Introducir contraseña para continuar");
                textInputDialog.setTitle("Contraseña:");
                Optional<String> pw = textInputDialog.showAndWait();
                if (!pw.isPresent()) {
                    contador = 0;
                    return;
                }
                MysqlConnector sql = null;
                try {
                    sql = new MysqlConnector();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (sql.checarAdmin(pw.get())) {
                    new Admin().start(primaryStage);
                } else {
                    new Alert(Alert.AlertType.ERROR, "La clave es incorrecta.").show();
                }
                contador = 0;
            } else {
                contador++;
            }
        });


        admin.setAlignment(Pos.BOTTOM_RIGHT);
        pane.setBottom(admin);
        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        //topima.getChildren().add(tec);
        //topima.getChildren().add(estado);
        topima.setAlignment(Pos.CENTER);


        centrin.setAlignment(Pos.CENTER);
        centrin.setSpacing(15);
        pane.setCenter(centrin);
        centrin.getChildren().add(alumno);
        centrin.getChildren().add(docente);

        pane.setTop(topima);

        primaryStage.setScene(new Scene(anchor));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
