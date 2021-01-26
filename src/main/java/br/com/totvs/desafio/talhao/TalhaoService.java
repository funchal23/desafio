package br.com.totvs.desafio.talhao;

import br.com.totvs.desafio.fazenda.Fazenda;
import br.com.totvs.desafio.fazenda.FazendaNaoEncontradaException;
import br.com.totvs.desafio.fazenda.FazendaRepository;
import br.com.totvs.desafio.comum.ListaVaziaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
           Talhao talhaoRetornado = new Talhao(talhaoRequest);
           talhaoRepository.save(talhaoRetornado);
           return talhaoRetornado;
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

    public Page<Fazenda> buscarPaginado(UUID idFazenda, Pageable page) throws Exception {
        Fazenda fazenda = fazendaRepository.findById(idFazenda).orElseThrow(() -> new FazendaNaoEncontradaException("Ao tentar buscar talhões a fazenda não foi encontrada"));
        Page<Fazenda> fazendas = fazendaRepository.findAllByFazenda(fazenda, page);
        if(!fazendas.isEmpty()){
            return fazendas;
        } else {
            throw new ListaVaziaException("Nenhum talhao foi encontrado no banco de dados");
        }
    }

    public void excluiPorId(UUID id) throws TalhaoNaoEncontradoException {
        Talhao talhao = talhaoRepository.findById(id).orElseThrow(()-> new TalhaoNaoEncontradoException(" Ao tentar excluir talhao o id informado não foi encontrado no banco de dados"));
        talhaoRepository.delete(talhao);
    }
}
