package com.example.denteslabv2.ui.ADM1.DB.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denteslabv2.R;

public class AdapterNo2Cliente extends RecyclerView.Adapter<AdapterNo2Cliente.MyViewHolder> {

    private String area,cep,cpf,endereco,nome,nomeSecretaria,referencia,telefone,telefoneSecretaria;
    private Context context;

    public AdapterNo2Cliente(String area, String cep, String cpf, String endereco, String nome, String nomeSecretaria, String referencia, String telefone, String telefoneSecretaria, Context context) {
        this.area = area;
        this.cep = cep;
        this.cpf = cpf;
        this.endereco = endereco;
        this.nome = nome;
        this.nomeSecretaria = nomeSecretaria;
        this.referencia = referencia;
        this.telefone = telefone;
        this.telefoneSecretaria = telefoneSecretaria;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter_no2_cliente,parent,false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(nome.equals("")){

            holder.Area.setText("Sem dados");
            holder.Cep.setText("Sem dados");
            holder.Cpf.setText("Sem dados");
            holder.Endereco.setText("Sem dados");
            holder.Nome.setText("Sem dados");
            holder.NomeSecretaria.setText("Sem dados");
            holder.Referencia.setText("Sem dados");
            holder.Telefone.setText("Sem dados");
            holder.TelefoneSecretaria.setText("Sem dados");

        }else {

            holder.Area.setText(area);
            holder.Cep.setText(cep);
            holder.Cpf.setText(cpf);
            holder.Endereco.setText(endereco);
            holder.Nome.setText(nome);
            holder.NomeSecretaria.setText(nomeSecretaria);
            holder.Referencia.setText(referencia);
            holder.Telefone.setText(telefone);
            holder.TelefoneSecretaria.setText(telefoneSecretaria);

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Area,Cep,Cpf,Endereco,Nome,NomeSecretaria,Referencia,Telefone,TelefoneSecretaria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        Area = itemView.findViewById(R.id.txt_area_no2);
        Cep = itemView.findViewById(R.id.txt_cep_no2);
        Cpf = itemView.findViewById(R.id.txt_cpf_no2);
        Endereco = itemView.findViewById(R.id.txt_endereco_no2);
        Nome = itemView.findViewById(R.id.txt_nome_no2);
        NomeSecretaria = itemView.findViewById(R.id.txt_nSecretaria_no2);
        Referencia = itemView.findViewById(R.id.txt_referencia_no2);
        Telefone = itemView.findViewById(R.id.txt_telefone_no2);
        TelefoneSecretaria = itemView.findViewById(R.id.txt_telefoneSecretaria_no2);

        }
    }

}
