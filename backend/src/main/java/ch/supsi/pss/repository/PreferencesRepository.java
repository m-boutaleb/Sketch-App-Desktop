package ch.supsi.pss.repository;

import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.PssLogger;
import ch.supsi.pss.model.Theme;

import java.util.prefs.Preferences;

public class PreferencesRepository {
    private static PreferencesRepository instance;
    private static final String USER_PREF_THEME = "user.theme";
    private static final String USER_PREF_LANGUAGE="user.language";
    private static final String USER_PREF_PATH="user.path";
    private final Preferences prefs;

    private PreferencesRepository(){
        prefs=Preferences.userRoot().node(this.getClass().getName());
    }

    public Language getPrefLanguage() {
        PssLogger.getInstance().info("LOADING PREF LANGUAGE...", getClass());
        return Language.fromStringToLang(prefs.get(USER_PREF_LANGUAGE ,""));
    }

    public String getPrefPathDir() {
        PssLogger.getInstance().info("LOADING PREF PATH...", getClass());
        return prefs.get(USER_PREF_PATH, "");
    }

    public static PreferencesRepository getInstance() {
        if(instance==null)
            instance=new PreferencesRepository();
        return instance;
    }

    public void updatePreferences(final String newPrefPath, final Language newPrefLanguage, final Theme newPrefTheme){
        PssLogger.getInstance().info("UPDATING PREFERENCES...", getClass());
        prefs.put(USER_PREF_LANGUAGE, newPrefLanguage.toString());
        prefs.put(USER_PREF_PATH, newPrefPath);
        prefs.put(USER_PREF_THEME, newPrefTheme.toString());
    }

    public Theme getPrefTheme() {
        PssLogger.getInstance().info("LOADING PREF THEME...", getClass());
        return Theme.fromStringToTheme(prefs.get(USER_PREF_THEME, ""));
    }
}
