package ch.supsi.pss.service.implementation;

import ch.supsi.pss.service.DateService;

import java.time.LocalDate;

public class DateServiceImpl implements DateService {

    private static DateServiceImpl dataService;

    public static DateServiceImpl getInstance(){
        if(dataService == null);
            dataService = new DateServiceImpl();

         return dataService;
    }

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
