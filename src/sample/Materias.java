package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class Materias extends Application{
    private  int numMaterias;
    private Map<String, String> materias;
    public Materias(Map<String, String> materias) {
        this.materias = materias;
        this.numMaterias = materias.size();
    }

    @Override
    public void start(Stage stage) {
        AnchorPane anchor= new AnchorPane();
        anchor.getStylesheets().add("thisnuts.css");
        anchor.setPrefSize(720,480);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();
        GridPane grid = new GridPane();
        StackPane  base = new StackPane();
        Button atras = new Button("Regresar");
        ImageView imgAtras=new ImageView("/imagenes/back.png");
        atras.setGraphic(imgAtras);
        atras.setOnAction(actionEvent -> {
            new Main().start(new Stage());
            stage.hide();
        });
        atras.setPrefHeight(50);
        atras.getStyleClass().add("btnAtras");
        base.getChildren().add(atras);
        base.setAlignment(Pos.CENTER_LEFT);
        base.setPadding(new Insets(0, 0, 5,5));
        borderPane.setBottom(base);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        anchor.getChildren().add(borderPane);
        BorderPane.setAlignment(grid, Pos.CENTER);
        borderPane.setCenter(grid);
        grid.setPadding(new Insets(0, 5, 5, 5));
        anchor.setPrefSize(stage.getWidth(),stage.getHeight());
        ImageView sep = new ImageView("/Imagenes/header.png");
        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        borderPane.setTop(topima);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        topima.setAlignment(Pos.CENTER);
        sep.fitWidthProperty().bind(stage.widthProperty());
        sep.setPreserveRatio(true);
        grid.heightProperty().addListener((observable -> {
            System.out.println(grid.getHeight());
        }));

        for(int i = 0; i < numMaterias; i ++){
                int columna = i % 3;
                int fila = i / 3;
                Button btn = new Button(materias.values().toArray()[i] + "");
                btn.getStyleClass().add("btnMateria");
                btn.prefHeightProperty().bind(grid.heightProperty().divide(numMaterias < 3 ? 1 : numMaterias /3));
                btn.prefWidthProperty().bind(grid.widthProperty().divide(numMaterias < 3 ? numMaterias : 3));
                GridPane.setConstraints(btn, columna, fila);
                grid.getChildren().add(btn);
        }

        Scene scene  = new Scene(anchor);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
