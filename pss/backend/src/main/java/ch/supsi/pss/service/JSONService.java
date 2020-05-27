package ch.supsi.pss.service;

import ch.supsi.pss.model.Sketch;
import org.json.simple.JSONObject;

public interface JSONService {
    JSONObject fromSketchToJSON(final Sketch sketchToParse);
}
