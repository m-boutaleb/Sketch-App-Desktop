package ch.supsi.pss;

import ch.supsi.pss.bundles.ResourceBundlePss;
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
        authenticateUser();
        initializePssApp();
        Parent root = FXMLLoader.load(getClass().getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.splashscreen")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void authenticateUser() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("IMMETTI PASSWORD DI GRUPPO: ");
        String inputUser;
        while(!(inputUser=scanner.nextLine()).equals("POLLO VENDETTA")&&!(inputUser.equals("PV")));
    }

    private void initializePssApp() {
        PssLogger.getInstance().initialize();
        loadAllFonts();
        PssLogger.getInstance().info("--------------------APPLICATION PSS STARTED--------------------", Pss.class);
    }

    public static void setDefaultIconAndTheme(final Stage stage){
        String propertyThemeToLoad = ResourceBundlePss.getInstance().getCurrentPropertyTheme();
        stage.getScene().getStylesheets().add(propertyThemeToLoad);
        stage.getIcons().add(new Image(PssFx.class.getResourceAsStream(ResourceBundlePss.getInstance().getPssBundles().getString("img.icon"))));
    }

    private void loadAllFonts() {
        Font.loadFont(PssFx.class
                //Secondo argomento rappresenta un font-size preso a caso
                .getResourceAsStream(ResourceBundlePss.getInstance().getPssBundles().getString("font.segoeui")), 12);
        Font.loadFont(PssFx.class
                .getResourceAsStream(ResourceBundlePss.getInstance().getPssBundles().getString("font.segoeuib")), 12);
        Font.loadFont(PssFx.class
                .getResourceAsStream(ResourceBundlePss.getInstance().getPssBundles().getString("font.segoeuii")), 12);
    }

    public static void main(String[] args) {
        launch(args);
    }
}