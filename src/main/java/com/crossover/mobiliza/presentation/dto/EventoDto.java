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

    private Long idOng;

    private List<Long> idsConfirmados = new ArrayList<>();

    private String nome;

    private String descricao;

    private String regiao;

    private String endereco;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataRealizacao;

    private String img;

    private String categoria;

    public EventoDto(Evento evento) {
        this.id = evento.getId();
        this.idOng = evento.getOng().getId();
        this.idsConfirmados = evento.getConfirmados().stream().map(Voluntario::getId).collect(Collectors.toList());
        this.nome = evento.getNome();
        this.descricao = evento.getDescricao();
        this.regiao = evento.getRegiao();
        this.endereco = evento.getEndereco();
        this.dataRealizacao = evento.getDataRealizacao();
        this.img = evento.getImg();
        this.categoria = evento.getOng().getCategoria();
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
                .img(img)
                .build();
    }

}
