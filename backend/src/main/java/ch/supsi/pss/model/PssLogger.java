package ch.supsi.pss.model;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class PssLogger {
    private static PssLogger instance;

    public static PssLogger getInstance() {
        if(instance==null)
            instance=new PssLogger();
        return instance;
    }
    private PssLogger(){
    }
    public void initialize(){
        BasicConfigurator.configure();
    }
    public void debug(final String debugString, final Class classCaller){
        Logger.getLogger(classCaller).debug(debugString);
    }
    public void error(final String errorString, final Class classCaller){
        Logger.getLogger(classCaller).error(errorString);
    }
    public void warn(final String warnString, final Class classCaller) {
        Logger.getLogger(classCaller).warn(warnString);
    }
    public void info(final String infoString, final Class classCaller){
        Logger.getLogger(classCaller).info(infoString);
    }
    public void error(final String errorString, final Exception e, final Class classCaller){
        Logger.getLogger(classCaller).error(errorString, e);
    }
    public void error(final Exception exception, final Class classCaller){
        Logger.getLogger(classCaller).error(exception);
    }
}
