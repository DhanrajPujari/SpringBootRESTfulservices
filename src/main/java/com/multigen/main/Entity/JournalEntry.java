package com.multigen.main.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.multigen.main.DTO.JournalEntryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "journalEntryPractice")
public class JournalEntry {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    @Column
    private String dateTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;


    public JournalEntryDTO toDTO(){
        return new JournalEntryDTO(this.id,this.title,this.content,this.dateTime, this.user.getId());
    }
}
