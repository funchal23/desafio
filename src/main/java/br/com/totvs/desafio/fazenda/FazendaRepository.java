package br.com.totvs.desafio.fazenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FazendaRepository extends JpaRepository<Fazenda, UUID> {
}
