package com.example.teste.business;

import com.example.teste.entities.Pauta;
import com.example.teste.service.PautaService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class FecharSessaoJob {

    private final long MIL_MILESSEGUNDOS = 1000;
    private final long UM_MINUTO = MIL_MILESSEGUNDOS * 60;

    @Autowired
    private PautaService pautaService;

    @Scheduled(fixedDelay = UM_MINUTO)
    public void encerrarSessao() {
        List<Pauta> pautaList = pautaService.listaPautas();
        for (Pauta item : pautaList) {
            if (Objects.nonNull(item.getSessao()) && Objects.isNull(item.getSessao().getFim())) {
                Date dataFimSessao = DateUtils.addMinutes(item.getSessao().getInicio(), item.getSessao().getDuracaoMinutos());
                String dataFormatadaFimSessao = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFimSessao);
                String dataHoraAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                if (dataFormatadaFimSessao.compareTo(dataHoraAtual) < 0) {
                    item.getSessao().setFim(dataFimSessao);
                    pautaService.atualizarPauta(item);
                }
            }
        }
    }
}