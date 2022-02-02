package com.example.teste.util;

import com.example.teste.exception.HttpUtilException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLContext;
import java.util.Optional;

public class HttpUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private final static ObjectMapper mapper = new ObjectMapper();
    private final static int timeout = 120000;
    private final static int tempoParaTentar = 10000;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static CloseableHttpClient closeableHttpClient;


    public static RespostaHttpClient executarGet(String uri) throws HttpUtilException {
        return executarGet(uri, true, null);
    }

    public static RespostaHttpClient executarGet(final String uri, boolean ssl, String autorizacao) throws HttpUtilException {

        try {

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            if (autorizacao != null) {
                httpGet.setHeader(HttpHeaders.AUTHORIZATION, autorizacao);
            }

            RespostaHttpClient respostaHttpClient = requisicaoGet(httpGet, ssl);

            return respostaHttpClient;

        } catch (Exception e) {

            LOGGER.error("Falha ao executar GET (uri: " + uri + " )\n" + e.getMessage(), e);
            throw new HttpUtilException(e);
        }
    }


    public static RespostaHttpClient requisicaoGet(HttpGet httpGet, boolean ssl) throws HttpUtilException {

        try {

            try (CloseableHttpResponse response = getCloseableHttpClient(ssl).execute(httpGet)) {
                return new RespostaHttpClient(Optional.ofNullable(response.getStatusLine()), EntityUtils.toString(response.getEntity()));
            }

        } catch (Exception e) {
            throw new HttpUtilException(e);
        }
    }

    public static PoolingHttpClientConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
            connManager.setDefaultMaxPerRoute(50);
            connManager.setMaxTotal(200);
            connectionManager = connManager;
        }
        return connectionManager;
    }

    public static CloseableHttpClient getCloseableHttpClient(boolean ssl) throws HttpUtilException {

        LOGGER.debug(getConnectionManager().getTotalStats().toString());
        RequestConfig.Builder requestBuilder = RequestConfig.custom();

        requestBuilder.setConnectTimeout(timeout);
        requestBuilder.setConnectionRequestTimeout(timeout);

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        if (ssl) {
            SSLContext sslContext = criarContextSSL();
            builder.setSSLContext(sslContext);
            builder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        }
        return builder.setConnectionManager(getConnectionManager()).build();
    }

    private static SSLContext criarContextSSL() throws HttpUtilException {

        try {
            return new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (Exception e) {
            LOGGER.error("Erro ao criar o contexto SSL: " + e.getMessage(), e);
            throw new HttpUtilException(e);
        }
    }
}