package ch.supsi.pss.service.implementation;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Sketch;
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

    private SketchServiceImpl() {
        this.sketchRepository = SketchRepository.getInstance();
        this.preferencesRepository = PreferencesRepository.getInstance();
        this.jsonService = JSONServiceImpl.getInstance();
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
                sketchRepository.saveSketchMetadata(jsonService.fromSketchToJSON(newSketch), prefDir, uuid.toString())&&
                sketchRepository.addSketchToRepo(newSketch);
    }

    @Override
    public boolean updateSketch(final byte[] newSketchImage, final Set<String> allTags, final Author author, final LocalDateTime time) {
        final Sketch newSketch=new Sketch(sketchRepository.getLastSavedSketchUUID(), newSketchImage, allTags, author, time);
        sketchRepository.removeSketchFromRepo(newSketch);
        return saveSketch(newSketch.getUUID(),newSketchImage, allTags, author, time);
    }

    @Override
    public boolean updatePreferences(final String newPrefPathDir, final Language newPrefLanguage) {
        return preferencesRepository.updatePreferences(newPrefPathDir, newPrefLanguage,jsonService.fromPreferencesToJSON(newPrefPathDir, newPrefLanguage.toString()));
    }

    @Override
    public boolean loadPreferences() {
        return preferencesRepository.loadPreferences();
    }

    @Override
    public boolean loadAllSketchData() {
        return sketchRepository.loadAllSketchData(preferencesRepository.getPrefPathDir());
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
        return sketchRepository.openSketch(path, sketchName);
    }

    @Override
    public Set<Sketch> getAllSketches() {
        return sketchRepository.getAllSketches();
    }
}
