package com.example.library.pro.service;

import com.example.library.pro.constants.DocumentType;
import com.example.library.pro.dao.*;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Book;
import com.example.library.pro.module.ConferenceProceeding;
import com.example.library.pro.module.Document;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.vo.BookVo;
import com.example.library.pro.vo.ConferenceProceedingVo;
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
    ConferenceProceedingDao conferenceProceedingDao;

    @Autowired
    LibDocumentsDao libDocumentsDao;

    @Autowired
    PublisherDao publisherDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    public Document getById(Long id) {
        Optional<Document> res = documentDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该文档不存在");
        }
        return res.get();
    }


    public List<LibDocuments> getLibDocumentsById(Long libId, Long documentId) {
        if (ObjectUtils.isEmpty(libId) || ObjectUtils.isEmpty(documentId)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "图书馆id和文档id不能为空");
        }
        List<LibDocuments> libDocuments = libDocumentsDao.findByLibIdAndDocumentId(libId, documentId);
        return libDocuments;
    }

    public LibDocuments addLibDocuments(Long libId, Long documentId) {

        if (ObjectUtils.isEmpty(libId) || ObjectUtils.isEmpty(documentId)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "图书馆id和文档id不能为空");
        }
        LibDocuments libDocuments = new LibDocuments();
        libDocuments.setDocumentId(documentId);
        libDocuments.setLibId(libId);
        List<LibDocuments> libDocumentsList = libDocumentsDao.findByLibIdAndDocumentId(libId, documentId);
        libDocuments.setNumber(libDocumentsList.stream().filter(Objects::nonNull).mapToLong(LibDocuments::getNumber).max().orElse(0) + 1L);
        libDocuments.setTotalNumber(libDocuments.getNumber());
        for (LibDocuments record : libDocumentsList) {
            record.setTotalNumber(libDocuments.getTotalNumber());

        }
        libDocumentsDao.saveAll(libDocumentsList);

        return libDocuments;
    }

    public ConferenceProceeding addConferenceProceedingVo(ConferenceProceedingVo conferenceProceedingVo) {

        Document document = new Document();
        document.setType(DocumentType.ConferenceProceeding);
        document.setTitle(conferenceProceedingVo.getTitle());
        document.setPublicationDate(conferenceProceedingVo.getPublicationDate());
        if (!publisherDao.existsById(conferenceProceedingVo.getPublisherId())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "publisher不存在");
        }
        document.setPublisherId(conferenceProceedingVo.getPublisherId());
        Document res = documentDao.save(document);

        ConferenceProceeding conferenceProceeding = new ConferenceProceeding();
        conferenceProceeding.setDate(conferenceProceedingVo.getDate());
        conferenceProceeding.setEditor(conferenceProceedingVo.getEditor());
        conferenceProceeding.setLocation(conferenceProceedingVo.getLocation());
        //会议纪要的id和文档的id是一样的
        conferenceProceeding.setId(res.getId());
        return conferenceProceedingDao.save(conferenceProceeding);

    }

    public Book addBookVo(BookVo bookVo) {
        Document document = new Document();
        document.setType(DocumentType.Book);
        document.setTitle(bookVo.getTitle());
        document.setPublicationDate(bookVo.getPublicationDate());
        if (!publisherDao.existsById(bookVo.getPublisherId())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "publisher不存在");
        }
        document.setPublisherId(bookVo.getPublisherId());
        Document res = documentDao.save(document);

        Book book = new Book();
        book.setISBN(bookVo.getISBN());
        if (!authorDao.existsById(bookVo.getAuthorId())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "author不存在");
        }
        //书籍的id和文档的id是一样的
        book.setAuthorId(bookVo.getAuthorId());
        book.setId(res.getId());
        return bookDao.save(book);

    }
}
