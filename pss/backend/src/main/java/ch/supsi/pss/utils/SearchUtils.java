package ch.supsi.pss.utils;

import ch.supsi.pss.model.Sketch;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchUtils {
    public static Set<Sketch> searchSketchByTag(Set<Sketch> allSketch, String text){
        final Pattern pattern=Pattern.compile(text);

        final Predicate<Set<String>> matched=tags->tags.parallelStream().map(pattern::matcher).anyMatch(Matcher::find);
        return allSketch.parallelStream().filter(s->matched.test(s.getAllTags())).collect(Collectors.toSet());
    }

}
