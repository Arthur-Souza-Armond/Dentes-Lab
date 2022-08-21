package com.example.denteslabv2.ui.Relatórios.cartao;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.Clientes;
import com.example.denteslabv2.ui.Clientes.adapter.ClientesAdapter;
import com.example.denteslabv2.ui.Clientes.adapter.TrabalhosClientesAdapter;
import com.example.denteslabv2.ui.Relatórios.cartao.adapter.AdapterCartao;
import com.example.denteslabv2.ui.Trabalhos.Entrada.adapter.AdapterPesquisa_servico;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ResultadosMensaisFragment extends Fragment {

    public ResultadosMensaisFragment() {
        // Required empty public constructor
    }

    //Bancod de dados
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Configurando o calendarView
    private MaterialCalendarView calendar;

    //XML
    private ImageButton Clientes;
    private SearchView sv_buscaCliente;
    private RecyclerView Rv_buscaCliente,Rv_resultadosMensais;
    private TextView Total;
    private LinearLayout linearCliente;
    private Button Enviar;

    //Adapters
    private ClientesAdapter clientesAdapter;
    private TrabalhosClientesAdapter trabalhosClientesAdapter;

    //Variaveis auxiliares
    private List<Clientes> listaClientes;
    private String Data;
    private List ID,DATA,VALOR,TIPO;
    private int total,totalDepesa,totalZao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cartao_despesa, container, false);

        // Configurações
        Clientes = view.findViewById(R.id.imgb_filtroClientes);

        sv_buscaCliente = view.findViewById(R.id.sv_filtroCliente);

        Rv_buscaCliente = view.findViewById(R.id.rv_nomeClienteFiltrados);
        Rv_resultadosMensais = view.findViewById(R.id.rv_resultadosMensais);

        Total = view.findViewById(R.id.txt_totalMensal);

        linearCliente = view.findViewById(R.id.linearBuscaClienteResultados);

        Enviar = view.findViewById(R.id.btn_enviarRelatorio);
        calendar = view.findViewById(R.id.calendarDatasResultados);

        linearCliente.setVisibility(View.GONE);

        Enviar.setVisibility(View.GONE);

        carregarData();

        //Filtrar por clientes

        Clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearCliente.setVisibility(View.VISIBLE);

            }
        });

        sv_buscaCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String textoDigitado;
                textoDigitado = s.toUpperCase();
                pesquisarCliente(textoDigitado);
                return true;
            }
        });

        Rv_buscaCliente.addOnItemTouchListener(new RecyclerItemClickListnner(
                getActivity(),
                Rv_buscaCliente,
                new RecyclerItemClickListnner.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        linearCliente.setVisibility(View.GONE);

                        CalendarDay day = calendar.getCurrentDate();

                        int auxmes,auxano;

                        auxano = day.getYear();
                        auxmes = (day.getMonth() + 1);

                        carregarResultados(auxmes,auxano,listaClientes.get(position).getNome());

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        Log.i("INFO","TESTEE: "+total);
        Log.i("INFO","TESTEE: "+totalDepesa);

        return view;
    }

    private void pesquisarCliente(String texto){

        //Configura lista
        listaClientes = new ArrayList<>();

        clientesAdapter = new ClientesAdapter(listaClientes,getActivity());

        Rv_buscaCliente.setHasFixedSize(true);
        Rv_buscaCliente.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_buscaCliente.setAdapter(clientesAdapter);

        if(texto.length() > 0){

            DatabaseReference data = database.child("Clientes");

            Query query = data.orderByChild("Nome_pesquisa")
                    .startAt(texto)
                    .endAt(texto + "\uf88f");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        listaClientes.add(ds.getValue(com.example.denteslabv2.ui.Clientes.Clientes.class));
                    }
                    clientesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }

    }

    private void carregarData(){

        CalendarDay day = calendar.getCurrentDate();

        Data = day.toString();

        int mes,ano;

        mes = (day.getMonth() + 1);
        ano = day.getYear();

        carregarResultados(mes,ano,"");

        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                int Mes,Ano;

                Mes = (date.getMonth() + 1);
                Ano = date.getYear();

                carregarResultados(Mes,Ano,"");

            }
        });

    }

    private void carregarResultados(int mes,int ano,String nomeCliente){

        ID = new ArrayList();
        DATA = new ArrayList();
        VALOR = new ArrayList();
        TIPO = new ArrayList();

        total = 0;
        totalDepesa = 0;
        totalZao = 0;

        trabalhosClientesAdapter = new TrabalhosClientesAdapter(TIPO,DATA,VALOR,ID,getActivity());

        Rv_resultadosMensais.setHasFixedSize(true);
        Rv_resultadosMensais.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_resultadosMensais.setAdapter(trabalhosClientesAdapter);

        DatabaseReference dataFixas = database.child("Relatorios").child("Fixas");

        Query query = dataFixas.orderByChild("Id");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    String[] auxiliar;

                    auxiliar = ds.child("Parcelas").getValue().toString().split(",");

                    if(ds.child("Parcelas").getValue().toString().equals("always")){

                        ID.add(ds.child("Nome").getValue().toString());
                        DATA.add(ds.child("TotalParcelas").getValue().toString());
                        VALOR.add(ds.child("Valor").getValue().toString());
                        totalDepesa = totalDepesa + Integer.parseInt(ds.child("Valor").getValue().toString());
                        TIPO.add("Despesa fixa");

                    }else{

                        for(int i = 1;i<Integer.parseInt(ds.child("TotalParcelas").getValue().toString());i++){

                            String data1;
                            int auxMes,auxAno;

                            data1 = auxiliar[i];

                            auxMes = Integer.parseInt(data1.substring(3,5));
                            auxAno = Integer.parseInt(data1.substring(6,10));

                            if(auxMes == mes && auxAno == ano){

                                ID.add(ds.child("Id").getValue().toString());
                                DATA.add(ds.child("TotalParcelas").getValue().toString());
                                VALOR.add(ds.child("Valor").getValue().toString());
                                totalDepesa = totalDepesa + Integer.parseInt(ds.child("Valor").getValue().toString());
                                TIPO.add("Despesa variavel");

                            }

                        }

                    }
                }
                trabalhosClientesAdapter.notifyDataSetChanged();
                Log.i("INFO","Despesa: "+totalDepesa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(!nomeCliente.equals("")){

            DatabaseReference data = database.child("Saidas");

            Query query2 = data.orderByChild("Nome_cliente")
                    .startAt(nomeCliente)
                    .endAt(nomeCliente + "\uf88f");

            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){

                        String dataAux;

                        dataAux = ds.child("Data_saida").getValue().toString();

                        int Mes,Ano;

                        Mes = Integer.parseInt(dataAux.substring(3,5));
                        Ano = Integer.parseInt(dataAux.substring(6,10));

                        if(Mes == mes && Ano == ano){

                            ID.add(ds.child("ID_trabalho").getValue().toString());
                            DATA.add(ds.child("Data_saida").getValue().toString());
                            VALOR.add(ds.child("Total").getValue().toString());
                            total = total + Integer.parseInt(ds.child("Total").getValue().toString());
                            TIPO.add("Cliente especifico");

                        }
                    }
                    trabalhosClientesAdapter.notifyDataSetChanged();
                    Log.i("INFO","Total: "+total);
                    totalZao = total - totalDepesa;

                    if (totalZao > 0) {

                        Total.setTextColor(getResources().getColor(R.color.Positivo));

                    }else{

                        Total.setTextColor(getResources().getColor(R.color.Negativo));

                    }

                    Total.setText("R$ "+String.valueOf(totalZao)+",00");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{

            DatabaseReference data = database.child("Saidas");

            Query query3 = data.orderByChild("ID_trabalho");

            query3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){

                        String dataAux;

                        dataAux = ds.child("Data_saida").getValue().toString();

                        int Mes,Ano;

                        Mes = Integer.parseInt(dataAux.substring(3,5));
                        Ano = Integer.parseInt(dataAux.substring(6,10));

                        if(Mes == mes && Ano == ano){

                            ID.add(ds.child("ID_trabalho").getValue().toString());
                            DATA.add(ds.child("Data_saida").getValue().toString());
                            VALOR.add(ds.child("Total").getValue().toString());
                            total = total + Integer.parseInt(ds.child("Total").getValue().toString());
                            TIPO.add("Cliente nao especificado");

                        }
                    }
                    trabalhosClientesAdapter.notifyDataSetChanged();
                    Log.i("INFO","Total: "+total);
                    totalZao = total - totalDepesa;

                    if (totalZao > 0) {

                        Total.setTextColor(getResources().getColor(R.color.Positivo));

                    }else{

                        Total.setTextColor(getResources().getColor(R.color.Negativo));

                    }

                    Total.setText("R$ "+String.valueOf(totalZao)+",00");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}