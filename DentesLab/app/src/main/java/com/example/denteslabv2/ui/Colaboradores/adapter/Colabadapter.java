package com.example.denteslabv2.ui.Colaboradores.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

import java.util.List;

public class Colabadapter extends RecyclerView.Adapter<Colabadapter.MyViewHolder> {

    private List Rv,Rv2;
    private Context context;
    private RecyclerView recyclerView;

    public Colabadapter(List list,List lis2, Context c){
        this.Rv = list;
        this.Rv2 = lis2;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_colabs_adapter,parent,false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.NomeC.setText(Rv.get(position).toString());
        holder.SetorC.setText("Nível: "+Rv2.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return this.Rv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView NomeC,SetorC;

        public MyViewHolder(View itemView){
            super(itemView);

            NomeC = itemView.findViewById(R.id.txt_colab_adapter);
            SetorC = itemView.findViewById(R.id.txt_setor_colab_adapter);

        }

    }



}
