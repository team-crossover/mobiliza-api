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

}
