package ch.supsi.pss.utility;

import ch.supsi.pss.bundles.ResourceBundlePss;

public class ClientUtilities {
    public static final int HD_HEIGHT = Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.height"));
    public static final int HD_WIDTH =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.landscape.width"));
    public static final int PORTRAIT_HEIGHT =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.height"));;
    public static final int PORTRAIT_WIDTH =Integer.parseInt(ResourceBundlePss.getInstance().getPssBundles().getString("sketch.portrait.width"));;
    public static final int CANVAS_WIDTH_PORTRAIT=400;
    public static final int CANVAS_HEIGHT_PORTRAIT=600;
    public static final int CANVAS_WIDTH_HD=600;
    public static final int CANVAS_HEIGHT_HD=400;
    public static final int TAGS_LIST_HEIGHT=200;
    public static final int TAGS_LIST_WIDTH=300;
}
