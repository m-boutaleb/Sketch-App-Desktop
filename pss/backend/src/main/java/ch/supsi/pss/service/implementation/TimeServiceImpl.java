package ch.supsi.pss.service.implementation;

import ch.supsi.pss.service.TimeService;

import java.time.LocalDateTime;

public class TimeServiceImpl implements TimeService {
    private static TimeServiceImpl instance;
    public static TimeService getInstance() {
        if(instance==null)
            instance= new TimeServiceImpl();
        return instance;
    }
    private TimeServiceImpl(){}
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
