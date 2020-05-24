package ch.supsi.pss.controller;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Sketch;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import java.io.ByteArrayInputStream;
import java.util.List;

public class SketchViewer {
    private final double THUMB_WIDTH = 70;
    private final double THUMB_HEIGHT = 70;
    private Stage stage;

    private Image parseImage(final byte[] image){
        return new Image(new ByteArrayInputStream(image));
    }

    private ImageView getImageThumb(final Image image){
        ImageView thumb = new ImageView(image);
        thumb.setFitHeight(THUMB_HEIGHT);
        thumb.setFitWidth(THUMB_WIDTH);
        return thumb;
    }

    public void getAndShowTableView(final Sketch... sketch){
        stage=new Stage();
        TableView<Sketch> resultTable = getTableView(sketch);
        stage.setTitle(ResourceBundlePss.getInstance().getLangBundles().getString("sketch.show"));
        PssFx.setDefaultIcon(stage);
        final ScrollPane pane= new ScrollPane(resultTable);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableView<Sketch> getTableView(final Sketch... results){
        var langRes=ResourceBundlePss.getInstance();
        TableView<Sketch> resultTable = new TableView();
        TableColumn<Sketch, ImageView> thumbColumn = new TableColumn(langRes.getString("tableview.column.sketch.image"));
        TableColumn<Sketch, TextArea> tagsColumn = new TableColumn<>(langRes.getLangBundles().getString("tableview.column.sketch.tags"));

        thumbColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sketch, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Sketch, ImageView> sketchImageViewCellDataFeatures) {
                Sketch current = sketchImageViewCellDataFeatures.getValue();
                ImageView thumb = getImageThumb(parseImage(current.getImage()));
                thumb.setSmooth(true);
                thumb.setCache(true);
                stage.widthProperty().addListener(i->{
                    var thumbWidth=stage.getWidth()*50/100;
                    thumb.setFitWidth(thumbWidth);
                    thumb.setFitHeight(thumbWidth*thumb.getImage().getHeight()/thumb.getImage().getWidth());
                });
                return new ObservableObjectValue<ImageView>() {
                    @Override
                    public ImageView get() {
                        return thumb;
                    }

                    @Override
                    public void addListener(ChangeListener<? super ImageView> changeListener) {
                    }

                    @Override
                    public void removeListener(ChangeListener<? super ImageView> changeListener) {
                    }

                    @Override
                    public ImageView getValue() {
                        return thumb;
                    }

                    @Override
                    public void addListener(InvalidationListener invalidationListener) {
                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {
                    }
                };
            }
        });


        tagsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sketch, TextArea>, ObservableValue<TextArea>>() {
            @Override
            public ObservableValue<TextArea> call(TableColumn.CellDataFeatures<Sketch, TextArea> sketchTextAreaCellDataFeatures) {
                Sketch currentSketch = sketchTextAreaCellDataFeatures.getValue();
                List<String> tagList = currentSketch.getAllTags();
                TextArea allTags = new TextArea();
                allTags.setEditable(false);

                for (String tag : tagList)
                    allTags.setText(allTags.getText()
                            + tag + "\n");

                return new ObservableObjectValue<TextArea>() {
                    @Override
                    public TextArea get() {
                        return allTags;
                    }

                    @Override
                    public void addListener(ChangeListener<? super TextArea> changeListener) {
                    }

                    @Override
                    public void removeListener(ChangeListener<? super TextArea> changeListener) {
                    }

                    @Override
                    public TextArea getValue() {
                        return allTags;
                    }

                    @Override
                    public void addListener(InvalidationListener invalidationListener) {
                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {
                    }
                };
            }
        });

        resultTable.getColumns().add(thumbColumn);
        resultTable.getColumns().add(tagsColumn);

        for(Sketch current: results)
            resultTable.getItems().add(current);

        resultTable.setOnMousePressed(event->{
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    var sketchImage=new ImageView(parseImage(resultTable.getSelectionModel().getSelectedItem().getImage()));
                    var sketchStage=new Stage();
                    PssFx.setDefaultIcon(sketchStage);
                    sketchStage.setScene(new Scene(new BorderPane(sketchImage) ,sketchImage.getImage().getWidth(), sketchImage.getImage().getHeight()));
                    sketchStage.show();
                }
        });

        stage.widthProperty().addListener(i->{
                    tagsColumn.setPrefWidth(stage.getWidth()*50/100);
                    thumbColumn.setPrefWidth(stage.getWidth()*50/100);
                });
        stage.setMinWidth(400);
        stage.setMinHeight(300);

        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return resultTable;
    }
}