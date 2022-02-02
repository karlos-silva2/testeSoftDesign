package com.example.teste.controllers;

import com.example.teste.entities.Pauta;
import com.example.teste.entities.request.PautaRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @PostMapping(consumes = "application/json")
    public PautaResponse adicionarPauta(@RequestBody PautaRequest pautaRequest){
        PautaResponse pautaResponse = pautaService.salvar(pautaRequest.getAssunto());
        return pautaResponse;
    }

    @GetMapping
    public List<Pauta> listaPautas(){
        return pautaService.listaPautas();
    }
}