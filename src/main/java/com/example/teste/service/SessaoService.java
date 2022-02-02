package com.example.teste.service;

import com.example.teste.entities.Pauta;
import com.example.teste.entities.Sessao;
import com.example.teste.entities.request.SessaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.repositories.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SessaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PautaRepository pautaRepository;

    public PautaResponse salvar(SessaoRequest sessaoRequest){
        PautaResponse pautaResponse = new PautaResponse();
        try {
            //Verificar se a pauta informada existe e faz cadastro da sessão na pauta
            verificaCadastraSessao(sessaoRequest, pautaResponse);
        }catch (Exception e){
            LOGGER.error("Erro ao realizar o cadastro da sessão na pauta!", e);
            pautaResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.ordinal());
            pautaResponse.setMessagem("Erro ao realizar o cadastro da sessão na pauta!");
        }
        return pautaResponse;
    }

    private void verificaCadastraSessao(SessaoRequest sessaoRequest, PautaResponse pautaResponse) {
        //Buscar pauta existe de acodo com o número passsado
        Pauta pautaPresente = getPauta(sessaoRequest.getNumeroPauta());
        if (Objects.nonNull(pautaPresente)){
            //Verifica se já existe Sessao cadastrada para pauta
            if (Objects.isNull(pautaPresente.getSessao())) {
                //Caso não exista, cadastrar sessao
                cadastrarSessao(sessaoRequest, pautaPresente);
                //Monta objeto de Retorno
                pautaResponse.setCode(HttpStatus.OK.value());
                pautaResponse.setMessagem("Sessão adicionada na pauta com sucesso!");
            }else{
                //Caso exista
                pautaResponse.setCode(HttpStatus.OK.value());
                pautaResponse.setMessagem("Já existe uma sessão cadastra para essa pauta!");
            }
            pautaResponse.setNumeroPauta(sessaoRequest.getNumeroPauta());
        }else{
            //Monta objeto de Retorno
            pautaResponse.setCode(HttpStatus.OK.value());
            pautaResponse.setMessagem("Não existe pauta cadastra com esse número!");
        }
    }

    private void cadastrarSessao(SessaoRequest sessaoRequest, Pauta pautaPresente) {
        //Caso exista a pauta, realiza o cadastra da sessão
        Sessao sessao = new Sessao();
        sessao.setInicio(sessaoRequest.getInicioSessao());
        sessao.setDuracaoMinutos(sessaoRequest.getDuracaoMinutosSessao());
        pautaPresente.setSessao(sessao);

        //Chamara interface para relizar a gravação no banco
        pautaRepository.save(pautaPresente);
    }

    private Pauta getPauta(Integer numeroPauta) {
        if (verificaPautaExistente(numeroPauta)){
            return pautaRepository.findByNumeroPauta(numeroPauta);
        }
        return null;
    }

    public Boolean verificaPautaExistente(Integer numeroPauta){
        Pauta pauta = pautaRepository.findByNumeroPauta(numeroPauta);
        if (Objects.nonNull(pauta)){
            return true;
        }
        return false;
    }
}
