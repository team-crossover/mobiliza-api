package com.crossover.mobiliza.presentation.controller;

import com.crossover.mobiliza.business.entity.Ong;
import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.service.GoogleAuthService;
import com.crossover.mobiliza.business.service.OngService;
import com.crossover.mobiliza.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

@RestController
public class OngController {

    @Autowired
    private UserService userService;

    @Autowired
    private OngService ongService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/ongs")
    private Collection<Ong> getAll() {
        // TODO: Add pagination to this
        return ongService.findAll();
    }

    @GetMapping(path = "/ongs/{id}")
    private Ong get(@PathVariable("id") long id) {
        Ong ong = ongService.findById(id);
        if (ong == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ong not found");
        return ong;
    }

    @PostMapping(path = "/ongs", params = {"googleIdToken"})
    private Ong save(@RequestBody Ong ong,
                     @RequestParam("googleIdToken") String googleIdToken,
                     HttpServletRequest request) {

        User user = googleAuthService.getOrCreateUserFromIdToken(googleIdToken);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google ID Token invalid");

        Ong currentOng = user.getOng();
        if (currentOng == null) {
            ong.setId(null);
            ong.setUser(user);
            currentOng = ongService.save(ong);
            user.setOng(currentOng);
            user = userService.save(user);
        } else {
            if (ong.getId() == null)
                ong.setId(currentOng.getId());
            else if (!Objects.equals(currentOng.getId(), ong.getId()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User already has another Ong - Is " + ong.getId() + " but should be " + currentOng.getId());
            ong.setUser(user);
            currentOng = ongService.save(ong);
        }
        return currentOng;
    }
}
