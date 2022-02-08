package com.example.teste.service;

import com.example.teste.entities.Pauta;
import com.example.teste.entities.dto.PautaTotalVotosDTO;
import com.example.teste.entities.response.PautaResponse;

import java.util.List;

public interface PautaService {

    void atualizarPauta(Pauta pauta);

    PautaResponse salvar(String assunto);

    List<PautaTotalVotosDTO> listaPautasTotalVotos();

    List<Pauta> listaPautas();

    Pauta findByNumeroPauta(Integer numeroPauta);

}