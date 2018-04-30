package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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

public class admin extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Panel de Administrador");
        AnchorPane anchor = new AnchorPane();
        AnchorPane topin = new AnchorPane();
        anchor.setPrefSize(720, 480);
        HBox topima = new HBox();
        HBox centrin = new HBox();
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
        Button btnpdf = new Button();





        btnver.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnver.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        btnpdf.prefHeightProperty().bind(pane.heightProperty().divide(2));
        btnpdf.prefWidthProperty().bind(pane.widthProperty().divide(2.1));
        anchor.getChildren().add(pane);
        anchor.getChildren().add(topin);
        pane.setTop(topin);
        topin.getChildren().add(topima);

        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        pane.setStyle("-fx-background-color: #745e8e;");
        btnver.setGraphic(ver);
        btnpdf.setGraphic(pdf);

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


        btnpdf.setOnAction(Event ->{

        });

    }

    public void generarReporteSalones() {
        MysqlConnector sql = null;
        try {
            sql = new MysqlConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> registrosId = sql.getEntradas();
        if (registrosId.size() == 0) {
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
        for (String registroId : registrosId) {
            ArrayList<String> datosLista = sql.getListaDatos(registroId);
            ArrayList<String> listaAlumnos = sql.getListaAlumnos(registroId);
            try {
                PdfPTable header = new PdfPTable(4);
                header.setWidthPercentage(100);
                header.setWidths(new int[]{1, 1, 1, 1});
                header.addCell("Aula ");
                header.addCell("Fecha ");
                header.addCell("Entrada " );
                header.addCell("Salida " );
                header.addCell("Nombre del docente ");
                header.addCell("Asignatura ");
                header.addCell("Carrera ");
                header.addCell("Cant. de Hombres ");
                header.addCell("Cant. de Mujeres ");

                reporte.add(header);

                for (int i = 0; i < listaAlumnos.size(); i++) {
                    String alumno = listaAlumnos.get(i);
                    reporte.add(new Paragraph((1 + i) + " - " + alumno));
                }
                reporte.newPage();

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        reporte.close();
    }



    public static void main(String[] args) {
        launch(args);
    }


}