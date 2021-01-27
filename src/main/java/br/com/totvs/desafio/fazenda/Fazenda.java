package br.com.totvs.desafio.fazenda;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(unique = true)
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
