package com.example.denteslabv2.ui.Trabalhos.saida;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.Clientes;
import com.example.denteslabv2.ui.Colaboradores.adapter.Colabadapter;
import com.example.denteslabv2.ui.Colaboradores.colabInfo.adapter.AdapterPrecosColab;
import com.example.denteslabv2.ui.Trabalhos.lab.adapter.AdapterTrabalhos;
import com.example.denteslabv2.ui.Trabalhos.saida.adapter.Adapter_saida;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaidaFragment extends Fragment {

    private RecyclerView Rv_trabalhos;
    private SearchView sv_trabalhos;
    private List ID,TIPO,DATA,CLIENTE,TOTAL,OBS,AUX,PREVISAO,PACIENTE;
    private Adapter_saida adapter_saida;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private TextInputEditText Id,Cliente,Tipo,Data,Total,Obs,PacienteText;
    private Button Salvar;
    private String ID2,TIPO2,DATA2,CLIENTE2,TOTAL2,OBS2,aux,PREVISAO2,DENTES2,COR2,Saida,Paciente,PAciente;
    private int IdAux;

    //Parte dos colaboradores
    private SearchView sv_colab;
    private RecyclerView Rv_colab;
    private List NOME_COLAB,SETOR_COLAB;
    private List Colabs;
    private Colabadapter colabadapter;
    private TextView nomeColab;
    private int auxColab;

    //Selecionar servicos colabs
    private RecyclerView Rv_1,Rv_2,Rv_3;
    private TextView nome1,nome2,nome3;
    private LinearLayout linear1,linear2,linear3;
    private List NoME,PrECO;
    private AdapterPrecosColab adapterPrecosColab;

    private String auxColabs;
    private int QntTrabalhosColab;

    private int IdSaidaAux;

    public SaidaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saida, container, false);

        //Inicia as listas
        ID = new ArrayList();
        TIPO = new ArrayList();
        DATA = new ArrayList();
        CLIENTE = new ArrayList();
        TOTAL = new ArrayList();
        OBS = new ArrayList();
        AUX = new ArrayList();
        PREVISAO = new ArrayList();


        Rv_trabalhos = view.findViewById(R.id.rv_lista_Ids);
        sv_trabalhos = view.findViewById(R.id.sv_trabalhos);
        Id = view.findViewById(R.id.txt_saida_id);
        Cliente = view.findViewById(R.id.txt_saida_cliente);
        Tipo = view.findViewById(R.id.txt_saida_tipo);
        Data = view.findViewById(R.id.txt_saida_data);
        Total = view.findViewById(R.id.txt_saida_total);
        Salvar = view.findViewById(R.id.btn_saida_salvar);
        Obs = view.findViewById(R.id.txt_saida_obs);
        PacienteText = view.findViewById(R.id.txt_pacienteSaida);
        nomeColab = view.findViewById(R.id.txt_nomeColab_saida);
        Colabs = new ArrayList();

        //Configura os servicos dos colabs ---------------------------------------------------------

        NoME = new ArrayList();
        PrECO = new ArrayList();

        sv_colab = view.findViewById(R.id.sv_buscaColab);
        Rv_colab = view.findViewById(R.id.Rv_listaColabSaida);

        Rv_trabalhos.setVisibility(View.GONE);
        Rv_colab.setVisibility(View.GONE);

        Rv_trabalhos.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_trabalhos.setHasFixedSize(true);
        adapter_saida = new Adapter_saida(ID,TIPO,DATA,CLIENTE,TOTAL,getActivity());
        Rv_trabalhos.setAdapter(adapter_saida);

        Rv_trabalhos.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_trabalhos,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                AUX.clear();

                                String Id2,Nome2,Tipo2,Data2,Total2,Obs2,paciente;

                                Id2 = ID.get(position).toString();
                                Nome2 = CLIENTE.get(position).toString();
                                Tipo2 = TIPO.get(position).toString();
                                Data2 = DATA.get(position).toString();
                                Total2 = TOTAL.get(position).toString();
                                Obs2 = OBS.get(position).toString();
                                paciente = PACIENTE.get(position).toString();

                                Id.setText(Id2);
                                Cliente.setText(Nome2);
                                Tipo.setText(Tipo2);
                                Data.setText(Data2);
                                Total.setText(Total2);
                                Obs.setText(Obs2);
                                PacienteText.setText(paciente);


                                sv_trabalhos.clearFocus();
                                sv_trabalhos.setQuery("",false);

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

        Rv_colab.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_colab,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                auxColab = 0;

                                String nome;
                                nome = NOME_COLAB.get(position).toString();

                                if(3 >= Colabs.size()){

                                    for (int i=0;i<Colabs.size();i++){

                                        Log.i("Info","Colab: "+Colabs.get(i));

                                        if(nome.equals(Colabs.get(i).toString())){
                                            auxColab = auxColab - 1;
                                        }else{
                                            auxColab = auxColab + 1;
                                        }

                                    }

                                    if(auxColab == Colabs.size()){

                                        Colabs.add(auxColab,nome);
                                        nomeColab.append(" / "+nome);

                                    }else{

                                        Toast.makeText(
                                                getActivity(),
                                                "Colaborador já selecionado",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }

                                    sv_colab.clearFocus();
                                    sv_colab.setQuery("",false);

                                    //carregarServicosColab();

                                }else{

                                    Toast.makeText(
                                            getActivity(),
                                            "Você já selecionou 3 colaboradores",
                                            Toast.LENGTH_LONG
                                    ).show();

                                }

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                String nome = NOME_COLAB.get(position).toString();

                                for(int i = 0;i < Colabs.size(); i++){

                                    if(Colabs.get(i).toString().equals(nome)){

                                        Log.i("TESTE","Teste");

                                        Colabs.remove(i);

                                        for(int k=0;k<Colabs.size();k++){
                                            Log.i("INFO LISTA",Colabs.get(k).toString());
                                        }

                                        nomeColab.setText("Colabs:");

                                        for(int j=0;j<Colabs.size();j++){
                                            if(Colabs.get(j) != null){
                                                nomeColab.append(" "+Colabs.get(j).toString());
                                            }
                                        }
                                    }
                                }
                                sv_colab.setQuery("",false);
                                //carregarServicosColab();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        sv_trabalhos.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String textoDigitado = newText.toUpperCase();
                pesquisarTrabalhos( textoDigitado );
                return true;
            }
        });

        sv_colab.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String textoDigitado = s.toUpperCase();
                carregarColabs(textoDigitado);
                return true;
            }
        });

        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Id.getText().toString().equals("") && Colabs.size() != 0) {

                    IdSaidaAux = 0;

                    String id;

                    id = Id.getText().toString();

                    DatabaseReference trabalhoRef = database.child("Trabalhos");

                    Log.i("INFO", "referencis: " + trabalhoRef);

                    Query query = trabalhoRef.orderByChild("ID")
                            .startAt(id)
                            .endAt(id + "\uf8ff");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ID2 = ds.child("ID").getValue().toString();
                                CLIENTE2 = ds.child("Nome_cliente").getValue().toString();
                                TIPO2 = ds.child("Nome_servico").getValue().toString();
                                DATA2 = ds.child("Data_entrada").getValue().toString();
                                TOTAL2 = ds.child("Total").getValue().toString();
                                OBS2 = ds.child("Observacao").getValue().toString();
                                PREVISAO2 = ds.child("Previsao_saida").getValue().toString();
                                DENTES2 = ds.child("Dentes").getValue().toString();
                                COR2 = ds.child("Cor").getValue().toString();
                                Paciente = ds.child("Nome_paciente").getValue().toString();

                                Log.i("INFO", "Recuperando: " + ds.child("ID").getValue());

                            }

                            DatabaseReference data2 = trabalhoRef.child(id);
                            data2.removeValue();

                            DatabaseReference SaidaRef = database.child("Saidas");//.child(id);
                            Query query1 = SaidaRef.orderByChild("ID_Trabalho");

                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        IdSaidaAux = IdSaidaAux + 1;
                                    }

                                    aux = String.valueOf(IdSaidaAux + 1);
                                    Log.i("INFO", "AUX: " + aux + " / " + " / " + IdSaidaAux);

                                    SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
                                    Date data = new Date();
                                    Saida = formatData.format(data);

                                    DatabaseReference SaidasRef2 = SaidaRef.child(aux);
                                    SaidasRef2.child("ID_trabalho").setValue(aux);
                                    SaidasRef2.child("Nome_cliente").setValue(CLIENTE2);
                                    SaidasRef2.child("Nome_servico").setValue(TIPO2);
                                    SaidasRef2.child("Data_entrada").setValue(DATA2);
                                    SaidasRef2.child("Total").setValue(TOTAL2);
                                    SaidasRef2.child("Observacao").setValue(OBS2);
                                    SaidasRef2.child("Data_saida").setValue(Saida);
                                    SaidasRef2.child("DENTES").setValue(DENTES2);
                                    SaidasRef2.child("COR").setValue(COR2);
                                    SaidasRef2.child("Paciente").setValue(Paciente);
                                    auxColabs = "";
                                    DatabaseReference dataColab = database.child("Usuarios");
                                    for(int i = 0 ;i<Colabs.size();i++){

                                        auxColabs = ("/"+Colabs.get(i).toString());
                                        DatabaseReference auxDataColab = dataColab.child(Colabs.get(i).toString()).child("Feitos").child(aux);

                                        auxDataColab.child("Id_Feitos").setValue(aux);
                                        auxDataColab.child("Tipo_feito").setValue(TIPO2);
                                        auxDataColab.child("data_feito").setValue(Saida);

                                    }
                                    SaidasRef2.child("Colaboradores").setValue(auxColabs);

                                    Log.i("INFO", "Salvamento: " + aux);

                                    Id.setText("");
                                    Cliente.setText("");
                                    Tipo.setText("");
                                    Data.setText("");
                                    Total.setText("");
                                    Obs.setText("");

                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "Saida realizada com sucesso",
                                            Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                                                /*DatabaseReference SaidasRef2 = SaidaRef.child(String.valueOf(IdAux));
                                                SaidasRef2.child("ID_trabalho").setValue(IdAux);
                                                SaidasRef2.child("Nome_cliente").setValue(CLIENTE2);
                                                SaidasRef2.child("Nome_servico").setValue(TIPO2);
                                                SaidasRef2.child("Data_entrada").setValue(DATA2);
                                                SaidasRef2.child("Total").setValue(TOTAL2);
                                                SaidasRef2.child("Observacao").setValue(OBS2);
                                                Log.i("INFO","Salvamento: "+aux);

                                                Id.setText("");
                                                Cliente.setText("");
                                                Tipo.setText("");
                                                Data.setText("");
                                                Total.setText("");
                                                Obs.setText("");

                                                Toast.makeText(
                                                        getActivity().getApplicationContext(),
                                                        "Saida realizada com sucesso",
                                                        Toast.LENGTH_LONG).show();*/

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else if( Id.getText().toString().equals("") ){

                    Toast.makeText(
                            getActivity(),
                            "Adcione primeiro a saída",
                            Toast.LENGTH_LONG
                    ).show();

                }else if( Colabs.size() == 0 ){

                    Toast.makeText(
                            getActivity(),
                            "Adcione um colaborador",
                            Toast.LENGTH_LONG
                    ).show();

                }

            }
        });

        return view;
    }

    public void pesquisarTrabalhos(String text){

        //limpar lista
        ID.clear();
        TIPO.clear();
        DATA.clear();
        CLIENTE.clear();
        TOTAL.clear();
        OBS.clear();
        PREVISAO.clear();
        PACIENTE = new ArrayList();

        Rv_trabalhos.setVisibility(View.VISIBLE);

        DatabaseReference trabalhoRef = database.child("Trabalhos");

        //Pesquisa clientes caso tenha texto na pesquisa
        if(text.length() > 0){
        Log.i("INFO","CHEGOU2");
            Query query = trabalhoRef.orderByChild("ID")
                    .startAt(text)
                    .endAt(text + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        ID.add(ds.child("ID").getValue());
                        TIPO.add(ds.child("Nome_servico").getValue());
                        DATA.add(ds.child("Data_entrada").getValue());
                        CLIENTE.add(ds.child("Nome_cliente").getValue());
                        TOTAL.add(ds.child("Total").getValue());
                        OBS.add(ds.child("Observacao").getValue());
                        PREVISAO.add(ds.child("Previsao_saida").getValue());
                        PACIENTE.add(ds.child("Nome_paciente").getValue().toString());
                        Log.i("INFO","PesquisaT: "+ds.child("ID").getValue());
                    }
                    adapter_saida.notifyDataSetChanged();

                    /*int total = listaClientes.size();
                    Log.i("INFO","Total: "+total);*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    private void carregarColabs(String texto){

        NOME_COLAB = new ArrayList();
        SETOR_COLAB = new ArrayList();

        Rv_colab.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_colab.setHasFixedSize(true);
        colabadapter = new Colabadapter(NOME_COLAB,SETOR_COLAB,getActivity());
        Rv_colab.setAdapter(colabadapter);

        DatabaseReference databaseReference = database.child("Usuarios");

        if(texto.length() > 0){

            Rv_colab.setVisibility(View.VISIBLE);

            Query query =databaseReference.orderByChild("Nome")
                    .startAt(texto)
                    .endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        if(Integer.parseInt(ds.child("NivelPermissao").getValue().toString()) == 1){

                            NOME_COLAB.add(ds.child("Nome").getValue());
                            SETOR_COLAB.add(ds.child("NivelPermissao").getValue());

                        }
                    }
                    colabadapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



    }

    /*private void carregarServicosColab(){

        if(Colabs.size() == 0){

            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);

            Log.i("INFO","TESTE1");

        }else if(Colabs.size() == 1){

            Log.i("INFO","TESTE1");

            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);

            NoME.clear();
            PrECO .clear();

            for(int i=0;i<Colabs.size();i++){

                NoME.clear();
                PrECO .clear();

                Rv_1.setLayoutManager(new LinearLayoutManager(getActivity()));
                Rv_1.setHasFixedSize(true);
                adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                Rv_1.setAdapter(adapterPrecosColab);

                nome1.setText(Colabs.get(i).toString());

                DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                Query query = data.orderByChild("Id_servico");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            NoME.add(ds.child("nome_servico").getValue());
                            PrECO.add(ds.child("preco_servico").getValue());
                        }
                        adapterPrecosColab.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }else if(Colabs.size() == 2){

            Log.i("INFO","TESTE2");

            //linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.VISIBLE);
            linear3.setVisibility(View.GONE);

            for(int i=0;i<Colabs.size();i++){

                if(i == 0){

                    NoME.clear();
                    PrECO .clear();

                    Rv_1.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Rv_1.setHasFixedSize(true);
                    adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                    Rv_1.setAdapter(adapterPrecosColab);

                    nome1.setText(Colabs.get(i).toString());

                    DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                    Query query = data.orderByChild("Id_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                NoME.add(ds.child("nome_servico").getValue());
                                PrECO.add(ds.child("preco_servico").getValue());
                            }
                            adapterPrecosColab.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else if(i == 1){

                    NoME.clear();
                    PrECO .clear();

                    Rv_2.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Rv_2.setHasFixedSize(true);
                    adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                    Rv_2.setAdapter(adapterPrecosColab);

                    nome2.setText(Colabs.get(i).toString());

                    DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                    Query query = data.orderByChild("Id_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                NoME.add(ds.child("nome_servico").getValue());
                                PrECO.add(ds.child("preco_servico").getValue());
                            }
                            adapterPrecosColab.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

        }else if(Colabs.size() == 3){

            Log.i("INFO","TESTE3");

            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.VISIBLE);
            linear3.setVisibility(View.VISIBLE);

            for(int i=0;i<Colabs.size();i++){

                if(i == 0){

                    NoME.clear();
                    PrECO .clear();

                    Rv_1.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Rv_1.setHasFixedSize(true);
                    adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                    Rv_1.setAdapter(adapterPrecosColab);

                    nome1.setText(Colabs.get(i).toString());

                    DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                    Query query = data.orderByChild("Id_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                NoME.add(ds.child("nome_servico").getValue());
                                PrECO.add(ds.child("preco_servico").getValue());
                            }
                            adapterPrecosColab.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else if(i == 1){

                    NoME.clear();
                    PrECO .clear();

                    Rv_2.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Rv_2.setHasFixedSize(true);
                    adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                    Rv_2.setAdapter(adapterPrecosColab);

                    nome2.setText(Colabs.get(i).toString());

                    DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                    Query query = data.orderByChild("Id_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                NoME.add(ds.child("nome_servico").getValue());
                                PrECO.add(ds.child("preco_servico").getValue());
                            }
                            adapterPrecosColab.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else if(i == 2){

                    NoME.clear();
                    PrECO .clear();

                    Rv_3.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Rv_3.setHasFixedSize(true);
                    adapterPrecosColab = new AdapterPrecosColab(NoME,PrECO,getActivity());
                    Rv_3.setAdapter(adapterPrecosColab);

                    nome3.setText(Colabs.get(i).toString());

                    DatabaseReference data = database.child("Usuarios").child(Colabs.get(i).toString()).child("Valores");

                    Query query = data.orderByChild("Id_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                NoME.add(ds.child("nome_servico").getValue());
                                PrECO.add(ds.child("preco_servico").getValue());
                            }
                            adapterPrecosColab.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

        }

    }*/

}
