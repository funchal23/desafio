package br.com.totvs.desafio.evento.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoTalhaoRepository extends JpaRepository<EventoTalhao, UUID> {
}
