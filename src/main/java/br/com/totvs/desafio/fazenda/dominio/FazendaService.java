package br.com.totvs.desafio.fazenda.dominio;

import br.com.totvs.desafio.comum.exception.ListaVaziaException;
import br.com.totvs.desafio.fazenda.exception.FazendaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FazendaService {

    private final FazendaRepository fazendaRepository;

    public void criarUmaFazenda(Fazenda fazenda) {
        fazendaRepository.save(fazenda);
    }

    public Fazenda alterarNome(String nome, UUID id) throws Exception {
        Optional<Fazenda> fazenda = fazendaRepository.findById(id);
        if (!fazenda.isEmpty()) {
            fazenda.get().setNome(nome);
            return fazenda.get();
        } else {
            throw new FazendaNaoEncontradaException("Falha ao alterar nome da fazenda " +
                                                    "Motivo: Não encontrado id da fazenda informado pelo cliente no banco de dados");
        }
    }

    public Fazenda buscarPorId(UUID id) throws Exception {
        Optional<Fazenda> fazenda = fazendaRepository.findById(id);
        if (!fazenda.isEmpty()) {
            return fazenda.get();
        } else {
            throw new FazendaNaoEncontradaException("Falha ao buscar fazenda com o id: " + id + " " +
                                                    "Motivo: Não encontrado id da fazenda informado pelo cliente no banco de dados");
        }
    }

    public Page<Fazenda> buscarPaginado(Pageable page) throws Exception {
        Page<Fazenda> fazendas = fazendaRepository.findAll(page);
        if(!fazendas.isEmpty()){
            return fazendas;
        } else {
            throw new ListaVaziaException("Nenhuma fazenda foi encontrada no banco de dados");
        }
    }

    public void excluirPorId(UUID id) throws FazendaNaoEncontradaException {
        Optional<Fazenda> fazenda = fazendaRepository.findById(id);
        if (!fazenda.isEmpty()) {
            fazendaRepository.delete(fazenda.get());
        } else {
            throw new FazendaNaoEncontradaException("Falha ao excluir fazenda " +
                                                    "Motivo: Não encontrado id da fazenda informado pelo cliente no banco de dados");
        }
    }
}
