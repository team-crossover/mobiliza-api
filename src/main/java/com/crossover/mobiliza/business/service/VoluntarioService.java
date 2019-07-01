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

    public Voluntario saveNonNullFields(Voluntario newVoluntario) {
        if (newVoluntario.getId() == null) {
            return save(newVoluntario);
        }

        Voluntario voluntario = findById(newVoluntario.getId());
        if (voluntario == null) {
            return save(newVoluntario);
        }

        if (newVoluntario.getDataNascimento() != null)
            voluntario.setDataNascimento(newVoluntario.getDataNascimento());
        if (newVoluntario.getEmail() != null)
            voluntario.setEmail(newVoluntario.getEmail());
        if (newVoluntario.getEventosConfirmados() != null)
            voluntario.setEventosConfirmados(newVoluntario.getEventosConfirmados());
        if (newVoluntario.getNome() != null)
            voluntario.setNome(newVoluntario.getNome());
        if (newVoluntario.getTelefone() != null)
            voluntario.setTelefone(newVoluntario.getTelefone());
        if (newVoluntario.getImgPerfil() != null)
            voluntario.setImgPerfil(newVoluntario.getImgPerfil());

        return repository.save(voluntario);
    }

}
