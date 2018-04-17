package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.omg.PortableServer.POA;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class Horario extends Application {
    private ArrayList<String> aulasDisponibles;
    private String numempleado;
    private String idmateria;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Horario");
        AnchorPane anchor = new AnchorPane();
        AnchorPane Ima = new AnchorPane();
        ImageView Imagen = new ImageView("/Imagenes/header.png");
        ImageView FlechaArriba = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo = new ImageView("/Imagenes/DOWN.png");
        ImageView FlechaArriba1 = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo1 = new ImageView("/Imagenes/DOWN.png");
        ImageView Horamas = new ImageView("/Imagenes/Hora.png");
        ImageView Horamenos = new ImageView("/Imagenes/Hora2.png");



        anchor.setPrefSize(720, 480);
        BorderPane Border = new BorderPane();
        Ima.getChildren().add(Imagen);
        Border.setTop(Ima);
        Imagen.fitWidthProperty().bind(primaryStage.widthProperty());


        //Centro botones y texto
        BorderPane Centro = new BorderPane();
        Centro.setPadding(new Insets(15,0,15,100));

        Button btnUP = new Button();
        Button btnDown = new Button();
        Button aceptar = new Button();
        aceptar.setText("ACEPTAR");
        aceptar.setFont(new Font("Arial", 45));
        Label cantidad = new Label();
        cantidad.setText("1");
        cantidad.setAlignment(Pos.CENTER);
        cantidad.setStyle("-fx-background-color: #ffffff;");
        cantidad.setFont(new Font("Arial",100));
        cantidad.setPadding(new Insets(0,0,0,0));

        //cantidad.setLayoutX(10);
        //cantidad.setLayoutY(10);




        //Lado derecho del border Clases
        VBox salones = new VBox();
        salones.setPadding(new Insets(50,100,50,0));
        salones.setAlignment(Pos.CENTER);
        salones.setSpacing(20);
        for(String aula : aulasDisponibles){
                Button btnAula = new Button("D"+aula);
                salones.getChildren().add(btnAula);
                btnAula.prefHeightProperty().bind(Border.heightProperty().divide(6));
                btnAula.prefWidthProperty().bind(Border.widthProperty().divide(6));
        }

        //boton.setPadding();
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
        //cen.getChildren().add(aceptar);
        Border.setLeft(Centro);
        //Border.setCenter(null);



        //Border centro poner salones
        Label Canti = new Label();
        BorderPane Horas = new BorderPane();
        Border.setCenter(Horas);
        HBox boton = new HBox();
        HBox boton1 = new HBox();
        Button mas = new Button();
        Button menos = new Button();
        Horas.setTop(boton);
        boton.setAlignment(Pos.CENTER);
        Horas.setBottom(boton1);
        boton1.setAlignment(Pos.CENTER);
        boton1.getChildren().add(menos);
        boton.getChildren().add(mas);
        Horas.setPadding(new Insets(15,200,15,0));
        Horas.setCenter(Canti);
        Canti.setText("1");
        Canti.setAlignment(Pos.CENTER);
        Canti.setStyle("-fx-background-color: #ffffff;");
        Canti.setFont(new Font("Arial",100));
        mas.setGraphic(FlechaArriba1);
        menos.setGraphic(FlechaAbajo1);

        //Border centro lado derecho AM Y PM
        VBox tiempo = new VBox();
        tiempo.setAlignment(Pos.CENTER_RIGHT);
        Label tiempoo = new Label();
        tiempoo.setText("AM");
        tiempoo.setAlignment(Pos.CENTER);
        tiempoo.setStyle("-fx-background-color: #ffffff;");
        tiempoo.setFont(new Font("Arial",70));
        tiempo.setAlignment(Pos.CENTER);
        tiempo.setSpacing(15);
        Button mass = new Button();
        Button menoss = new Button();
        tiempo.getChildren().add(mass);
        tiempo.getChildren().add(tiempoo);
        tiempo.getChildren().add(menoss);
        Horas.setRight(tiempo);

        mass.setGraphic(Horamas);
        menoss.setGraphic(Horamenos);



        Centro.setTop(Top);
        Centro.setBottom(Buttom);
        Border.setRight(salones);
        Centro.setLeft(cen);
        btnUP.setOnAction(Event ->{
            int cactual=Integer.parseInt(cantidad.getText());
            if(Integer.parseInt(cantidad.getText())>=1 && Integer.parseInt(cantidad.getText())<4 ){
                cantidad.setText(cactual+1+"");
            }else{

            }


        });
        btnDown.setOnAction(Event->{
            int cactual=Integer.parseInt(cantidad.getText());
            if(Integer.parseInt(cantidad.getText())>1 && Integer.parseInt(cantidad.getText())<=4 ){
                cantidad.setText(cactual-1+"");
            }else{

            }

        });

        //Agrandar botones y label
        btnUP.prefHeightProperty().bind(Border.heightProperty().divide(5));
        btnUP.prefWidthProperty().bind(Border.widthProperty().divide(5));

        btnDown.prefHeightProperty().bind(Border.heightProperty().divide(5));
        btnDown.prefWidthProperty().bind(Border.widthProperty().divide(5));

        aceptar.prefHeightProperty().bind(Border.heightProperty().divide(6));
        aceptar.prefWidthProperty().bind(Border.widthProperty().divide(6));

        cantidad.prefHeightProperty().bind(Border.heightProperty().divide(5));
        cantidad.prefWidthProperty().bind(Border.widthProperty().divide(5));

        mas.prefHeightProperty().bind(Border.heightProperty().divide(5));
        mas.prefWidthProperty().bind(Border.widthProperty().divide(5));

        menos.prefHeightProperty().bind(Border.heightProperty().divide(5));
        menos.prefWidthProperty().bind(Border.widthProperty().divide(5));

        Canti.prefHeightProperty().bind(Border.heightProperty().divide(5));
        Canti.prefWidthProperty().bind(Border.widthProperty().divide(5));


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

    public Horario(String numempleado, String idmateria) {
        this.numempleado = numempleado;
        this.idmateria = idmateria;
        MysqlConnector sql = null;
        try {
            sql = new MysqlConnector();
            ArrayList<String> aulasDisponibles = sql.aulasDisponibles();
            this.aulasDisponibles = aulasDisponibles;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        Platform.runLater(() -> new Horario("s", "s").start(new Stage()));


    }
}
