package ch.supsi.pss.utils;

import ch.supsi.pss.model.Sketch;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class SearchUtils {
    public static Set<Sketch> searchSketchByTag(Set<Sketch> allSketch, String text){
        Set<Sketch> results = new HashSet<>();
        final Pattern pattern=Pattern.compile(text);

        for(Sketch sketch: allSketch)
            for(String tag: sketch.getAllTags())
                if(pattern.matcher(tag).find())  results.add(sketch);

        return results;
    }

}
