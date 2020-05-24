package ch.supsi.pss.service.implementation;

import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.service.JSONService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONServiceImpl implements JSONService {
    private static JSONServiceImpl instance;
    private JSONServiceImpl(){}
    public static JSONServiceImpl getInstance() {
        if(instance==null)
            instance=new JSONServiceImpl();
        return instance;
    }

    @Override
    public JSONObject fromSketchToJSON(final Sketch sketchToParse) {
        JSONObject sketchData = new JSONObject();
        sketchData.put("UUID", sketchToParse.getUUID().toString());
        JSONObject userData = new JSONObject();
        userData.put("username", sketchToParse.getAuthor().getUsername());
        sketchData.put("User", userData);
        sketchData.put("Date", sketchToParse.getTime().toString());
        JSONArray tags = new JSONArray();
        sketchToParse.getAllTags().forEach(tags::add);
        sketchData.put("Tags", tags);
        return sketchData;
    }
}
