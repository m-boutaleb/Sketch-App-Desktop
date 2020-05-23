package ch.supsi.pss.service;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Sketch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface JSONService {
    JSONObject fromSketchToJSON(final Sketch sketchToParse);

    JSONObject fromPreferencesToJSON(final String newUserPrefPathDir, final String newUserPrefLanguage);

    static Sketch createSketchByJSON(final JSONObject sketchJSON){
        final JSONObject currUser = (JSONObject) sketchJSON.get("User");
        final JSONArray currTags = (JSONArray) sketchJSON.get("Tags");
        final Set<String> allTags = new HashSet<>(currTags);
        return new Sketch(UUID.fromString(sketchJSON.get("UUID").toString()),
                new Author(Long.valueOf(currUser.get("id").toString()),
                        currUser.get("firstNames").toString(),
                        currUser.get("lastNames").toString()), LocalDateTime.parse(sketchJSON.get("Date").toString()), allTags);
    };
}
