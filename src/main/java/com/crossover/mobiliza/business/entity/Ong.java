package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    private String nome;

    private String descricao;

    // ---

    @OneToOne(mappedBy = "ong")
    @JsonIgnore
    private User user;

}
