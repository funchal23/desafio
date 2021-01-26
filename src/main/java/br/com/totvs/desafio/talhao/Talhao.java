package br.com.totvs.desafio.talhao;

import br.com.totvs.desafio.fazenda.Fazenda;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Talhao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String codigo;
    private Float area;
    private Integer numeroSafra;
    private String dataSafra;
    private Float estimativaSafra;
    @ManyToOne(fetch = FetchType.LAZY)
    private Fazenda fazenda;

    public Talhao(TalhaoRequest talhaoRequest) {
        this.codigo = talhaoRequest.getCodigo();
        this.area = talhaoRequest.getArea();
        this.numeroSafra = talhaoRequest.getNumeroSafra();
        this.dataSafra = talhaoRequest.getDataSafra();
        this.estimativaSafra = talhaoRequest.getEstimativaSafra();
    }

    public Talhao() {
    }
}
