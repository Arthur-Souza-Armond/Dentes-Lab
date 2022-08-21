package com.example.denteslabv2.ui.Trabalhos.saida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class Adapter_saida extends RecyclerView.Adapter<Adapter_saida.MyViewHolder> {

    private List ID,TIPO,DATA,CLIENTE,TOTAL;
    private Context context;

    public Adapter_saida(List ID, List TIPO, List DATA, List CLIENTE, List TOTAL, Context context) {
        this.ID = ID;
        this.TIPO = TIPO;
        this.DATA = DATA;
        this.CLIENTE = CLIENTE;
        this.TOTAL = TOTAL;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = (LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adapter_id_saida,parent,false));
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Id.setText(ID.get(position).toString());
        holder.tipoS.setText("T: "+TIPO.get(position).toString());
        holder.data.setText("D: "+DATA.get(position).toString());
        holder.cliente.setText("C: "+CLIENTE.get(position).toString());
        holder.total.setText("R$ "+TOTAL.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Id, tipoS,data,cliente, total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.txt_lista_id);
            tipoS = itemView.findViewById(R.id.txt_servico_lista);
            data = itemView.findViewById(R.id.txt_data_trabalho_lista);
            cliente = itemView.findViewById(R.id.txt_nomeCliente_lista);
            total = itemView.findViewById(R.id.txt_total_lista);

        }
    }

}
