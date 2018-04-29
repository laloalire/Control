package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class teclado extends Application {
    private boolean alumno;

    @Override
    public void start(Stage primaryStage) {
        mostrarTeclado(new Stage());
    }
    public String mostrarTeclado(Stage padre) {

        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(padre);
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
        ImageView imgBackspace=new ImageView("/Imagenes/backspace.png");
        ImageView limpiar=new ImageView("/Imagenes/clean.png");

        String[] keys =
            {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "Limpiar", "0", "Corregir"
            };
        TextField txtTeclado=new TextField();
        txtTeclado.getStylesheets().add("thisnuts.css");
        txtTeclado.getStyleClass().add("txtTeclado");
        txtTeclado.setAlignment(Pos.CENTER);
        acomodar.setAlignment(Pos.CENTER);
        acomodar.getChildren().add(txtTeclado);

        GridPane numPad = new GridPane();

        for (int i = 0; i < 12; i++) {
            Button button = new Button(keys[i]);
            button.getStylesheets().add("thisnuts.css");
            button.getStyleClass().add("Teclado");
            button.setPrefSize(200, 200);
            button.setPadding(new Insets(40, 40, 40, 40));
            if(keys[i].equals("Corregir")){
                button.setGraphic(imgBackspace);
            } else if (keys[i].equals("Limpiar")) {
                button.setGraphic(limpiar);
            }

            numPad.add(button, i % 3, (int) Math.ceil(i / 3));
            final String key = keys[i];
            button.setOnAction(event  -> {
                String textoActual = txtTeclado.getText();
                if(key.equals("Corregir")) {
                    if(textoActual.length() > 0) {
                        int borrar = 1;
                        if(textoActual.matches("\\d{2}(CG)")){
                            borrar = 2;
                        }
                        txtTeclado.setText(textoActual.substring(0, textoActual.length() - borrar));
                    }
                } else if(key.equals("Limpiar")){
                    txtTeclado.setText("");
                } else {
                    textoActual = agregarCGSiSeRequiere(textoActual);
                    txtTeclado.setText(textoActual + key);
                    txtTeclado.setText(agregarCGSiSeRequiere(txtTeclado.getText()));
                }
            });

        }


        numPad.setPadding(new Insets(10, 10, 10, 10));
        numPad.setAlignment(Pos.CENTER);
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStylesheets().add("thisnuts.css");
        btnRegresar.getStyleClass().add("Regresar");
        btnRegresar.setOnAction(actionEvent -> {
            txtTeclado.setText("$");
            primaryStage.hide();
        });
        btnRegresar.setId("call-button");
        btnRegresar.setPrefSize(300, 200); //width es el triple de button.setPrefSize
        Button btnContinuar = new Button("Continuar");
        btnContinuar.getStylesheets().add("thisnuts.css");
        btnContinuar.getStyleClass().add("Continuar");
        btnContinuar.setOnAction(actionEvent -> {
            primaryStage.hide();
        });
        btnContinuar.setPrefSize(300, 200);
        //  call.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox parteBaja = new HBox();
        parteBaja.getChildren().addAll(btnRegresar, btnContinuar);
        numPad.add(parteBaja, 0, 4);
        parteBaja.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(parteBaja, 3);
        GridPane.setHgrow(parteBaja, Priority.ALWAYS);

        root.setCenter(acomodar);
        acomodar.getChildren().addAll(numPad);
        acomodar.setAlignment(Pos.CENTER);
        acomodar.setSpacing(5);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        return txtTeclado.getText();
    }

    private String agregarCGSiSeRequiere(String textoActual) {
        if(textoActual.matches("\\d{2}") && alumno){
            textoActual += "CG";
        }
        return textoActual;
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

    public teclado(boolean alumno) {
        this.alumno = alumno;
    }

    /**
     * <b> No se debe de usar </b>
     * Este consutructor no se debe de usar desde otra clase pinchis gatos, para eso esta el otro
     * este nada mas es para pruebas.
     * */
   public teclado() {
       alumno = true;
   }



}
