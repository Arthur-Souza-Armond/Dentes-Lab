package com.example.denteslabv2.ui.Clientes;

import java.io.Serializable;
import java.util.List;

public class Clientes implements Serializable {

    private String Nome;
    private String testenome;
    private String Telefone;
    private String CPF;
    private String Endereco;
    private String CEP;
    private String Referencia;
    private String cSecretaria_nome;
    private String cSecretaria_telefone;
    private String Area;
    private List NomeT,PrecoT;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {

        Nome = nome;
    }

    public String getTestenome() {
        return testenome;
    }

    public void setTestenome(String testenome) {
        this.testenome = testenome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String email) {
        CPF = email;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getSecretaria_nome() {
        return cSecretaria_nome;
    }

    public void setSecretaria_nome(String secretaria_nome) {
        cSecretaria_nome = secretaria_nome;
    }

    public String getSecretaria_telefone() {
        return cSecretaria_telefone;
    }

    public void setSecretaria_telefone(String secretaria_telefone) {
        cSecretaria_telefone = secretaria_telefone;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public List getNomeT() {
        return NomeT;
    }

    public void setNomeT(List nomeT) {
        NomeT = nomeT;
    }

    public List getPrecoT() {
        return PrecoT;
    }

    public void setPrecoT(List precoT) {
        PrecoT = precoT;
    }
}
