package br.com.totvs.desafio.talhao;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class TalhaoRequest {

    @Size(max = 10)
    @NotNull
    @NotEmpty
    private String codigo;
    @NotNull
    private Float area;
    @NotNull
    private Integer numeroSafra;
    @NotNull
    @NotEmpty
    private String dataSafra;
    @NotNull
    private Float estimativaSafra;

    public TalhaoRequest() {
    }
}
