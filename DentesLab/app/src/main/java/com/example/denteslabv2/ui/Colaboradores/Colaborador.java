package com.example.denteslabv2.ui.Colaboradores;

import java.io.Serializable;

public class Colaborador implements Serializable {

    private Long Id;
    private String Nome;
    private String Email;
    private String Telefone;
    private String Setor;
    private Long nivelAcesso;

    public Long getId()
    {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public Long getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Long nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getSetor() {
        return Setor;
    }

    public void setSetor(String setor) {
        Setor = setor;
    }
}
