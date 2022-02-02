package com.example.teste.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtil {

    private final static String FORMATO_DATA = "dd/MM/yyyy";
    private final static String FORMATO_DATA_EN = "yyyy-MM-dd";
    private final static String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm";
    private final static String FORMATO_DATA_HORA_SEGUNDO = "dd/MM/yyyy HH:mm:ss";

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern(FORMATO_DATA);

    public static ZonedDateTime parserLocalTimeUTC(String time) {

        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);

        return ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
    }

    public static Date transformarEmDate(String data) throws ParseException {

        return new SimpleDateFormat(FORMATO_DATA_EN).parse(data);
    }

    public static Date transformarEmDate(String data, String padrao) throws ParseException {

        return new SimpleDateFormat(padrao).parse(data);
    }

    /**
     * Obtém uma String no formato "yyyy-MM-dd" e formata para "dd/MM/yyyy".
     */
    public static String obterDataFormatada(String data) {

        return obterDataFormatada(data, FORMATO_DATA_EN, FORMATO_DATA);
    }

    /**
     * Obtém uma String em um dado formato original e formata para "dd/MM/yyyy".
     */
    public static String obterDataFormatada(String data, String original) {

        return obterDataFormatada(data, original, FORMATO_DATA);
    }
    
    /**
     * Obtém uma String em um dado formato original e formata para um dado formato de destino.
     */
    public static String obterDataFormatada(String data, String original, String destino) {
        DateTimeFormatter formatoOriginal   = DateTimeFormatter.ofPattern(original);
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern(destino);
        return formatoBrasileiro.format(LocalDate.parse(data, formatoOriginal));
    }

    public static LocalDate parseData(String data) {
        return LocalDate.parse(data, DATA_FORMATTER);
    }

    public static LocalDate parseData(String data, DateTimeFormatter formatter) {
        return LocalDate.parse(data, formatter);
    }

    public static String obterDataFormatada(Date data) {

        return new SimpleDateFormat(FORMATO_DATA).format(data);
    }

    public static String obterDataHoraFormatada(Date data) {

        return new SimpleDateFormat(FORMATO_DATA_HORA).format(data);
    }

    public static String obterDataHoraSegundoFormatada(Date data) {

        return new SimpleDateFormat(FORMATO_DATA_HORA_SEGUNDO).format(data);
    }

    public static Date obterDataInicialDoDia() {

        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        return localDateTimeToDate(inicioDoDia);
    }

    public static Date obterDataFinalDoDia() {

        LocalDateTime fimDia = LocalDate.now().atStartOfDay().plusDays(1);
        return localDateTimeToDate(fimDia);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static String obterDataFormatada(Date data, String formato) {
        return new SimpleDateFormat(formato).format(data);
    }
}