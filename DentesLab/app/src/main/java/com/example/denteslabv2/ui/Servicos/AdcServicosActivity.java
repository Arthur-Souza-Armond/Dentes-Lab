package com.example.denteslabv2.ui.Servicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdcServicosActivity extends AppCompatActivity {

    private TextInputEditText nome_servico,preco_servico;
    private Button salvarServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_servicos);

        nome_servico = findViewById(R.id.txt_nome_servico);
        preco_servico = findViewById(R.id.txt_preco_servico);
        salvarServico = findViewById(R.id.btn_salvar_servico);

        salvarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeServico = nome_servico.getText().toString();
                String precoServico = preco_servico.getText().toString();


                if (nomeServico != null && precoServico != null){

                    ServicosS servicosS = new ServicosS();
                    ServicosDAO servicosDAO = new ServicosDAO();
                    servicosS.setNome(nomeServico);
                    servicosS.setPreco(precoServico);

                    if(servicosDAO.salvar(servicosS)){
                        Toast.makeText(getApplicationContext(),
                                "Êxito ao salvar",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "ERRO ao salvar",
                                Toast.LENGTH_LONG).show();
                    }

                }else{

                    Toast.makeText(getApplicationContext(),
                            "Complete os campos",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
