package com.multigen.main.Repository;

import com.multigen.main.Entity.JournalEntry;
import com.multigen.main.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long> {
    List<JournalEntry> findByUserId(Long userId);

}
