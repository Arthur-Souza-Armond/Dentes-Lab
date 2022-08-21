package com.example.denteslabv2.ui.Relatórios.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Trabalhos.lab.adapter.AdapterTrabalhos;

import java.util.List;

public class Adapter_Fixas extends RecyclerView.Adapter<Adapter_Fixas.MyViewHolder> {

    private List NomeF,DataF,ValorF;
    private Context context;

    public Adapter_Fixas(List nomeF, List dataF, List valorF, Context context) {
        this.NomeF = nomeF;
        this.DataF = dataF;
        this.ValorF = valorF;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = (LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adapter_fixas,parent,false));

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(NomeF.size() == 0){
            holder.nome.setText("Buscando dados");
            holder.data.setText("Sem dados");
            holder.valor.setText("Sem dados");
        }else {
            holder.nome.setText(NomeF.get(position).toString());
            holder.data.setText("D: " + DataF.get(position).toString());
            holder.valor.setText("R$ " + ValorF.get(position).toString());
        }

    }

    @Override
    public int getItemCount() {
        if(NomeF.size() == 0){
            return 1;
        }else{
            return NomeF.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome,data,valor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txt_lista_fixas_nome);
            data = itemView.findViewById(R.id.txt_lista_fixas_data);
            valor = itemView.findViewById(R.id.txt_lista_fixas_valor);

        }
    }

}
