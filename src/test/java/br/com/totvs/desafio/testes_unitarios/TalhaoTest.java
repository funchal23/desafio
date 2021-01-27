package br.com.totvs.desafio.testes_unitarios;

import br.com.totvs.desafio.fazenda.Fazenda;
import br.com.totvs.desafio.fazenda.FazendaNaoEncontradaException;
import br.com.totvs.desafio.fazenda.FazendaRepository;
import br.com.totvs.desafio.fazenda.FazendaRequest;
import br.com.totvs.desafio.talhao.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

public class TalhaoTest {

    private TalhaoService talhaoService;

    @Mock
    TalhaoRepository talhaoRepository;

    @Mock
    FazendaRepository fazendaRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.talhaoService = new TalhaoService(talhaoRepository, fazendaRepository);
    }

    @Test
    @DisplayName("Deve falhar ao tentar criar talhao por já existir o codigo na fazenda")
    public void criarTalhaoComFalha() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Fazenda fazenda = umaFazenda();
        TalhaoRequest talhaoRequest = umTalhaoRequest();
        Talhao talhao = umTalhao();
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(fazenda));
        Mockito.when(talhaoRepository.findByFazendaAndCodigo(fazenda, talhaoRequest.getCodigo())).thenReturn(java.util.Optional.of(talhao));
        Assertions.assertThrows(TalhaoComCodigoExistenteNaFazendaException.class, ()->  talhaoService.insereTalhao(talhaoRequest, indice));
    }
    @Test
    @DisplayName("Deve falhar ao tentar criar talhao por não existir a fazenda")
    public void criarTalhaoComFalhaFazenda() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        UUID indiceNaoExistente = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130001");
        Fazenda fazenda = umaFazenda();
        TalhaoRequest talhaoRequest = umTalhaoRequest();
        Talhao talhao = umTalhao();
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(fazenda));
        Mockito.when(talhaoRepository.findByFazendaAndCodigo(fazenda, talhaoRequest.getCodigo())).thenReturn(java.util.Optional.of(talhao));
        Assertions.assertThrows(FazendaNaoEncontradaException.class, ()->  talhaoService.insereTalhao(talhaoRequest, indiceNaoExistente));
    }

    @Test
    @DisplayName("Deve obter sucesso ao tentar criar talhao")
    public void criarTalhaoComSucesso() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Fazenda fazenda = umaFazenda();
        TalhaoRequest talhaoRequest = umTalhaoRequest();
        Talhao talhao = umTalhao();
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(fazenda));
        Mockito.when(talhaoRepository.findByFazendaAndCodigo(fazenda, talhaoRequest.getCodigo())).thenReturn(Optional.empty());
        talhaoService.insereTalhao(talhaoRequest, indice);
        Talhao novoTalhao = new Talhao(talhaoRequest);
        novoTalhao.setFazenda(fazenda);
        Mockito.verify(talhaoRepository).save(novoTalhao);
    }

    @Test
    @DisplayName("Deve obter sucesso ao tentar mudar area e estimativa")
    public void alterarAreaEstimativaComSucesso() throws Exception {
        TalhaoRequestPut talhaoRequestPut = umTalhaoPut();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.of(talhao));

        talhaoService.alteraAreaEstimativa(talhaoRequestPut, indice);
        Assertions.assertEquals(40, talhao.getArea());
        Assertions.assertEquals(40, talhao.getEstimativaSafra());
    }

    @Test
    @DisplayName("Deve obter falha ao tentar mudar area e estimativa")
    public void alterarAreaEstimativaComFalha() throws Exception {
        TalhaoRequestPut talhaoRequestPut = umTalhaoPut();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.empty());
        Assertions.assertThrows(TalhaoNaoEncontradoException.class, ()-> talhaoService.alteraAreaEstimativa(talhaoRequestPut, indice));
    }

    @Test
    @DisplayName("Deve ter sucesso ao buscar talhao por id")
    public void buscaPorIdComSucesso() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.of(talhao));

        Talhao talhaoBuscado = talhaoService.buscarPorId(indice);
        Assertions.assertEquals(talhao.getCodigo(), talhaoBuscado.getCodigo());

    }

    @Test
    @DisplayName("Deve ter falhar ao buscar talhao por id")
    public void buscaPorIdComFalha() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.empty());
        Assertions.assertThrows(TalhaoNaoEncontradoException.class, ()-> talhaoService.buscarPorId(indice));
    }

    @Test
    @DisplayName("Deve ter sucesso ao excluir talhao por id")
    public void deletaPorIdComSucesso() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.of(talhao));
        talhaoService.excluiPorId(indice);
        Mockito.verify(talhaoRepository).delete(talhao);
    }

    @Test
    @DisplayName("Deve ter falha ao excluir talhao por id")
    public void deletaPorIdComFalha() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Talhao talhao = umTalhao();
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.empty());
        Assertions.assertThrows(TalhaoNaoEncontradoException.class, ()-> talhaoService.excluiPorId(indice));
    }

    private Fazenda umaFazenda() {
        FazendaRequest fazendaRequest = new FazendaRequest();
        fazendaRequest.setNome("Fazenda Funchal");
        fazendaRequest.setCnpj("47.587.220/0001-27");
        fazendaRequest.setCidade("Assis");
        fazendaRequest.setEstado("São Paulo");
        fazendaRequest.setLogradouro("Rua Silva");
        Fazenda fazenda = new Fazenda(fazendaRequest);
        return fazenda;
    }

    private TalhaoRequest umTalhaoRequest() {
        TalhaoRequest talhaoRequest = new TalhaoRequest();
        talhaoRequest.setCodigo("LEBRON23");
        talhaoRequest.setArea(500f);
        talhaoRequest.setNumeroSafra(4);
        talhaoRequest.setDataSafra("2020/2021");
        talhaoRequest.setEstimativaSafra(100f);
        return talhaoRequest;
    }

    private Talhao umTalhao(){
        return new Talhao(umTalhaoRequest());
    }

    private TalhaoRequestPut umTalhaoPut(){
        TalhaoRequestPut talhaoRequestPut = new TalhaoRequestPut();
        talhaoRequestPut.setArea(40f);
        talhaoRequestPut.setEstimativaSafra(40f);
        return talhaoRequestPut;
    }


}
