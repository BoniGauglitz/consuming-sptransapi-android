package br.com.olhovivo.olhovivoapp.services;

import java.util.List;

import br.com.olhovivo.olhovivoapp.models.Line;
import br.com.olhovivo.olhovivoapp.models.Stop;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

public interface OlhoVivoAPI {
    String API = "http://api.olhovivo.sptrans.com.br/";
    String TOKEN = "GET_YOUR_KEY_ON_http://www.sptrans.com.br/desenvolvedores/APIOlhoVivo.aspx";

    @POST("/v0/Login/Autenticar")
    Call<String> authenticateToken(@Query("token") String token);

    @GET("/v0/Linha/Buscar")
    Call<List<Line>> getBusLines(@Query("termosBusca") String termosBusca);

    @GET("/v0/Parada/Buscar")
    Call<List<Stop>> getStopsOnSpecificAddress(@Query("termosBusca") String termosBusca);
}