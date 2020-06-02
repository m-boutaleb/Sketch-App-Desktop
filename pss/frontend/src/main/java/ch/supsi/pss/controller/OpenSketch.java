package ch.supsi.pss.controller;

import ch.supsi.pss.model.Sketch;
import java.io.File;

public class OpenSketch {

    public void openSelectedSketch(final File sketchFile){
        if(sketchFile!=null) {
            Sketch current = SketchController.getInstance().openExistingSketch(sketchFile.getPath().replace(sketchFile.getName(), ""), sketchFile.getName());
            System.out.println(current);
            new SketchViewer().getAndShowTableView(current);
        }
    }
}