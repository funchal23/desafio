package br.com.totvs.desafio.fazenda.api;

import br.com.totvs.desafio.fazenda.dominio.Fazenda;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class FazendaDTO {
    private String nome;
    private String cnpj;
    private String cidade;
    private String estado;
    private String logradouro;

    public static Page<FazendaDTO> converterFazendasEmFazendasDTO(Page<Fazenda> fazendas) {
        return fazendas.map(FazendaDTO :: new);
    }
    public FazendaDTO(Fazenda fazenda) {
        this.nome = fazenda.getNome();
        this.cnpj = fazenda.getCnpj();
        this.cidade = fazenda.getEndereco().getCidade();
        this.estado = fazenda.getEndereco().getEstado();
        this.logradouro = fazenda.getEndereco().getLogradouro();
    }
}
