package br.edu.unilab.catraca.app;

import android.util.Log;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.edu.unilab.catraca.resource.Extrato;
import br.edu.unilab.catraca.resource.Usuario;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by erivando on 26/11/2016.
 */

public class AppServer {

    private static final HttpAuthentication AUTH_HEADER  = new HttpBasicAuthentication("catraca", "CaTr@CaUniLab2015");

    public static ArrayList<Usuario> getRecursoUsuarioLogin(String login) {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try {
            final String url = AppConfig.URL_RECURSO_USUARIO + "/" + convertBase64(login);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(AUTH_HEADER);
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
            HttpEntity<Usuario> requestEntity = new HttpEntity<Usuario>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(getMessageConverters());
            ResponseEntity<Usuario> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Usuario.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (!responseEntity.getBody().getUsuario().isEmpty()) {
                    lista=responseEntity.getBody().getUsuario();
                } else {
                    return lista;
                }
            } else {
                return lista;
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return lista;
    }

    public static ArrayList<Extrato> getRecursoExtratoUsuario(String login) {
        ArrayList<Extrato> lista = new ArrayList<Extrato>();

        try {
            final String url = AppConfig.URL_RECURSO_EXTRATO + "/" + convertBase64(login);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(AUTH_HEADER);
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
            HttpEntity<Extrato> requestEntity = new HttpEntity<Extrato>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(getMessageConverters());
            ResponseEntity<Extrato> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Extrato.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (!responseEntity.getBody().getExtrato().isEmpty()) {
                    lista=responseEntity.getBody().getExtrato();
                } else {
                    return lista;
                }
            } else {
                return lista;
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return lista;
    }

    private static List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }

    private static String convertBase64(String nome){
        String str = nome;
        String strEncode = Base64.encodeBytes(str.getBytes());
        return strEncode;
    }

}
