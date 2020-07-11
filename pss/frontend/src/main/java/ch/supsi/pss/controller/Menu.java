package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.utils.AppUtils;
import ch.supsi.pss.utils.DialogUtils;
import ch.supsi.pss.utils.FileUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem saveOption;
    private Stage stage;
    //variabile utilizzata per controllare se dare all'utente la possibilità di chiudere lo stage Preferences
    private boolean firstSetup;
    /**
     * Instanza della classe newSketchController  che serve per controllare se è stato salvato
     * tutto correttamente prima di chiudere l'applicazione
     */
    private NewSketch newSketchController;



    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        firstSetup=true;
    }

    @FXML
    private void newSketch(final ActionEvent actionEvent) {
        checkIfAllSaved();
        final Stage formatStage= new Stage();
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.sketchformat");
        final var loader= AppUtils.getFXMLLoader(fxmlFile);
        formatStage.setScene(new Scene(loader.getRoot()));
        loader.<SketchFormat>getController().initData(formatStage);
        formatStage.setOnCloseRequest(e->initCanvas(
                ((RadioButton)loader.<SketchFormat>getController().format.getSelectedToggle())
                        .getText()));
        formatStage.initModality(Modality.APPLICATION_MODAL);
        PssFx.setDefaultIconAndThemeAndOwner(formatStage, stage);
        formatStage.setResizable(false);
        formatStage.show();
    }

    @FXML
    private void openSketch(final ActionEvent actionEvent) {
        final var res=ResourceBundlePss.getInstance().getLangBundles();
        // Set extension filter
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(res.getString("menu.open.sketch.format"), "*"+FileUtils.SKETCH_EXTENSION);
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        final File selectedFile = fileChooser.showOpenDialog(stage.getScene().getWindow());
        final OpenSketch sketchOpener = new OpenSketch();
        sketchOpener.openSelectedSketch(selectedFile, stage);
    }

    void initData(final Stage stage){
        this.stage=stage;
        preferences(null);
    }

    @FXML
    private void close(final ActionEvent actionEvent) {
        exitApplication(actionEvent);
    }

    @FXML
    private void exitApplication(ActionEvent actionEvent){
        checkIfAllSaved();
        Platform.exit();
    }

    private void checkIfAllSaved() {
        if(checkSketch()&&newSketchController.isDrawing()) {
            final var res = ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayYesNoAlert(newSketchController, stage, res.getString("application.quit"),
                    res.getString("application.quit.header"),
                    res.getString("application.quit.context"));
        }
    }

    private void initCanvas(final String text) {
        saveOption.setDisable(false);
        final var canvas=new NewSketch(text, stage);
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.newsketch");
        final var loader= AppUtils.getFXMLLoader(fxmlFile, canvas);
        loader.setController(canvas);
        newSketchController=loader.getController();
        borderPane.setCenter(loader.getRoot());
        stage.setResizable(false);
    }

    @FXML
    private void preferences(final ActionEvent actionEvent) {
        stage.setOnCloseRequest(event->exitApplication(null));
        final Stage prefStage= new Stage();
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.preferences");
        final var loader= AppUtils.getFXMLLoader(fxmlFile);
        prefStage.setScene(new Scene(loader.getRoot()));
        loader.<Preferences>getController().initData(prefStage);
        prefStage.initModality(Modality.APPLICATION_MODAL);
        prefStage.setResizable(false);
        PssFx.setDefaultIconAndThemeAndOwner(prefStage, stage);
        if(firstSetup) {
            firstSetup= false;
            prefStage.setOnCloseRequest(Event::consume);
        }
        prefStage.show();
    }

    @FXML
    private void about(final ActionEvent actionEvent) {
        final Stage aboutStage=new Stage();
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.about");
        final var loader= AppUtils.getFXMLLoader(fxmlFile);
        aboutStage.setScene(new Scene(loader.getRoot()));
        PssFx.setDefaultIconAndThemeAndOwner(aboutStage, stage);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.setTitle(ResourceBundlePss.getInstance().getLangBundles().getString("about.title"));
        aboutStage.show();
    }

    @FXML
    private void find(final ActionEvent actionEvent) {
        final Stage findStage=new Stage();
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.search");
        final var loader= AppUtils.getFXMLLoader(fxmlFile);
        loader.<Search>getController().initData(findStage);
        findStage.setScene(new Scene(loader.getRoot()));
        PssFx.setDefaultIconAndThemeAndOwner(findStage, stage);
        findStage.initModality(Modality.APPLICATION_MODAL);
        findStage.setResizable(false);
        findStage.show();
    }

    @FXML
    private void tag(final ActionEvent actionEvent) {
        var res=ResourceBundlePss.getInstance().getLangBundles();
        if(!checkSketch()) {
            DialogUtils.displayAlert(res.getString("tag.title"),
                    res.getString("tag.error.header")  , res.getString("tag.error.context"), stage);
            return;
        }
        final Stage tagStage=new Stage();
        final var fxmlFile=ResourceBundlePss.getInstance().getPssBundles().getString("fxml.tag");
        final var loader= AppUtils.getFXMLLoader(fxmlFile);
        loader.<Tag>getController().initData(tagStage, newSketchController.getAllTags());
        tagStage.setScene(new Scene(loader.getRoot()));
        PssFx.setDefaultIconAndThemeAndOwner(tagStage, stage);
        tagStage.setResizable(false);
        tagStage.initModality(Modality.APPLICATION_MODAL);
        tagStage.initOwner(stage);
        tagStage.show();
    }

    private boolean checkSketch() {
        return newSketchController != null;
    }

    @FXML
    private void save(final ActionEvent actionEvent) {
        newSketchController.save(null);
    }
}
