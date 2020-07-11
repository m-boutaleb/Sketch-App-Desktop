package ch.supsi.pss.theme;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.controller.SketchController;
import ch.supsi.pss.model.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

import java.util.function.Supplier;

public class ThemeManager {
    private final ObjectProperty<Color> base;

    public static ThemeManager getInstance() {
        return instance==null?(instance=new ThemeManager()):instance;
    }

    private static ThemeManager instance;

    public <T extends Parent> T createThemedNode(final Supplier<T> factory) {
        final T node = factory.get();
        node.styleProperty().bind(cssProperty());
        node.getStylesheets().add(ResourceBundlePss.getInstance().getPssBundles().getString("css.config"));
        return node;
    }

    private ObjectProperty<Color> baseProperty() {
        return base ;
    }

    private final Color getBase() {
        return baseProperty().get();
    }

    public final void setBase(final Theme theme){
        base.set(Color.web(theme.getBaseColor()));
    }

    private final ReadOnlyStringWrapper css = new ReadOnlyStringWrapper() ;

    private ThemeManager() {
        final var theme=SketchController.getInstance().getPrefTheme();
        base=new SimpleObjectProperty<>(Color.web(theme
                ==null?(Theme.DEFAULT_THEME).getBaseColor():theme.getBaseColor()
        ));
        css.bind(Bindings.createStringBinding(() -> String.format(
                "-fx-base: %s;",
                toRgba(getBase())),
                base));
    }

    private String toRgba(Color color) {
        int r = (int) (255 * color.getRed());
        int g = (int) (255 * color.getGreen());
        int b = (int) (255 * color.getBlue());
        int a = (int) (255 * color.getOpacity());
        return String.format("#%02x%02x%02x%02x", r, g, b, a);
    }

    public ReadOnlyStringProperty cssProperty() {
        return css.getReadOnlyProperty();
    }

}