package Launcher;

import Control.InformationController;
import Control.MainController;
import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainPage.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 666, 475));
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        primaryStage.show();
        new FadeIn(root).play();
    }
}