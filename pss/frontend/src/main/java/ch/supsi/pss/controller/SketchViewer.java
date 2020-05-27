package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Sketch;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SketchViewer {
    private Stage stage;

    private ImageView parseImage(final byte[] image){
        return new ImageView(new Image(new ByteArrayInputStream(image)));
    }

    public void getAndShowTableView(final Sketch... sketch){
        stage=new Stage();
        TableView<SketchRow> resultTable = getTableView(sketch);
        stage.setTitle(ResourceBundlePss.getInstance().getLangBundles().getString("sketch.show"));
        final ScrollPane pane= new ScrollPane(resultTable);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        stage.setScene(new Scene(pane));
        PssFx.setDefaultIconAndTheme(stage);
        stage.show();
    }

    private TableView<SketchRow> getTableView(final Sketch... results){
        var allTagsTextArea=Arrays.stream(results).map(i->new TextArea( i.getAllTags().stream().reduce("", (s1, s2)->s1.concat("\n").concat(s2)))).collect(Collectors.toList());
        allTagsTextArea.forEach(ta->ta.setEditable(false));

        var allRows= IntStream.range(0, results.length).filter(i->results[i]!=null).mapToObj(i->new SketchRow(parseImage(results[i].getImage()),
               new ScrollPane(allTagsTextArea.get(i)))).collect(Collectors.toSet());

        var langRes=ResourceBundlePss.getInstance().getLangBundles();

        TableView<SketchRow> resultTable = new TableView();
        TableColumn<SketchRow, ImageView> thumbColumn = new TableColumn(langRes.getString("tableview.column.sketch.image"));
        TableColumn<SketchRow, ScrollPane> tagsColumn = new TableColumn<>(langRes.getString("tableview.column.sketch.tags"));

        thumbColumn.setCellValueFactory(new PropertyValueFactory<>("sketchImage"));
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("sketchTags"));

        resultTable.getColumns().add(thumbColumn);
        resultTable.getColumns().add(tagsColumn);

        allRows.forEach(resultTable.getItems()::add);

        resultTable.setOnMousePressed(event->{
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    var sketchImage=new ImageView(resultTable.getSelectionModel().getSelectedItem().getSketchImage().getImage());
                    var sketchStage=new Stage();
                    sketchStage.setScene(new Scene(new BorderPane(sketchImage) ,sketchImage.getImage().getWidth(), sketchImage.getImage().getHeight()));
                    PssFx.setDefaultIconAndTheme(sketchStage);
                    sketchStage.show();
                }
        });

        stage.widthProperty().addListener(i->{
            resultTable.getItems().stream().forEach(this::setImageWidth);
            tagsColumn.setPrefWidth(stage.getWidth()*50/100);
            thumbColumn.setPrefWidth(stage.getWidth()*50/100);
        });

        stage.setMinWidth(500);
        stage.setMinHeight(300);

        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return resultTable;
    }

    private void setImageWidth(final SketchRow sketchRow) {
        var sketchImage=sketchRow.getSketchImage();
        var stageHalfWidth=stage.getWidth()*50/100;
        sketchImage.setFitWidth(stageHalfWidth);
        sketchImage.setFitHeight(stageHalfWidth*sketchImage.getImage().getHeight()/sketchImage.getImage().getWidth());
    }
}