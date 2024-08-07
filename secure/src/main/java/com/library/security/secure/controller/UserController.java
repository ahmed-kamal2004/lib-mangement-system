package com.library.security.secure.controller;


import com.library.security.secure.dto.LogInDto;
import com.library.security.secure.dto.ResponseDto;
import com.library.security.secure.dto.UserDto;
import com.library.security.secure.services.IUserModelService;
import com.library.security.secure.utlities.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth/")
@RequiredArgsConstructor
public class UserController {

    private final IUserModelService userModelService;


    @PostMapping("log-in")
    public ResponseEntity<ResponseDto> logIn(@RequestBody LogInDto logInDto){

//        LogInDto logInDto = new LogInDto("string","string");

        String token = this.userModelService.logIn(logInDto);

        ResponseDto responseDto = new ResponseDto(HttpStatus.ACCEPTED,token);

        return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
    }



    @PostMapping("sign-up")
    public ResponseEntity<ResponseDto> singUp(@RequestHeader HttpHeaders httpHeaders, @RequestBody  UserDto userDto){
        this.userModelService.signUp(userDto);

        return new ResponseEntity<>(new ResponseDto(
                HttpStatus.CREATED,
                String.format(Constants.EMAIL_CREATED_SUCCESS_MESSAGE,userDto.getEmail())
        )
        , HttpStatus.CREATED);
    }


}
