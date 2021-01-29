package br.com.totvs.desafio.evento.api;

import br.com.totvs.desafio.evento.dominio.EventoTalhao;
import br.com.totvs.desafio.evento.dominio.TipoEvento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventoTalhaoDTO {

    private LocalDate dataDoEvento;
    private TipoEvento tipoEvento;
    private Float area;

    public EventoTalhaoDTO(EventoTalhao eventoTalhao) {
        this.dataDoEvento = eventoTalhao.getDataDoEvento();
        this.tipoEvento = eventoTalhao.getTipoEvento();
        this.area = eventoTalhao.getArea();
    }

    public EventoTalhaoDTO() {
    }

    public static List<EventoTalhaoDTO> retornaListaEventoTalhaoDTO(List<EventoTalhao> eventoTalhoes) {
        List<EventoTalhaoDTO> eventoTalhaoDTO = new ArrayList<>();
        for (EventoTalhao eventoTalhao:eventoTalhoes) {
            eventoTalhaoDTO.add(new EventoTalhaoDTO(eventoTalhao));
        }
        return eventoTalhaoDTO;
    }
}
