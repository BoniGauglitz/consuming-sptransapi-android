package br.com.olhovivo.olhovivoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import br.com.olhovivo.olhovivoapp.models.Line;
import br.com.olhovivo.olhovivoapp.models.Stop;
import br.com.olhovivo.olhovivoapp.services.OlhoVivoAPI;
import br.com.olhovivo.olhovivoapp.services.http.CustomOkHttpClient;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView tvResponse;
    EditText etBusLine;
    EditText etStops;
    OkHttpClient httpClient;
    Retrofit retrofit;
    OlhoVivoAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = (TextView) findViewById(R.id.tvResponse);
        etBusLine = (EditText) findViewById(R.id.etBusLine);
        etStops = (EditText) findViewById(R.id.etStops);
        setViewContent();

        httpClient = new CustomOkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(OlhoVivoAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        service = retrofit.create(OlhoVivoAPI.class);

        olhoVivoValidateToken();

    }

    public void setViewContent() {
        etBusLine.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    removeKeyboard();
                    olhoVivoGetBusLines();
                    return true;
                }
                return false;
            }
        });
        etStops.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    removeKeyboard();
                    olhoVivoGetStopsInSpecificAdress();
                    return true;
                }
                return false;
            }
        });
    }

    public void olhoVivoValidateToken() {
        service.authenticateToken(OlhoVivoAPI.TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
                tvResponse.setText("FALHOU");
            }
        });
    }

    public void olhoVivoGetBusLines() {
        service.getBusLines(etBusLine.getText().toString()).enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Response<List<Line>> response, Retrofit retrofit) {
                tvResponse.setText("");
                for (Line line : response.body()) {
                    if (line.Sentido == 1)
                        tvResponse.setText(tvResponse.getText() + "\n\n Numero: " + line.Letreiro + " - Sentido: " + line.DenominacaoTPTS);
                    else
                        tvResponse.setText(tvResponse.getText() + "\n\n Número: " + line.Letreiro + " - Sentido: " + line.DenominacaoTSTP);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                tvResponse.setText("FALHOU");
            }
        });
    }

    public void olhoVivoGetStopsInSpecificAdress() {
        service.getStopsOnSpecificAddress(etStops.getText().toString()).enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Response<List<Stop>> response, Retrofit retrofit) {

                tvResponse.setText("Paradas referentes ao endereço - " + etStops.getText().toString() + "\n\n");
                for (Stop stop : response.body()) {
                    tvResponse.setText(tvResponse.getText() + "\n\n Nome parada: " + stop.Nome + " - Endereço: " + stop.Endereco);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                tvResponse.setText("FALHOU");
            }
        });
    }

    public void removeKeyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        etBusLine.clearFocus();
        etStops.clearFocus();
    }
}