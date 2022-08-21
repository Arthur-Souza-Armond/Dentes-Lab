package com.example.denteslabv2.ui.Clientes.adapter;

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

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.MyViewHolder> {

    private List<Clientes> Rv;
    private Context context;

    public ClientesAdapter(List<Clientes> rv, Context context) {
        this.Rv = rv;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_clientes_adapter,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(Rv.size() == 0){
            holder.NomeCliente.setText("Buscando clientes");
            holder.TelefoneCliente.setText("Buscando cliente");
            holder.Endereco.setText("Buscando cliente");
            holder.Cep.setText("Buscando cliente");
        }else {

            Clientes clientes = Rv.get(position);

            holder.NomeCliente.setText(clientes.getNome());
            holder.TelefoneCliente.setText(clientes.getTelefone());
            holder.Endereco.setText(clientes.getEndereco());
            holder.Cep.setText(clientes.getCEP());

        }

    }

    @Override
    public int getItemCount() {
        if(Rv.size() == 0){
            return 1;
        }else{
            return Rv.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView NomeCliente,TelefoneCliente,Endereco,Cep;

        public MyViewHolder(View itemView) {
            super(itemView);

            NomeCliente = itemView.findViewById(R.id.txt_cliente_nome);
            TelefoneCliente = itemView.findViewById(R.id.txt_telefone_cliente);
            Endereco = itemView.findViewById(R.id.txt_endereco_cliente_adapter);
            Cep = itemView.findViewById(R.id.txt_cep_cliente_A);

        }
    }
}
