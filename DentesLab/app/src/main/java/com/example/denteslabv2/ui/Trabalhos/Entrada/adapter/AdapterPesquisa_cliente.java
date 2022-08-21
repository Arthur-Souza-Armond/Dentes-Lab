package com.example.denteslabv2.ui.Trabalhos.Entrada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.Clientes;

import java.util.List;

public class AdapterPesquisa_cliente extends RecyclerView.Adapter<AdapterPesquisa_cliente.MyViewHolder> {

    private List<Clientes> listaClientes;
    private Context context;

    public AdapterPesquisa_cliente(List<Clientes> l, Context c) {
        this.listaClientes = l;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_clientes_entrada,parent,false);
            return  new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Clientes clientes = listaClientes.get(position);
        holder.nome.setText(clientes.getNome());

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txt_nome_cliente_adapter_pesquisa);

        }
    }

}
