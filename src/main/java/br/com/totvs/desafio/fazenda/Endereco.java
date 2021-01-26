package br.com.totvs.desafio.fazenda;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Endereco {

    private String cidade;
    private String estado;
    private String logradouro;

    public Endereco() {
    }

    public Endereco(String cidade, String estado, String logradouro) {
        this.cidade = cidade;
        this.estado = estado;
        this.logradouro = logradouro;
    }
}
