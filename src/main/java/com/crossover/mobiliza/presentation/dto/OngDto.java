package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.Ong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OngDto {

    private Long id;

    @Size(min = 1, max = 32)
    private String nome;

    @Size(min = 1, max = 512)
    private String descricao;

    @Size(min = 1, max = 24)
    private String categoria;

    @Size(min = 1, max = 16)
    private String telefone;

    @Size(min = 1, max = 48)
    private String email;

    @Size(min = 1, max = 96)
    private String endereco;

    private Double latitude;

    private Double longitude;

    public OngDto(Ong ong) {
        this.id = ong.getId();
        this.nome = ong.getNome();
        this.descricao = ong.getDescricao();
        this.categoria = ong.getCategoria();
        this.telefone = ong.getTelefone();
        this.email = ong.getEmail();
        this.endereco = ong.getEndereco();
        this.latitude = ong.getLatitude();
        this.longitude = ong.getLongitude();
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
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
