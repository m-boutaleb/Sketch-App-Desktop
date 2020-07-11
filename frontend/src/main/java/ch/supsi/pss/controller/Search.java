package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.utils.DialogUtils;
import ch.supsi.pss.utils.SearchUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class Search implements Initializable {
    @FXML
    private TextField searchInput;
    private Stage stage;

    @FXML
    private void search(final ActionEvent mouseEvent) {
        final var textToSearch=searchInput.getText();
        final Set<Sketch> allSketch = SketchController.getInstance().getAllSketches();
        final Set<Sketch> results = SearchUtils.searchSketchByTag(allSketch, textToSearch);

        if (!textToSearch.equals("") && !results.isEmpty()) {
            final Sketch array[]=new Sketch[results.size()];
            new SketchViewer().getAndShowTableView(stage, results.toArray(array));
        }else{
            final ResourceBundle res= ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayAlert(res.getString("search.title"), res.getString("search.header")+"'"+textToSearch+"'" , res.getString("search.context"), stage);
        }
    }

    void initData(final Stage stage){
        this.stage=stage;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }
}
