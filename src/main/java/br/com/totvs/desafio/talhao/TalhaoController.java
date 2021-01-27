package br.com.totvs.desafio.talhao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/talhao")
public class TalhaoController {

    private final TalhaoService talhaoService;

    @PostMapping("/{idFazenda}")
    public ResponseEntity<?> insereTalhaoNaFazenda(@PathVariable("idFazenda") UUID idFazenda,@RequestBody @Valid TalhaoRequest talhaoRequest) throws Exception {
        Talhao talhao = talhaoService.insereTalhao(talhaoRequest, idFazenda);
        return ResponseEntity
                .created(URI.create("http://localhost:8080/api/v1/talhao/" + idFazenda + "/"+ talhao.getId()))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alteraNomeEstimativa(@PathVariable("id") UUID id, @RequestBody @Valid TalhaoRequestPut talhaoRequestPut) throws Exception {
        talhaoService.alteraAreaEstimativa(talhaoRequestPut, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fazenda/{idFazenda}")
    public Page<TalhaoDTO> buscaTodosTalhoesFazenda(@PathVariable("idFazenda") UUID idFazenda, @PageableDefault(sort = "codigo", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) throws Exception {
        Page<Talhao> talhoes = talhaoService.buscarPaginado(idFazenda, page);
        return TalhaoDTO.converterTalhaoEmTalhaoDTO(talhoes);
    }

    @GetMapping("/{id}")
    public TalhaoDTO buscaTalhaoPorId(@PathVariable("id") UUID id) throws Exception {
        Talhao talhao = talhaoService.buscarPorId(id);
        return new TalhaoDTO(talhao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluiTalhaoPorId(@PathVariable("id") UUID id) throws Exception {
        talhaoService.excluiPorId(id);
        return ResponseEntity.ok().build();
    }
}
