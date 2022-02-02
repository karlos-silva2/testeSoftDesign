package com.example.teste.controllers;


import com.example.teste.entities.request.SessaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.PautaService;
import com.example.teste.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    SessaoService sessaoService;

    @PostMapping(consumes = "application/json")
    public PautaResponse adicionarSessao(@RequestBody SessaoRequest sessaoRequest){
        return sessaoService.salvar(sessaoRequest);
    }
}
