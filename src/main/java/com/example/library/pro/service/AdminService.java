package com.example.library.pro.service;

import com.example.library.pro.dao.LibDocumentsDao;
import com.example.library.pro.dao.ReaderDao;
import com.example.library.pro.dao.ReserveAndBorrowListDao;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.Reader;
import com.example.library.pro.module.ReserveAndBorrowList;
import com.example.library.pro.vo.LibDocumentDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    ReaderDao readerDao;

    @Autowired
    LibDocumentsDao libDocumentsDao;

    @Autowired
    ReserveAndBorrowListDao reserveAndBorrowListDao;

    public List<Reader> getAllReaders() {
        return readerDao.findAll();
    }

    public List<LibDocumentDetailVo> searchDocumentCopyStatus(Long documentId, Long number) {
        ArrayList<LibDocumentDetailVo> res = new ArrayList<>();
        List<LibDocuments> libDocuments = libDocumentsDao.findAllByDocumentIdAndNumber(documentId, number);
        for(LibDocuments libDocument:libDocuments){
            LibDocumentDetailVo libDocumentDetailVo = new LibDocumentDetailVo();
            libDocumentDetailVo.setLibDocument(libDocument);
            List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByDocumentIdAndLibIdAndAndNumber(documentId, libDocument.getLibId(), number).stream().filter(reserveAndBorrowList -> {
                boolean isUncomplete = reserveAndBorrowList.getIsCompleted() == null || reserveAndBorrowList.getIsCompleted() == false;
                return isUncomplete;
            }).collect(Collectors.toList());
            if(records!=null&&records.size()!=0){
                libDocumentDetailVo.setReserveAndBorrowList(records.get(0));
            }

            res.add(libDocumentDetailVo);
        }

        return res;

    }
}
