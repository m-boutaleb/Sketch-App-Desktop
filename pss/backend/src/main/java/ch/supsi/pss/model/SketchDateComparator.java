package ch.supsi.pss.model;

import java.util.Comparator;

public class SketchDateComparator implements Comparator<Sketch> {
    @Override
    public int compare(final Sketch o1, final Sketch o2) {
        return o2.getTime().compareTo(o1.getTime());
    }
}
