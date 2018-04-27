package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class teclado extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchor = new AnchorPane();
        anchor.setStyle("-fx-background-color:#5c686d");
        BorderPane root = new BorderPane();
        VBox acomodar = new VBox();
        AnchorPane.setBottomAnchor(root, 10.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        anchor.getChildren().add(root);
        Scene scene = new Scene(anchor, 650, 750);


        String[] keys =
            {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "Borrar", "0", "Corregir"
            };
        TextField txtTeclado=new TextField("vegeta777");
        txtTeclado.getStylesheets().add("thisnuts.css");
        txtTeclado.getStyleClass().add("txtTeclado");
        acomodar.setAlignment(Pos.CENTER);
        acomodar.getChildren().add(txtTeclado);

        GridPane numPad = new GridPane();

        for (int i = 0; i < 12; i++) {
            Button button = new Button(keys[i]);
            button.getStylesheets().add("thisnuts.css");
            button.getStyleClass().add("Teclado");
            button.setPrefSize(200, 200);
            button.setPadding(new Insets(40, 40, 40, 40));

            numPad.add(button, i % 3, (int) Math.ceil(i / 3));

        }


        numPad.setPadding(new Insets(10, 10, 10, 10));
        numPad.setAlignment(Pos.CENTER);
        Button call = new Button("Regresar");
        call.setId("call-button");
        call.setPrefSize(600, 200); //width es el triple de button.setPrefSize
        //  call.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        numPad.add(call, 0, 4);

        GridPane.setColumnSpan(call, 3);
        GridPane.setHgrow(call, Priority.ALWAYS);

        root.setCenter(acomodar);
        acomodar.getChildren().addAll(numPad,call);
        acomodar.setAlignment(Pos.CENTER);
        acomodar.setSpacing(5);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        


    }
    Boolean banderaBorrar=false;
    public void checarnums(TextField numeros){
        if (numeros.getText().length() == 2 && !banderaBorrar) {
            numeros.setText(numeros.getText()+"CG");
        }
    }
    //METODO PARA QUE SE PONGA EL CG SOLITO BIEN PRRON

    public static void main(String[] args) {
        launch(args);
    }

}
