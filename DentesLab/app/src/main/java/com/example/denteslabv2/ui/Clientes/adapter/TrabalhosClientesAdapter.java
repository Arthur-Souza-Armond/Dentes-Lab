package com.example.denteslabv2.ui.Clientes.adapter;

import static com.example.denteslabv2.R.color.Negativo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class TrabalhosClientesAdapter extends RecyclerView.Adapter<TrabalhosClientesAdapter.MyViewHolder> {

    private List NOME,DATA,VALOR,ID;
    private Context context;

    public TrabalhosClientesAdapter(List NOME, List DATA, List VALOR,List ID, Context context) {
        this.NOME = NOME;
        this.DATA = DATA;
        this.VALOR = VALOR;
        this.ID = ID;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter_trabalhos_c,parent,false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(NOME.size() == 0){
            holder.Nome.setText("Sem dados");
            holder.Data.setText("Sem dados");
            holder.Valor.setText("Sem dados");
        }else{

            if(NOME.get(position).toString().equals("Despesa fixa")){

                holder.Valor.setTextColor(Color.RED);

            }else if(NOME.get(position).toString().equals("Despesa variavel")){

                holder.Valor.setTextColor(Color.YELLOW);

            }else if(NOME.get(position).toString().equals("Cliente especifico")){



            }else if (NOME.get(position).toString().equals("Cliente nao especificado")){



            }

            holder.Nome.setText(ID.get(position).toString());
            holder.Data.setText(DATA.get(position).toString());
            holder.Valor.setText("R$ "+VALOR.get(position).toString());
        }

    }

    @Override
    public int getItemCount() {
        if (NOME.size() == 0){
            return 1;
        }else{
            return NOME.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Nome,Data,Valor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nome = itemView.findViewById(R.id.txt_nome_c_adapter);
            Data = itemView.findViewById(R.id.txt_data_c_adapter);
            Valor = itemView.findViewById(R.id.txt_valor_c_adapter);

        }
    }

}
