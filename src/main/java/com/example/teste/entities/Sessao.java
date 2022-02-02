package com.example.teste.entities;

import java.util.Date;

public class Sessao {

    private Date inicio;
    private Integer duracaoMinutos;
    private Date fim;

    public Sessao(){

    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "inicio=" + inicio +
                ", duracaoMinutos=" + duracaoMinutos +
                ", fim=" + fim +
                '}';
    }
}
