package com.crossover.mobiliza.data.config;

import com.crossover.mobiliza.business.entity.Evento;
import com.crossover.mobiliza.business.entity.Ong;
import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.entity.Voluntario;
import com.crossover.mobiliza.business.service.EventoService;
import com.crossover.mobiliza.business.service.OngService;
import com.crossover.mobiliza.business.service.UserService;
import com.crossover.mobiliza.business.service.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Adds initial data to the database when the system is initialized.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private OngService ongService;

    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!alreadySetup) {
            createInitialEvents();
            alreadySetup = true;
        }
    }


    private void createInitialEvents() {
        User usuario = new User();
        usuario.setGoogleId("123");
        usuario = userService.save(usuario);

        Voluntario voluntario = new Voluntario();
        voluntario.setNome("Bette Daves");
        voluntario.setDataNascimento(LocalDate.of(1920, 12, 30));
        voluntario.setEmail("bettinha@holywoo.com");
        voluntario.setTelefone("12345-6789");
        voluntario.setUser(usuario);
        voluntario = voluntarioService.save(voluntario);

        usuario.setVoluntario(voluntario);
        usuario = userService.save(usuario);

        Ong ong = new Ong();
        ong.setNome("Clube do Gato");
        ong.setDescricao("Cuidamos de gatos perdidos, sem vida, sem rumo, possuídos etc. Não cuidamos de cachorros pois somos malignos.");
        ong.setUser(usuario);
        ong.setCategoria("Animais");
        ong.setEmail("gatos@larissa.com");
        ong.setEndereco("Rua C-75, Lote 12, Alameda dos Gatos, Setor Sul");
        ong.setRegiao("Sul");
        ong.setTelefone("1687468");
        ong = ongService.save(ong);

        Evento evento = new Evento();
        evento.setDataRealizacao(LocalDateTime.of(2019, 7, 10, 14, 30));
        evento.setNome("Feira de adoção de gatos");
        evento.setDescricao("Vamos adotar gatos!");
        evento.setEndereco("Rua C-75, Lote 12, Alameda dos Gatos, Setor Sul");
        evento.setRegiao("Sul");
        evento.setOng(ong);
        evento = eventoService.save(evento);

        Evento evento2 = new Evento();
        evento2.setDataRealizacao(LocalDateTime.of(2019, 8, 12, 13, 30));
        evento2.setNome("Castração de gatos");
        evento2.setDescricao("Vamos castrar gatos de graça!");
        evento2.setEndereco("Rua C-75, Lote 12, Alameda dos Gatos, Setor Sul");
        evento2.setRegiao("Sul");
        evento2.setOng(ong);
        evento2 = eventoService.save(evento2);

        Evento evento3 = new Evento();
        evento3.setDataRealizacao(LocalDateTime.of(2019, 6, 5, 18, 0));
        evento3.setNome("Beijar gatos");
        evento3.setDescricao("Vamos beijar uns gatos na pracinha!");
        evento3.setEndereco("Rua C-75, Lote 12, Alameda dos Gatos, Setor Sul");
        evento3.setRegiao("Sul");
        evento3.setOng(ong);
        evento3 = eventoService.save(evento3);
    }

}
