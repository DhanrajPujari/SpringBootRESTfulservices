package com.multigen.main.Services;

import com.multigen.main.DTO.JournalEntryDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;

import java.util.List;
import java.util.Optional;


public interface JournalEntryService {

    public JournalEntryDTO createNewJournalEntry(JournalEntryDTO journalEntryDTO, String userName) throws AppExceptions;


    public List<JournalEntryDTO> getAllGeneralEntriesOfUser(String userName)throws AppExceptions;


    public Optional<JournalEntryDTO> getEntryById(String userName,Long id)throws AppExceptions;

    public void deleteEntry(Long id,String userName)throws AppExceptions;

    public JournalEntryDTO updateEntry(Long id,JournalEntryDTO journalEntryDTO)throws AppExceptions;
}
