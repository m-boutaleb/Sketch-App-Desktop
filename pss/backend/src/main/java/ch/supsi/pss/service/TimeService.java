package ch.supsi.pss.service;

import java.time.LocalDateTime;

public interface TimeService {
    /**
     * Genera il la data corrente
     * @return data corrente generata
     */
    LocalDateTime getCurrentTime();
}
