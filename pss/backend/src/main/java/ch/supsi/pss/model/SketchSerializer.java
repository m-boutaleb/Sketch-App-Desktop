package ch.supsi.pss.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SketchSerializer {
    public Sketch createSketchByJSON(final JSONObject sketchJSON){
        final JSONObject currUser = (JSONObject) sketchJSON.get("User");
        final JSONArray currTags = (JSONArray) sketchJSON.get("Tags");
        final Set<String> allTags = new HashSet<>(currTags);
        return new Sketch(UUID.fromString(sketchJSON.get("UUID").toString()),
                new Author(currUser.get("username").toString()),
                LocalDateTime.parse(sketchJSON.get("Date").toString()), allTags);
    };
}
