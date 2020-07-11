package ch.supsi.pss;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.theme.ThemeManager;
import ch.supsi.pss.model.PssLogger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.util.Scanner;

public class PssFx extends Application {

    @Override
    public void stop(){
        PssLogger.getInstance().info("--------------------APPLICATION PSS FINISHED--------------------", getClass());
    }

    @Override
    public void start(final Stage stage) throws Exception {
        initializePssApp();
        Parent root = FXMLLoader.load(getClass().getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.splashscreen")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void initializePssApp() {
        PssLogger.getInstance().initialize();
        loadAllFonts();
        PssLogger.getInstance().info("--------------------APPLICATION PSS STARTED--------------------", Pss.class);
    }

    public static void setDefaultIconAndThemeAndOwner(final Stage stage, final Stage ownerStage){
        ThemeManager.getInstance().createThemedNode(stage.getScene()::getRoot);
        stage.getIcons().add(new Image(PssFx.class.getResourceAsStream(ResourceBundlePss.getInstance().getPssBundles().getString("img.icon"))));
        stage.initOwner(ownerStage);
    }

    private void loadAllFonts() {
        final var res=ResourceBundlePss.getInstance().getPssBundles();
        Font.loadFont(getClass().getResourceAsStream(res.getString("font.segoeui")), 16);
        Font.loadFont(getClass().getResourceAsStream(res.getString("font.segoeuib")), 16);
    }

    public static void main(String[] args) {
        launch(args);
    }
}