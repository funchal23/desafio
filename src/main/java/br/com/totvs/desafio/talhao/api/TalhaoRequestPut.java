package br.com.totvs.desafio.talhao.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TalhaoRequestPut {
    @NotNull
    private Float area;
    @NotNull
    private Float estimativaSafra;

}
