package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class teclado extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchor = new AnchorPane();
        BorderPane root = new BorderPane();
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        anchor.getChildren().add(root);
        Scene scene = new Scene(anchor, 800, 600);


        String[] keys =
            {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "Borrar", "0", "Corregir"
            };

        GridPane numPad = new GridPane();
        for (int i = 0; i < 12; i++) {
            Button button = new Button(keys[i]);
            button.getStylesheets().add("thisnuts.css");
            button.getStyleClass().add("Teclado");
            button.setPrefSize(130, 200);
            button.setPadding(new Insets(40, 40, 40, 40));

            numPad.add(button, i % 3, (int) Math.ceil(i / 3));

        }
        numPad.setPadding(new Insets(10, 10, 10, 10));
        numPad.setAlignment(Pos.CENTER);
        Button call = new Button("Regresar");
        call.setId("call-button");
        call.setPrefSize(390, 100); //width es el triple de button.setPrefSize
        //  call.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        numPad.add(call, 0, 4);

        GridPane.setColumnSpan(call, 3);
        GridPane.setHgrow(call, Priority.ALWAYS);

        root.setCenter(numPad);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
