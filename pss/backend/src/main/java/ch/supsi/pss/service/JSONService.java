package ch.supsi.pss.service;

import ch.supsi.pss.model.Sketch;
import org.json.simple.JSONObject;

public interface JSONService {
    /**
     * Metodo che si occupa di trasformare uno Sketch a JSON
     * @param sketchToParse sketch da trasformare
     * @return nuovo oggetto JSON creato
     */
    JSONObject fromSketchToJSON(final Sketch sketchToParse);

    /**
     * Metodo che si occupa di trasformare una stringa rappresentante
     * il percorso dove salvare gli sketch in oggetto JSON
     * @param newUserPrefPathDir percorso dove salvare sketch
     * @return JSONObject ottenuto dalla trasformazione
     */
    JSONObject fromPreferencesToJSON(final String newUserPrefPathDir, final String newUserPrefLanguage);

    /**
     * Metodo che crea sketch partendo da uno sketch
     * @param sketch sketch da trasformare in JSONObject
     */
    void createSketchByJSON(final JSONObject sketch);
}
