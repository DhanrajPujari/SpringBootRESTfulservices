package com.multigen.main.DTO;

import com.multigen.main.Entity.JournalEntry;
import com.multigen.main.Entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{


    private Long id;

    @NotBlank(message = "{user.name.absent}")
    private String userName;

    @NotBlank(message = "{user.email.absent }")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.absent}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])\\S{8,15}$", message = "{user.password.invalid}")
    private String password;


    private AccountType accountType;

    private List<JournalEntryDTO> journalEntries = new ArrayList<>();

    public User ToEntity() {
        User user = new User(this.id, this.userName, this.email, this.password, this.accountType, new ArrayList<>());


        List<JournalEntry> entries = this.journalEntries.stream()
                .map(journalEntryDTO -> journalEntryDTO.toEntity(user))
                .toList();

        user.setJournalEntries(entries);

        return user;
    }

}
