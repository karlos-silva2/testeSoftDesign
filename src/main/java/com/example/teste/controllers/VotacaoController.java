package com.example.teste.controllers;

import com.example.teste.entities.request.VotacaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {

    @Autowired
    private VotacaoService votacaoService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PautaResponse> adicionarVoto(@RequestBody VotacaoRequest votacaoRequest){
        PautaResponse pautaResponse = votacaoService.salvar(votacaoRequest);
        return (Objects.isNull(pautaResponse)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(pautaResponse, HttpStatus.OK);
    }
}
