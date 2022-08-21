package com.example.denteslabv2.ui.ADM1.DB.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class AdapterNo1DB extends RecyclerView.Adapter<AdapterNo1DB.MyViewHolder> {

    private List Titulo;
    private Context context;

    public AdapterNo1DB(List titulo, Context context) {
        this.Titulo = titulo;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter_no_1,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(Titulo.size() == 0){

            holder.TituloNo1.setText("Sem dados");

        }else{

            holder.TituloNo1.setText(Titulo.get(position).toString());

        }

    }

    @Override
    public int getItemCount() {
        if(Titulo.size() == 0){
            return 1;
        }else{
            return Titulo.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TituloNo1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TituloNo1 = itemView.findViewById(R.id.txt_tituloNo1);

        }
    }

}
