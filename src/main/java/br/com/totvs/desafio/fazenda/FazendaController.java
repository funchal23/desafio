package br.com.totvs.desafio.fazenda;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fazenda")
public class FazendaController {

    private final FazendaService fazendaService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody FazendaRequest fazendaRequest){
        Fazenda fazenda = new Fazenda(fazendaRequest);
        fazendaService.criarUmaFazenda(fazenda);
        return ResponseEntity
                .created(URI.create("http://localhost:8080/api/v1/fazenda/" + fazenda.getId()))
                .build();
    }

    @Transactional
    @PutMapping("/{novoNome}/{id}")
    public ResponseEntity<?> alteraNome(@PathVariable("novoNome") String novoNome, @PathVariable("id") UUID id) throws Exception {
        fazendaService.alterarNome(novoNome, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<FazendaDTO> buscaTodasAsFazendas(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) throws Exception {
        Page<Fazenda> fazendas = fazendaService.buscarPaginado(page);
        return FazendaDTO.converterFazendasEmFazendasDTO(fazendas);
    }

    @GetMapping("/{id}")
    public FazendaDTO buscaFazendaPorId(@PathVariable("id") UUID id) throws Exception {
        Fazenda fazenda = fazendaService.buscarPorId(id);
        return new FazendaDTO(fazenda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletaPorId(@PathVariable("id") UUID id) throws FazendaNaoEncontradaException {
        fazendaService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }
}
