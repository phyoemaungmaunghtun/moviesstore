package com.webstarterz.happycheetah.moviesstore;

public class moviesAlblum {
    private String name;
    private String codeno;
    private String url;

    public moviesAlblum(String name, String codeno, String url) {
        this.name = name;
        this.codeno = codeno;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCodeno() {
        return codeno;
    }

    public String getUrl() {
        return url;
    }
}