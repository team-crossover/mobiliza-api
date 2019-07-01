package com.crossover.mobiliza.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private Ong ong;

    @ManyToMany
    private Collection<Voluntario> confirmados = new ArrayList<>();

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
    private LocalDateTime dataRealizacao;

    @Basic(fetch = FetchType.LAZY)
    @Size(max = 10485760)
    private String img;

}
