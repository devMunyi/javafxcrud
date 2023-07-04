package com.example.fxmysqlcrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = null;
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/student.fxml"));
            root = loader.load();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assert root != null;
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX CRUD with MySQL");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        stage.setScene(scene);
        stage.show();
    }
}
