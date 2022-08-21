package com.example.denteslabv2.ui.Trabalhos.lab;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Trabalhos.lab.adapter.AdapterTrabalhos;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrabalhosLabFragment extends Fragment {

    private RecyclerView Rv_trabalhos;
    private List ID,NOME,DATA,TOTAL,SERVICO;
    private AdapterTrabalhos adapterTrabalhos;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private Dialog mDialog,mConfirmacao;
    private TextInputEditText nome,telefone,endereco,cep,nomeS,preco;
    private Button Trabalhos,Sim,Nao;
    private TextInputEditText DataS,DataE,Obs,Cor,Dentes,Paciente;
    private ImageButton editarTrabalho;
    private Button ConfirmarEdit;
    private TextView editandoTrabalho;
    private String Total;
    private ProgressBar carregat;

    public TrabalhosLabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trabalhos_lab, container, false);
        mDialog = new Dialog(getContext());
        mConfirmacao = new Dialog(getContext());

        ID = new ArrayList();
        NOME = new ArrayList();
        DATA = new ArrayList();
        TOTAL = new ArrayList();
        SERVICO = new ArrayList();

        Rv_trabalhos = view.findViewById(R.id.Rv_lista_trabalhos);
        carregat = view.findViewById(R.id.pb_carregarTLab);

        Rv_trabalhos.setVisibility(View.GONE);
        carregat.setVisibility(View.VISIBLE);

        carregarT();

        Rv_trabalhos.setHasFixedSize(true);
        Rv_trabalhos.setLayoutManager(new LinearLayoutManager(getActivity()));
        Rv_trabalhos.setAdapter(adapterTrabalhos);

        Rv_trabalhos.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_trabalhos,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Button txtClose;
                                /*TextView nome,telefone,endereco,cep;
                                TextView nomeS,preco;*/

                            mDialog.setContentView(R.layout.popup_trabalhos);

                                nome = mDialog.findViewById(R.id.txt_nome_popup);
                                telefone = mDialog.findViewById(R.id.txt_telefone_popup);
                                endereco = mDialog.findViewById(R.id.txt_endereco_popup);
                                nomeS = mDialog.findViewById(R.id.txt_nome_produto_popup);
                                preco = mDialog.findViewById(R.id.txt_preco_poduto_popup);
                                DataS = mDialog.findViewById(R.id.txt_dataSaida_popup);
                                DataE = mDialog.findViewById(R.id.txt_dataEntrada_popup);
                                Obs = mDialog.findViewById(R.id.txt_observacaoT_popup);
                                Dentes = mDialog.findViewById(R.id.txt_dentes_popup);
                                Cor = mDialog.findViewById(R.id.txt_cor_dentes_popup);
                                Paciente = mDialog.findViewById(R.id.txt_nomePaciente_popupT);

                                Trabalhos = mDialog.findViewById(R.id.btn_trabalhos_popup);

                                editarTrabalho = mDialog.findViewById(R.id.ib_editarTrabalho);
                                ConfirmarEdit = mDialog.findViewById(R.id.btn_confirmarEditTrabalho);
                                ConfirmarEdit.setVisibility(View.GONE);
                                editandoTrabalho = mDialog.findViewById(R.id.txt_editandoTrabalho);

                                editandoTrabalho.setVisibility(View.INVISIBLE);

                                nome.setEnabled(false);
                                telefone.setEnabled(false);
                                endereco.setEnabled(false);
                                nomeS.setEnabled(false);
                                preco.setEnabled(false);

                            txtClose = mDialog.findViewById(R.id.txt_fechar_popup);


                            String nomeCliente = NOME.get(position).toString();
                            String IdTrabalho = ID.get(position).toString();

                            DatabaseReference data = database.child("Trabalhos");

                            Query query = data.orderByChild("ID")
                                    .startAt(IdTrabalho)
                                    .endAt(IdTrabalho + "\uf8ff");

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()){
                                        nome.setText(ds.child("Nome_cliente").getValue().toString());
                                        telefone.setText(ds.child("Telefone_cliente").getValue().toString());
                                        endereco.setText(ds.child("Endereco_cliente").getValue().toString());
                                        nomeS.setText(ds.child("Nome_servico").getValue().toString());
                                        preco.setText(ds.child("Total").getValue().toString());
                                        DataS.setText(ds.child("Previsao_saida").getValue().toString());
                                        DataE.setText(ds.child("Data_entrada").getValue().toString());
                                        Obs.setText(ds.child("Observacao").getValue().toString());
                                        Dentes.setText(ds.child("Dentes").getValue().toString());
                                        Cor.setText(ds.child("Cor").getValue().toString());
                                        Paciente.setText(ds.child("Nome_paciente").getValue().toString());
                                        Total = ds.child("Total").getValue().toString();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            txtClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });

                            editarTrabalho.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    nome.setEnabled(true);
                                    telefone.setEnabled(true);
                                    endereco.setEnabled(true);
                                    nomeS.setEnabled(true);
                                    preco.setEnabled(true);

                                    Trabalhos.setVisibility(View.GONE);
                                    ConfirmarEdit.setVisibility(View.VISIBLE);
                                    editandoTrabalho.setVisibility(View.VISIBLE);

                                    ConfirmarEdit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String NomeC,Telefone,Endereco,Cep,NomeS,Preco,dataS,dataE,obs,dentes,cor,paciente;

                                            NomeC = nome.getText().toString();
                                            Telefone = telefone.getText().toString();
                                            Endereco = endereco.getText().toString();
                                            NomeS = nomeS.getText().toString();
                                            Preco = preco.getText().toString();
                                            dataS = DataS.getText().toString();
                                            dataE = DataE.getText().toString();
                                            obs = Obs.getText().toString();
                                            dentes = Dentes.getText().toString();
                                            cor = Cor.getText().toString();
                                            paciente = Paciente.getText().toString();

                                            if(!NomeC.equals("") && !Telefone.equals("") && !Endereco.equals("") &&
                                                    !NomeS.equals("") && !Preco.equals("") && !dataS.equals("") && !dataE.equals("")
                                                    && !obs.equals("")
                                            && !dentes.equals("") && !cor.equals("") && !paciente.equals("")){

                                                DatabaseReference DataAux = data.child(IdTrabalho);
                                                DataAux.removeValue();

                                                DatabaseReference DataNvId = data.child(IdTrabalho);

                                                DataNvId.child("ID").setValue(IdTrabalho);
                                                DataNvId.child("Nome_cliente").setValue(NomeC);
                                                DataNvId.child("Telefone_cliente").setValue(Telefone);
                                                DataNvId.child("Endereco_cliente").setValue(Endereco);
                                                DataNvId.child("Nome_servico").setValue(NomeS);
                                                DataNvId.child("Preco_servico").setValue(Preco);
                                                DataNvId.child("Previsao_saida").setValue(dataS);
                                                DataNvId.child("Data_entrada").setValue(dataE);
                                                DataNvId.child("Observacao").setValue(obs);
                                                DataNvId.child("Dentes").setValue(dentes);
                                                DataNvId.child("Cor").setValue(cor);
                                                DataNvId.child("Nome_paciente").setValue(paciente);
                                                DataNvId.child("Total").setValue(Preco);

                                                mDialog.dismiss();
                                                carregarTrabalhos();

                                            }

                                        }
                                    });

                                }
                            });

                            Trabalhos.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    DatabaseReference trabalhoRef = database.child("Trabalhos").child(ID.get(position).toString());

                                    mConfirmacao.setContentView(R.layout.popup_confirmacao_trabalhos);
                                    Sim = mConfirmacao.findViewById(R.id.btn_sim_confirmacao);
                                    Nao = mConfirmacao.findViewById(R.id.btn_nao_confirmacao);

                                    Sim.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            trabalhoRef.removeValue();
                                            mConfirmacao.dismiss();
                                            mDialog.dismiss();
                                            carregarTrabalhos();
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Sucesso ao remover",
                                                    Toast.LENGTH_LONG).show();


                                        }
                                    });

                                    Nao.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mConfirmacao.dismiss();
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Operação cancelada",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                mConfirmacao.show();
                                }
                            });

                            mDialog.show();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )

        );

        return view;
    }

    public void carregarT(){
        AsyncTask task = new carregarAsync();
        task.execute();
    }

    public void carregarTrabalhos(){

        //limpa as listas
        ID.clear();
        NOME.clear();
        DATA.clear();
        TOTAL.clear();

        //configura o adapter
        adapterTrabalhos = new AdapterTrabalhos(ID,NOME,DATA,TOTAL,getActivity());

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_trabalhos.setLayoutManager(layoutManager);
        Rv_trabalhos.setHasFixedSize(true);
        Rv_trabalhos.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_trabalhos.setAdapter( adapterTrabalhos );

        //Recupera o banco de dados
        DatabaseReference trabalhosRef = database.child("Trabalhos");

        Query query = trabalhosRef.orderByChild("ID");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ID.add(ds.child("ID").getValue());
                    NOME.add(ds.child("Nome_cliente").getValue());
                    DATA.add(ds.child("Previsao_saida").getValue());
                    TOTAL.add(ds.child("Total").getValue());
                    SERVICO.add(ds.child("Nome_servico").getValue());
                    Log.i("INFO","ID: "+ds.child("Total").getValue());
                }
                adapterTrabalhos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private class carregarAsync extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Rv_trabalhos.setVisibility(View.GONE);
            carregat.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            Rv_trabalhos.setVisibility(View.GONE);
            carregat.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Rv_trabalhos.setVisibility(View.VISIBLE);
            carregat.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            carregarTrabalhos();

            return null;
        }
    }

}
