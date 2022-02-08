package com.example.teste.controllers;

import com.example.teste.entities.dto.PautaTotalVotosDTO;
import com.example.teste.entities.request.PautaRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PautaResponse> adicionarPauta(@RequestBody PautaRequest pautaRequest){
        return ResponseEntity.ok(pautaService.salvar(pautaRequest.getAssunto()));
    }

    @GetMapping
    public ResponseEntity<List<PautaTotalVotosDTO>> listaPautas(){
        List<PautaTotalVotosDTO> pautaTotalVotosDTOS = pautaService.listaPautasTotalVotos();
        return (pautaTotalVotosDTOS.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(pautaTotalVotosDTOS, HttpStatus.OK);
    }
}