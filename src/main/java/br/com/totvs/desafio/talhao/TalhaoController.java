package br.com.totvs.desafio.talhao;

import br.com.totvs.desafio.fazenda.FazendaService;
import lombok.RequiredArgsConstructor;
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
}
