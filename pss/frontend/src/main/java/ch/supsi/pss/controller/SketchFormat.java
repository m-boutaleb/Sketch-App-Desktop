package ch.supsi.pss.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
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

    void initData(final Stage stage){
        this.stage=stage;
    }

    @FXML
    private void cancel(final ActionEvent mouseEvent) {
        stage.close();
    }

    @FXML
    private void createSketch(final ActionEvent mouseEvent) {
        final Window window = stage.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
