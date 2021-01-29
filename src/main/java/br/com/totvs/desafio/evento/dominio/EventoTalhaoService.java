package br.com.totvs.desafio.evento.dominio;


import br.com.totvs.desafio.evento.exception.AreaDoEventoMaiorQueAreaDoTalhaoException;
import br.com.totvs.desafio.evento.exception.EventoDeEncerramentoJaExistenteException;
import br.com.totvs.desafio.talhao.dominio.Talhao;
import br.com.totvs.desafio.talhao.dominio.TalhaoRepository;
import br.com.totvs.desafio.talhao.exception.TalhaoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventoTalhaoService {

    private final EventoTalhaoRepository eventoTalhaoRepository;
    private final TalhaoRepository talhaoRepository;


    public List<EventoTalhao> buscaEventosDoTalhao(UUID idTalhao) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(idTalhao).orElseThrow(() -> new TalhaoNaoEncontradoException("Ao buscar eventos o talhao não foi encontrado no banco de dados"));
        List<EventoTalhao> eventosDoTalhao = talhao.getEventosDoTalhao();
        return eventosDoTalhao;
    }

    public void insereEventoTalhao(EventoTalhao eventoTalhao, UUID idTalhao) throws TalhaoNaoEncontradoException, EventoDeEncerramentoJaExistenteException, AreaDoEventoMaiorQueAreaDoTalhaoException {
        Talhao talhao = talhaoRepository.findById(idTalhao).orElseThrow(() -> new TalhaoNaoEncontradoException("Ao inserir evento o talhao não foi encontrado no banco de dados"));

        if (eventoTalhao.getTipoEvento() == TipoEvento.ENCERRAMENTO) {
            boolean contains = talhao.confereSeTemEventoDeEncerramento();
            if (contains) {
                throw new EventoDeEncerramentoJaExistenteException();
            } else {
                verificaAreaDoEvento(eventoTalhao, talhao);
            }
        } else {
            verificaAreaDoEvento(eventoTalhao, talhao);
        }
    }

    public boolean temEventoNoTalhao(UUID idTalhao) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(idTalhao).orElseThrow(() -> new TalhaoNaoEncontradoException("O talhao não foi encontrado no banco de dados"));
        if (!talhao.getEventosDoTalhao().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void verificaAreaDoEvento(EventoTalhao eventoTalhao, Talhao talhao) throws AreaDoEventoMaiorQueAreaDoTalhaoException {
        Float areaDisponivel = talhao.buscaAreaDisponivelPorEvento(eventoTalhao.getTipoEvento());
        if (eventoTalhao.getArea() > areaDisponivel) {
            throw new AreaDoEventoMaiorQueAreaDoTalhaoException("A area do talhao não comporta a area do evento desejado!" +
                    "Desejado: " + eventoTalhao.getArea() + " sendo que diponivel para " + eventoTalhao.getTipoEvento() + " no talhão informado é de: " + areaDisponivel);
        } else {
            talhao.addEvento(eventoTalhao);
            eventoTalhaoRepository.save(eventoTalhao);
        }
    }
}
