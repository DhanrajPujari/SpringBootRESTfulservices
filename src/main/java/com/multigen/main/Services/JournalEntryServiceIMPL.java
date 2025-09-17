package com.multigen.main.Services;

import com.multigen.main.DTO.JournalEntryDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Entity.JournalEntry;
import com.multigen.main.Entity.User;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Repository.JournalEntryRepository;
import com.multigen.main.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JournalEntryServiceIMPL implements JournalEntryService{

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createNewJournalEntry(JournalEntryDTO journalEntryDTO,String userName) throws AppExceptions {

        User user = userRepository.findByUserName(userName);

        if (user == null){
            throw new AppExceptions("USER_NOT_FOUND");
        }

        LocalDateTime now = LocalDateTime.now().withNano(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        String formatted = now.format(formatter);

        journalEntryDTO.setDateTime(formatted);

        JournalEntry entry = new JournalEntry();
        entry.setContent(journalEntryDTO.getContent());
        entry.setTitle(journalEntryDTO.getTitle());
        entry.setDateTime(journalEntryDTO.getDateTime());
        entry.setUser(user);

//        return journalEntryRepository.save(entry);

        return journalEntryRepository.save(entry).getUser().ToDTO();

    }


    @Override
    public List<JournalEntryDTO> getAllGeneralEntriesOfUser(Long id,String userName)  throws AppExceptions{

        User user = userRepository.findByUserName(userName);

        if (user.getId().equals(id)){

        return journalEntryRepository.findByUserId(id).stream().map(JournalEntry::toDTO).toList();

        }else {

            throw new AppExceptions("USER_ID_AND_USERNAME_WRONG");
        }

    }




    @Override
    public Optional<JournalEntryDTO> getEntryById(String userName,Long id)throws AppExceptions {

        User user = userRepository.findByUserName(userName);


        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if (!journalEntries.isEmpty()){

            return Optional.ofNullable(journalEntryRepository.findById(id).orElseThrow(() -> new AppExceptions("ENTRY_NOT_FOUND")).toDTO());

        }else {
            throw new AppExceptions("USER_ID_AND_USERNAME_WRONG");
        }

    }

    //remaining

    @Override
    public void deleteEntry(Long id,String userName) throws AppExceptions {


        User user = userRepository.findByUserName(userName);

        com.multigen.main.Entity.JournalEntry journalEntry =user.getJournalEntries().stream().filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new AppExceptions("ENTRY_NOT_FOUND"));

        userRepository.save(user);

        journalEntryRepository.delete(journalEntry);

    }

    @Override
    public JournalEntryDTO updateEntry(Long id, JournalEntryDTO journalEntryDTO) throws AppExceptions {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userRepository.findByUserName(userName);

        if (user != null)

        {
            JournalEntry journalEntry = user.getJournalEntries().stream().filter(e -> Objects.equals(e.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new AppExceptions("ENTRY_NOT_FOUND"));

            LocalDateTime now = LocalDateTime.now().withNano(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
            String formatted = now.format(formatter);

            journalEntry.setContent(journalEntryDTO.getContent());
            journalEntry.setDateTime(formatted);
            journalEntry.setTitle(journalEntryDTO.getTitle());
            journalEntry.setId(id);

            return journalEntryRepository.save(journalEntry).toDTO();
        }else {

            throw new AppExceptions("USER_NOT_FOUND");
        }


    }

}
