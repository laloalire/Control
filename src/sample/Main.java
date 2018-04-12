package sample;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Prototipo.fxml"));
        primaryStage.setTitle("Hello World");
        AnchorPane anchor = new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(720, 480);
        HBox topima = new HBox();
        HBox centrin = new HBox();
        BorderPane pane = new BorderPane();
        ImageView doc = new ImageView("/Imagenes/docente.png");
        ImageView al = new ImageView("Imagenes/alumno.png");
        ImageView sep = new ImageView("/Imagenes/header.png");
        ImageView tec = new ImageView("/Imagenes/tec.png");
        ImageView estado = new ImageView("Imagenes/estado.png");
        sep.setPreserveRatio(true);
        Button docente = new Button();
        Button alumno = new Button();


        docente.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setContentText("Número de empleado: ");
            textInputDialog.getDialogPane().setHeaderText("Introducir o escanear número de empleado");
            textInputDialog.setTitle("Número de empleado");

            Optional<String> numEmp = textInputDialog.showAndWait();


            if (!numEmp.isPresent()) {
                return;
            }
            try {
                if (numEmp.get().matches("[0-9]+")) {
                    MysqlConnector sql = new MysqlConnector();
                    Map<String, String> materias = sql.pedirMaterias(numEmp.get());
                    sql.shutdown();
                    if (materias.size() == 0) {
                        new Alert(Alert.AlertType.INFORMATION, "Número de empleado no válido").show();
                    } else {
                        new Materias(materias).start(primaryStage);
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Error: Sólo está permitido introducir números.").show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        alumno.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setContentText("Número de control: ");
            textInputDialog.getDialogPane().setHeaderText("Introducir o escanear tarjeta con número de control");
            textInputDialog.setTitle("número de control");
            Optional<String> numCont = textInputDialog.showAndWait();


            if (!numCont.isPresent()) {
                return;
            }
            try {
                if (numCont.get().matches("([0-9]{2}(CG)[0-9]{4})")) {
                    MysqlConnector sql = new MysqlConnector();
                    ArrayList<Map<String, String>> clases = sql.revisarAlumno(numCont.get());
                    if (clases == null) {
                        new Alert(Alert.AlertType.ERROR, "Número de control no válido").show();
                    } else if(clases.size() == 0){
                        new Alert(Alert.AlertType.INFORMATION, "No hay ninguna clase en curso" ).show();
                    } else {
                        new Alumno(clases, numCont.get()).start(primaryStage);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Comprueba tu información").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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


        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        //topima.getChildren().add(tec);
        //topima.getChildren().add(estado);
        topima.setAlignment(Pos.CENTER);
        sep.fitWidthProperty().bind(primaryStage.widthProperty());


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
