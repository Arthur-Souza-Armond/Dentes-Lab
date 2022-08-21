package com.example.denteslabv2.ui.ADM1.DB;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.ADM1.DB.adapters.AdapterNo1DB;
import com.example.denteslabv2.ui.ADM1.DB.adapters.AdapterNo2Cliente;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBAdmFragment extends Fragment {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Configurar elementos XML
    private Button BtnClientes,BtnSaidas,BtnUsuarios,BtnRelatorios;
    private LinearLayout Lclientes,Lsaidas,Lusuarios,Lrelatorios;

    //Comuim nó1
    private List No1,AUX;
    private AdapterNo1DB adapterNo1DB;

    //Linear Clientes
    private RecyclerView Rv_clientes,Rv_cliente_detalhe;
    private String Area,Cep,Cpf,Endereco,Nome,NomeSecretaria,Referencia,Telefone,TelefoneSecretaria;
    private List tabelaNome,tabelaPreco;
    private AdapterNo2Cliente adapterNo2Cliente;
    private LinearLayout LinearClienteDetalhe;

    //Linear Saidas
    private RecyclerView Rv_saidas;
    private LinearLayout LinearSaidasDetalhe;

    //Linear Usuarios
    private RecyclerView Rv_usuarios;
    private LinearLayout LinearUsuariosDetalhe;

    //Linear Relatorios
    private RecyclerView Rv_relatorios;
    private LinearLayout LinearRelatoriosDetalhe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d_b_adm, container, false);

        //Configurando os XML ----------------------------------------------------------------------

        BtnClientes = view.findViewById(R.id.btn_clientesLista);
        BtnSaidas = view.findViewById(R.id.btn_SaidasLista);
        BtnUsuarios = view.findViewById(R.id.btn_usuariosLista);
        BtnRelatorios = view.findViewById(R.id.btn_relatorioLista);

        Lclientes = view.findViewById(R.id.linearClientes);
        Lsaidas = view.findViewById(R.id.linearSaidas);
        Lusuarios = view.findViewById(R.id.linearUsuarios);
        Lrelatorios = view.findViewById(R.id.linearRelatorios);
        LinearClienteDetalhe = view.findViewById(R.id.linearClienteDetalhe);
        LinearSaidasDetalhe = view.findViewById(R.id.linearSaidasDetalhe);
        LinearUsuariosDetalhe = view.findViewById(R.id.linearUsuarioDetalhes);
        LinearRelatoriosDetalhe = view.findViewById(R.id.linearRelatoriosDetalhe);

        //Configurando Lineares
        Lclientes.setVisibility(View.GONE);
        Lsaidas.setVisibility(View.GONE);
        Lusuarios.setVisibility(View.GONE);
        Lrelatorios.setVisibility(View.GONE);

        LinearClienteDetalhe.setVisibility(View.GONE);
        LinearSaidasDetalhe.setVisibility(View.GONE);
        LinearUsuariosDetalhe.setVisibility(View.GONE);
        LinearRelatoriosDetalhe.setVisibility(View.GONE);

        //Configuração dos recyclers
        Rv_clientes = view.findViewById(R.id.rv_clientes_db);
        Rv_saidas = view.findViewById(R.id.rv_saidas_db);
        Rv_usuarios = view.findViewById(R.id.rv_usuarios_db);
        Rv_relatorios = view.findViewById(R.id.rv_relatorios_db);

        Rv_cliente_detalhe = view.findViewById(R.id.Rv_cliente_detalhe);

        BtnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lclientes.setVisibility(View.VISIBLE);
                Lsaidas.setVisibility(View.GONE);
                Lusuarios.setVisibility(View.GONE);
                Lrelatorios.setVisibility(View.GONE);

                carregarCLientes();

                Rv_clientes.addOnItemTouchListener(
                        new RecyclerItemClickListnner(
                                getActivity(),
                                Rv_clientes,
                                new RecyclerItemClickListnner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        LinearClienteDetalhe.setVisibility(View.VISIBLE);
                                        LinearSaidasDetalhe.setVisibility(View.GONE);
                                        LinearUsuariosDetalhe.setVisibility(View.GONE);
                                        LinearRelatoriosDetalhe.setVisibility(View.GONE);

                                        carregarClienteEspecifico(position);

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );

            }
        });

        BtnSaidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lclientes.setVisibility(View.GONE);
                Lsaidas.setVisibility(View.VISIBLE);
                Lusuarios.setVisibility(View.GONE);
                Lrelatorios.setVisibility(View.GONE);

                carregarSaidas();

                Rv_clientes.addOnItemTouchListener(
                        new RecyclerItemClickListnner(
                                getActivity(),
                                Rv_clientes,
                                new RecyclerItemClickListnner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        LinearClienteDetalhe.setVisibility(View.GONE);
                                        LinearSaidasDetalhe.setVisibility(View.VISIBLE);
                                        LinearUsuariosDetalhe.setVisibility(View.GONE);
                                        LinearRelatoriosDetalhe.setVisibility(View.GONE);

                                        carregarClienteEspecifico(position);

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );

            }
        });

        BtnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lclientes.setVisibility(View.GONE);
                Lsaidas.setVisibility(View.GONE);
                Lusuarios.setVisibility(View.VISIBLE);
                Lrelatorios.setVisibility(View.GONE);

                carregarUsuarios();

                Rv_clientes.addOnItemTouchListener(
                        new RecyclerItemClickListnner(
                                getActivity(),
                                Rv_clientes,
                                new RecyclerItemClickListnner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        LinearClienteDetalhe.setVisibility(View.GONE);
                                        LinearSaidasDetalhe.setVisibility(View.GONE);
                                        LinearUsuariosDetalhe.setVisibility(View.VISIBLE);
                                        LinearRelatoriosDetalhe.setVisibility(View.GONE);

                                        carregarClienteEspecifico(position);

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );

            }
        });

        BtnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lclientes.setVisibility(View.GONE);
                Lsaidas.setVisibility(View.GONE);
                Lusuarios.setVisibility(View.GONE);
                Lrelatorios.setVisibility(View.VISIBLE);

                carregarRelatorios();

                Rv_clientes.addOnItemTouchListener(
                        new RecyclerItemClickListnner(
                                getActivity(),
                                Rv_clientes,
                                new RecyclerItemClickListnner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        LinearClienteDetalhe.setVisibility(View.GONE);
                                        LinearSaidasDetalhe.setVisibility(View.GONE);
                                        LinearUsuariosDetalhe.setVisibility(View.GONE);
                                        LinearRelatoriosDetalhe.setVisibility(View.VISIBLE);

                                        carregarClienteEspecifico(position);

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );

            }
        });

        return view;
    }

    private void carregarCLientes(){

        No1 = new ArrayList();

        adapterNo1DB = new AdapterNo1DB(No1,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_clientes.setLayoutManager(layoutManager);
        Rv_clientes.setHasFixedSize(true);
        Rv_clientes.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_clientes.setAdapter( adapterNo1DB );

        DatabaseReference data = database.child("Clientes");

        Query query = data.orderByChild("Nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    No1.add(ds.child("Nome").getValue());
                }
                adapterNo1DB.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarSaidas(){

        No1 = new ArrayList();

        adapterNo1DB = new AdapterNo1DB(No1,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_saidas.setLayoutManager(layoutManager);
        Rv_saidas.setHasFixedSize(true);
        Rv_saidas.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_saidas.setAdapter( adapterNo1DB );

        DatabaseReference data = database.child("Saidas");

        Query query = data.orderByChild("ID_trabalho");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    No1.add(ds.child("ID_trabalho").getValue());
                }
                adapterNo1DB.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarUsuarios(){

        No1 = new ArrayList();

        adapterNo1DB = new AdapterNo1DB(No1,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_usuarios.setLayoutManager(layoutManager);
        Rv_usuarios.setHasFixedSize(true);
        Rv_usuarios.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_usuarios.setAdapter( adapterNo1DB );

        DatabaseReference data = database.child("Usuarios");

        Query query = data.orderByChild("Nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    No1.add(ds.child("Nome").getValue());
                }
                adapterNo1DB.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarRelatorios(){

        No1 = new ArrayList();

        adapterNo1DB = new AdapterNo1DB(No1,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_clientes.setLayoutManager(layoutManager);
        Rv_clientes.setHasFixedSize(true);
        Rv_clientes.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_clientes.setAdapter( adapterNo1DB );

        DatabaseReference data = database.child("Relatorios").child("Movimentacoes");

        Query query = data.orderByChild("Nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    No1.add(ds.child("Nome").getValue());
                }
                adapterNo1DB.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarClienteEspecifico(int position){

        Nome = "";

        adapterNo2Cliente = new AdapterNo2Cliente(Area,Cep,Cpf,Endereco,Nome,NomeSecretaria,Referencia,Telefone,TelefoneSecretaria,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_cliente_detalhe.setLayoutManager(layoutManager);
        Rv_cliente_detalhe.setHasFixedSize(true);
        Rv_cliente_detalhe.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv_cliente_detalhe.setAdapter( adapterNo2Cliente );

        String Titulo = No1.get(position).toString();

        Log.i("INFO","TESTAO: "+Titulo);

        DatabaseReference data = database.child("Clientes").child(Titulo);

        Query query = data.orderByChild("Nome")
                .startAt(Titulo)
                .endAt(Titulo +"\uf88f");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Area = ds.child("Area").getValue().toString();
                    Cep = ds.child("CEP").getValue().toString();
                    Cpf = ds.child("CPF").getValue().toString();
                    Endereco = ds.child("Endereco").getValue().toString();
                    Nome = ds.child("Nome").getValue().toString();
                    NomeSecretaria = ds.child("Nome_secretaria").getValue().toString();
                    Referencia = ds.child("Referencia").getValue().toString();
                    Telefone = ds.child("Telefone").getValue().toString();
                    TelefoneSecretaria = ds.child("Telefone_secretaria").getValue().toString();
                }
                adapterNo2Cliente.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}