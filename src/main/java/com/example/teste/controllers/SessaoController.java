package com.example.teste.controllers;


import com.example.teste.entities.request.SessaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.PautaService;
import com.example.teste.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    SessaoService sessaoService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PautaResponse> adicionarSessao(@RequestBody SessaoRequest sessaoRequest){
        PautaResponse pautaResponse = sessaoService.salvar(sessaoRequest);
        return (Objects.isNull(pautaResponse)) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(pautaResponse, HttpStatus.OK);
    }
}
