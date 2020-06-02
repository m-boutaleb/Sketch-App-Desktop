package ch.supsi.pss.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SketchFormat implements Initializable {
    @FXML
    ToggleGroup format;
    private Stage stage;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    public void initData(final Stage stage){
        this.stage=stage;
    }

    @FXML
    public void cancel(final MouseEvent mouseEvent) {
        stage.close();
    }

    @FXML
    public void createSketch(final MouseEvent mouseEvent) {
        Window window = stage.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
