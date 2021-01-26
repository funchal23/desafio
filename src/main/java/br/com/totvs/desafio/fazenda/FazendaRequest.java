package br.com.totvs.desafio.fazenda;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class FazendaRequest {

    private String nome;
    private String cnpj;
    private String cidade;
    private String estado;
    private String logradouro;

    public FazendaRequest() {
    }

    public FazendaRequest(String nome, String cnpj, String cidade, String estado, String logradouro) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.estado = estado;
        this.logradouro = logradouro;
    }
}
