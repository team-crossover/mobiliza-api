package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Usuario;
import com.crossover.mobiliza.data.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UsuarioService extends ServiceBase<Usuario, Long, UsuarioRepository> {

    public Usuario findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

}
