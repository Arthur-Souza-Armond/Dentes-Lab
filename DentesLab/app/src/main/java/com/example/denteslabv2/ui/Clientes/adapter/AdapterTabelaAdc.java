package com.example.denteslabv2.ui.Clientes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.ConcurrentModificationException;
import java.util.List;

public class AdapterTabelaAdc extends RecyclerView.Adapter<AdapterTabelaAdc.MyViewHolder> {

    private List NOME,PRECO;
    private Context context;

    public AdapterTabelaAdc(List NOME, List PRECO, Context context) {
        this.NOME = NOME;
        this.PRECO = PRECO;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter_tabela_cliente,parent,false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(NOME.size() == 0){
            holder.nome.setText("Sem dados");
            holder.preco.setText("Sem dados");
        }else {
            holder.nome.setText(NOME.get(position).toString());
            holder.preco.setText("R$ "+PRECO.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        if(NOME.size() == 0){
            return 1;
        }else {
            return NOME.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome,preco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

                nome = itemView.findViewById(R.id.txt_NomeServico_tabelaC);
                preco = itemView.findViewById(R.id.txt_precoServico_tabelaC);

        }
    }

}
