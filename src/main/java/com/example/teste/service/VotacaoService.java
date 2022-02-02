package com.example.teste.service;

import com.example.teste.entities.Pauta;
import com.example.teste.entities.Sessao;
import com.example.teste.entities.StatusCPF;
import com.example.teste.entities.Votacao;
import com.example.teste.entities.request.VotacaoRequest;
import com.example.teste.entities.response.PautaResponse;
import com.example.teste.exception.HttpUtilException;
import com.example.teste.repositories.PautaRepository;
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
public class VotacaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PautaRepository pautaRepository;

    public PautaResponse salvar(VotacaoRequest votacaoRequest){
        PautaResponse pautaResponse = new PautaResponse();
        try {
            //Verificar se a pauta informada existe e se há sessão está aberta para votação
            verificaCadastraVoto(votacaoRequest, pautaResponse);
        }catch (Exception e){
            LOGGER.error("Erro ao realizar o cadastro do voto!", e);
            pautaResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.ordinal());
            pautaResponse.setMessagem("Erro ao realizar o cadastro do voto!");
        }
        return pautaResponse;
    }

    private void verificaCadastraVoto(VotacaoRequest votacaoRequest, PautaResponse pautaResponse) {
        //Buscar pauta existe de acodo com o número passsado
        Pauta pautaPresente = getPauta(votacaoRequest.getNumeroPauta());
        if (Objects.nonNull(pautaPresente)){
            //Verifica se já existe Sessao cadastrada e se está aberta para votação
            if (Objects.nonNull(pautaPresente.getSessao()) && (verificarSessaAberta(pautaPresente.getSessao()))) {
                //Caso exista sessão aberta, cadastrar voto
                verificaCadastraVoto(votacaoRequest, pautaPresente, pautaResponse);
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
    }

    private Boolean verificarSessaAberta(Sessao sessao){
        if (Objects.isNull(sessao.getFim())){
            return true;
        }
        return false;
    }

    private void verificaCadastraVoto(VotacaoRequest votacaoRequest, Pauta pautaPresente, PautaResponse pautaResponse) {

        Boolean votoPermitido = false;
        if (verificarCpfPodeVota(votacaoRequest.getCpf())) {
            List<Votacao> votacaoList;
            if (Objects.nonNull(pautaPresente.getVotacoes())) {
                votacaoList = pautaPresente.getVotacoes();
                List<Votacao> verificaCpfVotoExistente = pautaPresente.getVotacoes().stream().filter(c -> c.getCpf().equals(votacaoRequest.getCpf())).collect(Collectors.toList());
                votoPermitido = (verificaCpfVotoExistente.isEmpty()) ? true : false;
            } else {
                votacaoList = new ArrayList<>();
                votoPermitido = true;
            }

            if (votoPermitido) {
                if (pautaPresente.getSessao().getInicio().before(new Date())){
                    Votacao votacao = new Votacao();
                    votacao.setNome(votacaoRequest.getNome());
                    votacao.setCpf(votacaoRequest.getCpf());
                    votacao.setVoto(votacaoRequest.getVoto());
                    votacao.setData(new Date());
                    votacaoList.add(votacao);
                    pautaPresente.setVotacoes(votacaoList);

                    //Chamara interface para relizar a gravação no banco
                    pautaRepository.save(pautaPresente);

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
