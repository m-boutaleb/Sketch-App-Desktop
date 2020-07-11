package ch.supsi.pss.bundles;

import ch.supsi.pss.controller.SketchController;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Theme;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundlePss {
    private static ResourceBundlePss instance;
    private final ResourceBundle langBundles;
    private final ResourceBundle pssBundles;

    public ResourceBundle getLangBundles() {
        return langBundles;
    }

    public ResourceBundle getPssBundles() {
        return pssBundles;
    }

    private ResourceBundlePss(){
        pssBundles=ResourceBundle.getBundle("pss");
        final SketchController sketchController=SketchController.getInstance();
        final Language language=SketchController.getInstance().getPrefLang();
        if(language==null){
            langBundles =ResourceBundle.getBundle("pss_ita");
            Locale.setDefault(Locale.ITALIAN);
            return;
        }
        switch (language){
            case ENGLISH:
                langBundles = ResourceBundle.getBundle("pss_en");
                Locale.setDefault(Locale.ENGLISH);
                break;
            default:
                langBundles = ResourceBundle.getBundle("pss_ita");
        }
    }
    public static ResourceBundlePss getInstance(){
        return instance==null?(instance=new ResourceBundlePss()):instance;
    }
}
