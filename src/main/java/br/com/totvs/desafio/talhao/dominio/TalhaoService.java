package br.com.totvs.desafio.talhao.dominio;

import br.com.totvs.desafio.fazenda.dominio.Fazenda;
import br.com.totvs.desafio.fazenda.exception.FazendaNaoEncontradaException;
import br.com.totvs.desafio.fazenda.dominio.FazendaRepository;
import br.com.totvs.desafio.comum.exception.ListaVaziaException;
import br.com.totvs.desafio.talhao.api.TalhaoRequest;
import br.com.totvs.desafio.talhao.api.TalhaoRequestPut;
import br.com.totvs.desafio.talhao.exception.TalhaoComCodigoExistenteNaFazendaException;
import br.com.totvs.desafio.talhao.exception.TalhaoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TalhaoService {

    private final TalhaoRepository talhaoRepository;

    private final FazendaRepository fazendaRepository;

    public Talhao insereTalhao(TalhaoRequest talhaoRequest, UUID idFazenda) throws Exception {
        Fazenda fazenda = fazendaRepository.findById(idFazenda).orElseThrow(() -> new FazendaNaoEncontradaException("Ao tentar inserir talhão a fazenda não foi encontrada"));
        Optional<Talhao> talhao = talhaoRepository.findByFazendaAndCodigo(fazenda, talhaoRequest.getCodigo());
        if(talhao.isEmpty()){
           Talhao novoTalhao = new Talhao(talhaoRequest);
           novoTalhao.setFazenda(fazenda);
           talhaoRepository.save(novoTalhao);
           return novoTalhao;
        } else {
            throw new TalhaoComCodigoExistenteNaFazendaException("Para a fazenda: " + fazenda.getNome() + " já existe o código de talhao: " + talhaoRequest.getCodigo());
        }
    }

    public void alteraAreaEstimativa(TalhaoRequestPut talhaoResquestPut, UUID id) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(id).orElseThrow(() -> new TalhaoNaoEncontradoException("Id do talhao informado não foi encontrado no banco de dados"));
        talhao.setArea(talhaoResquestPut.getArea());
        talhao.setEstimativaSafra(talhaoResquestPut.getEstimativaSafra());
    }

    public Talhao buscarPorId(UUID id) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(id).orElseThrow(() -> new TalhaoNaoEncontradoException("Ao tentar buscar talhao o id informado não foi encontrado no banco de dados"));
        return talhao;
    }

    public Page<Talhao> buscarPaginado(UUID idFazenda, Pageable page) throws Exception {
        Fazenda fazenda = fazendaRepository.findById(idFazenda).orElseThrow(() -> new FazendaNaoEncontradaException("Ao tentar buscar talhões a fazenda não foi encontrada"));
        Page<Talhao> talhoes = talhaoRepository.findByFazenda(fazenda, page);
        if(!talhoes.isEmpty()){
            return talhoes;
        } else {
            throw new ListaVaziaException("Nenhum talhao foi encontrado no banco de dados");
        }
    }

    public void excluiPorId(UUID id) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(id).orElseThrow(()-> new TalhaoNaoEncontradoException(" Ao tentar excluir talhao o id informado não foi encontrado no banco de dados"));
        talhaoRepository.delete(talhao);
    }
}
