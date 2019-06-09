package com.crossover.mobiliza.data.repository;

import com.crossover.mobiliza.business.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {

    Optional<Ong> findByUserId(Long idOwner);

    Collection<Ong> findAllByCategoria(String categoria);

}