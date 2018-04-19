package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


public class Horario extends Application {
    private ArrayList<String> aulasDisponibles;
    private String numempleado;
    private String idmateria;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Horario");
        AnchorPane anchor = new AnchorPane();
        BorderPane Border = new BorderPane();
        HBox todo = new HBox();
        VBox Hora = new VBox();
        VBox Horas = new VBox();
        HBox Timepo = new HBox();
        VBox Salones = new VBox();
        AnchorPane Ima = new AnchorPane();
        ImageView Imagen = new ImageView("/Imagenes/header.png");
        ImageView FlechaArriba = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo = new ImageView("/Imagenes/DOWN.png");
        ImageView FlechaArriba1 = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo1 = new ImageView("/Imagenes/DOWN.png");
        ImageView Horamas = new ImageView("/Imagenes/Hora.png");
        ImageView Horamenos = new ImageView("/Imagenes/Hora2.png");


        anchor.setPrefSize(720, 480);


        // Imagen Arriba
        Ima.setStyle("-fx-background-color: #ffffff;");
        Border.setTop(Ima);
        Border.setStyle("-fx-background-color: #745e8e;");
        Ima.getChildren().add(Imagen);
        Imagen.setPreserveRatio(true);
        Imagen.fitWidthProperty().bind(primaryStage.widthProperty());


        //Hora
        Button HoraUP = new Button();
        Button HoraDown = new Button();
        Label cantidad = new Label();
        HoraUP.setGraphic(FlechaArriba);
        HoraDown.setGraphic(FlechaAbajo);
        HoraUP.setOnAction(actionEvent -> {
            int nuevaCant = Integer.parseInt(cantidad.getText().split(":")[0]) + 1;
            if (nuevaCant == 13) {
                nuevaCant = 1;
            }
            cantidad.setText(nuevaCant + ":00");
        });
        HoraDown.setOnAction(actionEvent -> {
            int nuevaCant = Integer.parseInt(cantidad.getText().split(":")[0]) - 1;
            if (nuevaCant == 0) {
                nuevaCant = 12;
            }
            cantidad.setText(nuevaCant + ":00");
        });

        cantidad.setAlignment(Pos.CENTER);
        cantidad.setStyle("-fx-background-color: #ffffff;");
        cantidad.setFont(new Font("Arial", 90));
        Hora.getChildren().addAll(HoraUP, cantidad, HoraDown);
        Hora.setAlignment(Pos.CENTER);

        //Horas
        Button btnUP = new Button();
        Button btnDown = new Button();
        Label cantidad1 = new Label();
        btnUP.setGraphic(FlechaArriba1);
        btnDown.setGraphic(FlechaAbajo1);
        cantidad1.setText("1");
        cantidad1.setAlignment(Pos.CENTER);
        cantidad1.setStyle("-fx-background-color: #ffffff;");
        cantidad1.setFont(new Font("Arial", 100));
        Horas.getChildren().addAll(btnUP, cantidad1, btnDown);
        Horas.setAlignment(Pos.CENTER);

        //Tiempo
        Calendar calendario = Calendar.getInstance();
        int hour = calendario.get(Calendar.HOUR);
        cantidad.setText(hour + ":00");
        Button btnMenos = new Button();
        Button btnMas = new Button();
        Label cantidad2 = new Label();
        EventHandler<ActionEvent> cambiarAMFM = Event -> {
            if (cantidad2.getText().equals("AM")) {
                cantidad2.setText("PM");
            } else {
                cantidad2.setText("AM");
            }
        };
        btnMas.setOnAction(cambiarAMFM);
        btnMenos.setOnAction(cambiarAMFM);
        btnMenos.setGraphic(Horamas);
        btnMas.setGraphic(Horamenos);
        cantidad2.setText("AM");
        cantidad2.setAlignment(Pos.CENTER);
        cantidad2.setStyle("-fx-background-color: #ffffff;");
        cantidad2.setFont(new Font("Arial", 100));
        Timepo.getChildren().addAll(btnMenos, cantidad2, btnMas);
        Timepo.setAlignment(Pos.CENTER);

        //Salones disponibles
        for (String aula : aulasDisponibles) {
            Button btnAula = new Button("D" + aula);
            Salones.getChildren().add(btnAula);
            btnAula.prefHeightProperty().bind(Border.heightProperty().divide(6));
            btnAula.prefWidthProperty().bind(Border.widthProperty().divide(6));
        }
        Salones.setSpacing(20);
        Salones.setAlignment(Pos.CENTER);

        //boton.setPadding();

        //Border.setCenter(null);


        //Border centro poner salones

        btnUP.setOnAction(Event -> {
            int cactual = Integer.parseInt(cantidad1.getText());
            if (Integer.parseInt(cantidad1.getText()) >= 1 && Integer.parseInt(cantidad1.getText()) < 4) {
                cantidad1.setText(cactual + 1 + "");
            }
        });
        btnDown.setOnAction(Event -> {
            int cactual = Integer.parseInt(cantidad1.getText());
            if (Integer.parseInt(cantidad1.getText()) > 1 && Integer.parseInt(cantidad1.getText()) <= 4) {
                cantidad1.setText(cactual - 1 + "");
            }
        });

        AnchorPane.setTopAnchor(Border, 0.0);
        AnchorPane.setBottomAnchor(Border, 0.0);
        AnchorPane.setRightAnchor(Border, 0.0);
        AnchorPane.setLeftAnchor(Border, 0.0);

        //Centro
        Border.setCenter(todo);
        todo.getChildren().addAll(Hora, Horas, Timepo);
        Hora.setSpacing(40);
        Horas.setSpacing(40);
        Timepo.setSpacing(40);

        //Agrandar botones y label
        HoraUP.prefHeightProperty().bind(Border.heightProperty().divide(7));
        HoraUP.prefWidthProperty().bind(Border.widthProperty().divide(7));

        HoraDown.prefHeightProperty().bind(Border.heightProperty().divide(7));
        HoraDown.prefWidthProperty().bind(Border.widthProperty().divide(7));

        btnUP.prefHeightProperty().bind(Border.heightProperty().divide(7));
        btnUP.prefWidthProperty().bind(Border.widthProperty().divide(7));

        btnDown.prefHeightProperty().bind(Border.heightProperty().divide(7));
        btnDown.prefWidthProperty().bind(Border.widthProperty().divide(7));

        cantidad.prefHeightProperty().bind(Border.heightProperty().divide(7));
        cantidad.prefWidthProperty().bind(Border.widthProperty().divide(7));

        cantidad1.prefHeightProperty().bind(Border.heightProperty().divide(7));
        cantidad1.prefWidthProperty().bind(Border.widthProperty().divide(7));

        cantidad2.prefHeightProperty().bind(Border.heightProperty().divide(7));
        cantidad2.prefWidthProperty().bind(Border.widthProperty().divide(7));


        todo.getChildren().addAll(Salones);
        todo.setAlignment(Pos.CENTER);
        todo.setSpacing(50);
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
