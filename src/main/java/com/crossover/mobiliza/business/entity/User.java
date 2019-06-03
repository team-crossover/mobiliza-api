package com.crossover.mobiliza.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ToString(exclude = {"eventos"})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String googleId;

    private boolean lastUsedAsOng;

    @OneToOne
    private Ong ong;

    @OneToOne
    private Voluntario voluntario;

    // ---

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Evento> eventos;
}
