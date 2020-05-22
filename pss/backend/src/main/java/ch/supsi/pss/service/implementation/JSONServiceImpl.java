package ch.supsi.pss.service.implementation;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.repository.SketchRepository;
import ch.supsi.pss.service.JSONService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JSONServiceImpl implements JSONService {
    private static JSONServiceImpl instance;
    private JSONServiceImpl(){}
    public static JSONServiceImpl getInstance() {
        if(instance==null)
            instance=new JSONServiceImpl();
        return instance;
    }

    @Override
    public void createSketchByJSON(final JSONObject sketch) {
        final JSONObject currUser = (JSONObject) sketch.get("User");
        final JSONArray currTags = (JSONArray) sketch.get("Tags");
        final Set<String> allTags = new HashSet<>(currTags);
        SketchRepository.getInstance().addSketchToRepo(new Sketch(UUID.fromString(sketch.get("UUID").toString()),
                new Author(Long.valueOf(currUser.get("id").toString()),
                        currUser.get("firstNames").toString(),
                        currUser.get("lastNames").toString()), LocalDateTime.parse(sketch.get("Date").toString()), allTags));
    }

    @Override
    public JSONObject fromSketchToJSON(final Sketch sketchToParse) {
        JSONObject sketchData = new JSONObject();
        sketchData.put("UUID", sketchToParse.getUUID().toString());
        JSONObject userData = new JSONObject();
        userData.put("id", sketchToParse.getAuthor().getId());
        userData.put("firstNames", sketchToParse.getAuthor().getFirstNames());
        userData.put("lastNames", sketchToParse.getAuthor().getLastNames());
        sketchData.put("User", userData);
        sketchData.put("Date", sketchToParse.getTime().toString());
        JSONArray tags = new JSONArray();
        sketchToParse.getAllTags().forEach(tags::add);
        sketchData.put("Tags", tags);
        return sketchData;
    }

    @Override
    public JSONObject fromPreferencesToJSON(final String newUserPrefPathDir , final String newUserPrefLanguage) {
        JSONObject userPreferencesData = new JSONObject();
        userPreferencesData.put("SavePath", newUserPrefPathDir);
        userPreferencesData.put("Language", newUserPrefLanguage);
        return userPreferencesData;
    }
}
