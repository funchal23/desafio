package br.com.totvs.desafio.evento.exception;

public class EventoDeEncerramentoJaExistenteException extends Exception{
    public EventoDeEncerramentoJaExistenteException(){
        super("Para esse talhao já existe um evento de encerramento e é permitido apenas um evento de encerramento por talhão");
    }
}
