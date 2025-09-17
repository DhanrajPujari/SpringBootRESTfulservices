package com.multigen.main.Controller;

import com.multigen.main.DTO.JournalEntryDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Services.JournalEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/journal")
public class JournalEntryAPI {
    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createJournalEntry(@Valid @RequestBody JournalEntryDTO journalEntryDTO) throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        return new ResponseEntity<>(journalEntryService.createNewJournalEntry(journalEntryDTO,userName),HttpStatus.CREATED);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<JournalEntryDTO>> getAllGeneralEntriesOfUser(@PathVariable Long id) throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return new ResponseEntity<>(journalEntryService.getAllGeneralEntriesOfUser(id,userName),HttpStatus.OK);

    }


    @GetMapping("/getSingleEntry/{id}")
    public ResponseEntity<Optional<JournalEntryDTO>> getUserById(@PathVariable Long id) throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        Optional<JournalEntryDTO> journalEntryDTO = journalEntryService.getEntryById(userName,id);
        return new ResponseEntity<>(journalEntryDTO, HttpStatus.FOUND);

    }

    //remaining

    @PutMapping("/update/{id}")
    public ResponseEntity<JournalEntryDTO> updateEntry(@PathVariable Long id,@RequestBody JournalEntryDTO journalEntryDTO) throws AppExceptions {

        return new ResponseEntity<>(journalEntryService.updateEntry(id,journalEntryDTO),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id) throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        journalEntryService.deleteEntry(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
