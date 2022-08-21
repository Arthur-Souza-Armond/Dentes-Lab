package com.example.denteslabv2.ui.Relatórios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Relatórios.cartao.ResultadosMensaisFragment;
import com.example.denteslabv2.ui.Relatórios.fragments.DespesasFixasFragment;

public class RelatoriosFragment extends Fragment {

    private Button Despesas,Resultados;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_relatorio, container, false);

        Despesas = view.findViewById(R.id.btn_fixosGestao);
        Resultados = view.findViewById(R.id.btn_cartaoGestao);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPagerGestao, new DespesasFixasFragment()).commit();

        Despesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.viewPagerGestao, new DespesasFixasFragment()).commit();

            }
        });

        Resultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.viewPagerGestao, new ResultadosMensaisFragment()).commit();

            }
        });

        return view;
    }

    /*public void carregarFixas(){

    NOMEF.clear();
    DATAF.clear();
    VALORF.clear();

    //Configurar Adapter
      adapter_fixas = new Adapter_Fixas(NOMEF,DATAF,VALORF,getActivity());

    //Configurar Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_fixas.setLayoutManager(layoutManager);
        Rv_fixas.setHasFixedSize(true);
        Rv_fixas.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_fixas.setAdapter( adapter_fixas );


        //Recupera do banco de dados
        DatabaseReference despesasFixas = database.child("Relatorios");
        DatabaseReference despesasFixas2 = despesasFixas.child("Fixas");

        Query query = despesasFixas2.orderByChild("Nome");

        I2 = 0;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    NOMEF.add(ds.child("Nome").getValue());
                    DATAF.add(ds.child("Data").getValue());
                    VALORF.add(ds.child("Valor").getValue());
                    Log.i("INFO","NomeF: "+ds.child("Valor").getValue());
                    I2 = I2 + Integer.parseInt(ds.child("Valor").getValue().toString());
                }
                DatabaseReference Movi2 = database.child("Relatorios").child("Movimentacoes").child("TotalD").child("Total");
                Movi2.setValue(I2*-1);
                adapter_fixas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Rv_fixas.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_fixas,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                String nome = NOMEF.get(position).toString();
                                DatabaseReference RefFixa = database.child("Relatorios").child("Fixas").child(nome);
                                Snackbar.make(view,"Deseja excluir despesa?", BaseTransientBottomBar.LENGTH_LONG)
                                        .setAction("SIM", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                RefFixa.removeValue();
                                                carregarFixas();
                                                carregarMovimentacoes();
                                            }
                                        }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                        .show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }*/

    /*public void carregarMovimentacoes(){

        NOMEM.clear();
        DATAM.clear();
        VALORM.clear();

        Rv_movimentacoes.clearFocus();

        adapter_movimentacoes = new Adapter_Movimentacoes(NOMEM,DATAM,VALORM,getActivity());

        //Configurar Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_movimentacoes.setLayoutManager(layoutManager);
        Rv_movimentacoes.setHasFixedSize(true);
        Rv_movimentacoes.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_movimentacoes.setAdapter( adapter_movimentacoes );

        carregarCalendar();*/

        /*DatabaseReference Movi2 = database.child("Relatorios").child("Movimentacoes").child("TotalD").child("Total");

        NOMEM.add("Despesas Fixas");
        DATAM.add(mesAnoSelecionado);
        Movi2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    VALORM.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        /*DatabaseReference despesasFixas = database.child("Relatorios").child("Fixas");

        Query query = despesasFixas.orderByChild("Nome");

        I2 = 0;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    /*NOMEM.add(ds.child("Nome").getValue());
                    DATAM.add(mesAnoSelecionado);*/
                    //VALORM.add(Integer.parseInt(ds.child("Valor").getValue().toString())*-1);
                    /*Log.i("INFO","Nome: "+ds.child("Nome").getValue());
                    I2 = I2 + Integer.parseInt(ds.child("Valor").getValue().toString())*-1;
                    /*DatabaseReference Movi2 = database.child("Relatorios").child("Movimentacoes").child("TotalD").child("Total");
                    Movi2.setValue(I2);*/
                /*}
                NOMEM.add("Despesas Fixas");
                DATAM.add(mesAnoSelecionado);
                VALORM.add(I2);
                adapter_movimentacoes.notifyDataSetChanged();
                Log.i("INFO","I2: "+I2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference Saidas = database.child("Saidas");

        Query query1 = Saidas.orderByChild("ID_trabalho");

        i = 0;

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String data = ds.child("Data_entrada").getValue().toString();
                    AUX = "";
                    AUX = data.substring(3,10);
                    if(mesAnoSelecionado.length() == 6){
                        mesAnoSelecionado = "0"+mesAnoSelecionado;
                    }

                    Log.i("DATAS S:",mesAnoSelecionado+"  "+AUX);
                    if(mesAnoSelecionado.equals(AUX)){
                        NOMEM.add(ds.child("Nome_cliente").getValue());
                        DATAM.add(ds.child("Data_entrada").getValue());
                        VALORM.add(ds.child("Total").getValue());
                        i = i + Integer.parseInt(ds.child("Total").getValue().toString());
                        Log.i("INFO","I: "+i);
                        /*DatabaseReference Movi = database.child("Relatorios").child("Movimentacoes").child("TotalS").child("Total");
                        Movi.setValue(i);*/

                        /*DatabaseReference Movi = database.child("Relatorios").child("Movimentacoes").child(mesAnoSelecionado).child("Fechamento");
                        DatabaseReference Movi2 = database.child("Relatorios").child("Movimentacoes").child(mesAnoSelecionado);

                        if(i == 0){
                            Movi.setValue(I2);
                            Total.setTextColor(getResources().getColor(R.color.Negativo));
                        }else{
                            int i3 = I2+i;
                            Movi.setValue(i3);

                            if (Math.signum(i3) == -1 ){
                                Total.setTextColor(getResources().getColor(R.color.Negativo));
                            }else {
                                Total.setTextColor(getResources().getColor(R.color.Positivo));
                            }
                            Movi2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Total.setText(snapshot.child("Fechamento").getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
                adapter_movimentacoes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void carregarTotal(){


        /*DatabaseReference total = database.child("Relatorios").child("Movimentacoes");

        Entradas = 0;
        Despesas = 0;

        Query query = total.child("TotalS");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Entradas = Integer.parseInt(ds.getValue().toString());
                }
                Query query1 = total.child("TotalD");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()){
                                Despesas = Integer.parseInt(ds.getValue().toString());
                            }
                            Log.i("INFO","TOTAL: "+Entradas);
                            Log.i("INFO","TOTAL2: "+Despesas);
                            int TOTALL = Entradas + Despesas;
                            String T = String.valueOf(TOTALL);
                            if (Math.signum(TOTALL) == -1 ){
                                Total.setTextColor(getResources().getColor(R.color.Negativo));
                            }else {
                                Total.setTextColor(getResources().getColor(R.color.Positivo));
                            }
                            Total.setText(T);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    /*}

    public void carregarCalendar(){

        CalendarDay dataAtual = calendar.getCurrentDate();

        mesAnoSelecionado = String.valueOf((dataAtual.getMonth() + 1) + "/" + dataAtual.getYear());

        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mesAnoSelecionado = String.valueOf((date.getMonth() + 1) + "/" + date.getYear());

            }
        });

    }*/

}