package com.example.library.pro.dao;

import com.example.library.pro.module.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolumeDao extends JpaRepository<Volume,Long> {
    List<Volume> findAllByJournalId(Long journalId);
}
