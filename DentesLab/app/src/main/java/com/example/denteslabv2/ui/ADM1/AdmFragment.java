package com.example.denteslabv2.ui.ADM1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.ADM1.ADMmensagem.AdapterMensagens;
import com.example.denteslabv2.ui.ADM1.ADMmensagem.MensagemAdmFragment;
import com.example.denteslabv2.ui.ADM1.DB.DBAdmFragment;
import com.example.denteslabv2.ui.ADM1.Lembretes.LembretesADMFragment;
import com.example.denteslabv2.ui.ADM1.Tools.FerramentasADMFragment;
import com.example.denteslabv2.ui.Colaboradores.adapter.Colabadapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdmFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private int Permissao = 0;
    private Button Menssage,DB,Ferramentas,Lembretes;
    private LinearLayout cadeadoLinear;

    //Linear de mensagem
    private ImageButton Icone1,Icone2;
    private int icon,Id,Id2;
    private Button criarMensagem;
    private TextInputEditText Titulo,corpo;
    private CardView divider1, divider2;
    private RecyclerView Rv_mensagensAntigas,Rv_colab;
    private TextView verMensagens;
    private LinearLayout linearCriarMensagem,linearMensagensAntigas;
    private List TITULO,CORPO,ICON,NOMECOLAB,NIVELCOLAB,ColabSelecionado;
    private AdapterMensagens adapterMensagens;
    private Colabadapter colabadapter;
    private String TItulo,COrpo,ICon;
    private Button EnviarMensagemAntiga;


    //Declaração dos lineares não estáticos
    private LinearLayout LMenssagem,LDB,LFerramentas,LLembretes;

    //Verficador de permissão
    private ImageView cadeado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adm, container, false);

        cadeadoLinear = view.findViewById(R.id.linearBloq);
        cadeadoLinear.setVisibility(View.GONE);
        cadeado = view.findViewById(R.id.img_lock);

        //Configurando botões ----------------------------------------------------------------------
        Menssage = view.findViewById(R.id.btn_mensagemAdm);
        DB = view.findViewById(R.id.btn_dbAdm);
        Ferramentas = view.findViewById(R.id.btn_ferramentasAdm);
        Lembretes = view.findViewById(R.id.btn_lembretesAdm);

        // Configura os elementos do LinearMensagem ------------------------------------------------

        Icone1 = view.findViewById(R.id.imgb_icon1);
        Icone2 = view.findViewById(R.id.imgb_icon2);

        criarMensagem = view.findViewById(R.id.btn_criarMensagem);

        Titulo = view.findViewById(R.id.txt_tituloMensagem);
        corpo = view.findViewById(R.id.txt_corpoMensagem);

        icon = 0;

        divider1 = view.findViewById(R.id.cardIcon1);
        divider2 = view.findViewById(R.id.cardIcon2);

        Rv_colab = view.findViewById(R.id.rv_colab);

        linearCriarMensagem = view.findViewById(R.id.linearCriarMensagem);

        ColabSelecionado = new ArrayList();

        //------------------------------------------------------------------------------------------

        Log.i("INFO TESTE",getContext().toString());

        String emailUsuario = auth.getCurrentUser().getEmail();

        verificarPermissao(emailUsuario);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerAdm, new MensagemAdmFragment()).commit();

        Menssage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MensagemSelec();

                /*//Primeiro escolho qual o linear desejado e fecho os outros
                LMenssagem.setVisibility(View.VISIBLE);
                LDB.setVisibility(View.GONE);
                LFerramentas.setVisibility(View.GONE);
                LLembretes.setVisibility(View.GONE);
                linearMensagensAntigas.setVisibility(View.GONE);

                Icone1.setImageResource(R.drawable.ic_trabalho_mensagem_24);
                Icone2.setImageResource(R.drawable.ic_pagamento_mensagem_24);

                divider1.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);

                carregarColaboradores();

                Rv_colab.addOnItemTouchListener(
                        new RecyclerItemClickListnner(
                                getActivity(),
                                Rv_colab,
                                new RecyclerItemClickListnner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        ColabSelecionado.add(NOMECOLAB.get(position).toString());

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                        for(int i=0;i<ColabSelecionado.size();i++){
                                            if(ColabSelecionado.get(i) == NOMECOLAB.get(position)){
                                                ColabSelecionado.remove(i);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );*/

                /*verMensagens.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        linearMensagensAntigas.setVisibility(View.VISIBLE);
                        linearCriarMensagem.setVisibility(View.GONE);

                        carregarMensagens();

                        Id2 = 0;*/

                        /*Rv_mensagensAntigas.addOnItemTouchListener(
                                new RecyclerItemClickListnner(
                                        getActivity(),
                                        Rv_mensagensAntigas,
                                        new RecyclerItemClickListnner.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                String mensagem = TITULO.get(position).toString();

                                                DatabaseReference data = database.child("ADM").child("Mesangem");

                                                Query query = data.orderByChild("Titulo")
                                                        .startAt(mensagem)
                                                        .endAt(mensagem + "\uf88f");

                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot ds : snapshot.getChildren()){
                                                            TItulo = ds.child("Titulo").getValue().toString();
                                                            COrpo = ds.child("Corpo").getValue().toString();
                                                            ICon = ds.child("ID_Icone").getValue().toString();
                                                        }

                                                        EnviarMensagemAntiga.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                                Query query = database.child("ADM").child("Mensagem")
                                                                        .orderByChild("Titulo");

                                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        for (DataSnapshot ds : snapshot.getChildren()){
                                                                            Id2 = Id2 + 1;
                                                                        }

                                                                        DatabaseReference data = database.child("ADM").child("Mensagem").child(String.valueOf(Id2));

                                                                        data.child("ID").setValue(Id);
                                                                        data.child("Titulo").setValue(TItulo);
                                                                        data.child("Corpo").setValue(COrpo);
                                                                        data.child("ID_Icone").setValue(ICon);

                                                                        Toast.makeText(getActivity(),
                                                                                "Mensagem enviada com sucesso",
                                                                                Toast.LENGTH_LONG).show();

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });
                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                            }

                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                            }
                                        }
                                )
                        );*/

                        /*verMensagens.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                linearMensagensAntigas.setVisibility(View.GONE);
                                linearCriarMensagem.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                });*/

                /*criarMensagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String titulo,Corpo;

                        titulo = Titulo.getText().toString();
                        Corpo = corpo.getText().toString();

                        if(!titulo.equals("") && !Corpo.equals("") && icon != 0){

                            Id = 0;

                            Query query = database.child("ADM").child("Mensagem")
                                    .orderByChild("Titulo");

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()){
                                        Id = Id + 1;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            DatabaseReference data = database.child("ADM").child("Mensagem").child(String.valueOf(Id));

                            data.child("ID").setValue(Id);
                            data.child("Titulo").setValue(titulo);
                            data.child("Corpo").setValue(Corpo);
                            data.child("ID_Icone").setValue(icon);

                            Titulo.setText("");
                            corpo.setText("");
                            divider1.setVisibility(View.GONE);
                            divider2.setVisibility(View.GONE);

                            Toast.makeText(getActivity(),
                                    "Sucesso ao salvar mensagem",
                                    Toast.LENGTH_LONG).show();

                            LMenssagem.setVisibility(View.GONE);

                        }

                    }
                });*/

            }
        });

        DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBSelec();

            }
        });

        Ferramentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FerramentasSelec();

            }
        });

        Lembretes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LembreteSelec();

            }
        });

        return view;
    }

    private void MensagemSelec(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerAdm, new MensagemAdmFragment()).commit();

    }

    private void DBSelec(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerAdm, new DBAdmFragment()).commit();

    }

    private void FerramentasSelec(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerAdm, new FerramentasADMFragment()).commit();

    }

    private void LembreteSelec(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerAdm, new LembretesADMFragment()).commit();

    }

    private void carregarMensagens(){

        TITULO = new ArrayList();
        CORPO = new ArrayList();
        ICON = new ArrayList();

        Rv_mensagensAntigas.clearFocus();

        //Adapter
        adapterMensagens = new AdapterMensagens(TITULO,CORPO,ICON,getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_mensagensAntigas.setLayoutManager(layoutManager);
        Rv_mensagensAntigas.setHasFixedSize(true);
        Rv_mensagensAntigas.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_mensagensAntigas.setAdapter( adapterMensagens );

        DatabaseReference data = database.child("ADM").child("Mensagem");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    TITULO.add(ds.child("Titulo").getValue());
                    CORPO.add(ds.child("Corpo").getValue());
                    ICON.add(ds.child("ID_Icone").getValue());
                }
                adapterMensagens.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarColaboradores(){

        NOMECOLAB = new ArrayList();
        NIVELCOLAB = new ArrayList();

        Rv_colab.clearFocus();

        //Adapter
        colabadapter = new Colabadapter(NOMECOLAB,NIVELCOLAB,getContext());

        //Configura o recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_colab.setLayoutManager(layoutManager);
        Rv_colab.setHasFixedSize(true);
        Rv_colab.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_colab.setAdapter( colabadapter );

        DatabaseReference data = database.child("Usuarios");

        Query query = data.orderByChild("Nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    NOMECOLAB.add(ds.child("Nome").getValue());
                    NIVELCOLAB.add(ds.child("NivelPermissao").getValue());
                    Log.i("INFO NOME",ds.child("Nome").getValue().toString());
                }
                colabadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void verificarPermissao(String emailUsuario) {

        Query query = database.child("Usuarios")
                .orderByChild("E-mail")
                .startAt(emailUsuario)
                .endAt(emailUsuario + "\uf88f");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("NivelPermissao").getValue().toString().equals("3")){

                        cadeadoLinear.setVisibility(View.GONE);

                    }else{

                        cadeadoLinear.setVisibility(View.VISIBLE);
                        cadeado.setImageResource(R.drawable.ic_permissao_lock_24);

                        Menssage.setClickable(false);
                        DB.setClickable(false);
                        Ferramentas.setClickable(false);
                        Lembretes.setClickable(false);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
