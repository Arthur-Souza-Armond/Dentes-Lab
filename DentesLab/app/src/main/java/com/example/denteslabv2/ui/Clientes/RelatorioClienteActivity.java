package com.example.denteslabv2.ui.Clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.adapter.TrabalhosClientesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class RelatorioClienteActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private Clientes clienteAtual;
    private TextView TextNome;
    private Button Pesquisar;

    //Calendario Inicio
    private MaterialCalendarView calendarInicio;

    //Calendario Final
    private MaterialCalendarView calendarFinal;

    // Pesquisar
    private List NOME,DATA,ID,VALOR;
    private TrabalhosClientesAdapter  trabalhosClientesAdapter;
    private LinearLayout inicio,fim,selecionados;
    private TextView Periodo,PeriodoNota,TextoNota,NomeNota;
    private RecyclerView Rv_TSelec;
    private int AuxTotal;
    private Button Imprimir;
    private LinearLayout linearRelatorioNota;

    private String Inicio,Fim;
    private String dia1,mes1,dia2,mes2;
    private String textoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_cliente);

        getSupportActionBar().hide();

        clienteAtual = (Clientes) getIntent().getSerializableExtra("Nome");

        calendarInicio = findViewById(R.id.calendarViewInicioRelatorioInicio);
        calendarFinal = findViewById(R.id.calendarViewInicioRelatorioFinal);
        TextNome = findViewById(R.id.txt_nomeClienteRelatorioA);
        Pesquisar = findViewById(R.id.btn_pesquisarDataRelatorio);

        //Lineares
        inicio = findViewById(R.id.linearInicioRelatorio);
        fim = findViewById(R.id.linearFinalRelatorio);
        selecionados = findViewById(R.id.linearTrabalhosSelecionados);

        //Linear Resultado
        Periodo = findViewById(R.id.txt_periodoSelecionado);
        Rv_TSelec = findViewById(R.id.rv_saidasClienteRelatorioSelec);
        PeriodoNota = findViewById(R.id.txt_periodoRelatorioNota);
        TextoNota = findViewById(R.id.txt_textoRelatorioNota);
        NomeNota = findViewById(R.id.txt_nomeClienteRelatorioN);
        Imprimir = findViewById(R.id.btn_imprimirRelatorioCliente);
        linearRelatorioNota = findViewById(R.id.linearRelatorioNota);

        selecionados.setVisibility(View.GONE);

        TextNome.setText(clienteAtual.getNome());

        Inicio = "";
        Fim = "";
        textoTotal = "";

        carregarDataInicio();
        carregarDataFinal();

        Pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Inicio.equals("") && !Fim.equals("")){

                    inicio.setVisibility(View.GONE);
                    fim.setVisibility(View.GONE);
                    Pesquisar.setVisibility(View.GONE);
                    selecionados.setVisibility(View.VISIBLE);

                    Periodo.setText("Início: "+Inicio+" | Fim: "+Fim);

                    String aux = "Início: "+Inicio+" | Fim: "+Fim;

                    PeriodoNota.setText(aux);
                    NomeNota.setText(clienteAtual.getNome());
                    TextoNota.setText("");

                    textoTotal = textoTotal + ("\n\n               "+clienteAtual.getNome()+"          \n\n");
                    textoTotal = textoTotal + ("          "+aux+"          \n\n");

                    AuxTotal = 0;

                    NOME = new ArrayList();
                    DATA = new ArrayList();
                    ID = new ArrayList();
                    VALOR = new ArrayList();

                    int dia,mes,ano,diaa,mess,anoo;

                    dia = Integer.parseInt(Inicio.substring(0,2));
                    mes = Integer.parseInt(Inicio.substring(3,5));
                    ano = Integer.parseInt(Inicio.substring(6,10));
                    diaa = Integer.parseInt(Fim.substring(0,2));
                    mess = Integer.parseInt(Fim.substring(3,5));
                    anoo = Integer.parseInt(Fim.substring(6,10));

                    Log.i("INFO","Inicio: "+dia+"/"+mes+"/"+ano+" Fim: "+diaa+"/"+mess+"/"+anoo);

                    if(ano == anoo || anoo > ano){

                        if(mes == mess || mess > mes){

                            if(dia == diaa || diaa > dia){

                                DatabaseReference data = database.child("Saidas");

                                Query query = data.orderByChild("Nome_cliente")
                                        .startAt(clienteAtual.getNome())
                                        .endAt(clienteAtual.getNome() + "\uf88f");

                                trabalhosClientesAdapter = new TrabalhosClientesAdapter(NOME,DATA,VALOR,ID,getApplicationContext());

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                Rv_TSelec.setLayoutManager(layoutManager);
                                Rv_TSelec.setHasFixedSize(true);
                                Rv_TSelec.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayout.VERTICAL));
                                Rv_TSelec.setAdapter(trabalhosClientesAdapter);

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds : snapshot.getChildren()){

                                            int diaSaida,mesSaida,anoSaida;

                                            diaSaida = Integer.parseInt(ds.child("Data_saida").getValue().toString().substring(0,2));
                                            mesSaida = Integer.parseInt(ds.child("Data_saida").getValue().toString().substring(3,5));
                                            anoSaida = Integer.parseInt(ds.child("Data_saida").getValue().toString().substring(6,10));

                                            if(anoo >= anoSaida && anoSaida >= ano ){

                                                if(mess >= mesSaida && mesSaida >= mes){

                                                    if(diaa >= diaSaida && diaSaida >= dia){

                                                        NOME.add(clienteAtual.getNome());
                                                        DATA.add(ds.child("Data_saida").getValue());
                                                        ID.add(ds.child("ID_trabalho").getValue());
                                                        VALOR.add(ds.child("Total").getValue());

                                                        TextoNota.append("-----------------------------------------------------------------------\n\n   ID de serviço: "+ds.child("ID_trabalho").getValue().toString()+"\n\n   Paciente: "+ds.child("Paciente").getValue().toString()+
                                                                "\n\n   "+ds.child("Nome_servico").getValue().toString()+"  =  R$ "+ds.child("Total").getValue().toString()+"\n\n");

                                                        textoTotal = textoTotal + ("-----------------------------------------------------------------------\n\n   ID de serviço: "+ds.child("ID_trabalho").getValue().toString()+"\n\n   Paciente: "+ds.child("Paciente").getValue().toString()+
                                                                "\n\n   "+ds.child("Nome_servico").getValue().toString()+"  =  R$ "+ds.child("Total").getValue().toString()+"\n\n");

                                                        AuxTotal = AuxTotal + Integer.parseInt(ds.child("Total").getValue().toString());

                                                    }

                                                }

                                            }

                                        }
                                        trabalhosClientesAdapter.notifyDataSetChanged();
                                        TextoNota.append("__________________________________________\n\n   Total:      R$ "+AuxTotal+",00\n\n");
                                        textoTotal = textoTotal + ("__________________________________________\n\n   Total:      R$ "+AuxTotal+",00\n\n");

                                        Imprimir.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(!TextoNota.getText().equals("")){



                                                    PackageManager pm=getPackageManager();
                                                    try {

                                                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                                                        waIntent.setType("text/plain");
                                                        String text = "";
                                                        Log.i("Info","Texto: "+textoTotal);

                                                        PackageInfo info=pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_META_DATA);
                                                        //Check if package exists or not. If not then code
                                                        //in catch block will be called
                                                        waIntent.setPackage("com.whatsapp");

                                                        waIntent.putExtra(Intent.EXTRA_TEXT, textoTotal);
                                                        startActivity(Intent.createChooser(waIntent, "Compartilhar com"));

                                                    } catch (PackageManager.NameNotFoundException e) {
                                                        Toast.makeText(RelatorioClienteActivity.this,
                                                                "WhatsApp not Installed",
                                                                Toast.LENGTH_LONG)
                                                                .show();
                                                    }

                                                    /*linearRelatorioNota.setDrawingCacheEnabled(true);
                                                    linearRelatorioNota.buildDrawingCache(true);
                                                    Bitmap bitmap = Bitmap.createBitmap(linearRelatorioNota.getDrawingCache());
                                                    PrintHelper printHelper = new PrintHelper(RelatorioClienteActivity.this);
                                                    printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);

                                                    printHelper.printBitmap("Relatorio "+clienteAtual.getNome()+" Periodo: "+Inicio+" - "+Fim+".jpg",bitmap);*/

                                                }else {

                                                    Toast.makeText(getApplicationContext(),
                                                            "Adcione algo ao relatório",
                                                            Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }else{

                                Toast.makeText(
                                        getApplicationContext(),
                                        "A data de início deve ser antes",
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        }else{

                            Toast.makeText(getApplicationContext(),
                                    "A data de início deve ser antes",
                                    Toast.LENGTH_LONG).show();

                        }

                    }else {

                        Toast.makeText(getApplicationContext(),
                                "A data de início deve ser antes",
                                Toast.LENGTH_LONG).show();

                    }

                }else if(Inicio.equals("")){

                    Toast.makeText(getApplicationContext(),
                            "Coloque a data de início",
                            Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(getApplicationContext(),
                            "Coloque a data de final",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void carregarDataFinal(){

        CalendarDay data = calendarInicio.getCurrentDate();


        calendarFinal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(10 > date.getDay()){
                    dia2 = String.valueOf("0"+date.getDay());
                }else{
                    dia2 = String.valueOf(date.getDay());
                }

                if(10 > (date.getMonth() + 1)){
                    mes2 = String.valueOf("0"+(date.getMonth() + 1));
                }else{
                    mes2 = String.valueOf(date.getMonth() + 1);
                }

                Fim = String.valueOf(dia2+"/"+mes2+"/"+date.getYear());
                Log.i("INFO","DATA: "+Fim);

            }
        });

    }

    private void carregarDataInicio(){

        calendarInicio.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(10 > date.getDay()){
                    dia1 = "0"+String.valueOf(date.getDay());
                }else{
                    dia1 = String.valueOf(date.getDay());
                }

                if(10 > (date.getMonth() + 1)){
                    mes1 = "0"+String.valueOf(date.getMonth() + 1);
                }else{
                    mes1 = String.valueOf(date.getMonth() + 1);
                }

                Inicio = String.valueOf(dia1+"/"+mes1+"/"+date.getYear());
                Log.i("INFO","DATA: "+Inicio);

            }
        });
    }

}