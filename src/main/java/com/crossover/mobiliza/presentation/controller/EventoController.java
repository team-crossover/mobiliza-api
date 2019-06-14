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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                                         @RequestParam(value = "categoria", required = false) String categoria,
                                         @RequestParam(value = "regiao", required = false) String regiao,
                                         @RequestParam(value = "finalizado", required = false) Boolean finalizado) {
        // TODO: Add pagination to this
        Stream<Evento> eventos;
        if (idOng != null) {
            eventos = eventoService.findAllByOngId(idOng).stream();
        } else {
            eventos = eventoService.findAll().stream();
        }

        if (regiao != null) {
            String finalRegiao = regiao.toLowerCase();
            eventos = eventos.filter(e -> e.getRegiao().toLowerCase().equals(finalRegiao));
        }

        if (categoria != null) {
            String finalCat = categoria.toLowerCase();
            eventos = eventos.filter(e -> e.getOng().getCategoria().toLowerCase().equals(finalCat));
        }
        if (finalizado != null) {
            LocalDateTime now = LocalDateTime.now();
            if (finalizado) {
                eventos = eventos.filter(e -> e.getDataRealizacao().isBefore(now));
            } else {
                eventos = eventos.filter(e -> !e.getDataRealizacao().isBefore(now));
            }
        }

        return eventos.map(EventoDto::new).collect(Collectors.toList());
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
                           HttpServletRequest request) throws IOException {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token invalid");

        Ong userOng = user.getOng();
        if (userOng == null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User doesn't have an Ong");
        if (eventoDto.getIdOng() != null && !Objects.equals(eventoDto.getIdOng(), userOng.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User's doesn't own this Event's Ong");

        eventoDto.setIdOng(user.getOng().getId());
        Evento evento = eventoDto.toEvento(ongService, voluntarioService);
        if (evento.getId() == null)
            evento = eventoService.save(evento);
        else
            evento = eventoService.saveNonNullFields(evento);
        return new EventoDto(evento);
    }
}
