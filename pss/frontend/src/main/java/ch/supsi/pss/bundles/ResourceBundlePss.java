package ch.supsi.pss.bundles;

import ch.supsi.pss.controller.SketchController;
import ch.supsi.pss.model.Language;

import java.util.ResourceBundle;

public class ResourceBundlePss {
    private static ResourceBundlePss instance;
    private final ResourceBundle langBundles;
    private final ResourceBundle pssBundles=ResourceBundle.getBundle("pss");

    public ResourceBundle getLangBundles() {
        return langBundles;
    }

    public ResourceBundle getPssBundles() {
        return pssBundles;
    }

    private ResourceBundlePss(){
        SketchController.getInstance().loadPreferences();
        Language language=SketchController.getInstance().getPrefLang();
        if(language==null){
            langBundles =ResourceBundle.getBundle("pss_ita");
            return;
        }
        switch (language){
            case ENGLISH:
                langBundles = ResourceBundle.getBundle("pss_en");
                break;
            case ITALIAN:
                langBundles = ResourceBundle.getBundle("pss_ita");
                break;
            default:
                langBundles =null;
        }
    }

    public String getString(final String key){
        return langBundles.getString(key);
    }

    public static ResourceBundlePss getInstance(){
        return instance==null?(instance=new ResourceBundlePss()):instance;
    }
}
