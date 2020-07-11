package ch.supsi.pss.model;

public enum Theme {
    DARK_THEME("Dark Theme","#3C3F41"), DEFAULT_THEME("Default Theme","#ECECEC");
    private final String theme;
    private final String baseColor;
    private Theme(final String theme, final String baseColor){
        this.theme=theme;
        this.baseColor=baseColor;
    }
    public static Theme fromStringToTheme(final String lowerCaseEnum){
        if(lowerCaseEnum==null||lowerCaseEnum.equals("")) {
            PssLogger.getInstance().error(new IllegalArgumentException("NO PREF LANGUAGE FOUND..."), Language.class);return null;
        }
        return Theme.valueOf(lowerCaseEnum.replaceAll(" ", "_").toUpperCase());
    }
    public String getBaseColor() {
        return baseColor;
    }
    @Override
    public String toString() {
        return theme;
    }
}
