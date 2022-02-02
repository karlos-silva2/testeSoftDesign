package com.example.teste.entities.request;

import java.util.Date;

public class SessaoRequest {

    private Integer numeroPauta;
    private Date inicioSessao;
    private Integer duracaoMinutosSessao = 1;

    public SessaoRequest(){

    }

    public Integer getNumeroPauta() {
        return numeroPauta;
    }

    public void setNumeroPauta(Integer numeroPauta) {
        this.numeroPauta = numeroPauta;
    }

    public Date getInicioSessao() {
        return inicioSessao;
    }

    public void setInicioSessao(Date inicioSessao) {
        this.inicioSessao = inicioSessao;
    }

    public Integer getDuracaoMinutosSessao() {
        return duracaoMinutosSessao;
    }

    public void setDuracaoMinutosSessao(Integer duracaoMinutosSessao) {
        this.duracaoMinutosSessao = duracaoMinutosSessao;
    }

    @Override
    public String toString() {
        return String.format(
                "SessaoRequest[numeroPauta=%s, inicioSessao='%s', duracaoMinutosSessao='%s']",
                numeroPauta, inicioSessao, duracaoMinutosSessao);
    }
}