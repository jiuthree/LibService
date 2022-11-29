package com.example.library.pro.service;

import com.example.library.pro.dao.*;
import com.example.library.pro.module.*;
import com.example.library.pro.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    ReaderDao readerDao;

    @Autowired
    LibDocumentsDao libDocumentsDao;

    @Autowired
    LibraryDao libraryDao;

    @Autowired
    DocumentDao documentDao;

    @Autowired
    ReserveAndBorrowListDao reserveAndBorrowListDao;

    public List<Reader> getAllReaders() {
        return readerDao.findAll();
    }

    public List<LibDocumentDetailVo> searchDocumentCopyStatus(Long documentId, Long number) {
        ArrayList<LibDocumentDetailVo> res = new ArrayList<>();
        List<LibDocuments> libDocuments = libDocumentsDao.findAllByDocumentIdAndNumber(documentId, number);
        for (LibDocuments libDocument : libDocuments) {
            LibDocumentDetailVo libDocumentDetailVo = new LibDocumentDetailVo();
            libDocumentDetailVo.setLibDocument(libDocument);
            List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByDocumentIdAndLibIdAndAndNumber(documentId, libDocument.getLibId(), number).stream().filter(reserveAndBorrowList -> {
                boolean isUncomplete = reserveAndBorrowList.getIsCompleted() == null || !reserveAndBorrowList.getIsCompleted();
                return isUncomplete;
            }).collect(Collectors.toList());
            if (records != null && records.size() != 0) {
                libDocumentDetailVo.setReserveAndBorrowList(records.get(0));
            }

            res.add(libDocumentDetailVo);
        }

        return res;

    }

    public List<LibraryTop10Readers> findTop10Readers() {
        ArrayList<LibraryTop10Readers> res = new ArrayList<>();
        List<Library> libraries = libraryDao.findAll();
        for (Library library : libraries) {
            LibraryTop10Readers libraryTop10Readers = new LibraryTop10Readers();
            libraryTop10Readers.setLibrary(library);
            libraryTop10Readers.setTopReaderList(new ArrayList<>());

            List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByLibIdAndIsCompleted(library.getId(), true);

            Map<Long, Long> result =
                    records.stream().collect(
                            Collectors.groupingBy(
                                    ReserveAndBorrowList::getReaderId, Collectors.counting()
                            )
                    );

            Map<Long, Long> finalMap = new LinkedHashMap<>();
            result.entrySet().stream()
                    .sorted(Map.Entry.<Long, Long>comparingByValue()
                            .reversed()).forEachOrdered(e -> {
                        Optional<Reader> reader = readerDao.findById(e.getKey());
                        if (reader.isPresent()) {
                            TopReader topReader = new TopReader();
                            topReader.setReader(reader.get());
                            topReader.setBorrowedDocumentCounts(e.getValue());
                            libraryTop10Readers.getTopReaderList().add(topReader);
                        }
                        finalMap.put(e.getKey(), e.getValue());
                    });

            if (libraryTop10Readers.getTopReaderList().size() > 10) {
                libraryTop10Readers.setTopReaderList(libraryTop10Readers.getTopReaderList().subList(0, 10));
            }

            res.add(libraryTop10Readers);
        }

        return res;
    }

    public List<LibraryTop10Documents> findTop10Documents() {
        ArrayList<LibraryTop10Documents> res = new ArrayList<>();

        List<Library> libraries = libraryDao.findAll();
        for (Library library : libraries) {
            LibraryTop10Documents libraryTop10Documents = new LibraryTop10Documents();
            libraryTop10Documents.setLibrary(library);
            libraryTop10Documents.setTopDocuments(new ArrayList<>());
            libraryTop10Documents.setBorrowedCounts(new ArrayList<>());

            List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByLibIdAndIsCompleted(library.getId(), true);

            Map<Long, Long> result =
                    records.stream().collect(
                            Collectors.groupingBy(
                                    ReserveAndBorrowList::getDocumentId, Collectors.counting()
                            )
                    );

            Map<Long, Long> finalMap = new LinkedHashMap<>();

            result.entrySet().stream()
                    .sorted(Map.Entry.<Long, Long>comparingByValue()
                            .reversed()).forEachOrdered(e -> {
                        Optional<Document> document = documentDao.findById(e.getKey());
                        if (document.isPresent()) {
                            libraryTop10Documents.getTopDocuments().add(document.get());
                            libraryTop10Documents.getBorrowedCounts().add(e.getValue());
                        }

                        finalMap.put(e.getKey(), e.getValue());
                    });

            if (libraryTop10Documents.getTopDocuments().size() > 10) {
                libraryTop10Documents.setTopDocuments(libraryTop10Documents.getTopDocuments().subList(0, 10));
                libraryTop10Documents.setBorrowedCounts(libraryTop10Documents.getBorrowedCounts().subList(0,10));
            }
            res.add(libraryTop10Documents);
        }

        return res;
    }

    public List<YearTop10Documents> findYearlyTop10Documents() {
        ArrayList<YearTop10Documents> res = new ArrayList<>();
        int year = LocalDateTime.now().getYear();
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByIsCompleted(true);
        records = records.stream().filter(reserveAndBorrowList -> {
            int timeOfyear = reserveAndBorrowList.getBDateTime().getYear();
            return  year - timeOfyear == 0;
        }).collect(Collectors.toList());

        Map<Long, Long> result =
                records.stream().collect(
                        Collectors.groupingBy(
                                ReserveAndBorrowList::getDocumentId, Collectors.counting()
                        )
                );
        Map<Long, Long> finalMap = new LinkedHashMap<>();


        result.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> {
                    Optional<Document> document = documentDao.findById(e.getKey());
                    if (document.isPresent()) {
                        YearTop10Documents yearTop10Documents = new YearTop10Documents();
                        yearTop10Documents.setDocument(document.get());
                        yearTop10Documents.setBorrowedNums(e.getValue());
                        res.add(yearTop10Documents);
                    }

                    finalMap.put(e.getKey(), e.getValue());
                });

        if(res.size()>10){
           return res.subList(0,10);
        }
        return res;
    }
}
