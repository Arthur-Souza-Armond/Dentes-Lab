package com.example.denteslabv2.ui.Servicos;

import java.io.Serializable;

public class Servicos implements Serializable {

    private String nomeS;
    private String precoS;

    public String getNomeS() {
        return nomeS;
    }

    public void setNomeS(String nomeS) {
        this.nomeS = nomeS;
    }

    public String getPrecoS() {
        return precoS;
    }

    public void setPrecoS(String precoS) {
        this.precoS = precoS;
    }
}
