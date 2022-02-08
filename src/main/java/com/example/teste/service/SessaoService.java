package com.example.teste.service;

import com.example.teste.entities.Pauta;
import com.example.teste.entities.request.SessaoRequest;
import com.example.teste.entities.response.PautaResponse;

public interface SessaoService {

    PautaResponse salvar(SessaoRequest sessaoRequest);

    Pauta getPauta(Integer numeroPauta);

    Boolean verificaPautaExistente(Integer numeroPauta);
}
