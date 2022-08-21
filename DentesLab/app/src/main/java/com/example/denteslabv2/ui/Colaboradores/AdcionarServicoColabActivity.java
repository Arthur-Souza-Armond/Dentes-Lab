package com.example.denteslabv2.ui.Colaboradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdcionarServicoColabActivity extends AppCompatActivity {

    private Button adcionar;
    private TextInputEditText nomeServico;
    private int AuxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcionar_servico_colab);

        getSupportActionBar().hide();

        adcionar = findViewById(R.id.btn_adcionarNomeServicoColab);

        nomeServico = findViewById(R.id.txt_nomeServicoColab);

        adcionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuxId = 0;

                String nome = nomeServico.getText().toString();

                if(!nome.equals("")){

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Servicos");

                    Query query = databaseReference.orderByChild("ID_servico");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                AuxId = AuxId + 1;
                            }

                            String auxID = String.valueOf(AuxId + 1);

                            DatabaseReference data = databaseReference.child(auxID);

                            data.child("ID_servico").setValue(auxID);
                            data.child("nome_servico").setValue(nome);

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sucesso ao salvar",
                                    Toast.LENGTH_LONG
                            ).show();

                            nomeServico.setText("");

                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{

                    Toast.makeText(
                            getApplicationContext(),
                            "Adcione o nome do servico",
                            Toast.LENGTH_LONG
                    ).show();

                }

            }
        });

    }
}