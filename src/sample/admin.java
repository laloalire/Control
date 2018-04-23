package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class admin extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Panel de Administrador");
        AnchorPane anchor = new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(720, 480);
        HBox topima = new HBox();
        HBox centrin = new HBox();
        BorderPane pane = new BorderPane();
        ImageView pdf = new ImageView("/Imagenes/pdf.png");
        ImageView ver = new ImageView("Imagenes/eye.png");
        ImageView sep = new ImageView("/Imagenes/header.png");
        ImageView tec = new ImageView("/Imagenes/tec.png");
        ImageView estado = new ImageView("Imagenes/estado.png");
        sep.setPreserveRatio(true);
        Button btnver = new Button();
        Button btnpdf = new Button();





        btnver.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnver.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        btnpdf.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnpdf.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        anchor.getChildren().add(pane);
        anchor.getChildren().add(topin);
        pane.setTop(topin);
        topin.getChildren().add(topima);

        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        pane.setStyle("-fx-background-color: #745e8e;");
        btnver.setGraphic(ver);
        btnpdf.setGraphic(pdf);

        //sep.setFitWidth(0.1);
        //sep.setFitWidth(0.1);






        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        //topima.getChildren().add(tec);
        //topima.getChildren().add(estado);
        topima.setAlignment(Pos.CENTER);



        centrin.setAlignment(Pos.CENTER);
        centrin.setSpacing(15);
        pane.setCenter(centrin);
        centrin.getChildren().add(btnver);
        centrin.getChildren().add(btnpdf);

        pane.setTop(topima);

        primaryStage.setScene(new Scene(anchor));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}