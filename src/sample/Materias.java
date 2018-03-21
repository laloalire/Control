package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Materias extends Application{
    private  int n=10;
    @Override
    public void start(Stage stage) {

        AnchorPane anchor= new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(720,480);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();

        HBox centrin = new HBox();
        GridPane grid = new GridPane();
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        anchor.getChildren().add(borderPane);
        borderPane.setCenter(grid);
        anchor.setPrefSize(720,480);
        ImageView sep = new ImageView("/Imagenes/header.png");
        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        borderPane.setTop(topima);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        //topima.getChildren().add(tec);
        //topima.getChildren().add(estado);
        topima.setAlignment(Pos.CENTER);
        sep.fitWidthProperty().bind(stage.widthProperty());
        sep.setPreserveRatio(true);
        for(int i =0; i < n;i ++){
                int columna = i % 3;
                int fila = i / 3;
                Button btn = new Button(fila+"/"+columna);
                btn.prefHeightProperty().bind(grid.heightProperty().divide(n < 3 ? 1 : n/3));
                btn.prefWidthProperty().bind(grid.widthProperty().divide(n < 3 ? n : 3));
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
