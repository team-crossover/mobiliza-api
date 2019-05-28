package com.crossover.mobiliza.business.entity;

import com.crossover.mobiliza.business.enums.Papel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @Size(min = 3, max = 32)
    private String username;

    @Size(min = 3, max = 16)
    private String senha;

    @Column(unique = true)
    @Size(min = 3, max = 128)
    private String email;

    @ManyToMany
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Collection<Papel> papeis;

}
