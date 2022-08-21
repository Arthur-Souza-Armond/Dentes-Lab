package com.example.denteslabv2.ui.Trabalhos.Entrada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Servicos.Servicos;
import com.example.denteslabv2.ui.Servicos.ServicosS;

import java.util.List;

public class AdapterPesquisa_servico extends RecyclerView.Adapter<AdapterPesquisa_servico.MyViewHolder> {

    private List listaServicos;
    private List listaPrecoServicos;
    private Context context;

    public AdapterPesquisa_servico(List l,List p, Context c) {
        this.listaServicos = l;
        this.context = c;
        this.listaPrecoServicos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_servicos_entrada,parent,false);
        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nome.setText(listaServicos.get(position).toString());
        holder.preco.setText("R$ "+listaPrecoServicos.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return listaServicos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome,preco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txt_nome_servico_lista);
            preco = itemView.findViewById(R.id.txt_preco_servicos_lista);

        }
    }

}
