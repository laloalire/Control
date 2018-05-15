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
import javafx.scene.image.Image;
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
        stage.setTitle("Fastware Key - Visualizar Registros");
        stage.getIcons().add(new Image("/Imagenes/icono.png"));
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
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ObservableList<TableColumn> tableColumns = tableView.getColumns();
        tableColumns.get(0).setPrefWidth(tableView.getWidth()/25); // aula
        tableColumns.get(1).setPrefWidth(tableView.getWidth()/15); // Fecha
        tableColumns.get(2).setPrefWidth(tableView.getWidth()/15); // Entrada
        tableColumns.get(3).setPrefWidth(tableView.getWidth()/15); // Salida
        tableColumns.get(4).setPrefWidth(tableView.getWidth()/11); // Nombre docente
        tableColumns.get(5).setPrefWidth(tableView.getWidth()/11); // Apellido paterno
        tableColumns.get(6).setPrefWidth(tableView.getWidth()/11); // Apellido materno
        tableColumns.get(7).setPrefWidth(tableView.getWidth()/6); // asignatura
        tableColumns.get(8).setPrefWidth(tableView.getWidth()/6); // carrera
        tableColumns.get(9).setPrefWidth(tableView.getWidth()/14); // cant hombres
        tableColumns.get(10).setPrefWidth(tableView.getWidth()/12.6); // cant mujeres
    }



    public void setUpTabla(TableView tabla) {
        TableColumn<ArrayList, String> aula = new TableColumn<>("Aula");
        aula.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(0) + ""));
        aula.setPrefWidth(80);
        aula.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> fecha = new TableColumn<>("Fecha");
        fecha.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(1) + ""));
        fecha.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> entrada = new TableColumn<>("Entrada");
        entrada.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(2) + ""));
        entrada.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> salida = new TableColumn<>("Salida");
        salida.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(3) + ""));
        salida.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> nombre = new TableColumn<>("Nombre Docente");
        nombre.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(4) + ""));
        nombre.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> apellidoPaterno = new TableColumn<>("Apellido Paterno");
        apellidoPaterno.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(5) + ""));
        apellidoPaterno.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> apellidoMaterno = new TableColumn<>("Apellido Materno");
        apellidoMaterno.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(6) + ""));
        apellidoMaterno.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> asignatura = new TableColumn<>("Asignatura");
        asignatura.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(7) + ""));
        asignatura.setStyle("-fx-alignment: center;");
        TableColumn<ArrayList, String> carrera = new TableColumn<>("Carrera");
        carrera.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(8) + ""));
        carrera.setStyle("-fx-alignment: center;");

        TableColumn<ArrayList, String> cantidadHombres = new TableColumn<>("Cant. de Hombres");
        cantidadHombres.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(9) + ""));
        cantidadHombres.setPrefWidth(80);
        cantidadHombres.setStyle("-fx-alignment: center;");

        TableColumn<ArrayList, String> cantidadMujeres = new TableColumn<>("Cant. de Mujeres");
        cantidadMujeres.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(10) + ""));
        cantidadMujeres.setStyle("-fx-alignment: center;");
        tabla.getColumns().addAll(aula, fecha,entrada,salida,nombre,apellidoPaterno, apellidoMaterno,asignatura, carrera, cantidadHombres, cantidadMujeres);
        tabla.setPrefWidth(1000);


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
