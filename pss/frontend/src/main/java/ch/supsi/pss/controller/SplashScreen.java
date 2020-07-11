package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.utils.AppUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static ch.supsi.pss.utils.AppUtils.*;

public class SplashScreen implements Initializable {
    private final Stage stage = new Stage();

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        new PssLauncher().start();
    }

    class PssLauncher extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(SPLASH_SCREEN_SLEEP);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final var fxmlFile=ResourceBundlePss.getInstance().
                                getPssBundles().getString("fxml.menu");
                        final var loader= AppUtils.getFXMLLoader(fxmlFile);
                        final Scene scene = new Scene(loader.getRoot());
                        stage.setTitle(ResourceBundlePss.getInstance().getLangBundles().getString("application.name"));
                        stage.setScene(scene);
                        PssFx.setDefaultIconAndThemeAndOwner(stage, null);
                        stage.setHeight(DEFAULT_MAIN_PAGE_HEIGHT);
                        stage.setWidth(DEFAULT_MAIN_PAGE_WIDTH);
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
