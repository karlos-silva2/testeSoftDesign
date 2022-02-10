package com.example.teste.connections;

import com.example.teste.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {


    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila){
        return new Queue(nomeFila,true,false,false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(RabbitMQConstants.NOME_EXCHANGES);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue filaVotacao = this.fila(RabbitMQConstants.FILA_VOTACAO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoVotacao = this.relacionamento(filaVotacao, troca);

        //Criação da fila no rabbitMQ
        this.amqpAdmin.declareQueue(filaVotacao);

        //Criacao da Exchanges
        this.amqpAdmin.declareExchange(troca);

        //Criacao do relacionamento
        this.amqpAdmin.declareBinding(ligacaoVotacao);
    }


}
