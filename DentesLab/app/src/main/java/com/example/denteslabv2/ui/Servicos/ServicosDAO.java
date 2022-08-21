package com.example.denteslabv2.ui.Servicos;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServicosDAO implements iServicosDAO {

    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public boolean salvar(ServicosS servicosS) {

        DatabaseReference servicoRef2 = database.child("Servicos");
        DatabaseReference servicoRef3 = servicoRef2.child(servicosS.getNome());

        servicoRef3.child("Nome").setValue(servicosS.getNome());
        servicoRef3.child("Nome_pesquisa").setValue(servicosS.getNome().toUpperCase());
        servicoRef3.child("Preco").setValue(servicosS.getPreco());



        return true;
    }

    @Override
    public boolean deletar(ServicosS servicosS) {
        return false;
    }
}
