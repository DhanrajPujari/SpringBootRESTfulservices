package com.multigen.main.Controller;

import com.multigen.main.DTO.LoginDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/user")
public class UserAPI {

    private final UserService userService;

    public UserAPI(UserService userService){
        this.userService=userService;
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return new ResponseEntity<>(userService.updateUser(userName,userDTO),HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUsers() throws AppExceptions {

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         String userName = authentication.getName();

        return new ResponseEntity<>(userService.getUser(userName),HttpStatus.FOUND);
    }

    @DeleteMapping("/delete")

    public ResponseEntity<?> deleteUser() throws AppExceptions {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        userService.deleteUser(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO>loginUser(@RequestBody @Valid LoginDTO loginDTO) throws AppExceptions{

        return new ResponseEntity<>(userService.LogonUser(loginDTO),HttpStatus.FOUND);

    }


}
