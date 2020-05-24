package ch.supsi.pss.utils;

import ch.supsi.pss.bundles.ResourceBundlePss;

public class ClientUtils {
    public static final int CANVAS_HEIGHT_HD = Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.height"));
    public static final int CANVAS_WIDTH_HD =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.width"));
    public static final int CANVAS_HEIGHT_PORTRAIT =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.height"));;
    public static final int CANVAS_WIDTH_PORTRAIT =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.width"));;
}
