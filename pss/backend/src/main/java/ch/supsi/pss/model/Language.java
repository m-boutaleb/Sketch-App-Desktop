package ch.supsi.pss.model;

public enum Language {
    ITALIAN("Italian"), ENGLISH("English");
    private final String language;
    private Language(final String language){
        this.language=language;
    }
    public static Language fromStringToEnum(final String lowerCaseEnum){
        return Language.valueOf(lowerCaseEnum.toUpperCase());
    }
    @Override
    public String toString() {
        return language;
    }
}
