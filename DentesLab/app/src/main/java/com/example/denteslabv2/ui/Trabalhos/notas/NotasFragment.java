package com.example.denteslabv2.ui.Trabalhos.notas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.Clientes;
import com.example.denteslabv2.ui.Clientes.adapter.ClientesAdapter;
import com.example.denteslabv2.ui.Clientes.adapter.TrabalhosClientesAdapter;
import com.example.denteslabv2.ui.Trabalhos.Entrada.adapter.AdapterPesquisa_cliente;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class NotasFragment extends Fragment {

    private SearchView sv_clientes, sv_saidas;
    private RecyclerView Rv_clientes,Rv_saidas;
    private Button Imprimir;

    private List<Clientes> listaClientes;
    private ClientesAdapter clientesAdapter;

    private List NOME,DATA,VALOR,ID;
    private TrabalhosClientesAdapter trabalhosClientesAdapter;

    //Variaveis auxiliares
    private String Nome,Endereco,Cep,Telefone,Id,DataEntrada,DataSaida,Paciente,TipoTrabalho,PrecoTrabalho,Total,IdCliente;
    private int Contador;

    //Textview
    private TextView NomeCliente,IdSaida;

    private CardView cardView2,cardView1;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Configurando o searchview
    private MaterialSearchView searchView;

    //Configurando os ImageButtons
    private ImageButton AdcionarCliente;
    private ImageButton AdcionarTrabalho;

    //Configurando notinha
    private TextView NomeC,EnderecoC,CepC,TelefoneC,IdT,DEntradaT,DSaidaT,DPacienteT,NomeT,PrecoT,ValorTotal;
    //SegundoTrabalho
    private TextView IdT2,DEntradaT2,DSaidaT2,DPacienteT2,NomeT2,PrecoT2,ValorTotal2;
    //TerceiroTrabalho
    private TextView IdT3,DEntradaT3,DSaidaT3,DPacienteT3,NomeT3,PrecoT3,ValorTotal3;

    //Lineares
    private LinearLayout linear1,linear2,linear3,LINEARNOTA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        // Configurando elementos XML --------------------------------------------------------------
        sv_clientes = view.findViewById(R.id.sv_busca_clienteNotas);
        sv_saidas = view.findViewById(R.id.sv_busca_saidas);

        Rv_clientes = view.findViewById(R.id.rv_lista_clientesNotas);
        Rv_saidas = view.findViewById(R.id.rv_lista_saidasNotas);

        NomeCliente = view.findViewById(R.id.txt_nome_cliente_notas);
        IdSaida = view.findViewById(R.id.txt_saida_notas);

        Imprimir = view.findViewById(R.id.btn_imprimir_nota);

        cardView2 = view.findViewById(R.id.cadview2);
        cardView2.setVisibility(View.GONE);
        cardView1 = view.findViewById(R.id.cadview);

        AdcionarCliente = view.findViewById(R.id.btn_adcionarClienteNotaS);
        AdcionarTrabalho = view.findViewById(R.id.btn_adcionarNovoTrabalhoS);

        NomeC = view.findViewById(R.id.txt_nomeNotinha);
        EnderecoC = view.findViewById(R.id.txt_enderecoNotinha);
        CepC = view.findViewById(R.id.txt_cepNotinha);
        TelefoneC = view.findViewById(R.id.txt_telefoneNotinha);
        IdT = view.findViewById(R.id.txt_idNotinha);
        DEntradaT = view.findViewById(R.id.txt_dentradaNotinha);
        DSaidaT = view.findViewById(R.id.txt_dsaidaNotinha);
        DPacienteT = view.findViewById(R.id.txt_pacienteNotinha);
        NomeT = view.findViewById(R.id.txt_nometrabalhoNotinha1);
        PrecoT = view.findViewById(R.id.txt_precoNotinha1);
        ValorTotal = view.findViewById(R.id.txt_totalNotinha);

        //Segundo trabalho
        IdT2 = view.findViewById(R.id.txt_idNotinha2);
        DEntradaT2 = view.findViewById(R.id.txt_dentradaNotinha2);
        DSaidaT2 = view.findViewById(R.id.txt_dsaidaNotinha2);
        DPacienteT2 = view.findViewById(R.id.txt_pacienteNotinha2);
        NomeT2 = view.findViewById(R.id.txt_nometrabalhoNotinha2);
        PrecoT2 = view.findViewById(R.id.txt_precoNotinha2);

        //Terceiro trabalho
        IdT3 = view.findViewById(R.id.txt_idNotinha3);
        DEntradaT3 = view.findViewById(R.id.txt_dentradaNotinha3);
        DSaidaT3 = view.findViewById(R.id.txt_dsaidaNotinha3);
        DPacienteT3 = view.findViewById(R.id.txt_pacienteNotinha3);
        NomeT3 = view.findViewById(R.id.txt_nometrabalhoNotinha3);
        PrecoT3 = view.findViewById(R.id.txt_precoNotinha3);

        linear1 = view.findViewById(R.id.t1);
        linear2 = view.findViewById(R.id.t2);
        linear3 = view.findViewById(R.id.t3);

        LINEARNOTA = view.findViewById(R.id.linearNotaInteira);

        linear1.setVisibility(View.GONE);
        linear2.setVisibility(View.GONE);
        linear3.setVisibility(View.GONE);

        Contador = 0;

        AdcionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cardView1.setVisibility(View.VISIBLE);
                Rv_clientes.setVisibility(View.VISIBLE);
                sv_clientes.setVisibility(View.VISIBLE);

            }
        });

        AdcionarTrabalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NomeCliente.getText().toString().equals(".")){

                    Toast.makeText(
                            getActivity(),
                            "Você precisa adcionar um cliente primeiro",
                            Toast.LENGTH_LONG
                    ).show();

                }else {
                    cardView2.setVisibility(View.VISIBLE);
                    Rv_saidas.setVisibility(View.VISIBLE);
                    sv_saidas.setVisibility(View.VISIBLE);
                }

            }
        });

        sv_clientes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String textoDigitado = s.toUpperCase();
                pesquisarClientes(textoDigitado);
                return true;
            }
        });

        sv_saidas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String textoDigitado = s.toUpperCase();
                pesquisarSaidas(textoDigitado);
                return true;
            }
        });

        Rv_clientes.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity(),
                        Rv_clientes,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Clientes clientes = new Clientes();

                                clientes = listaClientes.get(position);

                                Nome = clientes.getNome();
                                Endereco = clientes.getEndereco();
                                Cep = clientes.getCEP();
                                Telefone = clientes.getTelefone();

                                sv_clientes.clearFocus();
                                NomeCliente.setText(Nome);

                                NomeC.setText(Nome);
                                EnderecoC.setText(Endereco);
                                CepC.setText(Cep);
                                TelefoneC.setText(Telefone);

                                cardView1.setVisibility(View.GONE);
                                Rv_clientes.setVisibility(View.GONE);
                                sv_clientes.setVisibility(View.GONE);

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

        Rv_saidas.addOnItemTouchListener(new RecyclerItemClickListnner(
                getActivity(),
                Rv_saidas,
                new RecyclerItemClickListnner.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Contador = Contador + 1;

                        if(Contador <= 3 && Contador > 0){

                            String id = ID.get(position).toString();

                            DatabaseReference data = database.child("Saidas");

                            Query query = data.orderByChild("ID_trabalho")
                                    .startAt(id)
                                    .endAt(id + "\uf88f");

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds : snapshot.getChildren()){
                                        Id = ds.child("ID_trabalho").getValue().toString();
                                        DataEntrada = ds.child("Data_entrada").getValue().toString();
                                        DataSaida = ds.child("Data_saida").getValue().toString();
                                        Paciente = ds.child("Paciente").getValue().toString();
                                        TipoTrabalho = ds.child("Nome_servico").getValue().toString();
                                        PrecoTrabalho = ds.child("Total").getValue().toString();
                                    }

                                    sv_saidas.clearFocus();

                                    if(Contador == 1){

                                        IdSaida.setText(Id);

                                        linear1.setVisibility(View.VISIBLE);

                                        IdT.setText(Id);
                                        DEntradaT.setText(DataEntrada);
                                        DSaidaT.setText(DataSaida);
                                        DPacienteT.setText(Paciente);
                                        NomeT.setText(TipoTrabalho);
                                        PrecoT.setText(PrecoTrabalho);

                                        List valores = new ArrayList();
                                        valores.add(PrecoT.getText().toString());

                                        int total = 0;

                                        total = calcularTotal(Contador,valores);
                                        ValorTotal.setText("R$ "+String.valueOf(total));

                                    }else if(Contador == 2){

                                        IdSaida.append(", "+Id);

                                        linear2.setVisibility(View.VISIBLE);

                                        IdT2.setText(Id);
                                        DEntradaT2.setText(DataEntrada);
                                        DSaidaT2.setText(DataSaida);
                                        DPacienteT2.setText(Paciente);
                                        NomeT2.setText(TipoTrabalho);
                                        PrecoT2.setText(PrecoTrabalho);

                                        List valores = new ArrayList();
                                        valores.add(PrecoT.getText().toString());
                                        valores.add(PrecoT2.getText().toString());

                                        int total = 0;

                                        total = calcularTotal(Contador,valores);
                                        ValorTotal.setText("R$ "+String.valueOf(total));

                                    }else if(Contador == 3){

                                        IdSaida.append(", "+Id);

                                        linear3.setVisibility(View.VISIBLE);

                                        IdT3.setText(Id);
                                        DEntradaT3.setText(DataEntrada);
                                        DSaidaT3.setText(DataSaida);
                                        DPacienteT3.setText(Paciente);
                                        NomeT3.setText(TipoTrabalho);
                                        PrecoT3.setText(PrecoTrabalho);

                                        List valores = new ArrayList();
                                        valores.add(PrecoT.getText().toString());
                                        valores.add(PrecoT2.getText().toString());
                                        valores.add(PrecoT3.getText().toString());

                                        int total = 0;

                                        total = calcularTotal(Contador,valores);
                                        ValorTotal.setText("R$ "+String.valueOf(total));

                                    }

                                    cardView2.setVisibility(View.GONE);
                                    Rv_saidas.setVisibility(View.GONE);
                                    sv_saidas.setVisibility(View.GONE);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }else if(Contador > 3){

                            Toast.makeText(
                                    getActivity(),
                                    "Você já selecionou 3 trabalhos",
                                    Toast.LENGTH_LONG
                            ).show();

                            Contador = Contador - 1;

                            cardView2.setVisibility(View.GONE);
                            Rv_saidas.setVisibility(View.GONE);
                            sv_saidas.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        Imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(Contador == 1){

                        if(!NomeC.getText().toString().equals("") && !EnderecoC.getText().toString().equals("") && !CepC.getText().toString().equals("") &&
                                !TelefoneC.getText().toString().equals("") && !IdT.getText().toString().equals("") && !DEntradaT.getText().toString().equals("") &&
                        !DSaidaT.getText().toString().equals("") && !DPacienteT.getText().toString().equals("") && !NomeT.getText().toString().equals("") &&
                        !PrecoT.getText().toString().equals("")){

                            LINEARNOTA.setDrawingCacheEnabled(true);
                            LINEARNOTA.getWidth();
                            LINEARNOTA.getHeight();
                            //LINEARNOTA.buildDrawingCache(true);
                            Bitmap bitmap = Bitmap.createBitmap(LINEARNOTA.getDrawingCache());
                            PrintHelper printHelper = new PrintHelper(getActivity());
                            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);

                            printHelper.printBitmap("nota_cliente_"+IdT.getText().toString()+".jpg",bitmap);

                        }else{

                            Toast.makeText(
                                    getActivity(),
                                    "Complete todos os campos nescessários",
                                    Toast.LENGTH_LONG).show();

                        }

                    }else if(Contador == 2){

                        if(!NomeC.getText().toString().equals("") && !EnderecoC.getText().toString().equals("") && !CepC.getText().toString().equals("") &&
                                !TelefoneC.getText().toString().equals("") && !IdT.getText().toString().equals("") && !IdT2.getText().toString().equals("") &&
                        !DEntradaT.getText().toString().equals("") && !DEntradaT2.getText().toString().equals("") && !DSaidaT.getText().toString().equals("") &&
                        !DSaidaT2.getText().toString().equals("") && !DPacienteT.getText().toString().equals("") && !DPacienteT2.getText().toString().equals("") &&
                        !NomeT.getText().toString().equals("") && !NomeT2.getText().toString().equals("") && !PrecoT.getText().toString().equals("") &&
                        !PrecoT2.getText().toString().equals("")){

                            LINEARNOTA.setDrawingCacheEnabled(true);
                            LINEARNOTA.getWidth();
                            LINEARNOTA.getHeight();
                            //LINEARNOTA.buildDrawingCache(true);
                            Bitmap bitmap2 = Bitmap.createBitmap(LINEARNOTA.getDrawingCache());
                            PrintHelper printHelper2 = new PrintHelper(getActivity());
                            printHelper2.setScaleMode(PrintHelper.SCALE_MODE_FIT);

                            printHelper2.printBitmap("nota_cliente_"+IdT.getText().toString()+"_"+IdT2.getText().toString()+".jpg",bitmap2);

                        }else{

                            Toast.makeText(
                                    getActivity(),
                                    "Complete todos os campos nescessários",
                                    Toast.LENGTH_LONG).show();

                        }

                    }if(Contador == 3){

                        if(!NomeC.getText().toString().equals("") && !EnderecoC.getText().toString().equals("") && !CepC.getText().toString().equals("") &&
                                !TelefoneC.getText().toString().equals("") && !IdT.getText().toString().equals("") && !IdT2.getText().toString().equals("") &&
                                !IdT3.getText().toString().equals("") && !DEntradaT.getText().toString().equals("") && !DEntradaT2.getText().toString().equals("") &&
                                !DEntradaT3.getText().toString().equals("") && !DSaidaT.getText().toString().equals("") && !DSaidaT2.getText().toString().equals("") &&
                                !DSaidaT3.getText().toString().equals("") && !DPacienteT.getText().toString().equals("") && !DPacienteT2.getText().toString().equals("") &&
                                !DPacienteT3.getText().toString().equals("") && !NomeT.getText().toString().equals("") && !NomeT2.getText().toString().equals("") &&
                                !NomeT3.getText().toString().equals("") && !PrecoT.getText().toString().equals("") && !PrecoT2.getText().toString().equals("") && !PrecoT3.getText()
                                .toString().equals("")) {

                            LINEARNOTA.setDrawingCacheEnabled(true);
                            LINEARNOTA.getWidth();
                            LINEARNOTA.getHeight();
                            //LINEARNOTA.buildDrawingCache(true);
                            Bitmap bitmap3 = Bitmap.createBitmap(LINEARNOTA.getDrawingCache());
                            PrintHelper printHelper3 = new PrintHelper(getActivity());
                            printHelper3.setScaleMode(PrintHelper.SCALE_MODE_FIT);

                            printHelper3.printBitmap("nota_cliente_" + IdT.getText().toString() + "_" + IdT2.getText().toString() + "_" + IdT3.getText().toString() + ".jpg", bitmap3);

                        }else{

                            Toast.makeText(
                                    getActivity(),
                                    "Complete todos os campos nescessários",
                                    Toast.LENGTH_LONG).show();

                        }

                    }
            }
        });

        return view;
    }

    private void pesquisarClientes(String texto) {

        DatabaseReference clientesRef = database.child("Clientes");

        //limpar lista
        listaClientes = new ArrayList<>();

        clientesAdapter = new ClientesAdapter(listaClientes, getActivity());

        //Configura o Recycler View de clientes
        Rv_clientes.setHasFixedSize(true);
        Rv_clientes.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_clientes.setAdapter(clientesAdapter);

        //Pesquisa clientes caso tenha texto na pesquisa
        if (texto.length() > 0) {

            Query query = clientesRef.orderByChild("Nome_pesquisa")
                    .startAt(texto)
                    .endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        listaClientes.add(ds.getValue(Clientes.class));
                    }

                    clientesAdapter.notifyDataSetChanged();

                    /*int total = listaClientes.size();
                    Log.i("INFO","Total: "+total);*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void pesquisarSaidas(String texto){

       DatabaseReference data = database.child("Saidas");

       //Limpa listas
        NOME = new ArrayList();
        DATA = new ArrayList();
        VALOR = new ArrayList();
        ID = new ArrayList();

        //Adapter
        trabalhosClientesAdapter = new TrabalhosClientesAdapter(NOME,DATA,VALOR,ID,getActivity());

        //Configura o Recycler View de saidas
        Rv_saidas.setHasFixedSize(true);
        Rv_saidas.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_saidas.setAdapter(trabalhosClientesAdapter);

        if(texto.length() > 0){

            Query query = data.orderByChild("ID_trabalho")
                    .startAt(texto)
                    .endAt(texto + "\uf88f");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Log.i("INFO",ds.child("Nome_cliente").getValue().toString());
                        if(Nome.equals(ds.child("Nome_cliente").getValue())){
                            NOME.add(ds.child("Nome_cliente").getValue());
                            DATA.add(ds.child("Data_saida").getValue());
                            VALOR.add(ds.child("Total").getValue());
                            ID.add(ds.child("ID_trabalho").getValue());
                        }
                    }
                    trabalhosClientesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private int calcularTotal(int contador,List valores){

        int somador = 0;

        for(int i=0;i<contador;i++){

            somador = somador + Integer.parseInt(valores.get(i).toString());

        }

        return  somador;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_notinha_saida,menu);

        MenuItem item = menu.findItem(R.id.menu_pesquisar);
        searchView.setMenuItem(  item  );

    }
}