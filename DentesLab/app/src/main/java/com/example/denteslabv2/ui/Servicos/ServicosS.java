package com.example.denteslabv2.ui.Servicos;

import java.io.Serializable;

public class ServicosS implements Serializable {

    private String nome;
    private String preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
