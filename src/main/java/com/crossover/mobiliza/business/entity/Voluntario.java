package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voluntarios")
@ToString(exclude = {"user", "eventosConfirmados"})
public class Voluntario {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 128)
    private String nome;

    @Email
    @Size(max = 64)
    private String email;

    @Size(max = 24)
    private String telefone;

    private LocalDate dataNascimento;

    // ---

    @OneToOne(mappedBy = "ong")
    @JsonIgnore
    private User user;

    @ManyToMany(mappedBy = "confirmados")
    @JsonIgnore
    private Collection<Evento> eventosConfirmados = new ArrayList<>();

}
