package ch.supsi.pss.search;

import ch.supsi.pss.model.Sketch;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public class SearchResult {

    private static final double THUMB_WIDTH = 70;
    private static final double THUMB_HEIGHT = 70;

    private static SearchResult searchResult;

    public static SearchResult getSearchResultInstance(){
        if(searchResult == null)
            searchResult = new SearchResult();
        return searchResult;
    }

    Image parseImage(final byte[] image){
        return new Image(new ByteArrayInputStream(image));
    }

     ImageView getImageThumb(final Image image){
        ImageView thumb = new ImageView(image);
        thumb.setFitHeight(THUMB_HEIGHT);
        thumb.setFitWidth(THUMB_WIDTH);
        return thumb;
    }

    public ScrollPane getResultTable(final Set<Sketch> results){

        TableView<Sketch> resultTable= new TableView();
        TableColumn<Sketch,ImageView> thumbColumn = new TableColumn("Thumb");

        thumbColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sketch, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Sketch, ImageView> sketchImageViewCellDataFeatures) {
                Sketch current = sketchImageViewCellDataFeatures.getValue();
                ImageView thumb = getImageThumb(parseImage(current.getImage()));
                return  new ObservableObjectValue<ImageView>() {
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

        TableColumn<Sketch, TextArea> tags = new TableColumn<>("Tags");


        tags.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sketch, TextArea>, ObservableValue<TextArea>>() {
            @Override
            public ObservableValue<TextArea> call(TableColumn.CellDataFeatures<Sketch, TextArea> sketchTextAreaCellDataFeatures) {
                Sketch currentSketch =  sketchTextAreaCellDataFeatures.getValue();
                List<String> tagList = currentSketch.getAllTags();
                TextArea allTags= new TextArea();
                allTags.setEditable(false);

                for(String tag: tagList){
                    allTags.setText(allTags.getText()
                    + tag + "\n");
                }
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
        resultTable.getColumns().add(tags);

        
        for(Sketch current: results){
            resultTable.getItems().add(current);
        }


        ScrollPane pane = new ScrollPane(resultTable);
        pane.setFitToWidth(true);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TestThumb.stageTest.widthProperty().addListener(i->{
                tags.setPrefWidth(TestThumb.stageTest.getWidth()*50/100);
                thumbColumn.setPrefWidth(TestThumb.stageTest.getWidth()*50/100);
                pane.setPrefWidth(TestThumb.stageTest.getWidth());
            }
        );

        return pane;
    }
}
