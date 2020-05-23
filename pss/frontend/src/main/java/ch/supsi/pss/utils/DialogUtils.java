package ch.supsi.pss.utils;

import ch.supsi.pss.PssFx;
import ch.supsi.pss.controller.NewSketch;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class DialogUtils {
    /**
     * Mostra un Alert con il risultato dell'exception o errore a cui si è andati in
     * contro
     * @param titleMessage messaggio che riassume l'azione che ha generato il warning
     * @param headerMessage titolo rappresentativo dell'Errore
     * @param contextMessage messaggio contenente un consiglio per cosa fare per ovviare il problema
     */
    public static void displayAlert(final String titleMessage, final String headerMessage, final String contextMessage){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(titleMessage);
            alert.setHeaderText(headerMessage);
            PssFx.setDefaultIcon((Stage) alert.getDialogPane().getScene().getWindow());
            alert.setContentText(contextMessage);
            alert.showAndWait();
    }

    /**
     * Metodo che mostra alert la cui chiusura è possibile solo scegliendo un'opzione tra yes o no
     * @param newSkethController istanza del controller che si sta occupando dello sketch dove si chiede il salvataggio
     * @param stage Owner dell'alert
     * @param titleMessage messaggio che riassume l'azione che ha generato il warning
     * @param headerMessage titolo rappresentativo dell'Errore
     * @param contextMessage messaggio contenente un consiglio per cosa fare per ovviare il problema
     */
    public static void displayYesNoAlert(final NewSketch newSkethController, final Stage stage, final String titleMessage, final String headerMessage, final String contextMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(titleMessage);
        alert.setHeaderText(headerMessage);
        alert.initOwner(stage.getOwner());
        PssFx.setDefaultIcon((Stage) alert.getDialogPane().getScene().getWindow());
        alert.setContentText(contextMessage);
        Optional<ButtonType> res = alert.showAndWait();
        if(res.isPresent()) {
            if(res.get().equals(ButtonType.YES))
                newSkethController.save(null);
        }
    }
}
