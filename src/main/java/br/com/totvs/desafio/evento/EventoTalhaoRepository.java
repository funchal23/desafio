package br.com.totvs.desafio.evento;

import br.com.totvs.desafio.talhao.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoTalhaoRepository extends JpaRepository<EventoTalhao, UUID> {
}
