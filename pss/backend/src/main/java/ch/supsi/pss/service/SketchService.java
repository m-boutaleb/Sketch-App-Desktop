package ch.supsi.pss.service;

import ch.supsi.pss.model.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface SketchService {

    boolean saveSketch(final UUID uuid, final byte[] sketch, final Set<String> allTags, final Author author, final LocalDateTime timeCreation);

    boolean updateSketch(final byte[] newSketch, final Set<String> allTags, final Author author, final LocalDateTime time);

    void updatePreferences(final String newPrefPathDir, final Language newPrefLanguage, final Theme newPrefTheme);

    String getPrefPath();

    Language getPrefLang();

    Sketch openSketch(final String path,final String sketchName);

    Set<Sketch> getAllSketches();

    Theme getPrefTheme();
}
