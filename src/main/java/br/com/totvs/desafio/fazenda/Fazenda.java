package br.com.totvs.desafio.fazenda;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Fazenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String nome;
    private String cnpj;
    @Embedded
    private Endereco endereco;

    public Fazenda() {
    }

    public Fazenda(FazendaRequest fazendaRequest) {
        this.nome = fazendaRequest.getNome();
        this.cnpj = fazendaRequest.getCnpj();
        this.endereco = new Endereco(fazendaRequest.getCidade(), fazendaRequest.getEstado(), fazendaRequest.getLogradouro());
    }
}
