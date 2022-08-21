package com.example.denteslabv2.ui.Trabalhos.Entrada;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.Clientes;
import com.example.denteslabv2.ui.Servicos.Servicos;
import com.example.denteslabv2.ui.Servicos.ServicosS;
import com.example.denteslabv2.ui.Trabalhos.Entrada.adapter.AdapterPesquisa_cliente;
import com.example.denteslabv2.ui.Trabalhos.Entrada.adapter.AdapterPesquisa_servico;
import com.example.denteslabv2.ui.Trabalhos.TrabalhosFragment;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntradaFragment extends Fragment {

    private SearchView search_cliente,search_servico;
    private List<Clientes> listaClientes;
    private List<ServicosS> listaServicos;
    private List lista,precosListas,idTrabalho,id;
    private DatabaseReference database,clientesRef,servicosRef;
    private RecyclerView Rv_cliente_entrada,Rv_servicos_entrada;
    private AdapterPesquisa_cliente adapterPesquisa_cliente;
    private AdapterPesquisa_servico adapterPesquisa_servico;
    private TextView nome,telefone,endereco,Snome,Spreco;
    private Button salvar;
    private TextInputEditText Qservicos,DataEntrada,PrevisaoS,NomePaciente,Dentes,Cores;
    private EditText ObsT;
    private int Id,Id2;
    private String aux,dataFormada,auxDiasS;
    private TrabalhosFragment trabalhosFragment;
    private String CEP;

    // Lineares de controle de visualização
    private LinearLayout LbuscaCliente,LbuscaServico,LinfoCliente,LinfoServico,LinfoAdc;

    public EntradaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrada, container, false);

        search_cliente = view.findViewById(R.id.sv_busca_cliente);
        search_servico = view.findViewById(R.id.sv_busca_servicos);
        Rv_cliente_entrada = view.findViewById(R.id.rv_clientes_entrada);
        Rv_servicos_entrada = view.findViewById(R.id.rv_busca_servico);
        nome = view.findViewById(R.id.txt_nome_cliente_entrada);
        telefone = view.findViewById(R.id.txt_telefone_cliente_entrada);
        endereco = view.findViewById(R.id.txt_endereco_cliente_entrada);
        Snome = view.findViewById(R.id.txt_nome_servico_entrada);
        Spreco = view.findViewById(R.id.txt_preco_servico_entrada);
        salvar = view.findViewById(R.id.btn_salvar_entrada);
        Qservicos = view.findViewById(R.id.txt_quantidade_servicos_entrada);
        ObsT = view.findViewById(R.id.txt_obs_entrada);
        DataEntrada = view.findViewById(R.id.txt_data_entrada);
        PrevisaoS = view.findViewById(R.id.txt_preivsao_Saida);
        NomePaciente = view.findViewById(R.id.txt_nome_paciente_E);
        Dentes = view.findViewById(R.id.txt_dentes_sel);
        Cores = view.findViewById(R.id.txt_cor_dentes);

        //configuração dos lineares
        LbuscaCliente = view.findViewById(R.id.linearBuscaClienteEntrada);
        LbuscaServico = view.findViewById(R.id.linearBuscaServicoEntrada);
        LinfoCliente = view.findViewById(R.id.linearInfoClienteEntrada);
        LinfoServico = view.findViewById(R.id.linearInfoServicoEntrada);
        LinfoAdc = view.findViewById(R.id.linearInfoAdcEntrada);

        LbuscaCliente.setVisibility(View.VISIBLE);
        LbuscaServico.setVisibility(View.GONE);
        LinfoCliente.setVisibility(View.GONE);
        LinfoServico.setVisibility(View.GONE);
        LinfoAdc.setVisibility(View.GONE);
        salvar.setVisibility(View.GONE);

        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        dataFormada = formatData.format(data);
        DataEntrada.setText(dataFormada);
        int T = Integer.parseInt(dataFormada.substring(0,2))+7;

        if(T>30){
            int auxDias = T-30;
            if(10>auxDias){
               auxDiasS = String.valueOf(0) + String.valueOf(auxDias);
            }
            int auxT = Integer.parseInt(dataFormada.substring(3,5)) +1;
            String auxT2 = dataFormada.substring(6,10);
            String auxData = auxDiasS +"/"+ String.valueOf(auxT) +"/"+ auxT2;
            PrevisaoS.setText(auxData);
        }else{
            String T2 = dataFormada.substring(2,10);
            String T3 = String.valueOf(T) + T2;
            PrevisaoS.setText(T3);
        }

        //Configura a lista usada
        listaClientes = new ArrayList<>();
        listaServicos = new ArrayList<>();
        lista = new ArrayList();
        precosListas = new ArrayList();
        idTrabalho = new ArrayList();
        id = new ArrayList();

        //Configura as referencias do Firebase
        database = FirebaseDatabase.getInstance().getReference();
        clientesRef = database.child("Clientes");
        servicosRef = database.child("Servicos");

        //Configura o Recycler View de clientes
        Rv_cliente_entrada.setHasFixedSize(true);
        Rv_cliente_entrada.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterPesquisa_cliente = new AdapterPesquisa_cliente(listaClientes,getActivity());
        Rv_cliente_entrada.setAdapter(adapterPesquisa_cliente);

        //Configura o Recycler View de servicos
        Rv_servicos_entrada.setHasFixedSize(true);
        Rv_servicos_entrada.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterPesquisa_servico = new AdapterPesquisa_servico(lista,precosListas,getActivity());
        Rv_servicos_entrada.setAdapter(adapterPesquisa_servico);

        search_cliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String textoDigitado = newText.toUpperCase();
                pesquisarClientes( textoDigitado );
                return true;
            }
        });

        search_servico.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String textoDigitado = newText.toUpperCase();
                pesquisarServicos(textoDigitado);

                return true;
            }
        });

        Rv_cliente_entrada.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_cliente_entrada,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Clientes clientes = listaClientes.get(position);

                                LbuscaCliente.setVisibility(View.GONE);
                                LinfoCliente.setVisibility(View.VISIBLE);
                                LbuscaServico.setVisibility(View.VISIBLE);

                                clienteSelecionado(clientes,view);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        Rv_servicos_entrada.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_servicos_entrada,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String Nservico = lista.get(position).toString();
                                String Pservico = precosListas.get(position).toString();

                                LinfoServico.setVisibility(View.VISIBLE);
                                LbuscaServico.setVisibility(View.GONE);
                                LinfoAdc.setVisibility(View.VISIBLE);
                                salvar.setVisibility(View.VISIBLE);

                                servicoSelecionado(Nservico,Pservico);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nome.getText() != "" && telefone.getText() != "" && endereco.getText() != ""
                        && Snome.getText() != "" && Spreco.getText() != ""
                        && !Qservicos.getText().toString().equals("")
                        && !DataEntrada.getText().toString().equals("")
                        && !PrevisaoS.getText().toString().equals("")
                        && !NomePaciente.getText().toString().equals("")
                        && !Dentes.getText().toString().equals("")
                        && !Cores.getText().toString().equals("")
                        && !ObsT.getText().toString().equals("")
                        && !Dentes.getText().toString().equals("")) {

                    salvarEntrada(v);

                }else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Complete todos os campos",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public  void pesquisarClientes(String text){

        //limpar lista
        listaClientes.clear();

        //Pesquisa clientes caso tenha texto na pesquisa
        if(text.length() > 0){

            Query query = clientesRef.orderByChild("Nome_pesquisa")
                    .startAt(text)
                    .endAt(text + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        listaClientes.add(ds.getValue(Clientes.class));
                    }

                    adapterPesquisa_cliente.notifyDataSetChanged();

                    /*int total = listaClientes.size();
                    Log.i("INFO","Total: "+total);*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void pesquisarServicos(String text){

    //limpar lista
        //listaServicos.clear();
        lista.clear();
        precosListas.clear();

        //Pesquisa servicos caso tenha texto
        if(text.length() > 0){

            DatabaseReference clienteRef = database.child("Clientes");

            Query query = clienteRef.orderByChild("Nome_pesquisa")
                    .startAt(text)
                    .endAt(text + "\uf88f");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Query query1 = clienteRef.child(nome.getText().toString()).child("Tabela")
                                .orderByChild("Nome_pesquisa")
                                .startAt(text)
                                .endAt(text + "\uf88f");

                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    lista.add(ds.child("Nome").getValue());
                                    precosListas.add(ds.child("Preco").getValue());
                                    Log.i("INFO", "TABELA: " + ds.child("Nome").getValue());
                                }
                                adapterPesquisa_servico.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void clienteSelecionado(Clientes clientes,View view){

        Log.i("INFO","TESTE: "+clientes.getNome());

        nome.setText(clientes.getNome());
        telefone.setText(clientes.getTelefone());
        endereco.setText(clientes.getEndereco());

        search_cliente.clearFocus();
        search_cliente.setQuery("",false);

    }

    private void servicoSelecionado(String n, String s){

        Log.i("INFO","TESTE: "+n);

        Snome.setText(n);
        Spreco.setText(s);

        search_servico.clearFocus();
        search_servico.setQuery("",false);

    }

    private void salvarEntrada(View view){

        idTrabalho.clear();

            String Cnome,Ctelefone,Cendereco, nomeS,Obs,total,Data,Previsao,Paciente,DentesE,CoresE;
            int precoS,quantidade,total2;

            Cnome = nome.getText().toString();
            Ctelefone = telefone.getText().toString();
            Cendereco = endereco.getText().toString();
            nomeS = Snome.getText().toString();
            precoS = Integer.parseInt(Spreco.getText().toString());
            quantidade = Integer.parseInt(Qservicos.getText().toString());
            Obs = ObsT.getText().toString();
            Data = DataEntrada.getText().toString();
            Previsao = PrevisaoS.getText().toString();
            Paciente = NomePaciente.getText().toString();
            DentesE = Dentes.getText().toString();
            CoresE = Cores.getText().toString();

            total2 = precoS * quantidade;
            total = String.valueOf(total2);

            DatabaseReference trabalhosRef = database.child("Trabalhos");
            DatabaseReference saidasRef = database.child("Saidas");

            Query query = trabalhosRef.orderByChild("Id");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        idTrabalho.add(ds.child("ID").getValue());
                    }
                    if(idTrabalho.size() == 0){
                        Id = 0;
                        aux = String.valueOf(Id +1);
                    }else {
                        Id = Integer.parseInt(idTrabalho.get(idTrabalho.size() - 1).toString());
                        Log.i("INFO", "Ultimo ID: " + Id);
                        aux = String.valueOf(Id + 1);
                    }

                    DatabaseReference trabalhosRef2 = trabalhosRef.child(aux);

                    trabalhosRef2.child("ID").setValue(aux);
                    trabalhosRef2.child("Nome_cliente").setValue(Cnome);
                    trabalhosRef2.child("Telefone_cliente").setValue(Ctelefone);
                    trabalhosRef2.child("Endereco_cliente").setValue(Cendereco);
                    trabalhosRef2.child("Nome_servico").setValue(nomeS);
                    trabalhosRef2.child("Preco_servico").setValue(precoS);
                    trabalhosRef2.child("Total").setValue(total);
                    trabalhosRef2.child("Observacao").setValue(Obs);
                    trabalhosRef2.child("Data_entrada").setValue(Data);
                    trabalhosRef2.child("Previsao_saida").setValue(Previsao);
                    trabalhosRef2.child("Nome_paciente").setValue(Paciente);
                    trabalhosRef2.child("Dentes").setValue(DentesE);
                    trabalhosRef2.child("Cor").setValue(CoresE);

                    nome.setText("");
                    telefone.setText("");
                    endereco.setText("");
                    Spreco.setText("");
                    Snome.setText("");
                    Qservicos.setText("");
                    ObsT.setText("");
                    DataEntrada.setText("");
                    PrevisaoS.setText("");
                    NomePaciente.setText("");
                    Cores.setText("");

                    Intent intent = new Intent(getActivity(),NotaLabActivity.class);

                    intent.putExtra("Id",aux);
                    intent.putExtra("Entrada",Data);
                    intent.putExtra("Saida",Previsao);
                    intent.putExtra("TipoTrabalho",nomeS);
                    intent.putExtra("Dentes",DentesE);
                    intent.putExtra("Cor",CoresE);
                    intent.putExtra("Cliente",Cnome);
                    intent.putExtra("Paciente",Paciente);
                    intent.putExtra("Obs",Obs);

                    startActivity(intent);

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sucesso ao salvar",
                            Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}
