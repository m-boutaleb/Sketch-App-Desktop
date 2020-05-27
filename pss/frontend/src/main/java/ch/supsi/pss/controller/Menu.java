package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.utils.DialogUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    @FXML
    public BorderPane borderPane;
    @FXML
    public MenuItem saveOption;
    private Stage stage;
    private NewSketch newSketchController;


    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    @FXML
    public void newSketch(final ActionEvent actionEvent) {
        checkIfAllSaved();
        final Stage formatStage= new Stage();
        Parent sketchFormat = null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource(ResourceBundlePss.getInstance().getPssBundles().
                getString("fxml.sketchformat")), ResourceBundlePss.getInstance().getLangBundles());
        try {
            sketchFormat = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loader.<SketchFormat>getController().initData(formatStage, this);
        formatStage.setScene(new Scene(sketchFormat));
        formatStage.initModality(Modality.APPLICATION_MODAL);
        PssFx.setDefaultIconAndTheme(formatStage);
        formatStage.setResizable(false);
        formatStage.show();
    }

    @FXML
    public void openSketch(final ActionEvent actionEvent) {
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SKETCH files (*.skt)","*.skt");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage.getScene().getWindow());
        OpenSketch sketchOpener = new OpenSketch();
        sketchOpener.openSelectedSketch(selectedFile);
    }

    public void initData(final Stage stage){
        this.stage=stage;
        preferences(null);
    }

    @FXML
    public void close(final ActionEvent actionEvent) {
        exitApplication(actionEvent);
    }

    @FXML
    public void exitApplication(ActionEvent actionEvent){
        checkIfAllSaved();
        Platform.exit();
    }

    private void checkIfAllSaved() {
        if(newSketchController!=null&&newSketchController.isDrawing()) {
            var res = ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayYesNoAlert(newSketchController, stage, res.getString("application.quit"),
                    res.getString("application.quit.header"),
                    res.getString("application.quit.context"));
        }
    }

    public void initCanvas(final String text) {
        saveOption.setDisable(false);
        var canvas=new NewSketch(text, stage);
        FXMLLoader loader=new FXMLLoader(getClass().getResource(ResourceBundlePss.getInstance().
                getPssBundles().getString("fxml.newsketch")), ResourceBundlePss.getInstance().getLangBundles());
        Parent root = null;
        try {
            loader.setController(canvas);
            root=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newSketchController=loader.getController();
        borderPane.setCenter(root);
        stage.setResizable(false);
    }

    @FXML
    public void preferences(final ActionEvent actionEvent) {
        stage.setOnCloseRequest(event->exitApplication(null));
        final Stage prefStage= new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().
                getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.preferences")), ResourceBundlePss.getInstance().getLangBundles());
        final Preferences preferencesController=new Preferences(prefStage);
        Parent root=null;
        try {
            loader.setController(preferencesController);
            root=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefStage.setScene(new Scene(root));
        prefStage.initModality(Modality.APPLICATION_MODAL);
        prefStage.setResizable(false);
        PssFx.setDefaultIconAndTheme(prefStage);
        prefStage.show();
    }

    public void about(final ActionEvent actionEvent) {
        final Stage aboutStage=new Stage();
        Parent root=null;
        try {
            root=FXMLLoader.load(getClass().getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.about")), ResourceBundlePss.getInstance().getLangBundles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        aboutStage.setScene(new Scene(root));
        PssFx.setDefaultIconAndTheme(aboutStage);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.setTitle(ResourceBundlePss.getInstance().getLangBundles().getString("about.title"));
        aboutStage.show();
    }

    public void find(final ActionEvent actionEvent) {
        final Stage findStage=new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().
                getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.search")), ResourceBundlePss.getInstance().getLangBundles());
        Parent root=null;
        try {
            root=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        findStage.setScene(new Scene(root));
        PssFx.setDefaultIconAndTheme(findStage);
        findStage.initModality(Modality.APPLICATION_MODAL);
        findStage.setResizable(false);
        findStage.show();
    }

    public void tag(final ActionEvent actionEvent) {
        if(!checkSketch()) {
            var res=ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayAlert(res.getString("tag.title"),
                    res.getString("tag.error.header")  , res.getString("tag.error.context"));
            return;
        }
        final Stage tagStage=new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().
                getResource(ResourceBundlePss.getInstance().getPssBundles().getString("fxml.tag")), ResourceBundlePss.getInstance().getLangBundles());
        Parent root=null;
        try {
            root=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loader.<Tag>getController().initData(tagStage, newSketchController.getAllTags());
        tagStage.setScene(new Scene(root));
        PssFx.setDefaultIconAndTheme(tagStage);
        tagStage.setResizable(false);
        tagStage.initModality(Modality.APPLICATION_MODAL);
        tagStage.show();
    }

    private boolean checkSketch() {
        return newSketchController != null;
    }

    public void save(final ActionEvent actionEvent) {
        newSketchController.save(null);
    }
}
