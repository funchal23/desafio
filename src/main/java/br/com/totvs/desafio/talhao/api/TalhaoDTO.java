package br.com.totvs.desafio.talhao.api;

import br.com.totvs.desafio.talhao.dominio.Talhao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class TalhaoDTO {

    private String codigo;
    private Float area;
    private Integer numeroSafra;
    private String dataSafra;
    private String estimativaSafra;

    public static Page<TalhaoDTO> converterTalhaoEmTalhaoDTO(Page<Talhao> talhoes) {
        return talhoes.map(TalhaoDTO :: new);
    }
    public TalhaoDTO(Talhao talhao) {
        this.codigo = talhao.getCodigo();
        this.area = talhao.getArea();
        this.numeroSafra = talhao.getNumeroSafra();
        this.dataSafra = talhao.getDataSafra();
        Float calcSafra = talhao.getEstimativaSafra() / 60;
        this.estimativaSafra = (calcSafra.toString() + " SACAS");
    }
}
