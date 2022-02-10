package com.example.teste.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceVotacao {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviaParaFilaVoto(String nomeFila, Object mensagem){
        this.rabbitTemplate.convertAndSend(nomeFila, mensagem);
    }
}
