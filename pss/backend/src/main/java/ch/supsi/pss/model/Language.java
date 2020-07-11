package ch.supsi.pss.model;

public enum Language {
    ITALIANO("Italiano"), ENGLISH("English");
    private final String language;
    private Language(final String language){
        this.language=language;
    }
    public static Language fromStringToLang(final String lowerCaseEnum){
        if(lowerCaseEnum==null||lowerCaseEnum.equals("")) {
            PssLogger.getInstance().error(new IllegalArgumentException("NO PREF LANGUAGE FOUND..."), Language.class);return null;
        }
        return Language.valueOf(lowerCaseEnum.toUpperCase());
    }
    @Override
    public String toString() {
        return language;
    }
}
