package com.multigen.main.Controller;

import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicControllerAPI {
    private final UserService userService;

    public PublicControllerAPI(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws AppExceptions {

        userDTO = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
