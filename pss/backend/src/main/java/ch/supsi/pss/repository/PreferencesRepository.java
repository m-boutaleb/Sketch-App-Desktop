package ch.supsi.pss.repository;

import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.PssLogger;
import java.util.prefs.Preferences;

public class PreferencesRepository {
    private static PreferencesRepository instance;
    private static final String USER_PREF_LANGUAGE="user.language";
    private static final String USER_PREF_PATH="user.path";
    private final Preferences prefs;
    private String prefPathDir;
    private Language prefLanguage;

    private PreferencesRepository(){
        prefs=Preferences.userRoot().node(this.getClass().getName());
    }

    public Language getPrefLanguage() {
        PssLogger.getInstance().info("LOADING PREF LANGUAGE...", getClass());
        prefLanguage=Language.fromStringToEnum(prefs.get(USER_PREF_LANGUAGE ,""));
        return prefLanguage;
    }

    public String getPrefPathDir() {
        PssLogger.getInstance().info("LOADING PREF PATH...", getClass());
        prefPathDir=prefs.get(USER_PREF_PATH, "");
        return prefPathDir;
    }

    public static PreferencesRepository getInstance() {
        if(instance==null)
            instance=new PreferencesRepository();
        return instance;
    }

    public void loadPreferences(){
        prefPathDir=prefs.get(USER_PREF_PATH, "");
    }

    public void updatePreferences(final String newPrefPath,final Language newPrefLanguage){
        PssLogger.getInstance().info("UPDATING PREFERENCES...", getClass());
        prefs.put(USER_PREF_LANGUAGE, newPrefLanguage.toString());
        prefs.put(USER_PREF_PATH, newPrefPath);
        loadPreferences();
    }
}
