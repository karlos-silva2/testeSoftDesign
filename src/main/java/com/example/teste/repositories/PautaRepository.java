package com.example.teste.repositories;

import com.example.teste.entities.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepository extends MongoRepository<Pauta, String> {

    Pauta findByNumeroPauta(Integer numeroPauta);
}
