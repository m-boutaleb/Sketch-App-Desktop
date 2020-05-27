package ch.supsi.pss.model;

public enum Theme {
    DARKTHEME("Dark Theme"), DEFAULTTHEME("Default Theme");
    private final String theme;
    private Theme(final String theme){
        this.theme=theme;
    }
    public static Theme fromStringToTheme(final String lowerCaseEnum){
        if(lowerCaseEnum==null||lowerCaseEnum.equals("")) {
            PssLogger.getInstance().error(new IllegalArgumentException("NO PREF LANGUAGE FOUND..."), Language.class);return null;
        }
        return Theme.valueOf(lowerCaseEnum.replaceAll(" ", "").toUpperCase());
    }
    @Override
    public String toString() {
        return theme;
    }
}
