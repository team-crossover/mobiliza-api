package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Evento;
import com.crossover.mobiliza.data.repository.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@Transactional
public class EventoService extends EntityServiceBase<Evento, Long, EventoRepository> {

    public Collection<Evento> findAllByOngId(long ongId) {
        return repository.findAllByOngId(ongId);
    }

}
