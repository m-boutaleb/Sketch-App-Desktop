package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che si occupa di lanciare lo splash screen e di lanciare la schermata che si occupa
 * di chiedere le info all'utente
 */
public class SplashScreen implements Initializable {
    public Stage stage = new Stage();

    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        new UserInfoLauncher().start();
    }

    /**
     * Nested class che lancia il menu principale insieme alle preferenze
     */
    class UserInfoLauncher extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        FXMLLoader loader=new FXMLLoader(getClass().getResource(ResourceBundlePss.getInstance().
                                getPssBundles().getString("fxml.menu")),ResourceBundlePss.getInstance().getLangBundles());
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        PssFx.setDefaultIcon(stage);
                        Scene scene = new Scene(root);
                        stage.setTitle("Sketch App");
                        stage.setScene(scene);
                        stage.setHeight(600);
                        stage.setWidth(800);
                        stage.show();
                        loader.<Menu>getController().initData(stage);
                        stackPane.getScene().getWindow().hide();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
