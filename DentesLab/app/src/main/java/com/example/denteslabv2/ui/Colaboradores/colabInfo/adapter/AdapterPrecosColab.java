package com.example.denteslabv2.ui.Colaboradores.colabInfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class AdapterPrecosColab extends RecyclerView.Adapter<AdapterPrecosColab.MyViewHolder> {

    private List NOME,PRECO;
    private Context context;

    public AdapterPrecosColab(List NOME, List PRECO, Context context) {
        this.NOME = NOME;
        this.PRECO = PRECO;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter_precos_colab,parent,false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(NOME.size() == 0){
            holder.nome.setText("Buscando dados...");
            holder.preco.setText("Buscando dados...");
        }else{
            if(Integer.parseInt(PRECO.get(position).toString()) == 0){
                holder.nome.setText(NOME.get(position).toString());
                holder.preco.setText("");
            }else{
                holder.nome.setText(NOME.get(position).toString());
                holder.preco.setText("R$ "+PRECO.get(position).toString());
            }
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

        TextView nome,preco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txt_nomeServicoColab_Adapter);
            preco = itemView.findViewById(R.id.txt_valorServicoColab_Adapter);

        }
    }

}
