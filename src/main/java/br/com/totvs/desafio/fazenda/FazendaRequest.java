package br.com.totvs.desafio.fazenda;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class FazendaRequest {

    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String nome;
    @CNPJ(message = "CNPJ INVALIDO")
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
