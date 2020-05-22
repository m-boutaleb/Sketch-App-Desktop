package ch.supsi.pss.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class Tag implements Initializable {
    private Stage stage;
    private Set<String> allTags;
    @FXML
    TextField newTag;
    @FXML
    FlowPane allTagsPane;
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }


    public void initData(final Stage tagStage, final Set<String> allTags) {
        this.stage=tagStage;
        this.allTags=allTags;
        allTags.stream().map(Button::new).forEach(allTagsPane.getChildren()::addAll);
    }

    public void saveAndClose(final ActionEvent actionEvent) {
        this.stage.close();
    }

    public void saveTag(final ActionEvent actionEvent) {
        if(newTag.getText().equals("")||allTags.contains(newTag.getText()))
            return;
        allTags.add(newTag.getText());
        allTagsPane.getChildren().add(new Button(newTag.getText()));
    }
}
