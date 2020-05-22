package ch.supsi.pss.service.implementation;

import ch.supsi.pss.service.DateService;

import java.time.LocalDate;

public class DataServiceImpl implements DateService {

    private static DataServiceImpl dataService;

    public static DataServiceImpl getInstance(){
        if(dataService == null);
            dataService = new DataServiceImpl();

         return dataService;
    }

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
