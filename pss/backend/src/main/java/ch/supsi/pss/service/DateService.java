package ch.supsi.pss.service;

import java.time.LocalDate;

public interface DateService {

    /**
     * Genera la data di creazione dello sketch
     */
    LocalDate getCurrentDate();
}
