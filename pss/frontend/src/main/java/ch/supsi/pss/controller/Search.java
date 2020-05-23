package ch.supsi.pss.controller;

import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.search.SketchDisplay;
import ch.supsi.pss.utils.SearchUtilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class Search implements Initializable {
    @FXML
    public TextField searchInput;

    public void search(final MouseEvent mouseEvent) {
        SketchDisplay.getInstance().displayResults(searchInput.getText());
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {

    }
}
