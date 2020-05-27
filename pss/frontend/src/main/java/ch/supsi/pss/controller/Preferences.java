package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Theme;
import ch.supsi.pss.utils.DialogUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    TextField loadLocation;
    @FXML
    Button setPreferences;
    @FXML
    CheckBox saveToLoad;
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
        saveLocation.setText(SketchController.getInstance().getPrefPath());
        languageBox.getSelectionModel().select(SketchController.getInstance().getPrefLang());
        themeBox.getSelectionModel().select(SketchController.getInstance().getPrefTheme());
        chooseLoadDir.setOnMouseClicked(this::chooseLoadDir);
        chooseSaveDir.setOnMouseClicked(this::chooseSaveDir);
        languageBox.getItems().addAll(Language.values());
        themeBox.getItems().addAll(Theme.values());
        setPreferences.setOnMouseClicked(this::savePreferences);
        loadLocation.textProperty().addListener(i->saveToLoad.setDisable(loadLocation.getText().equals("")));
        saveToLoad.selectedProperty().addListener(i->saveLocation.setText(saveToLoad.isSelected()?loadLocation.getText():saveLocation.getText()));
    }

    private void displayInputRequest(){
        var res= ResourceBundlePss.getInstance().getLangBundles();
        DialogUtils.displayAlert(res.getString("preferences.title"), res.getString("preferences.error.header"), res.getString("preferences.error.context"));
    }

    private void savePreferences(MouseEvent mouseEvent){
        var sketchController=SketchController.getInstance();
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

        if(loadLocation!=null&&!loadLocation.getText().equals(""))
            sketchController.loadSketchData();

        sketchController.updatePreferences(saveLocation.getText(),languageChosen, themeChosen);
        this.stage.close();
    }

    private void chooseLoadDir(MouseEvent mouseEvent){
        DirectoryChooser loadLocationDir=new DirectoryChooser();
        File file=loadLocationDir.showDialog(new Stage());
        if(loadLocation!=null&&file!=null)
            loadLocation.setText(file.getAbsolutePath());
    }
    private void chooseSaveDir(MouseEvent mouseEvent){
        DirectoryChooser loadLocationDir=new DirectoryChooser();
        File file=loadLocationDir.showDialog(new Stage());
        if(saveLocation!=null&&file!=null)
            saveLocation.setText(file.getAbsolutePath());
    }
}
