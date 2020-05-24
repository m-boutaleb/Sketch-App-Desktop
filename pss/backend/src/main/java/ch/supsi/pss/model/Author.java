package ch.supsi.pss.model;

public class Author {
    private final String username;

    public Author(final String username){
        if(username ==null)
            PssLogger.getInstance().error(new IllegalArgumentException("USER'S DATA CANNOT BE NULL"), this.getClass());
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return  getUsername();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj != null ){
            Author u = (Author) obj;

            return username.equals(u.username);
        }
        else
            return false;
    }
}
