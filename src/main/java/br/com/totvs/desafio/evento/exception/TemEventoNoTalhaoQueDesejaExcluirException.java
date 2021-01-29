package br.com.totvs.desafio.evento.exception;

public class TemEventoNoTalhaoQueDesejaExcluirException extends Exception {
    public TemEventoNoTalhaoQueDesejaExcluirException(){
        super("Incapaz de alterar/excluir talhao devido a ter eventos vinculado a ele");
    }
}
