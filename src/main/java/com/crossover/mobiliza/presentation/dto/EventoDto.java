package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Evento;
import com.crossover.mobiliza.business.entity.Voluntario;
import com.crossover.mobiliza.business.service.OngService;
import com.crossover.mobiliza.business.service.VoluntarioService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoDto {

    private Long id;

    @NotNull
    private Long idOng;

    @ManyToMany
    private List<Long> idsConfirmados = new ArrayList<>();

    @NotBlank
    @Size(max = 64)
    private String nome;

    @NotBlank
    @Size(max = 128)
    private String descricao;

    @NotBlank
    private String regiao;

    @Size(max = 96)
    private String endereco;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataRealizacao;

    public EventoDto(Evento evento) {
        this.id = evento.getId();
        this.idOng = evento.getOng().getId();
        this.idsConfirmados = evento.getConfirmados().stream().map(Voluntario::getId).collect(Collectors.toList());
        this.nome = evento.getNome();
        this.descricao = evento.getDescricao();
        this.regiao = evento.getRegiao();
        this.endereco = evento.getEndereco();
        this.dataRealizacao = evento.getDataRealizacao();
    }

    public Evento toEvento(OngService ongService, VoluntarioService voluntarioService) {
        return Evento.builder()
                .id(id)
                .ong(ongService.findById(idOng))
                .confirmados(idsConfirmados.stream().map(voluntarioService::findById).collect(Collectors.toList()))
                .nome(nome)
                .descricao(descricao)
                .regiao(regiao)
                .endereco(endereco)
                .dataRealizacao(dataRealizacao)
                .build();
    }

}
