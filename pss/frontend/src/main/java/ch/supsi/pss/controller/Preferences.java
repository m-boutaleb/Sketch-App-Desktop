package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Theme;
import ch.supsi.pss.utils.DialogUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Preferences {
    private final Stage stage;
    @FXML
    ChoiceBox<Language> languageBox;
    @FXML
    ChoiceBox<Theme> themeBox;
    @FXML
    TextField saveLocation;
    @FXML
    Button setPreferences;
    @FXML
    Button chooseLoadDir;
    @FXML
    Button chooseSaveDir;

    public Preferences(final Stage prerefencesStage) {
        this.stage=prerefencesStage;
        stage.setOnCloseRequest(Event::consume);
    }

    @FXML
    public void initialize(){
        final SketchController controller=SketchController.getInstance();
        saveLocation.setText(controller.getPrefPath());
        languageBox.getSelectionModel().select(controller.getPrefLang());
        themeBox.getSelectionModel().select(controller.getPrefTheme());
        chooseSaveDir.setOnMouseClicked(this::chooseSaveDir);
        languageBox.getItems().addAll(Language.values());
        themeBox.getItems().addAll(Theme.values());
        setPreferences.setOnMouseClicked(this::savePreferences);
    }

    private void displayInputRequest(){
        var res= ResourceBundlePss.getInstance().getLangBundles();
        DialogUtils.displayAlert(res.getString("preferences.title"), res.getString("preferences.error.header"), res.getString("preferences.error.context"));
    }

    private void savePreferences(MouseEvent mouseEvent){
        final SketchController sketchController=SketchController.getInstance();
        if(saveLocation ==null||languageBox ==null||themeBox==null||saveLocation.getText() ==null
                ||saveLocation.getText().equals("")||languageBox.getSelectionModel().getSelectedItem()==null
        ||languageBox.getSelectionModel().getSelectedItem()==null){
            displayInputRequest();
            return;
        }
        Language languageChosen=Language.fromStringToLang(languageBox.getSelectionModel().getSelectedItem().toString());
        Theme themeChosen=Theme.fromStringToTheme(themeBox.getSelectionModel().getSelectedItem().toString());

        if(languageChosen!=SketchController.getInstance().getPrefLang()||themeChosen!=SketchController.getInstance().getPrefTheme()) {
            var res=ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayAlert(res.getString("preferences.title"), res.getString("preferences.language.update.header"),
                    res.getString("preferences.language.update.context"));
        }

        sketchController.updatePreferences(saveLocation.getText(),languageChosen, themeChosen);
        this.stage.close();
    }

    private void chooseSaveDir(MouseEvent mouseEvent){
        DirectoryChooser loadLocationDir=new DirectoryChooser();
        File file=loadLocationDir.showDialog(new Stage());
        if(saveLocation!=null&&file!=null)
            saveLocation.setText(file.getAbsolutePath());
    }
}
