package ch.supsi.pss.search;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.controller.SketchController;
import ch.supsi.pss.controller.SketchViewer;
import ch.supsi.pss.model.Sketch;
import ch.supsi.pss.utils.DialogUtils;
import ch.supsi.pss.utils.SearchUtils;

import java.util.ResourceBundle;
import java.util.Set;

public class SketchDisplay {

    private SketchDisplay(){}

    private static SketchDisplay instance;

    public static SketchDisplay getInstance() {
        if (instance == null)
            instance = new SketchDisplay();
        return instance;
    }

    public void displayResults(final String text) {
        final Set<Sketch> allSketch = SketchController.getInstance().getAllSketches();
        final Set<Sketch> results = SearchUtils.searchSketchByTag(allSketch, text);

        if (!text.equals("") && !results.isEmpty()) {
            final Sketch array[]=new Sketch[results.size()];
            new SketchViewer().getAndShowTableView(results.toArray(array));
        }else{
            final ResourceBundle res=ResourceBundlePss.getInstance().getLangBundles();
            DialogUtils.displayAlert(res.getString("search.title"), res.getString("search.header")+"'"+text+"'" , res.getString("search.context"));
        }
    }
}
