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
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.edu.unilab.catraca.resource.Usuario;

import static android.content.ContentValues.TAG;

/**
 * Created by erivando on 26/11/2016.
 */

public class AppServer {

    public static ArrayList<Usuario> getRecursoUsuarioLogin(String login) {
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        try {
            final String url = AppConfig.URL_RECURSO_USUARIO + "/" + login;
            HttpAuthentication authHeader = new HttpBasicAuthentication("catraca", "CaTr@CaUniLab2015");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
            HttpEntity<Usuario> requestEntity = new HttpEntity<Usuario>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(getMessageConverters());
            ResponseEntity<Usuario> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Usuario.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (!responseEntity.getBody().getUsuario().isEmpty()) {
                    usuario=responseEntity.getBody().getUsuario();
                } else {
                    return usuario;
                }
            } else {
                return usuario;
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return usuario;
    }

    private static List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }

}
