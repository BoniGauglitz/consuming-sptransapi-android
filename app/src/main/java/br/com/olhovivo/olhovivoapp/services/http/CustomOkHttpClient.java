package br.com.olhovivo.olhovivoapp.services.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import br.com.olhovivo.olhovivoapp.services.Interceptor.AddCookiesInterceptor;
import br.com.olhovivo.olhovivoapp.services.Interceptor.ReceivedCookiesInterceptor;

public class CustomOkHttpClient extends OkHttpClient {
    HttpLoggingInterceptor interceptor;

    public CustomOkHttpClient() {
        interceptor = createInterceptor();
        setInterceptors(interceptor);
    }

    public HttpLoggingInterceptor createInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
    public void setInterceptors(HttpLoggingInterceptor interceptor){
        this.interceptors().add(interceptor);
        this.interceptors().add(new AddCookiesInterceptor());
        this.interceptors().add(new ReceivedCookiesInterceptor());
    }
}
