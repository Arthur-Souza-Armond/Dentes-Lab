package com.example.denteslabv2.ui.Clientes;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.denteslabv2.PrincipalActivity;
import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.adapter.ClientesAdapter;
import com.example.denteslabv2.ui.Colaboradores.Colaborador;
import com.example.denteslabv2.ui.Principal.PrincipalFragment;
import com.example.denteslabv2.ui.Servicos.Servicos;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
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
public class ClientesFragment extends Fragment {

    private RecyclerView Rv_cliente;
    private List<Clientes> listaCliente;
    private ClientesAdapter clientesAdapter;
    private TextView teste;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FloatingActionButton fab;
    private String Usuario;
    private int Nivel;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public ClientesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        Rv_cliente = view.findViewById(R.id.rv_clientes);

        PrincipalActivity principalActivity = new PrincipalActivity();
        /*Nivel = principalActivity.getNivel();
        Usuario = principalActivity.getUsuario();*/

        /*if(Nivel < 2){
            Toast.makeText(getActivity().getApplicationContext(),"Você não tem nível de acesso para ver isso",Toast.LENGTH_LONG).show();
        }else {*/

        DatabaseReference data = database.child("Usuarios");

        Query query = data.orderByChild("E-mail")
                .startAt(auth.getCurrentUser().getEmail())
                .endAt(auth.getCurrentUser().getEmail() + "\uf88f");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Usuario = ds.child("NivelPermissao").getValue().toString();
                }

                if (Integer.parseInt(Usuario) >= 2){

                    carregarClientes();

                    Rv_cliente.addOnItemTouchListener(
                            new RecyclerItemClickListnner(
                                    getActivity().getApplicationContext(),
                                    Rv_cliente,
                                    new RecyclerItemClickListnner.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {

                                            Clientes clientes = listaCliente.get(position);
                                            Log.i("INFO", "Email: " + clientes.getCPF());
                                            Log.i("INFO", "Nome: " + clientes.getNome());

                                            Intent intent = new Intent(getActivity().getApplicationContext(), AdcClienteActivity.class);
                                            intent.putExtra("Nome", clientes);
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {

                                            //Recuperar o colaborador selecionado
                                            Clientes clienteSelecionado = listaCliente.get(position);

                                            //Pegar a referencia dos childs
                                            DatabaseReference CLIENTES = database.child("Clientes").child(clienteSelecionado.getNome());

                                            Snackbar.make(view, "Excluir cliente " + clienteSelecionado.getNome() + " ?", Snackbar.LENGTH_LONG)
                                                    .setAction("SIM", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            CLIENTES.removeValue();
                                                            Toast.makeText(getActivity().getApplicationContext(),
                                                                    "Colaborador excluido",
                                                                    Toast.LENGTH_LONG).show();

                                                        }
                                                    }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                                    .show();

                                        }

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        }
                                    }
                            )
                    );

                }else{

                    listaCliente = new ArrayList<>();

                    clientesAdapter = new ClientesAdapter(listaCliente,getActivity());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //}

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AdcClienteActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    public void carregarClientes(){

        listaCliente = new ArrayList<>();

        Rv_cliente.clearFocus();

        //Listar Clientes
        ClientesDAO cliente = new ClientesDAO();

        //Adapter
        clientesAdapter = new ClientesAdapter(listaCliente,getActivity());

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv_cliente.setLayoutManager(layoutManager);
        Rv_cliente.setHasFixedSize(true);
        Rv_cliente.addItemDecoration(new DividerItemDecoration(getParentFragment().getContext(), LinearLayout.VERTICAL));
        Rv_cliente.setAdapter( clientesAdapter );

        DatabaseReference clienteRef = database.child("Clientes");

        clienteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    listaCliente.add(ds.getValue(Clientes.class));
                    Log.i("INFO","Usuario: "+ds.getValue(Clientes.class));
                }
                clientesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
