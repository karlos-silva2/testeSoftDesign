package com.example.teste.service;

import com.example.teste.entities.request.VotacaoRequest;
import com.example.teste.entities.response.PautaResponse;

public interface VotacaoService {

    PautaResponse realizarValidacaoVoto(VotacaoRequest votacaoRequest);

}
