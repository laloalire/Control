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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Materias extends Application {
    private String numEmp;
    private int numMaterias;
    private Map<String, String> materias;

    public Materias(Map<String, String> materias, String numEmp) {
        this.materias = materias;
        this.numMaterias = materias.size();
        this.numEmp = numEmp;
    }

    @Override
    public void start(Stage stage) {
        AnchorPane anchor = new AnchorPane();
        anchor.getStylesheets().add("thisnuts.css");
        anchor.setPrefSize(1366, 768);
        BorderPane borderPane = new BorderPane();
        HBox topima = new HBox();
        GridPane grid = new GridPane();
        HBox base = new HBox();
        Button atras = new Button("Regresar");
        ImageView imgAtras = new ImageView("/Imagenes/back.png");
        Button generarLista = new Button("Listas de asistencia");
        ImageView imgLista = new ImageView("/Imagenes/checklist.png");
        generarLista.setGraphic(imgLista);
        StackPane baseDerecha = new StackPane();
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
        HBox.setHgrow(baseDerecha, Priority.ALWAYS);
        baseDerecha.setPadding(new Insets(0, 30, 0, 0));
        base.setAlignment(Pos.CENTER_LEFT);
        base.setPadding(new Insets(0, 0, 5, 5));
        borderPane.setBottom(base);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        anchor.getChildren().add(borderPane);
        BorderPane.setAlignment(grid, Pos.CENTER);
        borderPane.setCenter(grid);
        grid.setPadding(new Insets(0, 5, 5, 5));
        anchor.setPrefSize(stage.getWidth(), stage.getHeight());
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

        for (int i = 0; i < numMaterias; i++) {
            int columna = i % 3;
            int fila = i / 3;
            Button btn = new Button(materias.values().toArray()[i] + "");
            btn.getStyleClass().add("btnMateria");
            btn.prefHeightProperty().bind(grid.heightProperty().divide(numMaterias < 3 ? 1 : numMaterias / 3));
            btn.prefWidthProperty().bind(grid.widthProperty().divide(numMaterias < 3 ? numMaterias : 3));
            GridPane.setConstraints(btn, columna, fila);
            grid.getChildren().add(btn);
            final int index = i;
            btn.setOnAction(actionEvent -> {
                new Horario(numEmp, materias.keySet().toArray()[index] + "").start(new Stage());
                stage.hide();
            });
        }
        Scene scene = new Scene(anchor);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void generarReporte() {
        MysqlConnector sql = null;
        try {
            sql = new MysqlConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fechas[0] = null;
        this.fechas[1] = null;
        String[] fechas = seleccionarLapso();
        if (fechas[0] == null) {
            return;
        }
        ArrayList<String> registrosId = sql.getRegistros(numEmp, fechas[1], fechas[0]);
        if (registrosId.size() == 0) {
            new Alert(Alert.AlertType.ERROR, "Ninguna lista para este docente").show();
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
                header.addCell("Fecha: " + datosLista.get(0));
                header.addCell("Asignatura: " + datosLista.get(1));
                header.addCell("Carrera: " + datosLista.get(2));
                header.addCell("Grupo: " + datosLista.get(3));
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

    private String[] fechas = {null, null};

    private String[] seleccionarLapso() {
        final DateFormat formatFecha = new SimpleDateFormat("yyyy/MM/dd");
        VBox root = new VBox();
        Label texto = new Label("Seleccione el periodo de listas de asistencias");
        texto.getStyleClass().add("labelListas");
        Button btnDia = new Button("Día");
        Button btnSemana = new Button("Semana");
        Button btnMes = new Button("Mes");
        btnDia.setPrefSize(300, 100);
        btnSemana.setPrefSize(300, 100);
        btnMes.setPrefSize(300, 100);
        root.getStylesheets().add("thisnuts.css");
        btnDia.getStyleClass().add("btnMateria");
        btnMes.getStyleClass().add("btnMateria");
        btnSemana.getStyleClass().add("btnMateria");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(texto, btnDia, btnSemana, btnMes);
        root.setSpacing(5);
        root.setPadding(new Insets(5));
        Scene esena = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(esena);
        btnDia.setOnAction(actionEvent -> modificarVarFecha(formatFecha, stage, Calendar.DAY_OF_MONTH));
        btnSemana.setOnAction(actionEvent -> modificarVarFecha(formatFecha, stage, Calendar.WEEK_OF_MONTH));
        btnMes.setOnAction(actionEvent -> modificarVarFecha(formatFecha, stage, Calendar.MONTH));
        stage.showAndWait();
        return fechas;
    }

    private void modificarVarFecha(DateFormat formatFecha, Stage stage, int lapso) {
        Calendar cal = Calendar.getInstance();
        fechas[0] = formatFecha.format(cal.getTime());
        cal.add(lapso, -1);
        fechas[1] = formatFecha.format(cal.getTime());
        stage.hide();
    }

}
