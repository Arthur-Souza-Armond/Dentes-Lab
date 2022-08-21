package com.example.denteslabv2.ui.Clientes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientesDAO implements IClientesDAO {

    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public boolean salvar(Clientes clientes) {

        DatabaseReference userRef = database.child("Clientes");
        DatabaseReference userRef2 = userRef.child(clientes.getNome());
        DatabaseReference userRef3 = userRef.child(clientes.getNome()).child("Tabela");

        userRef2.child("Nome").setValue(clientes.getNome());
        userRef2.child("Nome_pesquisa").setValue(clientes.getNome().toUpperCase());
        userRef2.child("CPF").setValue(clientes.getCPF());
        userRef2.child("Telefone").setValue(clientes.getTelefone());
        userRef2.child("Endereco").setValue(clientes.getEndereco());
        userRef2.child("CEP").setValue(clientes.getCEP());
        userRef2.child("Referencia").setValue(clientes.getReferencia());
        userRef2.child("Nome_secretaria").setValue(clientes.getSecretaria_nome());
        userRef2.child("Telefone_secretaria").setValue(clientes.getSecretaria_telefone());
        userRef2.child("Area").setValue(clientes.getArea());

        return true;
    }

    @Override
    public boolean deletar(Clientes clientes) {
        return false;
    }
}
