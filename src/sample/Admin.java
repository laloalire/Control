package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Admin extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Panel de Administrador");
        AnchorPane anchor = new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(1366, 768);
        HBox topima = new HBox();
        HBox centrin = new HBox();
        Button atras = new Button("Regresar");
        atras.setOnAction(actionEvent -> {
            new Main().start(new Stage());
            primaryStage.hide();
        });
        atras.setPrefHeight(50);
        atras.getStyleClass().add("btnAtras");
        ImageView imgAtras = new ImageView("/Imagenes/back.png");
        atras.setGraphic(imgAtras);
        BorderPane pane = new BorderPane();
        ImageView pdf = new ImageView("/Imagenes/pdf.png");
        ImageView ver = new ImageView("Imagenes/eye.png");
        ImageView sep = new ImageView("/Imagenes/header.png");
        ImageView tec = new ImageView("/Imagenes/tec.png");
        ImageView estado = new ImageView("Imagenes/estado.png");
        sep.setPreserveRatio(true);
        topima.setSpacing(15);
        topima.setStyle("-fx-background-color: #ffffff;");
        topima.getChildren().add(sep);
        //topima.getChildren().add(tec);
        //topima.getChildren().add(estado);
        topima.setAlignment(Pos.CENTER);


        Button btnver = new Button();
        btnver.setOnAction(event -> {
            new Registros().start(new Stage());
            primaryStage.hide();
        });

        Button btnpdf = new Button();


        btnver.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnver.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        btnpdf.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnpdf.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        anchor.getChildren().add(pane);
        anchor.getChildren().add(topin);
        pane.setTop(topin);
        topin.getChildren().add(topima);
        centrin.getChildren().add(atras);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        pane.setStyle("-fx-background-color: #745e8e;");
        btnver.setGraphic(ver);
        btnpdf.setGraphic(pdf);
        btnver.setText("VER REGISTROS");
        btnpdf.setText("EXPORTAR REGISTROS");

        //sep.setFitWidth(0.1);
        //sep.setFitWidth(0.1);


        centrin.setAlignment(Pos.CENTER);
        centrin.setSpacing(15);
        pane.setCenter(centrin);
        centrin.getChildren().add(btnver);
        centrin.getChildren().add(btnpdf);

        pane.setTop(topima);

        primaryStage.setScene(new Scene(anchor));
        primaryStage.show();


        btnpdf.setOnAction(Event -> {
            generarReporteSalones();
        });

    }

    public void generarReporteSalones() {
        MysqlConnector sql = null;
        try {
            sql = new MysqlConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ArrayList<String>> listaRegistros = sql.getEntradas();
        if (listaRegistros.size() == 0) {
            new Alert(Alert.AlertType.ERROR, "No existe ninguna entrada registrada").show();
            return;
        }

        Document reporte = new Document(PageSize.LETTER);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return;
        }
        try {
            PdfWriter.getInstance(reporte, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        reporte.open();

        try {
            PdfPTable header = new PdfPTable(11);
            header.setWidthPercentage(100);
            header.setWidths(new int[]{1, 2, 2, 2, 2, 2, 2, 3, 3, 1, 1});
            header.addCell(new Phrase(new Chunk("Aula", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Fecha", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Entrada", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Salida", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Nombre del docente", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Apellido Paterno", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Apellido Materno", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Asignatura", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Carrera", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Cant. de Hombres ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));
            header.addCell(new Phrase(new Chunk("Cant. de Mujeres ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD))));



            for (int i = 0; i < listaRegistros.size(); i++) {
                ArrayList<String> registro = listaRegistros.get(i);
                for(int j = 0; j < registro.size(); j++){
                    Phrase phrase = new Phrase();
                    phrase.add(new Chunk(registro.get(j), new Font(Font.FontFamily.TIMES_ROMAN, 10)));
                    header.addCell(phrase);

                }
            }
            reporte.add(header);
            reporte.newPage();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        reporte.close();
        new Alert(Alert.AlertType.INFORMATION,"El documento se ha generado correctamente").show();


    }


    public static void main(String[] args) {
        launch(args);
    }


}