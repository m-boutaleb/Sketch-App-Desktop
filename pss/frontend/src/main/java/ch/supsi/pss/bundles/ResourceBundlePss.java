package ch.supsi.pss.bundles;

import ch.supsi.pss.controller.SketchController;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Theme;

import java.util.ResourceBundle;

public class ResourceBundlePss {
    private final String currentPropertyTheme;
    private static ResourceBundlePss instance;
    private final ResourceBundle langBundles;
    private final ResourceBundle pssBundles=ResourceBundle.getBundle("pss");

    public String getCurrentPropertyTheme() {
        return currentPropertyTheme;
    }

    public ResourceBundle getLangBundles() {
        return langBundles;
    }

    public ResourceBundle getPssBundles() {
        return pssBundles;
    }

    private ResourceBundlePss(){
        var skethController=SketchController.getInstance();
        Theme theme=skethController.getPrefTheme();
        switch((theme=((theme==null)?Theme.DEFAULTTHEME:theme))){
            case DEFAULTTHEME:
                currentPropertyTheme=getPssBundles().getString("theme.default");
                break;
            default:
                currentPropertyTheme=getPssBundles().getString("theme.dark");
        }
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
    public static ResourceBundlePss getInstance(){
        return instance==null?(instance=new ResourceBundlePss()):instance;
    }
}
