package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;


public class Horario extends Application {
    private String numempleado;
    private String idmateria;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Horario");
        AnchorPane anchor = new AnchorPane();
        AnchorPane Ima = new AnchorPane();
        ImageView Imagen = new ImageView("/Imagenes/header.png");
        ImageView FlechaArriba = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo = new ImageView("/Imagenes/DOWN.png");
        anchor.setPrefSize(720, 480);
        BorderPane Border = new BorderPane();
        Ima.getChildren().add(Imagen);
        Border.setTop(Ima);
        Imagen.fitWidthProperty().bind(primaryStage.widthProperty());


        //Centro botones y texto
        BorderPane Centro = new BorderPane();
        Button btnUP = new Button();
        Button btnDown = new Button();
        Button aceptar = new Button();
        aceptar.setText("ACEPTAR");
        aceptar.setFont(new Font("Arial",45));
        Label cantidad = new Label();
        cantidad.setText("1");
        cantidad.setTextAlignment(TextAlignment.CENTER);
        cantidad.setStyle("-fx-background-color: #ffffff;");
        cantidad.setFont(new Font("Arial",100));
        //cantidad.setLayoutX(10);
        //cantidad.setLayoutY(10);

        HBox Top = new HBox();
        Top.getChildren().add(btnUP);
        Top.setAlignment(Pos.CENTER);
        HBox Buttom = new HBox();
        Buttom.getChildren().add(btnDown);
        Buttom.setAlignment(Pos.CENTER);
        HBox cen = new HBox();
        cen.setAlignment(Pos.CENTER);
        cen.setSpacing(15);
        cen.getChildren().add(cantidad);
        cen.getChildren().add(aceptar);
        Border.setCenter(Centro);
        Centro.setTop(Top);
        Centro.setBottom(Buttom);
        Centro.setCenter(cen);



        //Agrandar botones y label
        btnUP.prefHeightProperty().bind(Border.heightProperty().divide(4));
        btnUP.prefWidthProperty().bind(Border.widthProperty().divide(4));

        btnDown.prefHeightProperty().bind(Border.heightProperty().divide(4));
        btnDown.prefWidthProperty().bind(Border.widthProperty().divide(4));

        aceptar.prefHeightProperty().bind(Border.heightProperty().divide(6));
        aceptar.prefWidthProperty().bind(Border.widthProperty().divide(6));

        cantidad.prefHeightProperty().bind(Border.heightProperty().divide(6));
        cantidad.prefWidthProperty().bind(Border.widthProperty().divide(6));



        Border.setStyle("-fx-background-color: #745e8e;");
        Ima.setStyle("-fx-background-color: #ffffff;");
        btnUP.setGraphic(FlechaArriba);
        btnDown.setGraphic(FlechaAbajo);



        AnchorPane.setTopAnchor(Border, 0.0);
        AnchorPane.setBottomAnchor(Border, 0.0);
        AnchorPane.setRightAnchor(Border, 0.0);
        AnchorPane.setLeftAnchor(Border, 0.0);




        anchor.getChildren().add(Border);
        primaryStage.setScene(new Scene(anchor));
        primaryStage.show();
    }
    public Horario(String numempleado, String idmateria){
        this.numempleado=numempleado;
        this.idmateria=idmateria;
    }

}
