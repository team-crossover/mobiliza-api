package com.crossover.mobiliza.presentation.controller;

import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.service.GoogleAuthService;
import com.crossover.mobiliza.business.service.UserService;
import com.crossover.mobiliza.presentation.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    private UserService ongService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping(path = "/users")
    private UserDto getByGoogleId(@RequestParam(value = "googleIdToken", required = true) String googleIdToken,
                                  @RequestParam(value = "asOng", required = false) Boolean asOng) {
        if (googleIdToken == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Google ID Token não encontrado");

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken, asOng);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User não pôde ser lido ou criado");

        UserDto userDto = new UserDto(user);
        userDto.setGoogleIdToken(googleIdToken);
        return userDto;
    }

}
