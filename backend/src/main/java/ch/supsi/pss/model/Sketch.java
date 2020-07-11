package ch.supsi.pss.model;

import ch.supsi.pss.repository.SketchRepository;

import java.time.LocalDateTime;
import java.util.*;

public class Sketch {
    private Author author;
    private LocalDateTime time;
    private Set<String> allTags;
    private UUID UUID;
    private byte[] image;

    public Sketch(final byte[] sketch, final UUID uuid, final Author author, final LocalDateTime timeCreation, final Set<String> allTags) {
        if(sketch==null||uuid==null||author==null||timeCreation==null||allTags==null)
            PssLogger.getInstance().error(new IllegalArgumentException("SKETCH/IMAGE/TIME/AUTHOR CANNOT BE NULL"), getClass());
        image=sketch;
        this.UUID=uuid;
        this.allTags=allTags;
        this.author=author;
        this.time=timeCreation;
        createDefaultTags();
    }

    private void createDefaultTags(){
        createTag();
    }

    private void createTag(){
        allTags.add(author.toString());
        allTags.add(time.toString());
    }

    /**
     * Attenzione costruttore da usare solo per avere un istanza della classe Sketch
     */
    public Sketch(){
    }

    /**
     * Costruttore usato solo per fare update di uno sketch
     * @param oldSketchUUID vecchio UUID dello sketch da aggiornare
     * @param newSketch immagine rappresentante il nuovo sketch
     * @param allTags tutti i tags associati al nuovo tag
     * @param author autore del nuovo sketch
     * @param time tempo dell'ultima modifica fatta
     */
    public Sketch(final UUID oldSketchUUID, final byte[] newSketch, final Set<String> allTags, final Author author, final LocalDateTime time ){
        if(author ==null||oldSketchUUID==null||allTags==null)
            PssLogger.getInstance().error(new IllegalArgumentException("AUTHOR/UUID/TAGS/IMAGE CANNOT BE NULL"), this.getClass());
        UUID = oldSketchUUID;
        this.time =time;
        this.author = author;
        this.image=newSketch;
        this.allTags=allTags;
        createDefaultTags();
    }

    /**
     * Questo costruttore usato solo per creare un nuovo sketch caricandolo da file
     * @param UUID uuid dello sketch
     * @param author utente che ha fatto l'ultima modifica allo sketch
     * @param time data e ora in cui è stato creato lo sketch
     * @param allTags tutti i tags che sono associati a quello sketch
     */
    public Sketch(final UUID UUID, final Author author, final LocalDateTime time, final Set<String> allTags){
        this.UUID = UUID;
        this.author = author;
        this.allTags=allTags;
        this.time=time;
        allTags.add(author.toString());
        allTags.add(time.toString());
    }

    public LocalDateTime getTime() {
        return time;
    }

    public UUID getUUID() {
        return UUID;
    }

    public void addTag(final String tag){
        allTags.add(tag);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    //ritorno sempre una copia dell'hashset in modo che all'esterno non me la modificano
    public Set<String> getAllTags() {
        return new HashSet<>(allTags);
    }


    public void setUUID(UUID uuid) {
        this.UUID = uuid;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Sketch sketch = (Sketch) o;
        return UUID.equals(sketch.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }

    @Override
    public String  toString(){

        return "\nUIID: " + UUID +
                 "\nAuthor: " + author +
                 "\nTime creation: " + time
                  + "\nTags: " + SketchSerializer.formatAllTags(this);
    }

    public void copyOf(final Sketch sketch) {
        this.allTags=sketch.allTags;
        this.time=sketch.time;
        this.author=sketch.author;
        this.image=sketch.image;
        this.UUID=sketch.UUID;
    }
}
