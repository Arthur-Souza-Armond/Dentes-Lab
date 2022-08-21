package com.example.denteslabv2.ui.Colaboradores.colabInfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterFeitosColab extends RecyclerView.Adapter<AdapterFeitosColab.MyViewHolder> {

    private List TIPO,DATA;
    private Context context;

    public AdapterFeitosColab(List TIPO, List DATA, Context context) {
        this.TIPO = TIPO;
        this.DATA = DATA;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_aapter_feitos_colab,parent,false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(TIPO.size() == 0){

            holder.tipo.setText("Buscando dados...");
            holder.data.setText("Buscando dados...");

        }else{

            holder.tipo.setText(TIPO.get(position).toString());
            holder.data.setText(DATA.get(position).toString());

        }

    }

    @Override
    public int getItemCount() {
        if(TIPO.size() == 0){
            return 1;
        }else{
            return TIPO.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tipo,data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tipo = itemView.findViewById(R.id.txt_tipoTrabalhoFeito_Adapter);
            data = itemView.findViewById(R.id.txt_dataTrabalhoFeito_Adapter);

        }
    }

}
