package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Ong;
import com.crossover.mobiliza.data.repository.OngRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@Transactional
public class OngService extends EntityServiceBase<Ong, Long, OngRepository> {

    public Ong findByUserId(Long idOwner) {
        return repository.findByUserId(idOwner).orElse(null);
    }

    public Collection<Ong> findAllByCategoria(String categoria) {
        return repository.findAllByCategoria(categoria);
    }

    public Ong saveNonNullFields(Ong newOng) {
        if (newOng.getId() == null) {
            return save(newOng);
        }

        Ong ong = findById(newOng.getId());
        if (ong == null) {
            return save(newOng);
        }

        if (newOng.getRegiao() != null)
            ong.setRegiao(newOng.getRegiao());
        if (newOng.getEventos() != null)
            ong.setEventos(newOng.getEventos());
        if (newOng.getCategoria() != null)
            ong.setCategoria(newOng.getCategoria());
        if (newOng.getDescricao() != null)
            ong.setDescricao(newOng.getDescricao());
        if (newOng.getEmail() != null)
            ong.setEmail(newOng.getEmail());
        if (newOng.getEndereco() != null)
            ong.setEndereco(newOng.getEndereco());
        if (newOng.getNome() != null)
            ong.setNome(newOng.getNome());
        if (newOng.getTelefone() != null)
            ong.setTelefone(newOng.getTelefone());
        if (newOng.getImgPerfil() != null)
            ong.setImgPerfil(newOng.getImgPerfil());

        return repository.save(ong);
    }
}
