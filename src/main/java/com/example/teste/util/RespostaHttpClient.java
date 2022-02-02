package com.example.teste.util;

import com.example.teste.exception.HttpUtilException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.util.Optional;

public class RespostaHttpClient {

    private Optional<StatusLine> statusLine;
    private String resposta;
    private final static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public RespostaHttpClient(String resposta) {
        super();
        this.resposta = resposta;
    }

    public RespostaHttpClient(Optional<StatusLine> statusLine, String resposta) {
        super();
        this.statusLine = statusLine;
        this.resposta = resposta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Optional<StatusLine> getCodigoHttp() {
        return statusLine;
    }

    public void setCodigoHttp(Optional<StatusLine> codigoHttp) {
        this.statusLine = codigoHttp;
    }

    public boolean isRespostaSucesso() {
        if (this.statusLine.isPresent()) {
            int statusCode = this.statusLine.get().getStatusCode();
            return statusCode == HttpStatus.SC_OK ||
                    statusCode == HttpStatus.SC_CREATED ||
                    statusCode == HttpStatus.SC_NO_CONTENT ||
                    statusCode == HttpStatus.SC_ACCEPTED;
        }
        return false;
    }

    public <T> T deserializarResposta(Class<T> classeSucesso) throws IOException, HttpUtilException {

        if (!isRespostaSucesso()) {
            throw new HttpUtilException(new Exception("CPF Invalido!" + this.getResposta()));
        }
        return mapper.readValue(this.getResposta(), classeSucesso);
    }
}
