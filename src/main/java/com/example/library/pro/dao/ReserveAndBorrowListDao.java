package com.example.library.pro.dao;

import com.example.library.pro.module.ReserveAndBorrowList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveAndBorrowListDao extends JpaRepository<ReserveAndBorrowList,Long> {
    List<ReserveAndBorrowList> findAllByReaderIdAndDocumentIdAndLibId(Long readerId,Long documentId,Long libId);

    List<ReserveAndBorrowList> findAllByReaderIdAndDocumentIdAndLibIdAndNumber(Long readerId,Long documentId,Long libId,Long number);

    List<ReserveAndBorrowList> findAllByReaderId(Long readerId);

    List<ReserveAndBorrowList> findAllByDocumentIdAndLibId(Long documentId,Long libId);

    List<ReserveAndBorrowList> findAllByDocumentIdAndLibIdAndAndNumber(Long documentId,Long libId,Long number);

    List<ReserveAndBorrowList> findAllByLibIdAndIsCompleted(Long library,Boolean isComplete);
}
