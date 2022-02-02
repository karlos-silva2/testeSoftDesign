package com.example.teste.entities.response;

public class PautaResponse {

    private Integer code;
    private String messagem;
    private Integer numeroPauta;

    public PautaResponse(){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessagem() {
        return messagem;
    }

    public void setMessagem(String messagem) {
        this.messagem = messagem;
    }

    public Integer getNumeroPauta() {
        return numeroPauta;
    }

    public void setNumeroPauta(Integer numeroPauta) {
        this.numeroPauta = numeroPauta;
    }
}
