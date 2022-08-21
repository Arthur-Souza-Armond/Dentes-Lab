package com.example.denteslabv2.ui.Relatórios.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.media.RatingCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class Adapter_Movimentacoes extends RecyclerView.Adapter<Adapter_Movimentacoes.MyViewHolder> {

    private List NOME,DATA,PRECO;
    private Context context;

    public Adapter_Movimentacoes(List nome, List data, List preco, Context context) {
        this.NOME = nome;
        this.DATA = data;
        this.PRECO = preco;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = (LayoutInflater.from(parent.getContext())).inflate(R.layout.lista_adapter_movimentacao,parent,false);

        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Nome.setText(NOME.get(position).toString());
        holder.Data.setText(DATA.get(position).toString());
        if(Math.signum(Integer.parseInt(PRECO.get(position).toString())) == -1 ){
            int negativo = holder.Preco.getResources().getColor(R.color.Negativo);
            holder.Preco.setTextColor(negativo);
        }else{
            int positivo = holder.Preco.getResources().getColor(R.color.Positivo);
            holder.Preco.setTextColor(positivo);
        }
        holder.Preco.setText("R$ "+PRECO.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return NOME.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {

        TextView Nome,Data,Preco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nome = itemView.findViewById(R.id.txt_nomeA_movi);
            Data = itemView.findViewById(R.id.txt_dataA_movi);
            Preco = itemView.findViewById(R.id.txt_valorA_movi);

        }
    }

}
