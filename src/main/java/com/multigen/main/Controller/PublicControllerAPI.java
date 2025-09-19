package com.multigen.main.Controller;

import com.multigen.main.DTO.LoginDTO;
import com.multigen.main.DTO.UserDTO;
import com.multigen.main.Exception.AppExceptions;
import com.multigen.main.Services.UserService;
import com.multigen.main.Services.UserDetailServiceIMPL;
import com.multigen.main.Utility.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Validated
public class PublicControllerAPI {
    private final UserService userService;

    public PublicControllerAPI(UserService userService){
        this.userService=userService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceIMPL userDetailServiceIMPL;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws AppExceptions {

        userDTO = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String>loginUser(@RequestBody @Valid LoginDTO loginDTO) throws AppExceptions{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));

            UserDetails userDetails = userDetailServiceIMPL.loadUserByUsername(loginDTO.getUserName());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.FOUND);

        }catch (Exception e){

            throw new AppExceptions("User Authentication Failed.");
        }


    }
}
