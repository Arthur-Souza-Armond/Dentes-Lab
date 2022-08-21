package com.example.denteslabv2.ui.RH;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.denteslabv2.ui.Colaboradores.colabInfo.COLABActivity;
import com.example.denteslabv2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RHFragment extends Fragment {

    private TextInputEditText nome,email,telefone;
    private RadioGroup radioGroup;
    private RadioButton financeiro,marketing,protetico;
    private Button cadastrar;
    private String Setor;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public RHFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rh, container, false);

        Intent intent = new Intent(getActivity().getApplicationContext(),COLABActivity.class);
        startActivity(intent);

        /*nome = view.findViewById(R.id.txt_nome_colab);
        email = view.findViewById(R.id.txt_email_colab);
        telefone = view.findViewById(R.id.txt_telefone_colab);

        radioGroup = view.findViewById(R.id.radioGroup);

        cadastrar = view.findViewById(R.id.btn_cadastrar);

        financeiro = view.findViewById(R.id.rb_financeiro);
        marketing = view.findViewById(R.id.rb_marketing);
        protetico = view.findViewById(R.id.rb_protetico);*/



        /*cadastrar.setOnClickListener(v -> {

            String nome_colab = nome.getText().toString();
            String email_colab = email.getText().toString();
            String telefone_colab = telefone.getText().toString();

            if(financeiro.isChecked()){
                Setor = "Financeiro";
            }else if(marketing.isChecked()){
                Setor = "Marketing";
            }else{
                Setor = "protetico";
            }


            DatabaseReference userRef = database.child("Colaboradores");
            DatabaseReference userRef2 = userRef.child(nome_colab);

            userRef2.child("Nome").setValue(nome_colab);
            userRef2.child("Email").setValue(email_colab);
            userRef2.child("Telefone").setValue(telefone_colab);
            userRef2.child("Setor").setValue(Setor);

        });*/

        /*cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeC = nome.getText().toString();
                String emailC = email.getText().toString();
                String telefoneC = telefone.getText().toString();
                if(financeiro.isChecked()){
                    Setor = "Financeiro";
                }else if(marketing.isChecked()){
                    Setor = "Marketing";
                }else if(protetico.isChecked()){
                    Setor = "Protetico";
                }

                if((!nomeC.isEmpty()) && (!emailC.isEmpty()) && (!telefoneC.isEmpty()) && (Setor != null)){

                    Colaborador colaborador = new Colaborador();
                    Colaboradores colab = new Colaboradores();
                    colaborador.setNome(nomeC);
                    colaborador.setEmail(emailC);
                    colaborador.setTelefone(telefoneC);
                    colaborador.setSetor(Setor);
                Log.i("INFO","Nome: "+colaborador.getNome());
                Log.i("INFO","Email: "+colaborador.getEmail());
                Log.i("INFO","Telefone: "+colaborador.getTelefone());
                Log.i("INFO","Setor: "+colaborador.getSetor());

                    if(colab.salvar(colaborador)){
                        Toast.makeText(getActivity().getApplicationContext(),
                                        "Sucesso ao salvar colaborador",
                                        Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "ERRO ao cadastrar novo colaborador",
                                    Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Você deve completar todos os campos",
                            Toast.LENGTH_LONG).show();
                }

            }
        });*/


        return  view;
    }

}
