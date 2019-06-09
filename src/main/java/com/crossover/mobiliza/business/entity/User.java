package com.crossover.mobiliza.business.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String googleId;

    @NotNull
    private boolean lastUsedAsOng;

    @OneToOne
    private Ong ong;

    @OneToOne
    private Voluntario voluntario;

}
