package ch.supsi.pss.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SketchFormat implements Initializable {
    @FXML
    ToggleGroup format;
    private Stage stage;
    private Menu menu;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    public void initData(final Stage stage, final Menu menu){
        this.stage=stage;
        this.menu=menu;
    }

    @FXML
    public void cancel(final MouseEvent mouseEvent) {
        stage.close();
    }

    @FXML
    public void createSketch(final MouseEvent mouseEvent) {
        stage.close();
        menu.initCanvas(((RadioButton)format.getSelectedToggle()).getText());
    }
}
