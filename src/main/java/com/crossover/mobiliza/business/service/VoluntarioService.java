package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Voluntario;
import com.crossover.mobiliza.data.repository.VoluntarioRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@Transactional
public class VoluntarioService extends EntityServiceBase<Voluntario, Long, VoluntarioRepository> {

    public Voluntario findByUserId(Long idOwner) {
        return repository.findByUserId(idOwner).orElse(null);
    }
    
}
