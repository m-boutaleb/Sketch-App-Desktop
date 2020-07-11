package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Tag implements Initializable {
    private Stage stage;
    private Set<String> allTags;
    @FXML
    private TextField newTag;
    @FXML
    private FlowPane allTagsPane;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    void initData(final Stage tagStage, final Set<String> allTags) {
        this.stage=tagStage;
        this.allTags=allTags;
        allTags.forEach(s->allTagsPane.getChildren().addAll(new Button(s)));
        allTagsPane.getChildren().forEach(t->setContextMenu((Button)t));
    }

    @FXML
    private void saveAndClose(final ActionEvent actionEvent) {
        this.stage.close();
    }

    private void setContextMenu(final Button button){
        final var res= ResourceBundlePss.getInstance().getLangBundles();
        final ContextMenu menu = new ContextMenu();
        final MenuItem item = new MenuItem(res.getString("tag.delete"));
        menu.getItems().add(item);
        button.setContextMenu(menu);
        item.setOnAction(e->{
            allTagsPane.getChildren().remove(button);
            allTags.remove(button.getText());
        });
    }

    @FXML
    private void saveTag(final ActionEvent actionEvent) {
        final var allNewTags=Arrays.asList(newTag.getText().split(";"));

        if(newTag.getText().equals("")||allNewTags.stream().anyMatch(allTags::contains))
            return;

        final var allButtons=allNewTags.stream().map(Button::new).collect(Collectors.toList());
        allButtons.forEach(b->setContextMenu(b));
        allTags.addAll(allNewTags);
        allTagsPane.getChildren().addAll(allButtons);

        newTag.setText("");
    }
}
