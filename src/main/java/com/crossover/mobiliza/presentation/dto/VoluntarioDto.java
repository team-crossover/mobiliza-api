package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Voluntario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioDto {

    private Long id;

    @NotBlank
    @Size(max = 128)
    private String nome;

    @Email
    @Size(max = 64)
    private String email;

    @Size(max = 24)
    private String telefone;

    @JsonFormat(pattern = "dd/MM/yyyy")
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
