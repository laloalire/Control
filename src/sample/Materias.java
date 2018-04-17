package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Materias extends Application{
    private String numEmp;
    private  int numMaterias;
    private Map<String, String> materias;
    public Materias(Map<String, String> materias, String numEmp) {
        this.materias = materias;
        this.numMaterias = materias.size();
        this.numEmp = numEmp;
    }

    @Override
    public void start(Stage stage) {
        AnchorPane anchor= new AnchorPane();
        anchor.getStylesheets().add("thisnuts.css");
        anchor.setPrefSize(720,480);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();
        GridPane grid = new GridPane();
        HBox  base = new HBox();
        Button atras = new Button("Regresar");
        ImageView imgAtras=new ImageView("/Imagenes/back.png");
        Button generarLista=new Button("Listas de asistencia");
        ImageView imgLista=new ImageView("/Imagenes/checklist.png");
        generarLista.setGraphic(imgLista);
        StackPane baseDerecha=new StackPane();
        generarLista.setOnAction(actionEvent -> generarReporte());
        atras.setGraphic(imgAtras);
        atras.setOnAction(actionEvent -> {
            new Main().start(new Stage());
            stage.hide();
        });
        atras.setPrefHeight(50);
        atras.getStyleClass().add("btnAtras");
        generarLista.setPrefHeight(50);

        base.getChildren().add(atras);
        baseDerecha.getChildren().add(generarLista);
        base.getChildren().add(baseDerecha);
        baseDerecha.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(baseDerecha,Priority.ALWAYS);
        baseDerecha.setPadding(new Insets(0,30,0,0));
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
                final int index = i;
                btn.setOnAction(actionEvent -> {
                    new Horario(materias.keySet().toArray()[index]+"", numEmp).start(new Stage());
                    stage.hide();
                });
        }

        Scene scene  = new Scene(anchor);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
    public void generarReporte() {
        Document reporte = new Document(PageSize.LETTER);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf", "*.pdf" ));
        File file =fileChooser.showSaveDialog(null);

        try {
            if(file != null) {
                PdfWriter.getInstance(reporte, new FileOutputStream(file));
                reporte.open();
                Paragraph titulo =new Paragraph("lel pidief");
                reporte.add(titulo);
                reporte.close();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo leer el archivo. Otra aplicacion pude estarlo utilizando").show();
            e.printStackTrace();
        }
    }

}
