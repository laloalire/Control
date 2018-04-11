package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class Alumno extends Application{
    private  int numClases;
    private ArrayList<Map<String, String>> clases;

    public Alumno(ArrayList<Map<String, String>>  clases) {
        this.clases = clases;
        this.numClases = clases.size();
    }

    @Override
    public void start(Stage stage) {
        AnchorPane anchor= new AnchorPane();
        anchor.getStylesheets().add("thisnuts.css");
        anchor.setPrefSize(720,480);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();
        GridPane grid = new GridPane();
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
        }
        Scene scene  = new Scene(anchor);
        stage.setScene(scene);
        stage.show();

    }
}
