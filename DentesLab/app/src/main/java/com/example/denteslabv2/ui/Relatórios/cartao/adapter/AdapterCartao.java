package com.example.denteslabv2.ui.Relatórios.cartao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class AdapterCartao extends RecyclerView.Adapter<AdapterCartao.MyViewHolder> {

    private List NOME,VALOR,PARCELA;
    private Context context;

    public AdapterCartao(List NOME, List VALOR, List PARCELA, Context context) {
        this.NOME = NOME;
        this.VALOR = VALOR;
        this.PARCELA = PARCELA;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = (LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_despesas_cartao,parent,false));

        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(NOME.size() == 0){

            holder.nome.setText("Buscando dados");
            holder.valor.setText("Sem dados");
            holder.parcela.setText("Sem dados");

        }else{

            holder.nome.setText(NOME.get(position).toString());
            holder.valor.setText("R$ "+VALOR.get(position).toString());
            holder.parcela.setText(PARCELA.get(position).toString());

        }

    }

    @Override
    public int getItemCount() {
        if(NOME.size() == 0){
            return 1;
        }else{
            return NOME.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome,valor,parcela;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txt_nomeAdapterCartao);
            valor = itemView.findViewById(R.id.txt_valorAdapterCartao);
            parcela = itemView.findViewById(R.id.txt_parcelaAdapterCartao);

        }
    }

}
