package ch.supsi.pss.controller;

import ch.supsi.pss.model.*;
import ch.supsi.pss.service.*;
import ch.supsi.pss.service.implementation.IdServiceImpl;
import ch.supsi.pss.service.implementation.SketchServiceImpl;
import ch.supsi.pss.service.implementation.TimeServiceImpl;

import java.util.Set;

public class SketchController {
    private static SketchController instance;
    private static SketchService sketchService;
    private final IdService idService;
    private final TimeService timeService;

    private SketchController(){
        sketchService= SketchServiceImpl.getInstance();
        idService= IdServiceImpl.getInstance();
        timeService= TimeServiceImpl.getInstance();
    }

    public Theme getPrefTheme(){
        return sketchService.getPrefTheme();
    }

    public void updatePreferences(final String newPrefPathDir,final Language newPrefLanguage, final Theme newPrefTheme){
        sketchService.updatePreferences(newPrefPathDir, newPrefLanguage, newPrefTheme);
    }

    public Sketch openExistingSketch(final String path, final String sketchName){
        return sketchService.openSketch(path, sketchName);
    }

    public String getPrefPath(){
        return sketchService.getPrefPath();
    }
    
    public Language getPrefLang(){
        return sketchService.getPrefLang();
    }

    public static SketchController getInstance(){
        if(instance==null)
            instance=new SketchController();
        return instance;
    }

    public Set<Sketch> getAllSketches(){
        return sketchService.getAllSketches();
    }

    public void newSketch(final byte[] sketch, final Set<String> allTags, final Author author){
        if(author==null||sketch==null||allTags==null)
            PssLogger.getInstance().error(new IllegalArgumentException("SKETCH IMAGE/TAGS/AUTHOR CANNOT BE NULL"),this.getClass());
        sketchService.saveSketch(idService.newID(), sketch, allTags, author, timeService.getCurrentTime());
    }

    public boolean updateSketch(final byte[] image, final Set<String> allTags, final Author author) {
        return sketchService.updateSketch(image, allTags, author, timeService.getCurrentTime());
    }
}
