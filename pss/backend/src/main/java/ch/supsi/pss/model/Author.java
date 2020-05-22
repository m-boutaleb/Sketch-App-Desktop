package ch.supsi.pss.model;

public class Author {
    //usato solo per identificare due persone con nome e cognome uguali
    private final Long id;
    private final String firstNames;
    private final String lastNames;

    public Author(final String firstNames, final String lastNames){
        if(firstNames==null||lastNames==null)
            PssLogger.getInstance().error(new IllegalArgumentException("USER'S DATA CANNOT BE NULL"), this.getClass());
        //Ritorna una sequenza di numeri che rappresenta il tempo in millisecondi -> quindi univoco
        id=System.currentTimeMillis();
        this.firstNames = firstNames;
        this.lastNames = lastNames;
    }

    /**
     * Costruttore usato per inizializzare un nuovo utente letto da file
     * @param id dell'utente
     * @param firstNames i nomi dell'utente
     * @param lastNames i cognomi dell'utente
     */
    public Author(final Long id, final String firstNames, final String lastNames){
        if(firstNames==null||lastNames==null||id==null)
            PssLogger.getInstance().error(new IllegalArgumentException("USER'S DATA CANNOT BE NULL"), this.getClass());
        this.id=id;
        this.firstNames = firstNames;
        this.lastNames = lastNames;
    }

    public Long getId() {
        return id;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public String getLastNames() {
        return lastNames;
    }

    @Override
    public String toString() {
        return  getFirstNames() + " " + getLastNames();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj != null ){
            Author u = (Author) obj;

            return firstNames.equals(u.firstNames)
                    && lastNames.equals(u.lastNames);
        }
        else
            return false;
    }
}
