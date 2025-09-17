package com.multigen.main.Services;

import com.multigen.main.DTO.LoginDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Entity.User;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMPL implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) throws AppExceptions {


        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

        if (user.isPresent())throw new AppExceptions("USER.FOUND");
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user1 = userDTO.ToEntity();

        user1 = userRepository.save(user1);
        System.out.println(!passwordEncoder.matches(user1.getPassword(),userDTO.getPassword()));
        return user1.ToDTO();


    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream().map(User::ToDTO).toList();

    }

    @Override
    public UserDTO getUser(String userName) throws AppExceptions {
        return userRepository.findByUserName(userName).ToDTO();
    }


    @Override
    public UserDTO LogonUser(LoginDTO loginDTO) throws AppExceptions {

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new AppExceptions("USER_NOT_FOUND"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))throw new AppExceptions("INVALID_CREDENTIALS");
        return user.ToDTO();

    }

    @Override
    public UserDTO updateUser(String userName,UserDTO userDTO) throws AppExceptions{

        User user = userRepository.findByUserName(userName);

        if (user != null){

            user.setId(userDTO.getId());
            user.setUserName(userDTO.getUserName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setAccountType(userDTO.getAccountType());

            return userRepository.save(user).ToDTO();
        }

        throw new AppExceptions("Some Error Occurred ! User Can't Updated");
    }

    @Override
    @Transactional
    public void deleteUser(String userName) throws AppExceptions {

        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new AppExceptions("USER_NOT_FOUND");
        }

        userRepository.delete(user);
    }

}
