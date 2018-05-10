package sample;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;


public class Registros extends Application {

    @Override
    public void start(Stage stage) {
        TableView tableView = new TableView();
        setUpTabla(tableView);
        llenarTabla(tableView);
        Label titulo = new Label("Registro");
        titulo.setStyle("-fx-font-size: 30px");
        Button atras = new Button("Regresar");
        atras.setOnAction(actionEvent -> {
            new Admin().start(new Stage());
            stage.hide();
        });
        atras.setPrefHeight(50);
        atras.getStyleClass().add("btnAtras");
        ImageView imgAtras = new ImageView("/Imagenes/back.png");
        atras.setGraphic(imgAtras);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setTop(titulo);
        borderPane.setBottom(atras);
        BorderPane.setAlignment(titulo, Pos.CENTER);
        BorderPane.setMargin(tableView, new Insets(10));
        BorderPane.setMargin(atras, new Insets(0, 0, 0, 10));
        AnchorPane root = new AnchorPane(borderPane);
        AnchorPane.setBottomAnchor(borderPane, 0d);
        AnchorPane.setTopAnchor(borderPane, 0d);
        AnchorPane.setRightAnchor(borderPane, 0d);
        AnchorPane.setLeftAnchor(borderPane, 0d);
        Scene scene = new Scene(root, 1366, 768);
        stage.setScene(scene);
        stage.show();
        for(Object column : tableView.getColumns()) {
            TableColumn columna1 = (TableColumn) column;
            columna1.setPrefWidth(tableView.getWidth() / (11));
        }
    }


    public void setUpTabla(TableView tabla) {
        TableColumn<ArrayList, String> aula = new TableColumn<>("Aula");
        aula.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(0) + ""));
        TableColumn<ArrayList, String> fecha = new TableColumn<>("Fecha");
        fecha.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(1) + ""));
        TableColumn<ArrayList, String> entrada = new TableColumn<>("Entrada");
        entrada.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(2) + ""));
        TableColumn<ArrayList, String> salida = new TableColumn<>("Salida");
        salida.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(3) + ""));
        TableColumn<ArrayList, String> nombre = new TableColumn<>("Nombre Docente");
        nombre.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(4) + ""));
        TableColumn<ArrayList, String> apellidoPaterno = new TableColumn<>("Apellido Paterno");
        apellidoPaterno.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(5) + ""));
        TableColumn<ArrayList, String> apellidoMaterno = new TableColumn<>("Apellido Materno");
        apellidoMaterno.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(6) + ""));
        TableColumn<ArrayList, String> asignatura = new TableColumn<>("Asignatura");
        asignatura.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(7) + ""));
        TableColumn<ArrayList, String> carrera = new TableColumn<>("Carrera");
        carrera.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(8) + ""));
        TableColumn<ArrayList, String> cantidadHombres = new TableColumn<>("Cant. de Hombres");
        cantidadHombres.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(9) + ""));
        TableColumn<ArrayList, String> cantidadMujeres = new TableColumn<>("Cant. de Mujeres");
        cantidadMujeres.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(10) + ""));
        tabla.getColumns().addAll(aula, fecha,entrada,salida,nombre,apellidoPaterno, apellidoMaterno,asignatura, carrera, cantidadHombres, cantidadMujeres);



    }

    private void llenarTabla(TableView tabla) {
        MysqlConnector sql = null;
        try {
            sql = new MysqlConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<ArrayList<String>> listaRegistros = sql.getEntradas();
        for (ArrayList<String> registro : listaRegistros) {
            tabla.getItems().add(registro);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
