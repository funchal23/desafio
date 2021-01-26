package br.com.totvs.desafio.testes_unitarios;

import br.com.totvs.desafio.fazenda.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FazendaTest {

    private FazendaService service;

    @Mock
    private FazendaRepository fazendaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.service = new FazendaService(fazendaRepository);
    }

    @Test
    @DisplayName("Deve criar uma  entidade fazenda")
    public void isFazendaEntity(){
        FazendaRequest fazendaRequest = new FazendaRequest();
        fazendaRequest.setNome("Fazenda Funchal");
        fazendaRequest.setCnpj("47.587.220/0001-27");
        fazendaRequest.setCidade("Assis");
        fazendaRequest.setEstado("São Paulo");
        fazendaRequest.setLogradouro("Rua Silva");
        Fazenda fazenda = new Fazenda(fazendaRequest);
        Assertions.assertEquals("Fazenda Funchal", fazenda.getNome());
        Assertions.assertEquals("47.587.220/0001-27", fazenda.getCnpj());
        Assertions.assertEquals("Assis", fazenda.getEndereco().getCidade());
        Assertions.assertEquals("São Paulo", fazenda.getEndereco().getEstado());
        Assertions.assertEquals("Rua Silva", fazenda.getEndereco().getLogradouro());
    }

    @Test
    @DisplayName("Deve falhar em alterar o nome da fazenda")
    public void alterarNomeFazendaComFalha() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(umaFazenda()));
        Assertions.assertThrows(FazendaNaoEncontradaException.class, () -> {
            service.alterarNome("Fazenda Silva", UUID.fromString("9c23e72a-5bfe-11eb-ae93-0242ac130002"));
        });
    }

    @Test
    @DisplayName("Deve obter sucesso ao alterar o nome da fazenda")
    public void alteraNomeFazendaComSucesso() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(umaFazenda()));
        Fazenda fazenda = service.alterarNome("Fazenda Silva", UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002"));
        Assertions.assertEquals("Fazenda Silva", fazenda.getNome());
        Assertions.assertEquals("47.587.220/0001-27", fazenda.getCnpj());
    }

    @Test
    @DisplayName("Deve falhar em buscar fazenda por id")
    public void buscaFazendaPorIdComFalha() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(umaFazenda()));
        Assertions.assertThrows(FazendaNaoEncontradaException.class, () -> {
            service.buscarPorId(UUID.fromString("9c23e72a-5bfe-11eb-ae93-0242ac130002"));
        });
    }

    @Test
    @DisplayName("Deve obter sucesso ao buscar fazenda por id")
    public void buscaFazendaPorIdComSucesso() throws Exception {
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(umaFazenda()));
        Fazenda fazenda = service.buscarPorId(UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002"));
        Assertions.assertEquals(fazenda.getNome(), umaFazenda().getNome());
        Assertions.assertEquals(fazenda.getCnpj(), umaFazenda().getCnpj());
        Assertions.assertEquals(fazenda.getEndereco().getCidade(), umaFazenda().getEndereco().getCidade());
        Assertions.assertEquals(fazenda.getEndereco().getEstado(), umaFazenda().getEndereco().getEstado());
        Assertions.assertEquals(fazenda.getEndereco().getLogradouro(), umaFazenda().getEndereco().getLogradouro());
    }

    @Test
    @DisplayName("Verifica se o metodo delete foi chamado")
    public void verificaDelete() throws FazendaNaoEncontradaException {
        Fazenda fazenda = umaFazenda();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(fazenda));
        service.excluirPorId(indice);
        Mockito.verify(fazendaRepository).delete(fazenda);
    }

    @Test
    @DisplayName("Deve falhar ao chamar o metodo delete")
    public void falhaDelete() throws FazendaNaoEncontradaException {
        Fazenda fazenda = umaFazenda();
        UUID indice = UUID.fromString("220d07b6-5bfc-11eb-ae93-0242ac130002");
        Mockito.when(fazendaRepository.findById(indice)).thenReturn(java.util.Optional.of(fazenda));
        Assertions.assertThrows(FazendaNaoEncontradaException.class, () -> service.excluirPorId(UUID.fromString("9c23e72a-5bfe-11eb-ae93-0242ac130002")));
    }

    private List<Fazenda> umaListaDeFazendas() {
        List<Fazenda> fazendas = new ArrayList<>();
        Fazenda fazenda1 = new Fazenda();
        Fazenda fazenda2 = new Fazenda();
        fazendas.add(fazenda1);
        fazendas.add(fazenda2);
        return fazendas;
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
}

