package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ongs")
@ToString(exclude = {"user", "eventos"})
public class Ong {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String nome;

    @NotBlank
    @Size(max = 512)
    private String descricao;

    @NotBlank
    @Size(max = 24)
    private String categoria;

    @NotBlank
    private String regiao;

    @Size(max = 96)
    private String endereco;

    @Size(max = 24)
    private String telefone;

    @Email
    @Size(max = 64)
    private String email;

//    TODO: Adicionar suporte a imagens
//    private Imagem imgPerfil;

    // ---

    @OneToOne(mappedBy = "ong")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "ong")
    @JsonIgnore
    private Collection<Evento> eventos = new ArrayList<>();

}
