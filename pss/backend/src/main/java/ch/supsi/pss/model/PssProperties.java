package ch.supsi.pss.model;

import java.io.IOException;
import java.util.Properties;

import static ch.supsi.pss.utils.FileUtils.PROPERTY_FILE_NAME;

public class PssProperties {
    private static PssProperties instance;
    private final Properties properties;

    public String getProperty(final String value){
        return properties.getProperty(value);
    }

    private PssProperties(){
        properties=new Properties();
        try {
            properties.load(getClass().getResourceAsStream(PROPERTY_FILE_NAME));
        } catch (IOException e) {
            PssLogger.getInstance().info("ERROR WHILE LOADING PROPERTIES FILE BACKEND", this.getClass());
        }
    }

    public static PssProperties getInstance() {
        return instance==null?(instance=new PssProperties()):instance;
    }
}
