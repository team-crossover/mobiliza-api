package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ong")
@ToString(exclude = {"user"})
public class Ong {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 1, max = 32)
    private String nome;

    @Size(min = 1, max = 512)
    private String descricao;

    @Size(min = 1, max = 24)
    private String categoria;

    @Size(min = 1, max = 16)
    private String telefone;

    @Size(min = 1, max = 48)
    private String email;

    @Size(min = 1, max = 96)
    private String endereco;

    private Double latitude;

    private Double longitude;

    // ---

    @OneToOne(mappedBy = "ong")
    @JsonIgnore
    private User user;

}
