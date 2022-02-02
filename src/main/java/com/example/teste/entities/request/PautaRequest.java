package com.example.teste.entities.request;

public class PautaRequest {

    private String assunto;

    public PautaRequest(){}

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return String.format(
                "PautaRequest[assunto='%s']",
                assunto);
    }
}
