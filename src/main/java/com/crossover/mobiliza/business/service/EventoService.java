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

    public Evento saveNonNullFields(Evento newEvento) {
        if (newEvento.getId() == null) {
            return save(newEvento);
        }

        Evento evento = findById(newEvento.getId());
        if (evento == null) {
            return save(newEvento);
        }

        if (newEvento.getRegiao() != null)
            evento.setRegiao(newEvento.getRegiao());
        if (newEvento.getDataRealizacao() != null)
            evento.setDataRealizacao(newEvento.getDataRealizacao());
        if (newEvento.getConfirmados() != null)
            evento.setConfirmados(newEvento.getConfirmados());
        if (newEvento.getDescricao() != null)
            evento.setDescricao(newEvento.getDescricao());
        if (newEvento.getEndereco() != null)
            evento.setEndereco(newEvento.getEndereco());
        if (newEvento.getNome() != null)
            evento.setNome(newEvento.getNome());
        if (newEvento.getImg() != null)
            evento.setImg(newEvento.getImg());

        return repository.save(evento);
    }

}
