package com.crossover.mobiliza.presentation.controller;

import com.crossover.mobiliza.business.entity.Evento;
import com.crossover.mobiliza.business.entity.Ong;
import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.service.*;
import com.crossover.mobiliza.presentation.dto.EventoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class EventoController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private OngService ongService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private VoluntarioService voluntarioService;

    @GetMapping("/eventos")
    private Collection<EventoDto> getAll(@RequestParam(value = "idOng", required = false) Long idOng,
                                         @RequestParam(value = "finalizado", required = false) Boolean finalizado) {
        // TODO: Add pagination to this
        Collection<Evento> eventos;
        if (idOng != null) {
            eventos = eventoService.findAllByOngId(idOng);
        } else {
            eventos = eventoService.findAll();
        }
        if (finalizado != null) {
            LocalDateTime now = LocalDateTime.now();
            if (finalizado) {
                eventos = eventos.stream().filter(e -> e.getDataRealizacao().isBefore(now)).collect(Collectors.toList());
            } else {
                eventos = eventos.stream().filter(e -> !e.getDataRealizacao().isBefore(now)).collect(Collectors.toList());
            }
        }
        return eventos.stream().map(EventoDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/eventos/{id}")
    private EventoDto get(@PathVariable("id") long id) {
        Evento evento = eventoService.findById(id);
        if (evento == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento not found");
        return new EventoDto(evento);
    }

    @PostMapping(path = "/eventos")
    private EventoDto save(@Valid @RequestBody EventoDto eventoDto,
                           @RequestParam("googleIdToken") String googleIdToken,
                           HttpServletRequest request) {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token invalid");

        Ong userOng = user.getOng();
        if (userOng == null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User doesn't have an Ong");
        if (eventoDto.getIdOng() != null && !Objects.equals(eventoDto.getIdOng(), userOng.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User's doesn't own this Event's Ong");

        Evento evento = eventoDto.toEvento(ongService, voluntarioService);
        evento = eventoService.save(evento);
        return new EventoDto(evento);
    }
}
