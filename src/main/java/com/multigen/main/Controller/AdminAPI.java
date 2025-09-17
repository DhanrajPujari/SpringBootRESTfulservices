package com.multigen.main.Controller;

import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/admin")
public class AdminAPI {

    private final UserService userService;

    public AdminAPI(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
