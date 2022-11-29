package com.example.library.pro.service;

import com.example.library.pro.constants.DocumentStatus;
import com.example.library.pro.dao.DocumentDao;
import com.example.library.pro.dao.ReaderDao;
import com.example.library.pro.dao.ReserveAndBorrowListDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Document;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.Reader;
import com.example.library.pro.module.ReserveAndBorrowList;
import com.example.library.pro.vo.ReaderVo;
import com.example.library.pro.vo.ReserveAndBorrowDetailVo;
import com.example.library.pro.vo.ReserveVo;
import com.example.library.pro.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReaderService {

    @Autowired
    ReaderDao readerDao;

    @Autowired
    DocumentService documentService;

    @Autowired
    ReserveAndBorrowListDao reserveAndBorrowListDao;

    @Autowired
    DocumentDao documentDao;


    public Reader registerReader(ReaderVo readerVo) {
        Reader reader = new Reader();
        reader.setAddress(readerVo.getAddress());
        reader.setName(readerVo.getName());
        reader.setType(readerVo.getType());

        if (ObjectUtils.isEmpty(readerVo.getCardNumber())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "读者卡号不能为空");
        }

        Optional<Reader> res = readerDao.findByCardNumber(readerVo.getCardNumber());
        if (res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该读者卡号已经被注册");
        }


        reader.setPhoneNumber(readerVo.getPhoneNumber());
        reader.setCardNumber(readerVo.getCardNumber());
        return readerDao.save(reader);
    }


    public void cancelRegister(Long id) {
        Optional<Reader> res = readerDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }
        readerDao.deleteById(id);

    }

    public Reader login(Long id, String password) {
        if (ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(password)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "id号和密码不能为空");
        }
        Optional<Reader> res = readerDao.findById(id);
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
                if (reserveAndBorrowList.getIsCompleted() != null && reserveAndBorrowList.getIsCompleted()) {
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

    public ReserveAndBorrowList borrowDocument(Long readerId, Long documentId, Long libId, Long number) {
        if (ObjectUtils.isEmpty(readerId) || ObjectUtils.isEmpty(documentId) || ObjectUtils.isEmpty(libId) || ObjectUtils.isEmpty(number)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "读者Id，文档Id,图书馆Id和文档副本编号不能为空");
        }
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByReaderIdAndDocumentIdAndLibIdAndNumber(readerId, documentId, libId, number);

        records = records.stream().filter(reserveAndBorrowList -> {
            boolean isReserved = reserveAndBorrowList.getStatus() == DocumentStatus.reserved;
            boolean isUnCompleted = reserveAndBorrowList.getIsCompleted() == null || !reserveAndBorrowList.getIsCompleted();
            return isReserved && isUnCompleted;
        }).collect(Collectors.toList());

        if (records == null || records.size() == 0) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "没有查询到对应预定文档的记录，无法借书!");
        }

        ReserveAndBorrowList record = records.get(0);


        record.setStatus(DocumentStatus.borrowed);
        record.setBDateTime(LocalDateTime.now());
        reserveAndBorrowListDao.save(record);

        return record;
    }

    public ReturnVo returnDocument(Long readerId, Long documentId, Long libId, Long number) {
        ReturnVo returnVo = new ReturnVo();
        returnVo.setCost(0l);

        if (ObjectUtils.isEmpty(readerId) || ObjectUtils.isEmpty(documentId) || ObjectUtils.isEmpty(libId) || ObjectUtils.isEmpty(number)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "读者Id，文档Id,图书馆Id和文档副本编号不能为空");
        }
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByReaderIdAndDocumentIdAndLibIdAndNumber(readerId, documentId, libId, number);
        records = records.stream().filter(reserveAndBorrowList -> {
            boolean isBorrowed = reserveAndBorrowList.getStatus() == DocumentStatus.borrowed;
            boolean isUnCompleted = reserveAndBorrowList.getIsCompleted() == null || !reserveAndBorrowList.getIsCompleted();
            return isBorrowed && isUnCompleted;
        }).collect(Collectors.toList());


        if (records == null || records.size() == 0 || records.get(0).getStatus() != DocumentStatus.borrowed) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "没有查询到对应借取文档的记录，无法借书!");
        }

        ReserveAndBorrowList record = records.get(0);

        record.setStatus(DocumentStatus.inLib);
        record.setRDateTime(LocalDateTime.now());
        record.setIsCompleted(true);

        Duration duration = Duration.between(record.getBDateTime(), record.getRDateTime());
        if (duration.toDays() > 20) {
            Reader reader = readerDao.findById(readerId).get();
            Long cost = reader.getCost();
            if (ObjectUtils.isEmpty(cost)) {
                cost = 0L;
            }
            cost += (duration.toDays() - 20) * 20;
            returnVo.setCost(returnVo.getCost()+(duration.toDays() - 20) * 20);
            reader.setCost(cost);
            readerDao.save(reader);
        }

        record = reserveAndBorrowListDao.save(record);

        returnVo.setReserveAndBorrowList(record);

        return returnVo;

    }

    public Reader readerLogin(Long cardNumber) {
        if (ObjectUtils.isEmpty(cardNumber)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "card number不能为空");
        }
        Optional<Reader> res = readerDao.findByCardNumber(cardNumber);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该账号不存在");
        }


        return res.get();
    }

    public List<ReserveAndBorrowDetailVo> getBorrowedDocumentsByReaderId(Long readerId) {

        ArrayList<ReserveAndBorrowDetailVo> res = new ArrayList<>();
        List<ReserveAndBorrowList> records = reserveAndBorrowListDao.findAllByReaderId(readerId).stream().filter(reserveAndBorrowList -> {
            return reserveAndBorrowList.getStatus() != null && reserveAndBorrowList.getStatus() == DocumentStatus.reserved;
        }).collect(Collectors.toList());
        for(ReserveAndBorrowList record:records){
            Optional<Document> document = documentDao.findById(record.getDocumentId());
            if(document.isPresent()){
                ReserveAndBorrowDetailVo reserveAndBorrowDetailVo = new ReserveAndBorrowDetailVo();
                reserveAndBorrowDetailVo.setDocument(document.get());
                reserveAndBorrowDetailVo.setReserveAndBorrowList(record);
                res.add(reserveAndBorrowDetailVo);
            }
        }
        return res;
    }
}
