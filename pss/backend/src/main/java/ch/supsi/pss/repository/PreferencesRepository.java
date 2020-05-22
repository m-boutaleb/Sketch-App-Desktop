package ch.supsi.pss.repository;

import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.PssLogger;
import ch.supsi.pss.model.PssProperties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

import static ch.supsi.pss.utility.FileManagerUtilities.DEFAULT_OS_PATH;

public class PreferencesRepository {
    private static PreferencesRepository instance;
    private String prefPathDir;
    private Language prefLanguage;

    private PreferencesRepository(){
    }

    public Language getPrefLanguage() {
        return prefLanguage;
    }

    public String getPrefPathDir() {
        return prefPathDir;
    }

    public void setPrefs(final String[] prefs) {
        if(prefs==null||prefs.length==0||prefs[0]==null||prefs[1]==null) {
            PssLogger.getInstance().info("NO PREFS DIR FOUND", this.getClass());
            return;
        }
        this.prefPathDir = prefs[0];
        this.prefLanguage=Language.fromStringToEnum(prefs[1]);
    }

    public static PreferencesRepository getInstance() {
        if(instance==null)
            instance=new PreferencesRepository();
        return instance;
    }

    public boolean loadPreferences(){
        PssLogger.getInstance().info("LOADING PREFERENCES...", getClass());
        try(Reader reader = new FileReader(DEFAULT_OS_PATH+ PssProperties.getInstance().getProperty("preferences.file"))) {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            setPrefs(new String[]{(String) jsonObject.get("SavePath"),(String) jsonObject.get("Language") });
        } catch (ParseException e) {
            PssLogger.getInstance().error("ERROR WHILE PARSING JSON OBJECT", this.getClass());
        } catch (IOException ex) {
            PssLogger.getInstance().error("ERROR WHILE READING PROPERTY FILE", getClass());
        }
        return true;
    }

    public boolean updatePreferences(final String newPrefPath,final Language newPrefLanguage, JSONObject prefsJson){
        PssLogger.getInstance().info("UPDATING PREFERENCES...", getClass());
        final File dir=new File(DEFAULT_OS_PATH+PssProperties.getInstance().getProperty("preferences.directory"));
        if(!dir.exists())
            PssLogger.getInstance().info(dir.mkdir()?"NEW PREF DIR CREATED":"ERROR CREATING PREF DIR", this.getClass());
        try (Writer writer = new FileWriter(DEFAULT_OS_PATH+ PssProperties.getInstance().getProperty("preferences.file"))) {
            writer.write(prefsJson.toString());
        } catch (FileNotFoundException e) {
            PssLogger.getInstance().error("ERROR WHILE LOADING PROPERTY FILE",this.getClass());
        } catch (IOException e) {
            PssLogger.getInstance().error("ERROR WHILE READING PROPERTY FILE",this.getClass());
        }
        setPrefs(new String[]{newPrefPath, newPrefLanguage.toString()});
        return true;
    }
}
