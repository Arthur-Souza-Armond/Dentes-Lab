package com.example.denteslabv2.ui.Principal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.MainActivity;
import com.example.denteslabv2.PrincipalActivity;
import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.ADM1.ADMmensagem.AdapterMensagens;
import com.example.denteslabv2.ui.ADM1.ADMmensagem.Mensagem;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalFragment extends Fragment {

    private int NivelUsuario;
    private String NomeUsuario;
    private String USUARIO,NOME;
    private ProgressBar CarregarPB;
    private RecyclerView Rv_Mensagem;
    private ImageView ok;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private List Titulo,Corpo,IdICon,IDMensagem;
    private AdapterMensagens adapterMensagens;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        USUARIO = "";

        CarregarPB = view.findViewById(R.id.pb_carregar_mensagens);
        Rv_Mensagem = view.findViewById(R.id.rv_mensagens);
        ok = view.findViewById(R.id.img_fechar_notificacao);

        Rv_Mensagem.setVisibility(View.GONE);
        CarregarPB.setVisibility(View.VISIBLE);
        ok.setVisibility(View.GONE);

        Intent intent = getActivity().getIntent();

        Rv_Mensagem.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity(),
                        Rv_Mensagem,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                DeletarNotificacao(IDMensagem.get(position).toString());

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                ));

        USUARIO = (String) intent.getSerializableExtra("Email");

        DatabaseReference data = database.child("Usuarios");

        Query query = data.orderByChild("E-mail")
                .startAt(USUARIO)
                .endAt(USUARIO + "\uf88f");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    Log.i("INFO","TESTEEE: "+ds.child("Nome").getValue().toString());

                    NOME = ds.child("Nome").getValue().toString();
                }

                carregarRecycler(NOME);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    public void carregarNome(){

        PrincipalActivity principalActivity = new PrincipalActivity();

    }

    private void carregarRecycler(String NOME){

        Titulo = new ArrayList();
        Corpo = new ArrayList();
        IdICon = new ArrayList();
        IDMensagem = new ArrayList();

        adapterMensagens = new AdapterMensagens(Titulo,Corpo,IdICon,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_Mensagem.setLayoutManager(layoutManager);
        Rv_Mensagem.setHasFixedSize(true);
        Rv_Mensagem.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        Rv_Mensagem.setAdapter( adapterMensagens );

        DatabaseReference data = database.child("Usuarios").child(NOME).child("Mensagens");

        Query query1 = data.orderByChild("ID_Mensagem");

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Titulo.add(ds.child("Titulo").getValue().toString());
                    Corpo.add(ds.child("Corpo").getValue().toString());
                    IdICon.add(Integer.parseInt(ds.child("Id_Icone").getValue().toString()));
                    IDMensagem.add(ds.child("ID_Mensagem").getValue().toString());
                }
                adapterMensagens.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Rv_Mensagem.setVisibility(View.VISIBLE);
        CarregarPB.setVisibility(View.GONE);

    }

    private void DeletarNotificacao(String titulo){

        ok.setVisibility(View.VISIBLE);

        DatabaseReference data = database.child("Usuarios").child(NOME).child("Mensagens").child(titulo);

        data.removeValue();

        carregarRecycler(NOME);

    }

}