package br.com.olhovivo.olhovivoapp;

import android.app.Application;

import java.util.HashSet;

public class OlhoVivoApplication extends Application {

    private static OlhoVivoApplication instance = new OlhoVivoApplication();
    public static HashSet<String> staticCookie = new HashSet<String>();

    public OlhoVivoApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}