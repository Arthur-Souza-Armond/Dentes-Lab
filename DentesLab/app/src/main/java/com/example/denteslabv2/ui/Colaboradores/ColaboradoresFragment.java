package com.example.denteslabv2.ui.Colaboradores;


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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.denteslabv2.PrincipalActivity;
import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Colaboradores.adapter.Colabadapter;
import com.example.denteslabv2.ui.Colaboradores.colabInfo.COLABActivity;
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

import static androidx.recyclerview.widget.RecyclerView.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboradoresFragment extends Fragment {

    private RecyclerView Rv;
    private List listaNome,ListaNivel;
    private List<Colaborador> listaColab = new ArrayList<>();
    private Colabadapter colabadapter;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FloatingActionButton Adc;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public ColaboradoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colaboradores, container, false);

        listaNome = new ArrayList();
        ListaNivel = new ArrayList();

        Rv = view.findViewById(R.id.rv_colab);
        Adc = view.findViewById(R.id.fab_C);

        Adc.setVisibility(INVISIBLE);

        DatabaseReference data = database.child("Usuarios");

        Query query = data.orderByChild("E-mail")
                .startAt(auth.getCurrentUser().getEmail())
                .endAt(auth.getCurrentUser().getEmail() + "\uf88f");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(Integer.parseInt(ds.child("NivelPermissao").getValue().toString()) >= 2){

                        Adc.setVisibility(VISIBLE);

                    }else{

                        Adc.setVisibility(INVISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Adc.setVisibility(INVISIBLE);

        PrincipalActivity principalActivity = new PrincipalActivity();
        /*Colabs colabs = new Colabs();
        Log.i("INFO",colabs.getUsuario()+"/"+colabs.getNivel());*/

        carregarTarefas();

        Adc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),AdcionarServicoColabActivity.class);
                startActivity(intent);

            }
        });

        Rv.addOnItemTouchListener(
                new RecyclerItemClickListnner(
                        getActivity().getApplicationContext(),
                        Rv,
                        new RecyclerItemClickListnner.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //Recuperar o colaborador selecionado
                                String nome = listaNome.get(position).toString();
                                Log.i("INFO","TESTE: "+nome);

                                //Envia para a nova Activity
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Sucesso ao clicar",
                                        Toast.LENGTH_LONG).show();

                                 Intent intent = new Intent(getActivity().getApplicationContext(), COLABActivity.class);
                                 intent.putExtra("Nome",nome);
                                 startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar o colaborador selecionado
                                String nome = listaNome.get(position).toString();

                                //Pegar a referencia dos childs
                                DatabaseReference COLABORADORES = database.child("Colaboradores").child(nome);

                                Snackbar.make(view, "Excluir colab "+nome +" ?", Snackbar.LENGTH_LONG)
                                        .setAction("SIM", new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                COLABORADORES.removeValue();
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


        /*database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carregarTarefas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        return  view;
    }

    public void carregarTarefas(){

        listaNome.clear();
        ListaNivel.clear();

        //Listar os colaboradores
        Colaboradores colab = new Colaboradores();

        //Adapter
        colabadapter = new Colabadapter(listaNome,ListaNivel,getActivity());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv.setLayoutManager(layoutManager);
        Rv.setHasFixedSize(true);
        Rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Rv.setAdapter( colabadapter );

        DatabaseReference userRef = database.child("Usuarios");

        Query query = userRef.orderByChild("NivelPermissao");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    listaNome.add(ds.child("Nome").getValue());
                    ListaNivel.add(ds.child("NivelPermissao").getValue());
                    Log.i("INFO","Usuario: "+ds.child("Nome").getValue());
                }
                colabadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
