package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.Admin;
import sample.Model.DataSource;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("View/login&register.fxml"));
//            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("Class Room");
            primaryStage.setScene(new Scene(root, 800, 700));
            primaryStage.setMinWidth(700);
            primaryStage.setMinHeight(600);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        DataSource.getInstance().open();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }
}
