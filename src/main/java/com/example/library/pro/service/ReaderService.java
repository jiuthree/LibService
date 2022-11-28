package com.example.library.pro.service;

import com.example.library.pro.constants.DocumentStatus;
import com.example.library.pro.dao.ReaderDao;
import com.example.library.pro.dao.ReserveAndBorrowListDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.Reader;
import com.example.library.pro.module.ReserveAndBorrowList;
import com.example.library.pro.vo.ReaderVo;
import com.example.library.pro.vo.ReserveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    @Autowired
    ReaderDao readerDao;

    @Autowired
    DocumentService documentService;

    @Autowired
    ReserveAndBorrowListDao reserveAndBorrowListDao;

    public Reader registerReader(ReaderVo readerVo) {
        Reader reader = new Reader();
        reader.setAddress(readerVo.getAddress());
        reader.setName(readerVo.getName());
        reader.setType(readerVo.getType());

        if (ObjectUtils.isEmpty(readerVo.getPhoneNumber()) || ObjectUtils.isEmpty(readerVo.getPassword())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "手机号和密码不能为空");
        }

        Optional<Reader> res = readerDao.findByPhoneNumber(readerVo.getPhoneNumber());
        if (res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该手机号已经被注册");
        }


        reader.setPhoneNumber(readerVo.getPhoneNumber());
        reader.setPassword(readerVo.getPassword());
        return readerDao.save(reader);
    }


    public void cancelRegister(Long id) {
        Optional<Reader> res = readerDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }
        readerDao.deleteById(id);

    }

    public Reader login(String phoneNumber, String password) {
        if (ObjectUtils.isEmpty(phoneNumber) || ObjectUtils.isEmpty(password)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "手机号和密码不能为空");
        }
        Optional<Reader> res = readerDao.findByPhoneNumber(phoneNumber);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }
        if (!password.equals(res.get().getPassword())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "密码不正确");
        }

        return res.get();
    }

    public Reader getReaderById(Long id) {
        Optional<Reader> res = readerDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }

        return res.get();
    }

    public ReserveVo reserveDocument(ReserveVo reserveVo) {
        Long readerId = reserveVo.getReaderId();
        Long documentId = reserveVo.getDocumentId();
        Long libId = reserveVo.getLibId();
        if (ObjectUtils.isEmpty(readerId) || ObjectUtils.isEmpty(documentId) || ObjectUtils.isEmpty(libId)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "读者Id，文档Id和图书馆Id不能为空");
        }
        // 预定前读者必须被注册
        Optional<Reader> reader = readerDao.findById(readerId);
        if (!reader.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "读者不存在!");
        }

        // 预定的书籍必须有剩余才可以预定，就是查看有没有剩余的副本
        List<LibDocuments> libDocumentsList = documentService.getLibDocumentsById(reserveVo.getLibId(), reserveVo.getDocumentId());
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByDocumentIdAndLibId(reserveVo.getDocumentId(), reserveVo.getLibId());
        long documentNumber = -1;
        for (LibDocuments libDocuments : libDocumentsList) {
            Long number = libDocuments.getNumber();
            boolean isAvailable = true;
            //要找到一个编码是没有被借阅或者预定的
            for (ReserveAndBorrowList reserveAndBorrowList : records) {
                if (reserveAndBorrowList.getNumber() == null || reserveAndBorrowList.getNumber() != number) {
                    continue;
                }
                if (reserveAndBorrowList.getStatus() == DocumentStatus.reserved || reserveAndBorrowList.getStatus() == DocumentStatus.borrowed) {
                    isAvailable = false;
                    break;
                }

            }
            if (isAvailable) {

                documentNumber = number;
            }

        }
        if (documentNumber == -1) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该文档在该图书馆没有剩余的数量了!");
        }


        //读者预定或者借阅的书籍不能超过10本
        long count = reserveAndBorrowListDao.findAllByReaderId(reserveVo.getReaderId()).stream().filter(reserveAndBorrowList -> {
            DocumentStatus status = reserveAndBorrowList.getStatus();
            return status != null && (status == DocumentStatus.reserved || status == DocumentStatus.borrowed);
        }).count();
        if (count >= 10) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该读者已经预定或者借阅的文档数超过10本！");
        }


        //预定书籍的逻辑
        ReserveAndBorrowList reserveInfo = new ReserveAndBorrowList();
        reserveInfo.setDocumentId(reserveVo.getDocumentId());
        reserveInfo.setReaderId(reserveVo.getReaderId());
        reserveInfo.setLibId(reserveVo.getLibId());
        reserveInfo.setNumber(documentNumber);
        reserveInfo.setStatus(DocumentStatus.reserved);

        LocalDateTime localDateTime = LocalDateTime.now();

        reserveInfo.setReserveDate(localDateTime);
        reserveAndBorrowListDao.save(reserveInfo);
        reserveVo.setReserveDate(localDateTime);
        reserveVo.setNumber(documentNumber);
        reserveVo.setStatus(DocumentStatus.reserved);

        return reserveVo;
    }
}
