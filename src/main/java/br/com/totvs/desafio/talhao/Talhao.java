package br.com.totvs.desafio.talhao;

import br.com.totvs.desafio.evento.EventoTalhao;
import br.com.totvs.desafio.evento.TipoEvento;
import br.com.totvs.desafio.fazenda.Fazenda;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(fetch = FetchType.LAZY)
    private List<EventoTalhao> eventosDoTalhao = new ArrayList<EventoTalhao>();


    public Talhao(TalhaoRequest talhaoRequest) {
        this.codigo = talhaoRequest.getCodigo();
        this.area = talhaoRequest.getArea();
        this.numeroSafra = talhaoRequest.getNumeroSafra();
        this.dataSafra = talhaoRequest.getDataSafra();
        this.estimativaSafra = talhaoRequest.getEstimativaSafra();
    }

    public Talhao() {
    }

    public void addEvento(EventoTalhao eventoTalhao) {
        this.eventosDoTalhao.add(eventoTalhao);
    }

    public float buscaAreaDisponivelPorEvento(TipoEvento tipoEvento) {
        float areaParaSerDevolvida = 0;
        for (EventoTalhao eventoTalhao:this.eventosDoTalhao) {
            if(eventoTalhao.getTipoEvento() == tipoEvento)
                areaParaSerDevolvida += eventoTalhao.getArea();
        }
        return this.area - areaParaSerDevolvida;
    }

    public boolean confereSeTemEventoDeEncerramento() {
        for (EventoTalhao eventoTalhao:this.eventosDoTalhao) {
            if(eventoTalhao.getTipoEvento() == TipoEvento.ENCERRAMENTO){
                return true;
            }
        }
        return false;
    }
}
