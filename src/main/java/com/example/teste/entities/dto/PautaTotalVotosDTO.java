package com.example.teste.entities.dto;

public class PautaTotalVotosDTO {

    private String assunto;
    private Integer numeroPauta;
    private Long sim;
    private Long nao;

    public PautaTotalVotosDTO(){}

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getNumeroPauta() {
        return numeroPauta;
    }

    public void setNumeroPauta(Integer numeroPauta) {
        this.numeroPauta = numeroPauta;
    }

    public Long getSim() {
        return sim;
    }

    public void setSim(Long sim) {
        this.sim = sim;
    }

    public Long getNao() {
        return nao;
    }

    public void setNao(Long nao) {
        this.nao = nao;
    }

    @Override
    public String toString() {
        return "PautaTotalVotosDTO{" +
                "assunto='" + assunto + '\'' +
                ", numeroPauta=" + numeroPauta +
                ", sim='" + sim + '\'' +
                ", nao='" + nao + '\'' +
                '}';
    }
}
