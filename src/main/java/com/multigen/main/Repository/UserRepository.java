package com.multigen.main.Repository;

import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);
    public User findByUserName(String userName);
    public User deleteByUserName(String userName);
}
