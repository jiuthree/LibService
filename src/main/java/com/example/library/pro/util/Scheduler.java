package com.example.library.pro.util;

import com.example.library.pro.dao.ReserveAndBorrowListDao;
import com.example.library.pro.module.ReserveAndBorrowList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    @Autowired
    ReserveAndBorrowListDao reserveAndBorrowListDao;

    @Scheduled(cron = "0 0 18 * * ?")
    public void clearInvalidReserve(){
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAll();
        for(ReserveAndBorrowList reserveAndBorrowList: records){
            if(reserveAndBorrowList.getBDateTime() == null){
                reserveAndBorrowListDao.deleteById(reserveAndBorrowList.getId());
            }
        }

    }


}
