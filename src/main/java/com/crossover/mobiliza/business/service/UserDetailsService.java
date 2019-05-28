package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Usuario;
import com.crossover.mobiliza.business.enums.Papel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null)
            throw new UsernameNotFoundException("Não encontrou usuário com username = " + username);

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getSenha(),
                getAuthoritiesByUsuario(usuario)
        );
    }

    public Collection<GrantedAuthority> getAuthoritiesByUsuario(Usuario usuario) {
        if (usuario == null)
            return Collections.emptyList();

        Collection<Papel> papeis = usuario.getPapeis();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Papel papel : papeis) {
            String name = papel.getAuthorityName();
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

}