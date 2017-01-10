
package br.com.olhovivo.olhovivoapp.services.Interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.olhovivo.olhovivoapp.OlhoVivoApplication;

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                OlhoVivoApplication.staticCookie.add(header);
            }
        }
        return originalResponse;
    }
}
