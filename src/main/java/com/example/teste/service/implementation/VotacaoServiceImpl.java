package com.example.teste.service.implementation;

import com.example.teste.constants.RabbitMQConstants;
import com.example.teste.entities.Pauta;
import com.example.teste.entities.Sessao;
import com.example.teste.entities.StatusCPF;
import com.example.teste.entities.Votacao;
import com.example.teste.entities.request.VotacaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.exception.HttpUtilException;
import com.example.teste.service.PautaService;
import com.example.teste.service.RabbitMQServiceVotacao;
import com.example.teste.service.VotacaoService;
import com.example.teste.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PautaService pautaService;

    @Autowired
    private RabbitMQServiceVotacao rabbitMQServiceVotacao;

    public PautaResponse realizarValidacaoVoto(VotacaoRequest votacaoRequest){
        try {
            //Verificar se a pauta informada existe e se há sessão está aberta para votação
            return verificarEEnviarParaFilaVoto(votacaoRequest);
        }catch (Exception e){
            LOGGER.error("Erro ao realizar o cadastro do voto!", e);
        }
        return null;
    }

    private PautaResponse verificarEEnviarParaFilaVoto(VotacaoRequest votacaoRequest) {
        PautaResponse pautaResponse = new PautaResponse();

        //Buscar pauta de acordo com o número passsado
        Pauta pautaPresente = getPauta(votacaoRequest.getNumeroPauta());
        if (Objects.nonNull(pautaPresente)){

            //Verifica se já existe Sessao cadastrada e se está aberta para votação
            if (Objects.nonNull(pautaPresente.getSessao()) && (verificarSessaAberta(pautaPresente.getSessao()))) {
                //Caso exista sessão aberta, verificar CPF e cadastrar voto
                enviarParaFilaVoto(votacaoRequest, pautaPresente, pautaResponse);
            }else{
                //Caso não exista ou não foi aberta a sessão
                pautaResponse.setCode(HttpStatus.OK.value());
                pautaResponse.setMessagem("Não existe sessão para está pauta ou ela ainda não está aberta para votação!");
            }
            pautaResponse.setNumeroPauta(votacaoRequest.getNumeroPauta());
        }else{
            //Monta objeto de Retorno
            pautaResponse.setCode(HttpStatus.OK.value());
            pautaResponse.setMessagem("Não existe pauta cadastra com esse número!");
        }
        return pautaResponse;
    }

    private Boolean verificarSessaAberta(Sessao sessao){
        if (Objects.isNull(sessao.getFim())){
            return true;
        }
        return false;
    }

    private void enviarParaFilaVoto(VotacaoRequest votacaoRequest, Pauta pautaPresente, PautaResponse pautaResponse) {

        Boolean votoPermitido = false;
        if (verificarCpfPodeVota(votacaoRequest.getCpf())) {
            if (Objects.nonNull(pautaPresente.getVotacoes())) {
                List<Votacao> verificaCpfVotoExistente = pautaPresente.getVotacoes().stream().filter(c -> c.getCpf().equals(votacaoRequest.getCpf())).collect(Collectors.toList());
                votoPermitido = (verificaCpfVotoExistente.isEmpty()) ? true : false;
            } else {
                votoPermitido = true;
            }

            if (votoPermitido) {
                if (pautaPresente.getSessao().getInicio().before(new Date())){
                    rabbitMQServiceVotacao.enviaParaFilaVoto(RabbitMQConstants.FILA_VOTACAO,votacaoRequest);
                    pautaResponse.setCode(HttpStatus.OK.value());
                    pautaResponse.setMessagem("Voto realizado com sucesso!");
                } else {
                    pautaResponse.setCode(HttpStatus.OK.value());
                    pautaResponse.setMessagem("Sessão ainda não está aberta para receber votos!");
                }
            } else {
                pautaResponse.setCode(HttpStatus.OK.value());
                pautaResponse.setMessagem("Seu voto já foi realizado neste Pauta. Obrigado!");
            }
        }else{
            pautaResponse.setCode(HttpStatus.OK.value());
            pautaResponse.setMessagem("Infelizmente você não pode realizar o voto nessa pauta ou seu CPF pode está errado. Verifique por favor!");
        }
    }

    private Boolean verificarCpfPodeVota(String cpf){
        Boolean retorno = true;
        try {
            String url = "https://user-info.herokuapp.com/users/" + cpf;
            StatusCPF statusCPF = HttpUtil.executarGet(url).deserializarResposta(StatusCPF.class);
            if (Objects.isNull(statusCPF) || !statusCPF.getStatus().equals("ABLE_TO_VOTE")){
                retorno = false;
            }
        } catch (IOException | HttpUtilException e) {
            retorno = false;
        }
        return retorno;
    }

    private Pauta getPauta(Integer numeroPauta) {
        if (verificaPautaExistente(numeroPauta)){
            return pautaService.findByNumeroPauta(numeroPauta);
        }
        return null;
    }

    public Boolean verificaPautaExistente(Integer numeroPauta){
        Pauta pauta = pautaService.findByNumeroPauta(numeroPauta);
        if (Objects.nonNull(pauta)){
            return true;
        }
        return false;
    }
}
