package com.example.teste.entities;

import java.util.Date;

public class Votacao {

    private String cpf;
    private String nome;
    private String voto;
    private Date data;

    public Votacao(){

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Votacao{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", voto='" + voto + '\'' +
                ", data=" + data +
                '}';
    }
}
