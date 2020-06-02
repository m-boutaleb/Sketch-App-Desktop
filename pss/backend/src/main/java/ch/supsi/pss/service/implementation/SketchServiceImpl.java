package ch.supsi.pss.service.implementation;

import ch.supsi.pss.model.*;
import ch.supsi.pss.repository.PreferencesRepository;
import ch.supsi.pss.repository.SketchRepository;
import ch.supsi.pss.service.JSONService;
import ch.supsi.pss.service.SketchService;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class SketchServiceImpl implements SketchService {
    private static SketchServiceImpl instance;
    private final SketchRepository sketchRepository;
    private final PreferencesRepository preferencesRepository;
    private final JSONService jsonService;
    private final SketchSerializer sketchSerializer;

    private SketchServiceImpl() {
        this.sketchRepository = SketchRepository.getInstance();
        this.preferencesRepository = PreferencesRepository.getInstance();
        this.jsonService = JSONServiceImpl.getInstance();
        this.sketchSerializer=new SketchSerializer();
    }

    public static SketchServiceImpl getInstance() {
        if(instance==null)
            instance= new SketchServiceImpl();
        return instance;
    }

    @Override
    public boolean saveSketch(final UUID uuid, final byte[] sketchImage, final Set<String> allTags, final Author author, final LocalDateTime timeCreation) {
        final Sketch newSketch=new Sketch(sketchImage,uuid,author, timeCreation ,allTags);
        final String prefDir=getPrefPath();
        return sketchRepository.saveSketchImage(sketchImage, prefDir, uuid.toString()) &&
                sketchRepository.saveSketchMetadata(jsonService.fromSketchToJSON(newSketch), prefDir, uuid.toString());
    }

    @Override
    public boolean updateSketch(final byte[] newSketchImage, final Set<String> allTags, final Author author, final LocalDateTime time) {
        final Sketch newSketch=new Sketch(sketchRepository.getLastSavedSketchUUID(
                getAllSketches()), newSketchImage, allTags, author, time);
        return saveSketch(newSketch.getUUID(),newSketchImage, allTags, author, time);
    }

    @Override
    public void updatePreferences(final String newPrefPathDir, final Language newPrefLanguage, final Theme newPrefTheme) {
        preferencesRepository.updatePreferences(newPrefPathDir, newPrefLanguage, newPrefTheme);
    }

    @Override
    public String getPrefPath() {
        return preferencesRepository.getPrefPathDir();
    }

    @Override
    public Language getPrefLang() {
        return preferencesRepository.getPrefLanguage();
    }

    @Override
    public Sketch openSketch(final String path, final String sketchName) {
        return sketchRepository.openSketch(path, sketchName, sketchSerializer);
    }

    @Override
    public Set<Sketch> getAllSketches() {
        return sketchRepository.getAllSketches(preferencesRepository.getPrefPathDir(), sketchSerializer);
    }

    @Override
    public Theme getPrefTheme() {
        return preferencesRepository.getPrefTheme();
    }
}
