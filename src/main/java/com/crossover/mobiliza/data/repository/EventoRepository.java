package com.crossover.mobiliza.data.repository;

import com.crossover.mobiliza.business.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Collection<Evento> findAllByOngId(long ongId);

}