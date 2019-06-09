package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Ong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OngDto {

    private Long id;

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

    public OngDto(Ong ong) {
        this.id = ong.getId();
        this.nome = ong.getNome();
        this.descricao = ong.getDescricao();
        this.categoria = ong.getCategoria();
        this.telefone = ong.getTelefone();
        this.email = ong.getEmail();
        this.endereco = ong.getEndereco();
        this.regiao = ong.getRegiao();
    }

    public Ong toOng() {
        return Ong.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .categoria(categoria)
                .telefone(telefone)
                .email(email)
                .endereco(endereco)
                .regiao(regiao)
                .build();
    }

}
