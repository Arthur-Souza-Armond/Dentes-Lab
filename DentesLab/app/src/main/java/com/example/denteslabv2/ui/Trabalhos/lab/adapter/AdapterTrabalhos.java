package com.example.denteslabv2.ui.Trabalhos.lab.adapter;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class AdapterTrabalhos extends RecyclerView.Adapter<AdapterTrabalhos.MyViewHolder> {

    private List listaId,listaNome,listaData,listaTotal;
    private Context context;

    public AdapterTrabalhos(List i, List n, List d, List t, Context c) {
        this.listaId = i;
        this.listaNome = n;
        this.listaData = d;
        this.listaTotal = t;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = (LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_trabalhos_adapter,parent,false));
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String auxData;
        String[] auxNome;

        holder.id.setText(listaId.get(position).toString());

        auxNome = listaNome.get(position).toString().split(" ");
        holder.cliente.setText(auxNome[0].concat(" "+auxNome[1]));

        auxData = listaData.get(position).toString().substring(0,5);
        holder.data.setText(auxData);
        holder.total.setText("R$ "+listaTotal.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return listaId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,cliente,data,total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.txt_id_trabalho);
            cliente = itemView.findViewById(R.id.txt_nomeDentista_trabalho);
            data = itemView.findViewById(R.id.txt_dataEntrada_trabalho);
            total = itemView.findViewById(R.id.txt_total_trabalho);

        }
    }

}
