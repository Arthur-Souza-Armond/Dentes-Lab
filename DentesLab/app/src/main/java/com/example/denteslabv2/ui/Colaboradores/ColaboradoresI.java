package com.example.denteslabv2.ui.Colaboradores;

import java.util.List;

public interface ColaboradoresI {

    public boolean salvar(Colaborador colaborador);
    public boolean atualizar(Colaborador colaborador);
    public boolean deletar(Colaborador colaborador);
    public List<Colaborador> listar();

}
