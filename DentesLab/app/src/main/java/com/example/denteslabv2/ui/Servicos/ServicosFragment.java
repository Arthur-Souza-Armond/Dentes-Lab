package com.example.denteslabv2.ui.Servicos;


import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.ClientesDAO;
import com.example.denteslabv2.ui.Servicos.adapter.ServicosAdapter;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicosFragment extends Fragment {

    private FloatingActionButton fab;
    private List listaServicos,listaPreco;
    private ServicosAdapter servicosAdapter;
    private RecyclerView Rv_servicos;
    private TextInputEditText nome,preco;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private Dialog mDialog;
    private Button fechar,edit,save;
    private String Aux;

    public ServicosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicos, container, false);

        Rv_servicos = view.findViewById(R.id.rv_servicos);
        listaServicos = new ArrayList<>();
        listaPreco = new ArrayList<>();
        mDialog = new Dialog(getActivity());
        carregarServicos();

        Rv_servicos.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv_servicos,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                mDialog.setContentView(R.layout.popup_servicos);
                                nome = mDialog.findViewById(R.id.txt_nome_servico_popup);
                                preco = mDialog.findViewById(R.id.txt_preco_servico_popup);
                                fechar = mDialog.findViewById(R.id.btn_fechar);
                                edit = mDialog.findViewById(R.id.btn_edit_popup);
                                save = mDialog.findViewById(R.id.btn_salvar_popup);

                                save.setVisibility(View.GONE);

                                nome.setText(listaServicos.get(position).toString());
                                preco.setText(listaPreco.get(position).toString());

                                nome.setEnabled(false);
                                preco.setEnabled(false);

                                fechar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                    }
                                });

                                edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        nome.setEnabled(true);
                                        preco.setEnabled(true);

                                        edit.setVisibility(View.GONE);
                                        save.setVisibility(View.VISIBLE);

                                        String NomeM = nome.getText().toString();

                                        save.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String Nome = nome.getText().toString();
                                                String Preco = preco.getText().toString();

                                                DatabaseReference servicoRef = database.child("Servicos").child(NomeM);
                                                servicoRef.removeValue();
                                                DatabaseReference servicoRef2 = database.child("Servicos").child(Nome);

                                                servicoRef2.child("Nome").setValue(Nome);
                                                servicoRef2.child("Nome_pesquisa").setValue(Nome.toUpperCase());
                                                servicoRef2.child("Preco").setValue(Preco);

                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Sucesso ao editar",
                                                        Toast.LENGTH_LONG).show();
                                                mDialog.dismiss();
                                                carregarServicos();

                                            }
                                        });

                                    }
                                });

                                mDialog.show();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                DatabaseReference auxRef = database.child("Servicos").child(listaServicos.get(position).toString());
                                Snackbar.make(view, "Excluir serviço "+listaServicos.get(position).toString() +" ?", Snackbar.LENGTH_LONG)
                                        .setAction("SIM", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                auxRef.removeValue();
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Colaborador excluido",
                                                        Toast.LENGTH_LONG).show();
                                                carregarServicos();

                                            }
                                        }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                        .show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                ));

        fab = view.findViewById(R.id.fab_servicos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AdcServicosActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    public void carregarServicos(){

        listaServicos.clear();
        listaPreco.clear();

        ClientesDAO clientesDAO = new ClientesDAO();

        //Configura o adapter
        servicosAdapter = new ServicosAdapter(listaServicos,listaPreco,getActivity());

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_servicos.setLayoutManager(layoutManager);
        Rv_servicos.setHasFixedSize(true);
        Rv_servicos.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_servicos.setAdapter( servicosAdapter );

        DatabaseReference servicosRef = database.child("Servicos");

        Query query = servicosRef.orderByChild("Nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    listaServicos.add(ds.child("Nome").getValue());
                    listaPreco.add(ds.child("Preco").getValue());
                    Log.i("INFO","Nproduto: "+ds.child("Nome").getValue());
                }
                servicosAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
