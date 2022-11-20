package com.example.library.pro.service;

import com.example.library.pro.dao.DocumentDao;
import com.example.library.pro.dao.LibDocumentsDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Document;
import com.example.library.pro.module.LibDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    DocumentDao documentDao;

    @Autowired
    LibDocumentsDao libDocumentsDao;

    public Document getById(Long id) {
        Optional<Document> res = documentDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该文档不存在");
        }
        return res.get();
    }


    public List<LibDocuments> getLibDocumentsById(Long libId, Long documentId) {
        if(ObjectUtils.isEmpty(libId)||ObjectUtils.isEmpty(documentId)){
            throw new RequestException(HttpStatus.BAD_REQUEST,"图书馆id和文档id不能为空");
        }
        List<LibDocuments> libDocuments = libDocumentsDao.findByLibIdAndDocumentId(libId, documentId);
        return libDocuments;
    }

    public LibDocuments addLibDocuments(Long libId, Long documentId) {

        if(ObjectUtils.isEmpty(libId)||ObjectUtils.isEmpty(documentId)){
            throw new RequestException(HttpStatus.BAD_REQUEST,"图书馆id和文档id不能为空");
        }
        LibDocuments libDocuments  = new LibDocuments();
        libDocuments.setDocumentId(documentId);
        libDocuments.setLibId(libId);
        List<LibDocuments> libDocumentsList = libDocumentsDao.findByLibIdAndDocumentId(libId, documentId);
        libDocuments.setNumber(libDocumentsList.stream().filter(Objects::nonNull).mapToLong(LibDocuments::getNumber).max().orElse(0) + 1L);
        libDocuments.setTotalNumber(libDocuments.getNumber());
        for(LibDocuments record: libDocumentsList){
            record.setTotalNumber(libDocuments.getTotalNumber());

        }
        libDocumentsDao.saveAll(libDocumentsList);

        return libDocuments;
    }
}
