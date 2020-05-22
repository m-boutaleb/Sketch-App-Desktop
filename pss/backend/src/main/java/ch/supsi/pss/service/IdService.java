package ch.supsi.pss.service;

import java.util.UUID;

public interface IdService {
    /**
     * Si occupa di generare un nuovo UUID
     * @return UUID generato
     */
    UUID newID();
}
