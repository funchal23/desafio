package br.com.totvs.desafio.fazenda;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.UUID;

public interface FazendaRepository extends JpaRepository<Fazenda, UUID> {
    Page<Fazenda> findAllByFazenda(Fazenda fazenda, Pageable page);
}
