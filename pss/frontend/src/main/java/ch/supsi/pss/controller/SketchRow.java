package ch.supsi.pss.controller;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;


public class SketchRow {
    private final ImageView sketchImage;
    private final ScrollPane sketchTags;

    public SketchRow(final ImageView sketchImage, final ScrollPane sketchTags) {
        this.sketchImage = sketchImage;
        this.sketchTags = sketchTags;
        sketchTags.setFitToWidth(true);
        sketchTags.setFitToHeight(true);
        sketchImage.fitHeightProperty().addListener(i->
                sketchTags.setPrefHeight(sketchImage.getFitHeight()));
    }

    public ImageView getSketchImage() {
        return sketchImage;
    }

    public ScrollPane getSketchTags() {
        return sketchTags;
    }
}
