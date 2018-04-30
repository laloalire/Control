package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;


public class Horario extends Application {
    private ArrayList<String> aulasDisponibles;
    private String numempleado;
    private String idmateria;
    public static int horaActual;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Horario");
        AnchorPane anchor = new AnchorPane();
        BorderPane Border = new BorderPane();
        HBox todo = new HBox();
        VBox Hora = new VBox();
        VBox Horas = new VBox();
        HBox Timepo = new HBox();
        VBox Tiempo2 = new VBox();
        VBox Salones = new VBox();
        AnchorPane Ima = new AnchorPane();
        ImageView Imagen = new ImageView("/Imagenes/header.png");
        ImageView FlechaArriba = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo = new ImageView("/Imagenes/DOWN.png");
        ImageView FlechaArriba1 = new ImageView("/Imagenes/UP.png");
        ImageView FlechaAbajo1 = new ImageView("/Imagenes/DOWN.png");
        ImageView Horamas = new ImageView("/Imagenes/Hora.png");
        ImageView Horamenos = new ImageView("/Imagenes/Hora2.png");


        anchor.setPrefSize(1366, 768);


        // Imagen Arriba
        Ima.setStyle("-fx-background-color: #ffffff;");
        Border.setTop(Ima);
        Border.setStyle("-fx-background-color: #745e8e;");
        Ima.getChildren().add(Imagen);
        Imagen.setPreserveRatio(true);
        Imagen.fitWidthProperty().bind(primaryStage.widthProperty());


        //Hora
        Label HoraS = new Label();
        HoraS.setText("Seleccionar hora de inicio");
        HoraS.setTextFill(Color.WHITE);
        HoraS.setFont(new Font("Arial",20));
        Button HoraUP = new Button();
        Button HoraDown = new Button();
        Label lblHora = new Label();
        HoraUP.setGraphic(FlechaArriba);
        HoraDown.setGraphic(FlechaAbajo);
        HoraUP.setOnAction(actionEvent -> {
            int nuevaCant = Integer.parseInt(lblHora.getText().split(":")[0]) + 1;
            if (nuevaCant == 13) {
                nuevaCant = 1;

            }
            lblHora.setText(nuevaCant + ":00");
        });
        HoraDown.setOnAction(actionEvent -> {
            int nuevaCant = Integer.parseInt(lblHora.getText().split(":")[0]) - 1;
            if (nuevaCant == 0) {
                nuevaCant = 12;
            }
            lblHora.setText(nuevaCant + ":00");
        });

        lblHora.setAlignment(Pos.CENTER);
        lblHora.setStyle("-fx-background-color: #ffffff;");
        lblHora.setFont(new Font("Arial", 90));
        Hora.getChildren().addAll(HoraS,HoraUP, lblHora, HoraDown);
        Hora.setAlignment(Pos.CENTER);

        //Horas
        Label HoraS1 = new Label();
        HoraS1.setText("Duración de la clase (Horas)");
        HoraS1.setTextFill(Color.WHITE);
        HoraS1.setFont(new Font("Arial",20));
        Button btnUP = new Button();
        Button btnDown = new Button();
        Label lblCantidadHoras = new Label();
        btnUP.setGraphic(FlechaArriba1);
        btnDown.setGraphic(FlechaAbajo1);
        lblCantidadHoras.setText("1");
        lblCantidadHoras.setAlignment(Pos.CENTER);
        lblCantidadHoras.setStyle("-fx-background-color: #ffffff;");
        lblCantidadHoras.setFont(new Font("Arial", 100));
        Horas.getChildren().addAll(HoraS1,btnUP, lblCantidadHoras, btnDown);
        Horas.setAlignment(Pos.CENTER);

        //Tiempo
        Label Horario = new Label();
        Horario.setText("Indicar horario");
        Horario.setTextFill(Color.WHITE);
        Horario.setFont(new Font("Arial",20));
        Calendar calendario = Calendar.getInstance();
        int hour = calendario.get(Calendar.HOUR);
        lblHora.setText(hour + ":00");
        Button btnMenos = new Button();
        Button btnMas = new Button();
        Label lblMeridiano = new Label();
        btnMas.setOnAction(Event->{
            if(lblMeridiano.getText()=="AM"){
                lblMeridiano.setText("PM");
            }
        });
        //IFS DE VALIDACION DE HORARIO PARA QUE NO PUEDAN METER HORAS DE MADRUGADA



        btnMenos.setOnAction(Event->{
            if(lblMeridiano.getText()=="PM" && hour>7){
                lblMeridiano.setText("AM");
            }
        });
        btnMenos.setGraphic(Horamas);
        btnMas.setGraphic(Horamenos);
        lblMeridiano.setText("AM");
        lblMeridiano.setAlignment(Pos.CENTER);
        lblMeridiano.setStyle("-fx-background-color: #ffffff;");
        lblMeridiano.setFont(new Font("Arial", 100));
        Timepo.getChildren().addAll(btnMenos, lblMeridiano, btnMas);
        Timepo.setAlignment(Pos.CENTER);
        Tiempo2.setAlignment(Pos.CENTER);
        Tiempo2.setSpacing(50);
        Tiempo2.getChildren().add(Horario);
        Tiempo2.getChildren().add(Timepo);

        //Salones disponibles
        Label salonesD = new Label();
        salonesD.setText("Seleccionar Aula");
        salonesD.setTextFill(Color.WHITE);
        Salones.getChildren().add(salonesD);
        salonesD.setFont(new Font("Arial",20));
        for (String aula : aulasDisponibles) {
            Button btnAula = new Button("D" + aula);
            Salones.getChildren().add(btnAula);
            btnAula.prefHeightProperty().bind(Border.heightProperty().divide(6));
            btnAula.prefWidthProperty().bind(Border.widthProperty().divide(6));
            btnAula.setOnAction(event -> {
                int cantidadHoras = Integer.parseInt(lblCantidadHoras.getText());
                if (mostrarAlerta(lblHora, lblMeridiano, btnAula, cantidadHoras)) return;
                int inicio = Integer.parseInt(lblHora.getText().split(":")[0]);
                if(lblMeridiano.getText().equals("PM")){
                    inicio += 12;
                }
                int fin = inicio + cantidadHoras;
                insertarRegistro(primaryStage, aula, inicio, fin);
            });
            btnAula.prefHeightProperty().bind(Border.heightProperty().divide(7));
            btnAula.prefWidthProperty().bind(Border.widthProperty().divide(7));
        }
        Salones.setSpacing(20);
        Salones.setAlignment(Pos.CENTER);

        //boton.setPadding();

        //Border.setCenter(null);


        //Border centro poner salones

        btnUP.setOnAction(Event -> {
            int cactual = Integer.parseInt(lblCantidadHoras.getText());
            if (Integer.parseInt(lblCantidadHoras.getText()) >= 1 && Integer.parseInt(lblCantidadHoras.getText()) < 4) {
                lblCantidadHoras.setText(cactual + 1 + "");
            }
        });
        btnDown.setOnAction(Event -> {
            int cactual = Integer.parseInt(lblCantidadHoras.getText());
            if (Integer.parseInt(lblCantidadHoras.getText()) > 1 && Integer.parseInt(lblCantidadHoras.getText()) <= 4) {
                lblCantidadHoras.setText(cactual - 1 + "");
            }
        });

        AnchorPane.setTopAnchor(Border, 0.0);
        AnchorPane.setBottomAnchor(Border, 0.0);
        AnchorPane.setRightAnchor(Border, 0.0);
        AnchorPane.setLeftAnchor(Border, 0.0);

        //Centro
        Border.setCenter(todo);
        todo.getChildren().addAll(Hora, Horas, Tiempo2);
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

        lblHora.prefHeightProperty().bind(Border.heightProperty().divide(7));
        lblHora.prefWidthProperty().bind(Border.widthProperty().divide(7));

        lblCantidadHoras.prefHeightProperty().bind(Border.heightProperty().divide(7));
        lblCantidadHoras.prefWidthProperty().bind(Border.widthProperty().divide(7));

        lblMeridiano.prefHeightProperty().bind(Border.heightProperty().divide(7));
        lblMeridiano.prefWidthProperty().bind(Border.widthProperty().divide(7));


        todo.getChildren().addAll(Salones);
        todo.setAlignment(Pos.CENTER);
        todo.setSpacing(50);
        anchor.getChildren().add(Border);
        primaryStage.setScene(new Scene(anchor));
        primaryStage.show();
    }

    private void insertarRegistro(Stage primaryStage, String aula, int inicio, int fin) {
        try {
            MysqlConnector sql = new MysqlConnector();
            int aulaid = Integer.parseInt(aula);
            int asignatura = Integer.parseInt(idmateria);
            sql.insertarRegistro(inicio+":00", fin+":00", aulaid, asignatura, numempleado);
            new Alert(Alert.AlertType.INFORMATION, "Clase registrada").showAndWait();
            new Main().start(new Stage());
            primaryStage.hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean mostrarAlerta(Label lblHora, Label lblMeridiano, Button btnAula, int cantidadHoras) {
        int horaInicio = Integer.parseInt(lblHora.getText().split(":")[0]);
        int horaFin = horaInicio + cantidadHoras;
        String finMeridiano = lblMeridiano.getText();
        if(horaFin > 12) {
            horaFin -= 12;
            finMeridiano = "PM";
        }else if(horaFin == 12){
            finMeridiano = "PM";
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "Hora Inicio: "+ horaInicio +  ":00 "+lblMeridiano.getText()+"\nHora Fin: "+horaFin+":00 "+finMeridiano+" \nAula: "+btnAula.getText());
        alerta.setHeaderText("Se creara una clase con las siguiente infomación");
        Optional<ButtonType> result  = alerta.showAndWait();
        return !result.isPresent() || result.get() != ButtonType.OK;
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
        }


    }

    public static void main(String[] args) {
        Platform.runLater(() -> new Horario("s", "s").start(new Stage()));


    }
}
