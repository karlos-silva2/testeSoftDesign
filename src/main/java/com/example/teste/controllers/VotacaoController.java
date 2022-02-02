package com.example.teste.controllers;

import com.example.teste.entities.request.VotacaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {

    @Autowired
    private VotacaoService votacaoService;

    @PostMapping(consumes = "application/json")
    public PautaResponse adicionarVoto(@RequestBody VotacaoRequest votacaoRequest){
        return votacaoService.salvar(votacaoRequest);
    }
}
