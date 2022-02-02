package com.example.teste.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author carlos silva
 * Entidade Responsável por gerenciar o cadastramentos das pautas, sessões e votações
 */
@Document(collection = "Pauta")
public class Pauta {

    @Id
    private String id;
    private Date data;
    private String assunto;
    private Integer numeroPauta;
    private Sessao sessao;
    private List<Votacao> votacoes;

    public Pauta(){}

    public Pauta(String assunto, Integer numeroPauta) {
        this.data = new Date();
        this.assunto = assunto;
        this.numeroPauta = numeroPauta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getNumeroPauta() {
        return numeroPauta;
    }

    public void setNumeroPauta(Integer numeroPauta) {
        this.numeroPauta = numeroPauta;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public List<Votacao> getVotacoes() {
        return votacoes;
    }

    public void setVotacoes(List<Votacao> votacoes) {
        this.votacoes = votacoes;
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id='" + id + '\'' +
                ", data=" + data +
                ", assunto='" + assunto + '\'' +
                ", numeroPauta=" + numeroPauta +
                ", sessao=" + sessao +
                ", votacaoList=" + votacoes +
                '}';
    }
}
