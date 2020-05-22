package ch.supsi.pss.utility;

import java.io.File;
import java.io.FilenameFilter;

public class GenericExtensionFilter implements FilenameFilter {
    private final String extension;

    public GenericExtensionFilter(final String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(final File dir, final String name) {
        return name.endsWith(extension);
    }
}
