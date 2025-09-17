package com.multigen.main.DTO;

import com.multigen.main.Entity.JournalEntry;
import com.multigen.main.Entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryDTO {

    private Long id;

    @NotBlank(message = "{journal.title.empty}")
    private String title;

    private String content;

    private String dateTime;

    private Long userId;

    public JournalEntry toEntity(User user){
        return new JournalEntry(this.id,this.title,this.content, this.dateTime, user);
    }
}