package br.com.totvs.desafio.testes_unitarios;

import br.com.totvs.desafio.evento.dominio.EventoTalhao;
import br.com.totvs.desafio.evento.dominio.EventoTalhaoRepository;
import br.com.totvs.desafio.evento.dominio.EventoTalhaoService;
import br.com.totvs.desafio.evento.dominio.TipoEvento;
import br.com.totvs.desafio.fazenda.dominio.Fazenda;
import br.com.totvs.desafio.fazenda.api.FazendaRequest;
import br.com.totvs.desafio.talhao.dominio.Talhao;
import br.com.totvs.desafio.talhao.exception.TalhaoNaoEncontradoException;
import br.com.totvs.desafio.talhao.dominio.TalhaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventoTalhaoTest {


    private EventoTalhaoService eventoTalhaoService;

    @Mock
    EventoTalhaoRepository eventoTalhaoRepository;
    @Mock
    TalhaoRepository talhaoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        eventoTalhaoService = new EventoTalhaoService(eventoTalhaoRepository, talhaoRepository);
    }

    @Test
    @DisplayName("Testa função addEvento")
    public void testaAddEvento() {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = eventos();

        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));

        Assertions.assertEquals(2, talhao.getEventosDoTalhao().size());
    }

    @Test
    @DisplayName("Testa função buscaAreaDisponivelPorEvento quando tem evento")
    public void testabuscaAreaDisponivelPorEventoQuandoTemEvento() {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = eventos();
        talhao.addEvento(eventos.get(0));
        float valor = talhao.buscaAreaDisponivelPorEvento(TipoEvento.PLANTIO);
        Assertions.assertEquals(50, valor);
    }

    @Test
    @DisplayName("Testa função buscaAreaDisponivelPorEvento quando não tem evento")
    public void testabuscaAreaDisponivelPorEventoQuandoNaoTemEvento() {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = eventos();
        talhao.addEvento(eventos.get(0));
        float valor = talhao.buscaAreaDisponivelPorEvento(TipoEvento.COLHEITA);
        Assertions.assertEquals(talhao.getArea(), valor);
    }

    @Test
    @DisplayName("Deve ter sucesso ao buscar eventos")
    public void buscaEventosComSucesso() throws TalhaoNaoEncontradoException {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = eventos();
        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));
        talhao.addEvento(eventos.get(2));
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        List<EventoTalhao> eventoTalhoes = eventoTalhaoService.buscaEventosDoTalhao(indice);
        Assertions.assertEquals(3, eventoTalhoes.size());
    }

    @Test
    @DisplayName("Deve ter falha ao buscar eventos")
    public void buscaEventosComFalha() {
        Talhao talhao = umTalhao();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.empty());
        Assertions.assertThrows(TalhaoNaoEncontradoException.class, () -> eventoTalhaoService.buscaEventosDoTalhao(indice));
    }

    @Test
    @DisplayName("Deve ter sucesso ao vereficar se tem eventos quando tem eventos")
    public void verificaEventosComSucessoComEventos() throws TalhaoNaoEncontradoException {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = eventos();
        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));
        talhao.addEvento(eventos.get(2));
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        boolean verificado = eventoTalhaoService.temEventoNoTalhao(indice);
        Assertions.assertEquals(true, verificado);
    }

    @Test
    @DisplayName("Deve ter sucesso ao vereficar se tem eventos quando não tem eventos")
    public void verificaEventosComSucessoQuandoNaoTemEventos() throws TalhaoNaoEncontradoException {
        Talhao talhao = umTalhao();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        boolean verificado = eventoTalhaoService.temEventoNoTalhao(indice);
        Assertions.assertEquals(false, verificado);
    }

    @Test
    @DisplayName("Deve ter falhar ao vereficar se tem eventos")
    public void verificaEventosComFalha() {
        Talhao talhao = umTalhao();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(Optional.empty());
        Assertions.assertThrows(TalhaoNaoEncontradoException.class, () -> eventoTalhaoService.temEventoNoTalhao(indice));
    }

    @Test
    @DisplayName("Deve ter sucesso ao verificar area de evento")
    public void verificaAreaEventosComSucesso() throws AreaDoEventoMaiorQueAreaDoTalhaoException {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = outrosEventos();
        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));
        talhao.addEvento(eventos.get(2));
        EventoTalhao eventoTalhao = umEventoComSucesso();
        eventoTalhaoService.verificaAreaDoEvento(eventoTalhao, talhao);
        Mockito.verify(eventoTalhaoRepository).save(eventoTalhao);
        Assertions.assertEquals(4, talhao.getEventosDoTalhao().size());
    }

    @Test
    @DisplayName("Deve ter falhar ao verificar area de evento")
    public void verificaAreaEventosComFalha() throws AreaDoEventoMaiorQueAreaDoTalhaoException {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = outrosEventos();
        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));
        talhao.addEvento(eventos.get(2));
        EventoTalhao eventoTalhao = umEventoComFalha();
        Assertions.assertThrows(AreaDoEventoMaiorQueAreaDoTalhaoException.class, () -> eventoTalhaoService.verificaAreaDoEvento(eventoTalhao, talhao));
    }

    @Test
    @DisplayName("Deve ter sucesso ao inserir evento")
    public void inserirEventoComSucesso() throws Exception {
        Talhao talhao = umTalhao();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        EventoTalhao eventoTalhao = umEventoComSucesso();
        eventoTalhaoService.insereEventoTalhao(eventoTalhao, indice);
        Assertions.assertEquals(1, talhao.getEventosDoTalhao().size());
        Assertions.assertEquals(30, talhao.getEventosDoTalhao().get(0).getArea());
    }

    @Test
    @DisplayName("Deve ter sucesso ao inserir evento encerramento")
    public void inserirEventoComSucessoEncerramento() throws Exception {
        Talhao talhao = umTalhao();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        EventoTalhao eventoTalhao = umEventoComSucessoEncerramento();
        eventoTalhaoService.insereEventoTalhao(eventoTalhao, indice);
        Assertions.assertEquals(1, talhao.getEventosDoTalhao().size());
        Assertions.assertEquals(60, talhao.getEventosDoTalhao().get(0).getArea());
        Assertions.assertEquals(TipoEvento.ENCERRAMENTO, talhao.getEventosDoTalhao().get(0).getTipoEvento());
    }

    @Test
    @DisplayName("Deve ter falha ao inserir evento encerramento")
    public void inserirEventoComFalhaEncerramento() throws Exception {
        Talhao talhao = umTalhao();
        List<EventoTalhao> eventos = outrosEventos();
        talhao.addEvento(eventos.get(0));
        talhao.addEvento(eventos.get(1));
        talhao.addEvento(eventos.get(2));
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(talhaoRepository.findById(indice)).thenReturn(java.util.Optional.of(talhao));
        EventoTalhao eventoTalhao = umEventoComFalhaEncerramento();
        Assertions.assertThrows(EventoDeEncerramentoJaExistenteException.class, () -> eventoTalhaoService.insereEventoTalhao(eventoTalhao, indice));
    }


    private Talhao umTalhao() {
        Talhao talhao = new Talhao();
        talhao.setCodigo("LEBRON23");
        talhao.setArea(100f);
        talhao.setNumeroSafra(20);
        talhao.setDataSafra("2020");
        talhao.setEstimativaSafra(12000f);
        talhao.setFazenda(umaFazenda());
        return talhao;
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

    private List<EventoTalhao> eventos() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(50f);
        eventoTalhao.setTipoEvento(TipoEvento.PLANTIO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        EventoTalhao eventoTalhao1 = new EventoTalhao();
        eventoTalhao1.setArea(50f);
        eventoTalhao1.setTipoEvento(TipoEvento.ENCERRAMENTO);
        eventoTalhao1.setDataDoEvento(LocalDate.now());
        EventoTalhao eventoTalhao2 = new EventoTalhao();
        eventoTalhao2.setArea(50f);
        eventoTalhao2.setTipoEvento(TipoEvento.ENCERRAMENTO);
        eventoTalhao2.setDataDoEvento(LocalDate.now());
        List<EventoTalhao> eventoTalhoes = new ArrayList<>();
        eventoTalhoes.add(eventoTalhao);
        eventoTalhoes.add(eventoTalhao1);
        eventoTalhoes.add(eventoTalhao2);
        return eventoTalhoes;
    }

    private List<EventoTalhao> outrosEventos() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(50f);
        eventoTalhao.setTipoEvento(TipoEvento.PLANTIO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        EventoTalhao eventoTalhao1 = new EventoTalhao();
        eventoTalhao1.setArea(50f);
        eventoTalhao1.setTipoEvento(TipoEvento.ENCERRAMENTO);
        eventoTalhao1.setDataDoEvento(LocalDate.now());
        EventoTalhao eventoTalhao2 = new EventoTalhao();
        eventoTalhao2.setArea(50f);
        eventoTalhao2.setTipoEvento(TipoEvento.COLHEITA);
        eventoTalhao2.setDataDoEvento(LocalDate.now());
        List<EventoTalhao> eventoTalhoes = new ArrayList<>();
        eventoTalhoes.add(eventoTalhao);
        eventoTalhoes.add(eventoTalhao1);
        eventoTalhoes.add(eventoTalhao2);
        return eventoTalhoes;
    }

    private EventoTalhao umEventoComSucesso() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(30f);
        eventoTalhao.setTipoEvento(TipoEvento.PLANTIO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        return eventoTalhao;
    }

    private EventoTalhao umEventoComFalha() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(60f);
        eventoTalhao.setTipoEvento(TipoEvento.PLANTIO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        return eventoTalhao;
    }

    private EventoTalhao umEventoComSucessoEncerramento() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(60f);
        eventoTalhao.setTipoEvento(TipoEvento.ENCERRAMENTO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        return eventoTalhao;
    }

    private EventoTalhao umEventoComFalhaEncerramento() {
        EventoTalhao eventoTalhao = new EventoTalhao();
        eventoTalhao.setArea(30f);
        eventoTalhao.setTipoEvento(TipoEvento.ENCERRAMENTO);
        eventoTalhao.setDataDoEvento(LocalDate.now());
        return eventoTalhao;
    }

}
