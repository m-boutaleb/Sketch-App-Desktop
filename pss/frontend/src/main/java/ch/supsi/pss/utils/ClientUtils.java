package ch.supsi.pss.utils;

import ch.supsi.pss.bundles.ResourceBundlePss;

public class ClientUtils {
    public static final int HD_HEIGHT = Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.height"));
    public static final int HD_WIDTH =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.width"));
    public static final int PORTRAIT_HEIGHT =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.height"));;
    public static final int PORTRAIT_WIDTH =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.width"));;
    public static final int CANVAS_WIDTH_PORTRAIT=PORTRAIT_WIDTH;
    public static final int CANVAS_HEIGHT_PORTRAIT=PORTRAIT_HEIGHT;
    public static final int CANVAS_WIDTH_HD=HD_WIDTH;
    public static final int CANVAS_HEIGHT_HD=HD_HEIGHT;
}
