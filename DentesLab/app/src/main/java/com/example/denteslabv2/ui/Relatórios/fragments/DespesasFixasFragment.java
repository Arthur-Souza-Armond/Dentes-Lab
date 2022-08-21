package com.example.denteslabv2.ui.Relatórios.fragments;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Relatórios.adapter.Adapter_Fixas;
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
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

public class DespesasFixasFragment extends Fragment {

    public DespesasFixasFragment() {
        // Required empty public constructor
    }

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Adciona despesa
    private TextInputEditText Nome,Valor,Data;
    private Button AdcionarDespesa,abrirLayout;
    private LinearLayout linearAdc,linearParcelas;
    private MaterialCalendarView calendarInicio,calendarFinal,calendarFiltro;
    private TextView dataInicio,dataFinal;
    private Button tbParcela;

    //Configuração Recycler
    private Adapter_Fixas adapterFixas;
    private RecyclerView Rv_despesasFixas;
    private List NOME,DATA,VALOR;

    //Var auxiliar
    private int IdAux;
    private String Inicio,Fim;
    private int totalParcelas;
    private String auxData;
    private List ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_despesas_fixas, container, false);

        //Configura o Recycler
        Rv_despesasFixas = view.findViewById(R.id.Rv_despesasFixas);

        //Configura os inputs
        Nome = view.findViewById(R.id.txt_nomeDespesaFixa);
        Valor = view.findViewById(R.id.txt_valorDespesaFixa);
        Data = view.findViewById(R.id.txt_dataDeDebito);

        //Configura o botão
        AdcionarDespesa = view.findViewById(R.id.btn_adcionarDespesa);
        abrirLayout = view.findViewById(R.id.btn_abrirLayoutDespesa);

        //Configura o linear
        linearAdc = view.findViewById(R.id.linearAdcDespesa);

        //Var auxiliar na adição
        IdAux = 0;

        //Parcelas
        tbParcela = view.findViewById(R.id.btn_adcionaParcela);
        linearParcelas = view.findViewById(R.id.linearParcelas);
        calendarInicio = view.findViewById(R.id.calendarViewInicioParcelas);
        calendarFinal = view.findViewById(R.id.calendarViewFinalParcelas);
        dataInicio = view.findViewById(R.id.txt_dataInicioParcela);
        dataFinal = view.findViewById(R.id.txt_dataFinalParcela);
        calendarFiltro = view.findViewById(R.id.calendarDatasDespesas);

    //----------------------------------------------------------------------------------------------

        linearAdc.setVisibility(View.GONE);

        //carregarDatas();
        //carregarDespesas();
        carregarDatas();

        Rv_despesasFixas.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity(),
                        Rv_despesasFixas,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                String id;

                                id = ID.get(position).toString();

                                DatabaseReference data = database.child("Relatorios").child("Fixas").child(id);

                                data.removeValue();

                                Toast.makeText(getActivity(),
                                        "Despesa removida",
                                        Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        abrirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearAdc.setVisibility(View.VISIBLE);

                linearParcelas.setVisibility(View.GONE);

                tbParcela.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linearParcelas.setVisibility(View.VISIBLE);
                        Data.setVisibility(View.GONE);
                    }
                });

                AdcionarDespesa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(linearParcelas.getVisibility() == View.VISIBLE){

                            String nome,data,valor;

                            nome = Nome.getText().toString();
                            data = Data.getText().toString();
                            valor = Valor.getText().toString();

                            int dia,mes,ano,diaa,mess,anoo;

                            dia = Integer.parseInt(Inicio.substring(0,2));
                            mes = Integer.parseInt(Inicio.substring(3,5));
                            ano = Integer.parseInt(Inicio.substring(6,10));

                            diaa = Integer.parseInt(Fim.substring(0,2));
                            mess = Integer.parseInt(Fim.substring(3,5));
                            anoo = Integer.parseInt(Fim.substring(6,10));

                            IdAux = 0;

                            linearParcelas.setVisibility(View.GONE);

                            tbParcela.setVisibility(View.GONE);

                            Log.i("INFO","COm");

                            linearParcelas.setVisibility(View.VISIBLE);

                            if(anoo > ano) {

                                if (dia == diaa) {

                                    if (!nome.equals("") && !valor.equals("")) {

                                        totalParcelas = 0;

                                        totalParcelas = 12 - mes;

                                        totalParcelas = totalParcelas + mess;

                                        DatabaseReference dataAdc = database.child("Relatorios").child("Fixas");

                                        Query query = dataAdc.orderByChild("Id");

                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    IdAux = IdAux + 1;
                                                }

                                                int auxiliar = IdAux + 1;

                                                DatabaseReference databaseAux = dataAdc.child(String.valueOf(auxiliar));

                                                databaseAux.child("Id").setValue(auxiliar);
                                                databaseAux.child("Nome").setValue(nome);
                                                databaseAux.child("Data_vencimento").setValue(Inicio.substring(0,2));
                                                databaseAux.child("Valor").setValue(valor);
                                                databaseAux.child("TotalParcelas").setValue(totalParcelas);

                                                int aux2,aux3;

                                                aux2 = 0;
                                                aux3 = 0;

                                                aux2 = Integer.parseInt(Inicio.substring(3,5));

                                                auxData = "";

                                                for (int i = 0; i < totalParcelas; i++) {

                                                    String datas;

                                                    aux2 = aux2 + 1;

                                                    Log.i("INFO","AUX: "+aux2);

                                                    if (12 >= aux2) {

                                                        auxData = auxData + "," + String.valueOf(Inicio.substring(0, 2) + (Integer.parseInt(Inicio.substring(3, 5)) + aux2) + Inicio.substring(6, 10));

                                                    } else {

                                                        aux3 = aux2 - 12;

                                                        if (10 > aux3){

                                                            auxData = auxData + "," + String.valueOf(Inicio.substring(0, 2) +"/"+ "0"+aux3 +"/"+ (Integer.parseInt(Inicio.substring(6, 10)) + 1));

                                                        }else{

                                                            auxData = auxData + "," + String.valueOf(Inicio.substring(0, 2) +"/"+ aux3 +"/"+ (Integer.parseInt(Inicio.substring(6, 10)) + 1));

                                                        }

                                                    }
                                                }

                                                databaseAux.child("Parcelas").setValue(auxData);

                                                Toast.makeText(getActivity(),
                                                        "Despesa adcionada",
                                                        Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        linearAdc.setVisibility(View.GONE);
                                        carregarDatas();

                                    } else {

                                        Toast.makeText(getActivity(),
                                                "Complete todos os campos",
                                                Toast.LENGTH_LONG).show();

                                    }

                                }else{

                                    Toast.makeText(
                                            getActivity(),
                                            "Dias diferentes de cobrança",
                                            Toast.LENGTH_LONG
                                    ).show();

                                }

                            }else if(anoo == ano){

                                if(mess > mes){

                                    if(dia == diaa){

                                        if (!nome.equals("") && !data.equals("") && !valor.equals("")) {

                                            totalParcelas = 0;

                                            totalParcelas = mess - mes;

                                            //totalParcelas = totalParcelas + mess;

                                            DatabaseReference dataAdc = database.child("Relatorios").child("Fixas");

                                            Query query = dataAdc.orderByChild("Id");

                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        IdAux = IdAux + 1;
                                                    }

                                                    int auxiliar = IdAux + 1;

                                                    DatabaseReference databaseAux = dataAdc.child(String.valueOf(auxiliar));

                                                    databaseAux.child("Id").setValue(auxiliar);
                                                    databaseAux.child("Nome").setValue(nome);
                                                    databaseAux.child("Data_vencimento").setValue(Inicio.substring(0,2));
                                                    databaseAux.child("Valor").setValue(valor);
                                                    databaseAux.child("TotalParcelas").setValue(totalParcelas);

                                                    int aux2,aux3;

                                                    aux2 = 0;
                                                    aux3 = 0;

                                                    aux2 = Integer.parseInt(Inicio.substring(3,5));

                                                    auxData = "";

                                                    for (int i = 0; i < totalParcelas; i++) {

                                                        String datas;

                                                        aux2 = aux2 + 1;

                                                        Log.i("INFO","AUX: "+aux2);

                                                        if (12 >= aux2) {

                                                            if(10 > aux2){

                                                                auxData = auxData + "," + String.valueOf(Inicio.substring(0, 2) +"/"+ "0"+aux2 +"/"+ Inicio.substring(6, 10));

                                                            }else{

                                                                auxData = auxData + "," + String.valueOf(Inicio.substring(0, 2) +"/"+ aux2 +"/"+ Inicio.substring(6, 10));

                                                            }
                                                        }
                                                    }

                                                    databaseAux.child("Parcelas").setValue(auxData);

                                                    Toast.makeText(getActivity(),
                                                            "Despesa adcionada",
                                                            Toast.LENGTH_LONG).show();

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            linearAdc.setVisibility(View.GONE);
                                            carregarDatas();

                                        } else {

                                            Toast.makeText(getActivity(),
                                                    "Complete todos os campos",
                                                    Toast.LENGTH_LONG).show();

                                        }

                                    }else{

                                        Toast.makeText(getActivity(),
                                                "Dias diferentes de cobrança",
                                                Toast.LENGTH_LONG).show();

                                    }

                                }else{

                                    Toast.makeText(
                                            getActivity(),
                                            "Não existe nenhuma parcela nessa data",
                                            Toast.LENGTH_LONG
                                    ).show();

                                }

                            }else{

                                Toast.makeText(
                                        getActivity(),
                                        "Problema com o ano",
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        }else if(linearParcelas.getVisibility() == View.GONE){

                            String nome,data,valor;

                            nome = Nome.getText().toString();
                            data = Data.getText().toString();
                            valor = Valor.getText().toString();

                            IdAux = 0;

                            DatabaseReference dataBase = database.child("Relatorios").child("Fixas");

                            Query query = dataBase.orderByChild("Id");

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()){
                                        IdAux = IdAux + 1;
                                    }

                                    String auxiliar = String.valueOf(IdAux + 1);

                                    DatabaseReference dataFixa = dataBase.child(auxiliar);

                                    dataFixa.child("Id").setValue(auxiliar);
                                    dataFixa.child("Nome").setValue(nome);
                                    dataFixa.child("Data_vencimento").setValue(data);
                                    dataFixa.child("Valor").setValue(valor);
                                    dataFixa.child("TotalParcelas").setValue("always");
                                    dataFixa.child("Parcelas").setValue("always");

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            linearAdc.setVisibility(View.GONE);
                            carregarDatas();

                        }

                     }
                });

            }
        });

        return view;
    }

    private void carregarDespesas(int mes,int ano){

        NOME = new ArrayList();
        DATA = new ArrayList();
        VALOR = new ArrayList();
        ID = new ArrayList();

        adapterFixas = new Adapter_Fixas(NOME,DATA,VALOR,getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_despesasFixas.setLayoutManager(layoutManager);
        Rv_despesasFixas.setHasFixedSize(true);
        Rv_despesasFixas.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_despesasFixas.setAdapter( adapterFixas );

        DatabaseReference data = database.child("Relatorios").child("Fixas");

        Query query = data.orderByChild("Id");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                    if (ds.child("Parcelas").getValue().toString().equals("always")){

                        NOME.add(ds.child("Nome").getValue().toString());
                        DATA.add(ds.child("Data_vencimento").getValue().toString());
                        VALOR.add(ds.child("Valor").getValue().toString());
                        ID.add(ds.child("Id").getValue().toString());

                    }else{

                        String[] datas;

                        datas = ds.child("Parcelas").getValue().toString().split(",");

                        for (int i = 1; i < Integer.parseInt(ds.child("TotalParcelas").getValue().toString()); i++){

                            String MES,ANO,DaTA;

                            DaTA = datas[i];
                            MES = DaTA.substring(3,5);
                            ANO = DaTA.substring(6,10);

                            Log.i("INFO","D: "+mes+" R: "+Integer.parseInt(MES));
                            Log.i("INFO","D: "+ano+" R: "+Integer.parseInt(ANO));

                            if(mes == Integer.parseInt(MES) && ano == Integer.parseInt(ANO)){

                                NOME.add(ds.child("Nome").getValue().toString());
                                DATA.add(ds.child("Data_vencimento").getValue().toString());
                                VALOR.add(ds.child("Valor").getValue().toString());
                                ID.add(ds.child("Id").getValue().toString());

                            }
                        }

                    }
                }
                adapterFixas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarDatas(){

        calendarInicio.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if((date.getMonth() + 1) < 10){

                    String aux;

                    aux = String.valueOf("0"+(date.getMonth() + 1));
                    Inicio = String.valueOf(date.getDay()+"/"+aux+"/"+date.getYear());
                    dataInicio.setText(Inicio);


                }else{

                    Inicio = String.valueOf(date.getDay()+"/"+(date.getMonth() + 1)+"/"+date.getYear());
                    dataInicio.setText(Inicio);

                }

            }
        });

        calendarFinal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if((date.getMonth() + 1) < 10){

                    String aux;

                    aux = String.valueOf("0"+(date.getMonth() + 1));
                    Fim = String.valueOf(date.getDay()+"/"+aux+"/"+date.getYear());
                    dataFinal.setText(Fim);


                }else{

                    Fim = String.valueOf(date.getDay()+"/"+(date.getMonth() + 1)+"/"+date.getYear());
                    dataFinal.setText(Fim);

                }
            }
        });

        CalendarDay day = calendarFiltro.getCurrentDate();
        String dataMes = "";
        String dataAno = "";
        String dataDia = "";

        dataMes = String.valueOf(day.getMonth() + 1);
        dataAno = String.valueOf(day.getYear());

        carregarDespesas(Integer.parseInt(dataMes),Integer.parseInt(dataAno));

        calendarFiltro.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                int mes,ano;

                mes = (date.getMonth() + 1);
                ano = date.getYear();

                carregarDespesas(mes,ano);

            }
        });

    }

}