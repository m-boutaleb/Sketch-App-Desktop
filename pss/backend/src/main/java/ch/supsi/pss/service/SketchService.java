package ch.supsi.pss.service;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Language;
import ch.supsi.pss.model.Sketch;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface SketchService {
    /**
     * Metodo che salva sketch passandolo allo sketch repository
     * @param uuid generato dallo UUIDService
     * @param sketch stream di byte che rappresenta l'immagine dello sketch
     * @param allTags tutti i tags associati allo sketch
     * @param author creatore dello sketch
     * @param timeCreation tempo di creazione dello sketch
     * @return ritorna vero o falso a seconda della scrittura avvenuta con successo
     */
    boolean saveSketch(final UUID uuid, final byte[] sketch, final Set<String> allTags, final Author author, final LocalDateTime timeCreation);

    /**
     * Aggiorna lo sketch aperto ma già salvato sovrascrivendo quello precedente
     * @param newSketch immagine rappresentate lo nouvo sketch generato
     * @param allTags tutti i tags dello nuovo sketch
     * @param author autore del nuovo sketch
     * @return ritorna true o false a seconda della scrittura avvenuta con successo
     */
    boolean updateSketch(final byte[] newSketch, final Set<String> allTags, final Author author, final LocalDateTime time);

    /**
     * Metodo che si occupa di aggiornare le preferenze dell'utente
     * @param newPrefPathDir nuovo Percorso di salavtaggio preferito
     * @param newPrefLanguage nnuova lingua utente preferita
     * @return true o false a seconda della riuscita o meno dell'operazione dei aggiornamento
     */
    boolean updatePreferences(final String newPrefPathDir, final Language newPrefLanguage);

    /**
     * Carica da file tutti le preferenze scritte su file delegando il compito al repository delle
     * preferenze
     * @return ritorna true o false a seconda della riuscita del caricamento
     */
    boolean loadPreferences();

    /**
     * Carica tutti gli sketch specifiacti in una directory nelle preferenze
     * @return true o false a seconda se il caricamento è avvenuto con successo
     */
    boolean loadAllSketchData();

    /**
     * @return stringa del percorso delle preferenze
     */
    String getPrefPath();

    /**
     * @return stringa rappresentante la lingua preferita dall'utente
     */
    Language getPrefLang();

    /**
     * Apre Sketch esistente che si trova in una determinata locazione del file system
     * @param path percorso dello sketch
     * @param sketchName nome del file
     * @return lo sketch con tutti i dati completi
     */
    Sketch openSketch(String path, String sketchName);
}
