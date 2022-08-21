package com.example.denteslabv2.ui.Servicos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Servicos.Servicos;
import com.example.denteslabv2.ui.Servicos.ServicosS;
import com.example.denteslabv2.ui.Servicos.adapter.ServicosAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.MyViewHolder> {

    private List Rv,Rv_preco;
    private Context context;
    private Servicos servicos;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public ServicosAdapter(List rv, List Rv, Context context){
        this.Rv = rv;
        this.Rv_preco = Rv;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_servicos_adapter,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nomeServico.setText(Rv.get(position).toString());
        holder.precoServico.setText("R$ "+Rv_preco.get(position).toString()+",00");

    }

    @Override
    public int getItemCount() {
        return Rv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeServico,precoServico;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeServico = itemView.findViewById(R.id.txt_nome_servicos);
            precoServico = itemView.findViewById(R.id.txt_preco_servicos);

        }

    }

}
