package com.example.teste.entities.request;

public class VotacaoRequest {

    private String cpf;
    private String nome;
    private String voto;
    private Integer numeroPauta;

    public VotacaoRequest(){

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public Integer getNumeroPauta() {
        return numeroPauta;
    }

    public void setNumeroPauta(Integer numeroPauta) {
        this.numeroPauta = numeroPauta;
    }

    @Override
    public String toString() {
        return "VotacaoRequest{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", voto='" + voto + '\'' +
                ", numeroPauta=" + numeroPauta +
                '}';
    }
}
