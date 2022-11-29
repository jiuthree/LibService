package com.example.library.pro.service;

import com.example.library.pro.constants.DocumentType;
import com.example.library.pro.dao.*;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.*;
import com.example.library.pro.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
    
    @Autowired
    JournalDao journalDao;
    
    @Autowired
    VolumeDao volumeDao;

    @Autowired
    LibraryDao libraryDao;

    public Document getById(Long id) {
        Optional<Document> res = documentDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该文档不存在");
        }
        return res.get();
    }

    public List<Document> findAll(){
        return documentDao.findAll();
    }

    public List<Document> findByTitleLike(String title){
        return documentDao.findByTitleLike("%"+title+"%");
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
        
        book.setAuthorId(bookVo.getAuthorId());
        //书籍的id和文档的id是一样的
        book.setId(res.getId());
        return bookDao.save(book);

    }

    public JournalVo addJournalVo(JournalVo journalVo) {
        Document document = new Document();
        document.setType(DocumentType.Journal);
        document.setTitle(journalVo.getTitle());
        document.setPublicationDate(journalVo.getPublicationDate());
        if (!publisherDao.existsById(journalVo.getPublisherId())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "publisher不存在");
        }
        document.setPublisherId(journalVo.getPublisherId());
        Document res = documentDao.save(document);
        Journal journal = new Journal();
        journal.setId(res.getId());
        journal.setName(journalVo.getName());
        journal.setEditor(journalVo.getEditor());
        journal.setScope(journalVo.getScope());
        List<Volume> volumes = journalVo.getVolumes();
        for(Volume volume:volumes){
            volume.setJournalId(journal.getId());
        }
        
        volumeDao.saveAll(volumes);
        journalVo.setVolumes(volumeDao.findAllByJournalId(journal.getId()));
        Journal savedJournal = journalDao.save(journal);
        journal.setId(savedJournal.getId());

        return journalVo;
    }

    public DocumentDetailVo getDetailById(Long id) {

        DocumentDetailVo documentDetailVo = new DocumentDetailVo();
        Document document = getById(id);
        documentDetailVo.setId(document.getId());
        documentDetailVo.setTitle(document.getTitle());
        if (!publisherDao.existsById(document.getPublisherId())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "publisher不存在");
        }
        documentDetailVo.setPublisher(publisherDao.findById(document.getPublisherId()).get());
        documentDetailVo.setPublicationDate(document.getPublicationDate());
        documentDetailVo.setType(document.getType());
        documentDetailVo.setLibDocumentVos(new ArrayList<>());
        List<LibDocuments> records = libDocumentsDao.findAllByDocumentId(document.getId());
        for(LibDocuments libDocuments: records){
            LibDocumentVo libDocumentVo = new LibDocumentVo();
            libDocumentVo.setId(libDocuments.getId());
            libDocumentVo.setDocumentId(document.getId());
            libDocumentVo.setLibId(libDocuments.getLibId());
            Optional<Library> library = libraryDao.findById(libDocuments.getLibId());
            if(library.isPresent()){
                libDocumentVo.setLibraryName(library.get().getName());
                libDocumentVo.setLibraryLocation(library.get().getLocation());
            }
            libDocumentVo.setNumber(libDocuments.getNumber());
            libDocumentVo.setTotalNumber(libDocuments.getTotalNumber());
            documentDetailVo.getLibDocumentVos().add(libDocumentVo);
        }
        documentDetailVo.setType(document.getType());
        DocumentContent documentContent = new DocumentContent();
        switch (document.getType()){
            case ConferenceProceeding:{
                ConferenceProceedingVo conferenceProceedingVo = new ConferenceProceedingVo();
                Optional<ConferenceProceeding> conferenceProceeding = conferenceProceedingDao.findById(document.getId());
                if(!conferenceProceeding.isPresent()){
                    throw new RequestException(HttpStatus.BAD_REQUEST, "conferenceProceeding不存在,documentId:"+document.getId());
                }
                conferenceProceedingVo.setEditor(conferenceProceeding.get().getEditor());
                conferenceProceedingVo.setLocation(conferenceProceeding.get().getLocation());
                conferenceProceedingVo.setDate(conferenceProceeding.get().getDate());
                documentContent.setConferenceProceedingVo(conferenceProceedingVo);
                break;
            }
            case Book:{
                BookVo bookVo = new BookVo();
                Optional<Book> book = bookDao.findById(document.getId());
                if(!book.isPresent()){
                    throw new RequestException(HttpStatus.BAD_REQUEST, "book不存在,documentId:"+document.getId());
                }
                bookVo.setISBN(book.get().getISBN());
                bookVo.setAuthorId(book.get().getAuthorId());
                documentContent.setBookVo(bookVo);
                break;
            }
            case Journal:{
                JournalVo journalVo = new JournalVo();
                Optional<Journal> journal = journalDao.findById(document.getId());
                if(!journal.isPresent()){
                    throw new RequestException(HttpStatus.BAD_REQUEST, "journal不存在,documentId:"+document.getId());
                }
                journalVo.setName(journal.get().getName());
                journalVo.setScope(journal.get().getScope());
                journalVo.setEditor(journal.get().getEditor());
                journalVo.setId(journal.get().getId());
                journalVo.setVolumes(volumeDao.findAllByJournalId(journal.get().getId()));

                documentContent.setJournalVo(journalVo);
                break;
            }

        }

        documentDetailVo.setDocumentContent(documentContent);

        return documentDetailVo;
    }

    public List<Document> findByPublisherName(String publisherName) {
        ArrayList<Document> res = new ArrayList<>();
        List<Publisher> publishers = publisherDao.findByNameLike("%" + publisherName + "%");
        for(Publisher publisher:publishers){
            List<Document> documents = documentDao.findAllByPublisherId(publisher.getId());
            res.addAll(documents);
        }
        return res;

    }
}
