package br.com.totvs.desafio.talhao;

import br.com.totvs.desafio.fazenda.Fazenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TalhaoRepository extends JpaRepository<Talhao, UUID> {
    Optional<Talhao> findByFazendaAndCodigo(Fazenda fazenda, String codigo);

    Page<Talhao> findByFazenda(Fazenda fazenda,Pageable page);
}
