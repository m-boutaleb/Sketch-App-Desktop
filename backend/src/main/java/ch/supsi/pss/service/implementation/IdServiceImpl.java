package ch.supsi.pss.service.implementation;

import ch.supsi.pss.service.IdService;

import java.util.UUID;

public class IdServiceImpl implements IdService {
    private static IdServiceImpl instance;

    public static IdServiceImpl getInstance() {
        if(instance==null)
            instance=new IdServiceImpl();
        return instance;
    }

    @Override
    public UUID newID() {
        return UUID.randomUUID();
    }
}
