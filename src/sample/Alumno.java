package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.lang.reflect.Executable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Alumno extends Application{
    private  int numClases;
    private ArrayList<Map<String, String>> clases;
    private String numControl;

    public Alumno(ArrayList<Map<String, String>>  clases, String numControl) {
        this.clases = clases;
        this.numClases = clases.size();
        this.numControl = numControl;
    }

    @Override
    public void start(Stage stage) {
        AnchorPane anchor= new AnchorPane();
        anchor.getStylesheets().add("thisnuts.css");
        anchor.setPrefSize(720,480);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();
        GridPane grid = new GridPane();
        StackPane base = new StackPane();
        Button atras = new Button("Regresar");
        atras.setOnAction(actionEvent -> {
            new Main().start(new Stage());
            stage.hide();
        });
        atras.setPrefHeight(50);
        atras.getStyleClass().add("btnAtras");
        ImageView imgAtras = new ImageView("/Imagenes/back.png");
        atras.setGraphic(imgAtras);
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
        for(int i = 0; i < numClases; i ++){
            int columna = i % 3;
            int fila = i / 3;
            String texto = clases.get(i).get("asignatura")  +"\n"+ clases.get(i).get("maestro") +"\nD" +clases.get(i).get("aula");
            Button btn = new Button(texto);
            btn.setTextAlignment(TextAlignment.CENTER);
            btn.getStyleClass().add("btnMateria");
            btn.prefHeightProperty().bind(grid.heightProperty().divide(numClases < 3 ? 1 : numClases /3));
            btn.prefWidthProperty().bind(grid.widthProperty().divide(numClases < 3 ? numClases : 3));
            GridPane.setConstraints(btn, columna, fila);
            grid.getChildren().add(btn);
            //Evento del boton
            final int index = i;
            btn.setOnAction(event -> {
                try {
                    MysqlConnector sql = new MysqlConnector();
                    sql.registrarAlumno(clases.get(index).get("Rg_id"), numControl);
                    new Alert(Alert.AlertType.INFORMATION, "Te has registrado correctamene").showAndWait();
                    new Main().start(new Stage());
                    stage.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        Scene scene  = new Scene(anchor);
        stage.setScene(scene);
        stage.show();

    }
}
