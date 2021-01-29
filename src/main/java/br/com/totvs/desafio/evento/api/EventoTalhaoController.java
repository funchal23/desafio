package br.com.totvs.desafio.evento.api;

import br.com.totvs.desafio.evento.dominio.EventoTalhao;
import br.com.totvs.desafio.evento.dominio.EventoTalhaoService;
import br.com.totvs.desafio.talhao.exception.TalhaoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventos")
public class EventoTalhaoController {

    private final EventoTalhaoService eventoTalhaoService;

    @PostMapping("/{idTalhao}")
    public List<EventoTalhaoDTO> buscaEventosDoTalhao(@PathVariable("idTalhao") UUID idTalhao) throws TalhaoNaoEncontradoException {
        List<EventoTalhao> eventoTalhoes = eventoTalhaoService.buscaEventosDoTalhao(idTalhao);
        return EventoTalhaoDTO.retornaListaEventoTalhaoDTO(eventoTalhoes);
    }
}
