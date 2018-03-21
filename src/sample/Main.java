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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Prototipo.fxml"));
        primaryStage.setTitle("Hello World");
        AnchorPane anchor= new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(720,480);
        HBox topima = new HBox();
        HBox centrin = new HBox();
        BorderPane pane = new BorderPane();
        ImageView doc=new ImageView("/Imagenes/docente.png");
        ImageView al=new ImageView("Imagenes/alumno.png");
        ImageView sep = new ImageView("/Imagenes/header.png");
        ImageView tec = new ImageView("/Imagenes/tec.png");
        ImageView estado = new ImageView("Imagenes/estado.png");
        sep.setPreserveRatio(true);
        Button docente= new Button();
        Button alumno= new Button();


        docente.setOnAction(event -> {

            try {
                MysqlConnector sql=new MysqlConnector();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        alumno.setOnAction(event ->{

        });


        docente.prefHeightProperty().bind(pane.heightProperty().divide(2));
        docente.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        alumno.prefHeightProperty().bind(pane.heightProperty().divide(2));
        alumno.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        anchor.getChildren().add(pane);
        anchor.getChildren().add(topin);
        pane.setTop(topin);
        topin.getChildren().add(topima);

        AnchorPane.setTopAnchor(pane,0.0);
        AnchorPane.setBottomAnchor(pane,0.0);
        AnchorPane.setRightAnchor(pane,0.0);
        AnchorPane.setLeftAnchor(pane,0.0);
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

        primaryStage.setScene(new Scene (anchor));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
