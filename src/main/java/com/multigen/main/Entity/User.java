package com.multigen.main.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.multigen.main.DTO.AccountType;
import com.multigen.main.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PracticeUsers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private AccountType accountType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<JournalEntry> journalEntries = new ArrayList<>();


    public UserDTO ToDTO(){
        return new UserDTO(this.id,this.userName,this.email,this.password,this.accountType,this.journalEntries.stream().map(JournalEntry::toDTO).toList());
    }

}
