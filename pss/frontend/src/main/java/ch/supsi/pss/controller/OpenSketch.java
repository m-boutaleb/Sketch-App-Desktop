package ch.supsi.pss.controller;

import ch.supsi.pss.model.Sketch;
import javafx.stage.Stage;

import java.io.File;

class OpenSketch {

    void openSelectedSketch(final File sketchFile, final Stage owner){
        if(sketchFile!=null) {
            final Sketch current = SketchController.getInstance().openExistingSketch(sketchFile.getPath().replace(sketchFile.getName(), ""), sketchFile.getName());
            new SketchViewer().getAndShowTableView(owner, current);
        }
    }
}