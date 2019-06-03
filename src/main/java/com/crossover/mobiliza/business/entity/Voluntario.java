package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voluntario")
@ToString(exclude = {"user"})
public class Voluntario {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    // ---

    @OneToOne(mappedBy = "voluntario")
    @JsonIgnore
    private User user;

}
