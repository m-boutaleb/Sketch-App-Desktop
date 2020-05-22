package ch.supsi.pss.utility;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Sketch;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class SearchUtilities {
    public static Set<Sketch> searchSketchByTags(Set<Sketch> allSketch, String text){
        Set<Sketch> results = new HashSet<>();

        for(Sketch sketch: allSketch){
            for(String tag: sketch.getAllTags()){
                if(tag.equals(text))  results.add(sketch);
            }
        }
        return results;
    }

}
