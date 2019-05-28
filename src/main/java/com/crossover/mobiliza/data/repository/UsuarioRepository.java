package com.crossover.mobiliza.data.repository;

import com.crossover.mobiliza.business.entity.Usuario;
import com.crossover.mobiliza.business.enums.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    long countByPapeisContains(Collection<Papel> papel);

}