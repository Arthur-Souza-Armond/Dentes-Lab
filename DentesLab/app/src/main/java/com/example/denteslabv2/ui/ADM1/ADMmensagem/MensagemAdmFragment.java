package com.example.denteslabv2.ui.ADM1.ADMmensagem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Colaboradores.adapter.Colabadapter;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MensagemAdmFragment extends Fragment {

    //Elementos XML --------------------------------------------------------------------------------
    private TextInputEditText Titulo,Corpo;
    private Button CriarMensagem;
    private ImageButton IconeTrabalho,IconeFinanceiro;
    private RecyclerView Rv_colab;
    private CardView Icon1,Icon2;
    private TextView NomeColabsSelec;

    //Var auxiliares -------------------------------------------------------------------------------
    private int IdIcone,aux,ID;
    private String colabs;
    private List NomeColab,NivelPermissao,CSelec;
    private Colabadapter colabadapter;

    //Declarações DB -------------------------------------------------------------------------------
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mensagem_adm, container, false);

        //Localizando os elementos -----------------------------------------------------------------
        Titulo = view.findViewById(R.id.txt_tituloMensagem);
        Corpo = view.findViewById(R.id.txt_corpoMensagem);
        CriarMensagem = view.findViewById(R.id.btn_criarMensagem);

        Icon1 = view.findViewById(R.id.cardIcon1);
        Icon2 = view.findViewById(R.id.cardIcon2);

        IconeTrabalho = view.findViewById(R.id.imgb_icon1);
        IconeFinanceiro = view.findViewById(R.id.imgb_icon2);

        IconeTrabalho.setImageResource(R.drawable.ic_trabalho_mensagem_24);
        IconeFinanceiro.setImageResource(R.drawable.ic_pagamento_mensagem_24);

        Rv_colab = view.findViewById(R.id.rv_colab);
        NomeColabsSelec = view.findViewById(R.id.txt_nomeColabsSelec);

        //Inicializando as varaiveis auxiliares ----------------------------------------------------
        colabs = "";
        IdIcone = 0;
        aux = 0;
        CSelec = new ArrayList();

        Icon1.setVisibility(View.GONE);
        Icon2.setVisibility(View.GONE);

        carregarColabs();

        Rv_colab.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity(),
                        Rv_colab,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                aux = 0;

                                String nome = NomeColab.get(position).toString();

                                for(int i=0;i<CSelec.size();i++){
                                    if(CSelec.get(i) == nome){
                                        aux = aux - 1;
                                    }else{
                                        aux = aux + 1;
                                    }
                                }

                                if(aux == CSelec.size()){
                                    CSelec.add(aux,nome);
                                    NomeColabsSelec.append(" "+nome);
                                    for(int k=0;k<CSelec.size();k++){
                                        Log.i("INFO LISTA",CSelec.get(k).toString());
                                    }
                                }else{
                                    Toast.makeText(getActivity(),
                                            "Colab já selecionado",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                String nome = NomeColab.get(position).toString();

                                for(int i =0;i < CSelec.size(); i++){

                                    if(CSelec.get(i) == nome){

                                        CSelec.remove(i);

                                        for(int k=0;k<CSelec.size();k++){
                                            Log.i("INFO LISTA",CSelec.get(k).toString());
                                        }

                                        NomeColabsSelec.setText("Colabs:");

                                        for(int j=0;j<CSelec.size();j++){
                                            if(CSelec.get(j) != null){
                                                NomeColabsSelec.append(" "+CSelec.get(j).toString());
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        IconeTrabalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IdIcone = 2131165346;
                Icon1.setVisibility(View.VISIBLE);
                Icon2.setVisibility(View.GONE);

            }
        });

        IconeFinanceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IdIcone = 2131165334;
                Icon2.setVisibility(View.VISIBLE);
                Icon1.setVisibility(View.GONE);

            }
        });



        CriarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titulo,corpo;

                titulo = Titulo.getText().toString();
                corpo = Corpo.getText().toString();

                if(!titulo.equals("") && !corpo.equals("") && IdIcone != 0 && CSelec.size() != 0){

                    ID = 0;

                    for(int i=0;i<CSelec.size();i++){

                        DatabaseReference data = database.child("Usuarios").child(CSelec.get(i).toString()).child("Mensagens");

                        Query query = data.orderByChild("ID");

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    ID = ID + 1;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference data2 = data.child(String.valueOf(ID));

                        data2.child("ID_Mensagem").setValue(ID);
                        data2.child("Titulo").setValue(titulo);
                        data2.child("Corpo").setValue(corpo);
                        data2.child("Id_Icone").setValue(IdIcone);

                        Titulo.setText("");
                        Corpo.setText("");
                        NomeColabsSelec.setText("Colabs:");
                        Icon1.setVisibility(View.GONE);
                        Icon2.setVisibility(View.GONE);

                        Toast.makeText(
                                getActivity(),
                                "Mensagem enviada",
                                Toast.LENGTH_LONG).show();

                    }


                }else if(titulo.equals("")){

                    Toast.makeText(getActivity(),
                            "Titulo vazio",
                            Toast.LENGTH_LONG).show();

                }else if (corpo.equals("")){

                    Toast.makeText(getActivity(),
                            "Corpo vazio",
                            Toast.LENGTH_LONG).show();

                }else if(IdIcone == 0){

                    Toast.makeText(getActivity(),
                            "Selecione uma imagem",
                            Toast.LENGTH_LONG).show();

                }else if(CSelec.size() == 0){

                    Toast.makeText(getActivity(),
                            "Selecione pelo menos um colaborador",
                            Toast.LENGTH_LONG).show();

                }



            }
        });

        return view;
    }

    private void carregarColabs(){

        NomeColab = new ArrayList();
        NivelPermissao = new ArrayList();

        colabadapter = new Colabadapter(NomeColab,NivelPermissao,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_colab.setLayoutManager(layoutManager);
        Rv_colab.setHasFixedSize(true);
        Rv_colab.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_colab.setAdapter( colabadapter );

        DatabaseReference userRef = database.child("Usuarios");

        Query query = userRef.orderByChild("NivelPermissao");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    NomeColab.add(ds.child("Nome").getValue());
                    NivelPermissao.add(ds.child("NivelPermissao").getValue());
                    Log.i("INFO","Usuario: "+ds.child("Nome").getValue());
                }
                colabadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}