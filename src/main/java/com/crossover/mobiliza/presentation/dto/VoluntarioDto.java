package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioDto {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    public VoluntarioDto(Voluntario voluntario) {
        this.id = voluntario.getId();
        this.nome = voluntario.getNome();
        this.telefone = voluntario.getTelefone();
        this.email = voluntario.getEmail();
        this.dataNascimento = voluntario.getDataNascimento();
    }

    public Voluntario toVoluntario() {
        return Voluntario.builder()
                .id(id)
                .nome(nome)
                .telefone(telefone)
                .email(email)
                .dataNascimento(dataNascimento)
                .build();
    }

}
