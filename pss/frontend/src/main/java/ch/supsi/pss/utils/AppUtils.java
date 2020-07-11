package ch.supsi.pss.utils;

import ch.supsi.pss.bundles.ResourceBundlePss;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AppUtils {
    public static final long SPLASH_SCREEN_SLEEP=1_200;
    public static final int DEFAULT_MAIN_PAGE_WIDTH=800;
    public static final int DEFAULT_MAIN_PAGE_HEIGHT=600;

    public static FXMLLoader getFXMLLoader(final String fxmlFile, final Object... controllers){
        final FXMLLoader loader=new FXMLLoader(AppUtils.class.
                getResource(fxmlFile), ResourceBundlePss.getInstance().getLangBundles());
        if(controllers!=null&&controllers.length!=0)
            loader.setController(controllers[0]);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
