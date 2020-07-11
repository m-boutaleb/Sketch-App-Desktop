package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.theme.ThemeManager;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Theme;
import ch.supsi.pss.utils.DialogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Preferences implements Initializable {
    private Stage stage;
    @FXML
    private ChoiceBox<Language> languageBox;
    @FXML
    private ChoiceBox<Theme> themeBox;
    @FXML
    private TextField saveLocation;

    private void displayInputRequest(){
        var res= ResourceBundlePss.getInstance().getLangBundles();
        DialogUtils.displayAlert(res.getString("preferences.title"), res.getString("preferences.error.header"), res.getString("preferences.error.context"), stage);
    }

    @FXML
    private void savePreferences(ActionEvent mouseEvent){
        final SketchController sketchController=SketchController.getInstance();
        if(saveLocation ==null||languageBox ==null||themeBox==null||saveLocation.getText() ==null
                ||saveLocation.getText().equals("")||languageBox.getSelectionModel().getSelectedItem()==null
        ||languageBox.getSelectionModel().getSelectedItem()==null){
            displayInputRequest();
            return;
        }
        Language languageChosen=Language.fromStringToLang(languageBox.getSelectionModel().getSelectedItem().toString());
        Theme themeChosen=Theme.fromStringToTheme(themeBox.getSelectionModel().getSelectedItem().toString());

        if(languageChosen!=sketchController.getPrefLang()) {
            var res=ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayAlert(res.getString("preferences.title"), res.getString("preferences.language.update.header"),
                    res.getString("preferences.language.update.context"), stage);
        }
        ThemeManager.getInstance().setBase(themeChosen);
        sketchController.updatePreferences(saveLocation.getText(),languageChosen, themeChosen);
        this.stage.close();
    }

    @FXML
    private void chooseSaveDir(ActionEvent mouseEvent){
        DirectoryChooser loadLocationDir=new DirectoryChooser();
        File file=loadLocationDir.showDialog(new Stage());
        if(saveLocation!=null&&file!=null)
            saveLocation.setText(file.getAbsolutePath());
    }

    void initData(final Stage prefStage) {
        this.stage=prefStage;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        final SketchController controller=SketchController.getInstance();
        saveLocation.setText(controller.getPrefPath());
        languageBox.getSelectionModel().select(controller.getPrefLang());
        themeBox.getSelectionModel().select(controller.getPrefTheme());
        languageBox.getItems().addAll(Language.values());
        themeBox.getItems().addAll(Theme.values());
    }
}
