package com.crossover.mobiliza.presentation.controller;

import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.entity.Voluntario;
import com.crossover.mobiliza.business.service.GoogleAuthService;
import com.crossover.mobiliza.business.service.UserService;
import com.crossover.mobiliza.business.service.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

@RestController
public class VoluntarioController {

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserService userService;

    @GetMapping("/voluntarios")
    private Collection<Voluntario> getAll() {
        // TODO: Add pagination to this
        return voluntarioService.findAll();
    }

    @GetMapping(path = "/voluntarios/{id}")
    private Voluntario get(@PathVariable("id") long id) {
        Voluntario voluntario = voluntarioService.findById(id);
        if (voluntario == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voluntario not found");
        return voluntario;
    }

    @PostMapping(path = "/voluntarios", params = {"googleIdToken"})
    private Voluntario save(@RequestBody Voluntario voluntario,
                     @RequestParam("googleIdToken") String googleIdToken) {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token invalid");

        Voluntario currentVolunt = user.getVoluntario();
        if (currentVolunt == null) {
            voluntario.setId(null);
            voluntario.setUser(user);
            currentVolunt = voluntarioService.save(voluntario);
            user.setVoluntario(currentVolunt);
            user = userService.save(user);
        } else {
            if (voluntario.getId() == null)
                voluntario.setId(currentVolunt.getId());
            else if (!Objects.equals(currentVolunt.getId(), voluntario.getId()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User already has another Voluntario - Is " + voluntario.getId() + " but should be " + currentVolunt.getId());
            voluntario.setUser(user);
            currentVolunt = voluntarioService.save(voluntario);
        }
        return currentVolunt;
    }

}