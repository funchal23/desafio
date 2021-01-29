package br.com.totvs.desafio.evento.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class EventoTalhao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate dataDoEvento;
    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;
    private Float area;

    public EventoTalhao(UUID id, LocalDate dataDoEvento, TipoEvento tipoEvento, Float area) {
        this.id = id;
        this.dataDoEvento = dataDoEvento;
        this.tipoEvento = tipoEvento;
        this.area = area;
    }

    public EventoTalhao() {
    }
}
