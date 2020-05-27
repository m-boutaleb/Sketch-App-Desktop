package ch.supsi.pss.service;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.model.Theme;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface SketchService {

    boolean saveSketch(final UUID uuid, final byte[] sketch, final Set<String> allTags, final Author author, final LocalDateTime timeCreation);

    boolean updateSketch(final byte[] newSketch, final Set<String> allTags, final Author author, final LocalDateTime time);

    void updatePreferences(final String newPrefPathDir, final Language newPrefLanguage, final Theme newPrefTheme);

    boolean loadAllSketchData();

    String getPrefPath();

    Language getPrefLang();

    Sketch openSketch(String path, String sketchName);

    Set<Sketch> getAllSketches();

    Theme getPrefTheme();
}
