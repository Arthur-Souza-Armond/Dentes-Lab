package com.example.denteslabv2.ui.Trabalhos.Entrada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.denteslabv2.R;

public class NotaLabActivity extends AppCompatActivity {

    private LinearLayout linearNota;
    private TextView Id, DataSaida, DataEntrada, TipoServico, Dentes, Cor, NomeCliente, NomePaciente, Obs;
    private String id,dataSaida,dataEntrada, tipoServico, dentes, cor, nomeCliente, nomePaciente, obs;
    private Button imprimir,Voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_lab);

        getSupportActionBar().hide();

        linearNota = findViewById(R.id.linearNotaLab);
        imprimir = findViewById(R.id.btn_imprimirNotaLab);
        Voltar = findViewById(R.id.btn_voltar);

        Id = findViewById(R.id.txt_IdNotaLab);
        DataSaida = findViewById(R.id.txt_DataSaidaNotaLab);
        DataEntrada = findViewById(R.id.txt_DataEntradaNotaLab);
        TipoServico = findViewById(R.id.txt_TipoServicoNotaLab);
        Dentes = findViewById(R.id.txt_DentesNotaLab);
        Cor = findViewById(R.id.txt_CorDentesNotaLab);
        NomeCliente = findViewById(R.id.txt_clienteNotaLab);
        NomePaciente = findViewById(R.id.txt_pacienteNotaLab);
        Obs = findViewById(R.id.txt_ObservacoesNotaLab);

        Intent intent = getIntent();

        id = intent.getSerializableExtra("Id").toString();
        dataSaida = intent.getSerializableExtra("Saida").toString();
        dataEntrada = intent.getSerializableExtra("Entrada").toString();
        tipoServico = intent.getSerializableExtra("TipoTrabalho").toString();
        dentes = intent.getSerializableExtra("Dentes").toString();
        cor = intent.getSerializableExtra("Cor").toString();
        nomeCliente = intent.getSerializableExtra("Cliente").toString();
        nomePaciente = intent.getSerializableExtra("Paciente").toString();
        obs = intent.getSerializableExtra("Obs").toString();

        Id.setText(id);
        DataSaida.setText(dataSaida);
        DataEntrada.setText(dataEntrada);
        TipoServico.setText(tipoServico);
        Dentes.setText(dentes+" /");
        Cor.setText(" "+cor);
        NomeCliente.setText(nomeCliente+" / ");
        NomePaciente.setText(nomePaciente);
        Obs.setText(obs);

        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearNota.setDrawingCacheEnabled(true);
                linearNota.buildDrawingCache(true);
                Bitmap bitmap = Bitmap.createBitmap(linearNota.getDrawingCache());
                PrintHelper printHelper = new PrintHelper(NotaLabActivity.this);
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                printHelper.printBitmap("nota_"+id+".jpg",bitmap);

            }
        });

        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }
}