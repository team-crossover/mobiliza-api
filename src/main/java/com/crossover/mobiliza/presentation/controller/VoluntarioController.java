package com.crossover.mobiliza.presentation.controller;

import com.crossover.mobiliza.business.entity.Evento;
import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.entity.Voluntario;
import com.crossover.mobiliza.business.service.EventoService;
import com.crossover.mobiliza.business.service.GoogleAuthService;
import com.crossover.mobiliza.business.service.UserService;
import com.crossover.mobiliza.business.service.VoluntarioService;
import com.crossover.mobiliza.presentation.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private EventoService eventoService;

    @GetMapping("/voluntarios")
    private Collection<Voluntario> getAll() {
        // TODO: Add pagination to this
        return voluntarioService.findAll();
    }

    @GetMapping(path = "/voluntarios/{id}")
    private Voluntario get(@PathVariable("id") long id) {
        Voluntario voluntario = voluntarioService.findById(id);
        if (voluntario == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voluntario não encontrado");
        return voluntario;
    }

    @DeleteMapping(path = "/voluntarios")
    private UserDto deletarMeuVoluntario(@RequestParam("googleIdToken") String googleIdToken,
                                         HttpServletRequest request) throws IOException {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token inválido");

        Voluntario vol = user.getVoluntario();
        if (vol == null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User não possui um Voluntario");

        // Remove do user
        user.setVoluntario(null);
        user = userService.save(user);

        // Remove confirmação dos eventos
        for (Evento evtConfirmado : vol.getEventosConfirmados()) {
            evtConfirmado.getConfirmados().removeIf(evt -> evt.getId().equals(vol.getId()));
            eventoService.save(evtConfirmado);
        }

        voluntarioService.deleteById(vol.getId());
        return new UserDto(user);
    }

    @PostMapping(path = "/voluntarios", params = {"googleIdToken"})
    private Voluntario save(@RequestBody Voluntario voluntario,
                            @RequestParam("googleIdToken") String googleIdToken) {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token inválido");

        Voluntario currentVolunt = user.getVoluntario();
        if (currentVolunt == null) {
            voluntario.setId(null);
            voluntario.setUser(user);
            currentVolunt = voluntarioService.save(voluntario);
            user.setVoluntario(currentVolunt);
            user.setLastUsedAsOng(false);
            user = userService.save(user);
        } else {
            if (voluntario.getId() == null)
                voluntario.setId(currentVolunt.getId());
            else if (!Objects.equals(currentVolunt.getId(), voluntario.getId()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User já possui outro Voluntário");
            voluntario.setUser(user);
            currentVolunt = voluntarioService.saveNonNullFields(voluntario);
            user.setLastUsedAsOng(false);
            user = userService.save(user);
        }
        return currentVolunt;
    }

}
