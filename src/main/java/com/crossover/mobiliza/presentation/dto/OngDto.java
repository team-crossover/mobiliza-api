package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Ong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OngDto {

    private Long id;

    private String nome;

    private String descricao;

    private String categoria;

    private String regiao;

    private String endereco;

    private String telefone;

    private String email;

    private String imgPerfil;

    public OngDto(Ong ong) {
        this.id = ong.getId();
        this.nome = ong.getNome();
        this.descricao = ong.getDescricao();
        this.categoria = ong.getCategoria();
        this.telefone = ong.getTelefone();
        this.email = ong.getEmail();
        this.endereco = ong.getEndereco();
        this.regiao = ong.getRegiao();
        this.imgPerfil = ong.getImgPerfil();
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
                .imgPerfil(imgPerfil)
                .build();
    }

}
