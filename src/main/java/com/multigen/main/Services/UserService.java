package com.multigen.main.Services;

import com.multigen.main.DTO.LoginDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public UserDTO registerNewUser(UserDTO userDTO) throws AppExceptions;
    public List<UserDTO> getAllUsers();
    public UserDTO getUser(String userName)throws AppExceptions;;
    public UserDTO LoginUser(LoginDTO loginDTO) throws AppExceptions;

    public UserDTO updateUser(String userName,UserDTO userDTO)throws AppExceptions;

    public void deleteUser (String userName)throws AppExceptions;
}
