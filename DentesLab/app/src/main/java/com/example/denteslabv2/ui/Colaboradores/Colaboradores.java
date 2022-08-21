package com.example.denteslabv2.ui.Colaboradores;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Colaboradores implements ColaboradoresI {

    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public boolean salvar(Colaborador colaborador) {

        DatabaseReference userRef = database.child("Colaboradores");
        DatabaseReference userRef2 = userRef.child(colaborador.getNome());

        userRef2.child("Nome").setValue(colaborador.getNome());
        userRef2.child("Email").setValue(colaborador.getEmail());
        userRef2.child("Telefone").setValue(colaborador.getTelefone());
        userRef2.child("Setor").setValue(colaborador.getSetor());
        userRef2.child("Acesso").setValue(colaborador.getNivelAcesso());

        return true;
    }

    @Override
    public boolean atualizar(Colaborador colaborador) {
        return false;
    }

    @Override
    public boolean deletar(Colaborador colaborador) {
        return false;
    }

    @Override
    public List<Colaborador> listar() {

        List<Colaborador> colabs = new ArrayList<>();

        DatabaseReference userRef = database.child("Colaboradores");




        return null;
    }
}
