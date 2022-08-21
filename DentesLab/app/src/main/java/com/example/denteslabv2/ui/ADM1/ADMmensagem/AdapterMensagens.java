package com.example.denteslabv2.ui.ADM1.ADMmensagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class AdapterMensagens extends RecyclerView.Adapter<AdapterMensagens.MyViewHolder> {

    private List TITULO,CORPO,ICON;
    private Context context;

    public AdapterMensagens(List Titulo,List Corpo,List Icon, Context Context){
        this.TITULO = Titulo;
        this.CORPO = Corpo;
        this.ICON = Icon;
        this.context = Context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_mensagens_adapter,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.titulo.setText(TITULO.get(position).toString());
            holder.corpo.setText(CORPO.get(position).toString());
            if(Integer.parseInt(ICON.get(position).toString()) == 2131165334){
                holder.icone.setImageResource(R.drawable.ic_pagamento_mensagem_24);
            }else {
                holder.icone.setImageResource(R.drawable.ic_trabalho_mensagem_24);
            }
    }

    @Override
    public int getItemCount() {
            return TITULO.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo,corpo;
        ImageView icone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txt_tituloMensagemAdapter);
            corpo = itemView.findViewById(R.id.txt_corpoMensagemAdapter);
            icone = itemView.findViewById(R.id.img_iconeMensagemAdapter);

        }
    }

}
