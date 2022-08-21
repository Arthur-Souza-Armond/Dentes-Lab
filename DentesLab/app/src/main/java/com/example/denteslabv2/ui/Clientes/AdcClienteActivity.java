package com.example.denteslabv2.ui.Clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Clientes.adapter.AdapterTabelaAdc;
import com.example.denteslabv2.ui.Clientes.adapter.TrabalhosClientesAdapter;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class AdcClienteActivity extends AppCompatActivity {

    private TextInputEditText nome_cliente_cadastro,email_cliente_cadastro,telefone_cliente_cadastro,endereco_cliente_cadastro,cep_cliente_cadastro,
            referencia_cliente_cadastro,secretaria_nome_cadastro,secretaria_telefone_cadastro,Area;
    private TextInputLayout teste1, teste2;
    private RadioButton cirurgiao,ortodontista,dentistica;
    private Button cadastrar_cliente;
    private String nome,email,telefone,endereco,cep,referencia,nome_secretaria,telefone_secretaria,area,teste;
    private Clientes clientesAtual;
    private TextView novoEdit,NomeC;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private RecyclerView Rv_servicos;
    private TrabalhosClientesAdapter trabalhosClientesAdapter;
    private List NOME,DATA,VALOR,ID,NOMET,PRECOT;
    private LinearLayout LinearAdc,LinearTabela;
    private Dialog mDialog,mDialogS;
    public static int I;
    private TextInputEditText NomClienteP,NomeServicoP,ValorServicoP,DataServicoP,ObsServicoP;
    private AdapterTabelaAdc adapterTabelaAdc;
    private RecyclerView Rv_Tabela;
    //Configuração de adcionar produto
    private TextInputEditText NomeTabela,PrecoTabela;
    private Button Salvar,Adc;
    private LinearLayout LinearAdcP;
    //Configuração do popup
    private TextInputEditText nomeS,precoS;
    private Button SalvarS;
    //
    private MaterialCalendarView calendar;
    private String mesAnoSC,AUX;
    //Nota
    private LinearLayout linearNotas;

    //Relatorio
    private Button Relatorio;

    //Pagamento
    private RecyclerView Rv_pagamento;
    private Button abrirLinearPagamento,salvarNovoPagamento;
    private TextInputEditText valorPagamento;
    private MaterialCalendarView calendarDataPagamento,calendarExibicaoPagamento;
    private LinearLayout linearPagamento,linearPagamentoss;
    private String dataPagamento,dataSelecionadaPagamentos;
    private int auxIdPagamento;
    private List DATAP,VALORP,IDP;
    private AdapterTabelaAdc pagamentoTabela;
    private TextView TotalPagamentosMes,totalAPagarText;
    private int totalPagamentos,totalAPagar;

    private String diaAuxPay,mesAuxPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_cliente);

        getSupportActionBar().hide();

        nome_cliente_cadastro = findViewById(R.id.txt_nome_cliente_cadastro);
        email_cliente_cadastro = findViewById(R.id.txt_email_cliente_cadastro);
        telefone_cliente_cadastro = findViewById(R.id.txt_telefone_cliente_cadastro);
        endereco_cliente_cadastro = findViewById(R.id.txt_endereco_cliente_cadastro);
        cep_cliente_cadastro = findViewById(R.id.txt_cep_cliente_cadastro);
        referencia_cliente_cadastro = findViewById(R.id.txt_referencia_cliente_cadastro);
        secretaria_nome_cadastro = findViewById(R.id.txt_nomesecretaria_cliente_cadastro);
        secretaria_telefone_cadastro = findViewById(R.id.txt_telefonesecretaria_cliente_cadastro);
        Area = findViewById(R.id.txt_area_atuacao);

        //Confura a adição dos produtos
        NomeTabela = findViewById(R.id.txt_NomeTabela_servico);
        PrecoTabela = findViewById(R.id.txt_PrecoTabela_servico);
        Salvar = findViewById(R.id.btn_salvarNova_Tabela);
        Adc = findViewById(R.id.btn_adc_tabela_servicos);

        teste1 = findViewById(R.id.teste1);
        teste2 = findViewById(R.id.teste2);

        mDialog = new Dialog(AdcClienteActivity.this);
        mDialogS = new Dialog(AdcClienteActivity.this);

        cadastrar_cliente = findViewById(R.id.btn_cadastrar_cliente_cadastro);

        secretaria_nome_cadastro.setVisibility(View.VISIBLE);
        secretaria_telefone_cadastro.setVisibility(View.VISIBLE);
        teste1.setVisibility(View.VISIBLE);
        teste2.setVisibility(View.VISIBLE);

        novoEdit = findViewById(R.id.txt_edit_novo);
        Rv_servicos = findViewById(R.id.rv_servicos_clienteC);
        Rv_Tabela = findViewById(R.id.rv_tabela_servicos);

        LinearAdc = findViewById(R.id.LinearAdc);
        LinearTabela = findViewById(R.id.linearTabela);
        LinearAdcP = findViewById(R.id.linearAdcTabela);

        calendar = findViewById(R.id.calendarViewC);

        //Pagamento
        Rv_pagamento = findViewById(R.id.rv_pagamentosRealizados);
        abrirLinearPagamento = findViewById(R.id.btn_abrirLinearPagamento);
        salvarNovoPagamento = findViewById(R.id.btn_SalvarPagamentoCliente);
        valorPagamento = findViewById(R.id.txt_valorPagamentoCliente);
        calendarDataPagamento = findViewById(R.id.calendarViewDataPagamento);
        calendarExibicaoPagamento = findViewById(R.id.calendarViewDataPagamentos);
        linearPagamento = findViewById(R.id.linearPagamentoCliente);
        linearPagamentoss = findViewById(R.id.linearPagamentos);
        TotalPagamentosMes = findViewById(R.id.txt_totalPagamentosMes);
        totalAPagarText = findViewById(R.id.txt_valorApagar);

        //Relatorio
        Relatorio = findViewById(R.id.btn_novoRelatorio);

        NOME = new ArrayList();
        DATA = new ArrayList();
        VALOR = new ArrayList();
        ID = new ArrayList();
        NOMET = new ArrayList();
        PRECOT = new ArrayList();

        clientesAtual = (Clientes) getIntent().getSerializableExtra("Nome");

        if(clientesAtual != null){

            novoEdit.setText("Editar cliente");
            Rv_servicos.setVisibility(View.VISIBLE);
            LinearAdc.setVisibility(View.VISIBLE);
            LinearAdcP.setVisibility(View.GONE);
            linearPagamento.setVisibility(View.GONE);
            linearPagamentoss.setVisibility(View.VISIBLE);

            cadastrar_cliente.setText("Alterar");

            //carregarTrabalhos();

            MyRunnable runnable = new MyRunnable();
            new Thread(runnable).start();

            Runnable2 runnable2 = new Runnable2();
            new Thread(runnable2).start();

            CarregarTabela carregarTabela = new CarregarTabela();
            new Thread(carregarTabela).start();

            /*AsyncPagamentos asyncPagamentos = new AsyncPagamentos();
            asyncPagamentos.execute(1);*/

            dataPagamento = "";
            auxIdPagamento = 0;

            carregarCalendar();

            cadastrar_cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SalvarAlteracoes();
                }
            });

            Adc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LinearAdcP.setVisibility(View.VISIBLE);

                    Salvar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String Nome,Preco,Cliente;

                            Nome = NomeTabela.getText().toString();
                            Preco = PrecoTabela.getText().toString();
                            Cliente = clientesAtual.getNome().toString();

                            if(!Nome.equals("") && !Preco.equals("") && !Cliente.equals("")){

                                DatabaseReference data = database.child("Clientes").child(Cliente).child("Tabela").child(Nome);
                                data.child("Nome").setValue(Nome);
                                data.child("Nome_pesquisa").setValue(Nome.toUpperCase());
                                data.child("Preco").setValue(Preco);

                                LinearAdcP.setVisibility(View.INVISIBLE);
                                carregarTabela.run();

                            }else {

                                Toast.makeText(getApplicationContext(),
                                        "Complete todos os campos",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });

            abrirLinearPagamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    linearPagamento.setVisibility(View.VISIBLE);

                    salvarNovoPagamento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String valor;

                            valor = valorPagamento.getText().toString();

                            if(!valor.equals("") && !dataPagamento.equals("")){

                                DatabaseReference data = database.child("Clientes").child(clientesAtual.getNome()).child("Pagamentos");

                                Query query = data.orderByChild("Id");

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds : snapshot.getChildren()){

                                            auxIdPagamento = auxIdPagamento + 1;

                                        }

                                        String auxiliar;

                                        auxiliar = String.valueOf(auxIdPagamento + 1);

                                        DatabaseReference dataPagamentoDB = data.child(auxiliar);

                                        dataPagamentoDB.child("Valor_pagamento").setValue(valor);
                                        dataPagamentoDB.child("Data_pagamento").setValue(dataPagamento);
                                        dataPagamentoDB.child("Id").setValue(auxiliar);

                                        linearPagamento.setVisibility(View.GONE);
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Pagamento adcionado",
                                                Toast.LENGTH_LONG
                                        ).show();

                                        carregarPagamentos(dataSelecionadaPagamentos);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                /*AsyncPagamentos asyncPagamentos1 = new AsyncPagamentos();
                                asyncPagamentos1.execute(1);*/

                            }else if(valor.equals("")){

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Adcione o valor do pagamento",
                                        Toast.LENGTH_LONG
                                ).show();

                            }else if(dataPagamento.equals("")){

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Adcione a data de pagamento",
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        }
                    });

                }
            });

            Rv_servicos.addOnItemTouchListener(
                    new RecyclerItemClickListnner(
                            getApplicationContext(),
                            Rv_servicos,
                            new RecyclerItemClickListnner.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    I = position;
                                    /*MyThread thread = new MyThread();
                                    thread.start();*/
                                    mDialog.setContentView(R.layout.popup_trabalhos_cliente);

                                    //XML do popup
                                    NomClienteP = mDialog.findViewById(R.id.txt_nomeCliente_C);
                                    NomeServicoP = mDialog.findViewById(R.id.txt_nomeServicos_C);
                                    ValorServicoP = mDialog.findViewById(R.id.txt_valorServico_popup_C);
                                    DataServicoP = mDialog.findViewById(R.id.txt_dataServico_C);
                                    ObsServicoP = mDialog.findViewById(R.id.txt_observacao_C);
                                    Button Excluir = mDialog.findViewById(R.id.btn_excluir_saida);
                                    Button Editar = mDialog.findViewById(R.id.btn_editarSaida);
                                    linearNotas = mDialog.findViewById(R.id.LinearNota);
                                    TextInputEditText nomePaciente = mDialog.findViewById(R.id.txt_nomePaciente_popup);
                                    TextInputEditText Dentes = mDialog.findViewById(R.id.txt_dentes_popup_servicosC);
                                    TextInputEditText Dentrada = mDialog.findViewById(R.id.txt_dataEntrada_servico_popupC);

                                    NomeC = mDialog.findViewById(R.id.txt_nomeC_popup);
                                    NomeC.setText(NOME.get(position).toString());

                                    DatabaseReference trabalhosRef = database.child("Saidas");

                                    Query query = trabalhosRef.orderByChild("ID_trabalho")
                                            .startAt(ID.get(position).toString())
                                            .endAt(ID.get(position).toString() + "\uf88f");

                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()){
                                                NomClienteP.setText(ds.child("Nome_cliente").getValue().toString());
                                                NomeServicoP.setText(ds.child("Nome_servico").getValue().toString());
                                                ValorServicoP.setText(ds.child("Total").getValue().toString());
                                                DataServicoP.setText(ds.child("Data_saida").getValue().toString());
                                                nomePaciente.setText(ds.child("Paciente").getValue().toString());
                                                Dentes.setText(ds.child("DENTES").getValue().toString());
                                                Dentrada.setText(ds.child("Data_entrada").getValue().toString());
                                                if(!ds.child("Observacao").getValue().toString().equals("")){
                                                    ObsServicoP.setText(ds.child("Observacao").getValue().toString());
                                                }else{
                                                    ObsServicoP.setText("Sem observações");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    /*EmitirNota.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PdfDocument document = new PdfDocument();

                                            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100,100,1).create();

                                            PdfDocument.Page page = document.startPage(pageInfo);

                                            View content = linearNotas;
                                            content.draw(page.getCanvas());

                                            try {
                                                FileWriter fileWriter = new FileWriter("testeNota.pdf");
                                                PrintWriter writer = new PrintWriter(fileWriter);
                                                writer.println("TESTE");
                                                writer.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            File file = new File("/storage/self/primary/DCIM/NotasDL");


                                            //document.close();

                                        }
                                    });*/

                                    Editar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                                            String idSaida = ID.get(position).toString();

                                                            DatabaseReference data = database.child("Saidas").child(idSaida);

                                                            String NomeSerivco,NomeCliente,valorServico,NomePaciente,dentes,DiaEntrada,obs;

                                                            NomeSerivco = NomeServicoP.getText().toString();
                                                            NomeCliente = NomClienteP.getText().toString();
                                                            valorServico = ValorServicoP.getText().toString();
                                                            NomePaciente = nomePaciente.getText().toString();
                                                            dentes = Dentes.getText().toString();
                                                            DiaEntrada = DataServicoP.getText().toString();
                                                            obs = ObsServicoP.getText().toString();

                                                            data.child("ID_trabalho").setValue(idSaida);
                                                            data.child("Nome_cliente").setValue(NomeCliente);
                                                            data.child("Nome_servico").setValue(NomeSerivco);
                                                            data.child("Total").setValue(valorServico);
                                                            data.child("Data_saida").setValue(DiaEntrada);
                                                            data.child("Paciente").setValue(NomePaciente);
                                                            data.child("DENTES").setValue(dentes);
                                                            data.child("Observacao").setValue(NomePaciente);

                                                            Toast.makeText(
                                                                    getApplicationContext(),
                                                                    "Atuailizado com sucesso",
                                                                    Toast.LENGTH_LONG
                                                            ).show();

                                                            mDialog.dismiss();
                                                            MyRunnable myRunnable = new MyRunnable();
                                                            myRunnable.run();

                                        }
                                    });

                                    Excluir.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Snackbar.make(view, "Excluir saida de Id: " + ID.get(position) + " de R$" +PRECOT.get(position) + " ?", Snackbar.LENGTH_LONG)
                                                    .setAction("SIM", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            String idSaida = ID.get(position).toString();

                                                            DatabaseReference data = database.child("Saidas").child(idSaida);
                                                            data.removeValue();
                                                            Toast.makeText(getApplicationContext(),
                                                                    "Saida excluida",
                                                                    Toast.LENGTH_LONG).show();

                                                            mDialog.dismiss();
                                                            MyRunnable myRunnable = new MyRunnable();
                                                            myRunnable.run();


                                                        }
                                                    }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                                    .show();

                                        }
                                    });

                                    mDialog.show();
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                }
                            }
                    )
            );

            Rv_Tabela.addOnItemTouchListener(
                    new RecyclerItemClickListnner(
                            getApplicationContext(),
                            Rv_Tabela,
                            new RecyclerItemClickListnner.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    mDialogS.setContentView(R.layout.popup_editar_servico);

                                    //xml popup
                                    nomeS = mDialogS.findViewById(R.id.txt_nomeS_popup);
                                    precoS = mDialogS.findViewById(R.id.txt_precoS_popup);
                                    SalvarS = mDialogS.findViewById(R.id.btn_popup_salvar_S);

                                    nomeS.setText(NOMET.get(position).toString());
                                    precoS.setText(PRECOT.get(position).toString());

                                    SalvarS.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String nome,preco;
                                            nome = nomeS.getText().toString();
                                            preco = precoS.getText().toString();
                                            if(!nome.equals("") && !preco.equals("")){
                                                DatabaseReference data = database.child("Clientes")
                                                .child(clientesAtual.getNome()).child("Tabela").child(NOMET.get(position).toString());

                                                data.removeValue();

                                                DatabaseReference data2 = database.child("Clientes").child(clientesAtual.getNome())
                                                        .child("Tabela").child(nome);

                                                data2.child("Nome").setValue(nome);
                                                data2.child("Preco").setValue(preco);

                                                carregarTabela.run();

                                                mDialogS.dismiss();
                                            }
                                        }
                                    });

                                    mDialogS.show();

                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                    DatabaseReference data = database.child("Clientes").child(clientesAtual.getNome())
                                            .child("Tabela").child(NOMET.get(position).toString());

                                    Snackbar.make(view,"Deseja excluir o produto "+NOMET.get(position)+"?", BaseTransientBottomBar.LENGTH_LONG)
                                            .setAction("SIM", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    data.removeValue();
                                                    carregarTabela.run();
                                                }
                                            }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                            .show();
                                }

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                }
                            }
                    )
            );

            Rv_pagamento.addOnItemTouchListener(new RecyclerItemClickListnner(
                    getApplicationContext(),
                    Rv_pagamento,
                    new RecyclerItemClickListnner.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                            String id;

                            id = IDP.get(position).toString();

                            DatabaseReference data = database.child("Clientes").child(clientesAtual.getNome()).child("Pagamentos").child(id);

                            Snackbar.make(view,"Deseja excluir o pagamento de "+VALORP+"?", BaseTransientBottomBar.LENGTH_LONG)
                                    .setAction("SIM", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            data.removeValue();
                                            carregarPagamentos(dataSelecionadaPagamentos);

                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Pagamento removido",
                                                    Toast.LENGTH_LONG
                                            ).show();

                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.Cor1))
                                    .show();

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    }
            ));

            Relatorio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(AdcClienteActivity.this,RelatorioClienteActivity.class);
                    intent.putExtra("Nome",clientesAtual);
                    startActivity(intent);

                }
            });

        }else{

            Rv_servicos.setVisibility(View.GONE);
            LinearAdc.setVisibility(View.GONE);
            LinearAdcP.setVisibility(View.GONE);
            LinearTabela.setVisibility(View.GONE);
            linearPagamento.setVisibility(View.GONE);
            linearPagamentoss.setVisibility(View.GONE);


            cadastrar_cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nome = nome_cliente_cadastro.getText().toString();
                    email = email_cliente_cadastro.getText().toString();
                    telefone = telefone_cliente_cadastro.getText().toString();
                    endereco = endereco_cliente_cadastro.getText().toString();
                    cep = cep_cliente_cadastro.getText().toString();
                    referencia = referencia_cliente_cadastro.getText().toString();
                    nome_secretaria = secretaria_nome_cadastro.getText().toString();
                    telefone_secretaria = secretaria_telefone_cadastro.getText().toString();
                    area = Area.getText().toString();



                    if(!nome.isEmpty() && !email.isEmpty() && !telefone.isEmpty() && !endereco.isEmpty() && !cep.isEmpty() && !referencia.isEmpty()){

                        Clientes clientes = new Clientes();
                        ClientesDAO clientesDAO = new ClientesDAO();

                        clientes.setNome(nome);
                        clientes.setCPF(email);
                        clientes.setTelefone(telefone);
                        clientes.setEndereco(endereco);
                        clientes.setCEP(cep);
                        clientes.setReferencia(referencia);
                        clientes.setSecretaria_nome(nome_secretaria);
                        clientes.setSecretaria_telefone(telefone_secretaria);
                        clientes.setArea(area);

                        if(clientesDAO.salvar(clientes)){
                            Toast.makeText(getApplicationContext(),
                                    "Êxito ao cadastrar",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "ERRO ao cadastrar",
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

    class MyRunnable implements  Runnable{
        @Override
        public void run() {
            /*mDialog.setContentView(R.layout.popup_trabalhos_cliente);

            NomeC = mDialog.findViewById(R.id.txt_nomeC_popup);
            NomeC.setText(NOME.get(I).toString());

            mDialog.show();*/
            NOME.clear();
            DATA.clear();
            VALOR.clear();
            ID.clear();

            totalAPagar = 0;

            trabalhosClientesAdapter = new TrabalhosClientesAdapter(NOME,DATA,VALOR,ID,getApplicationContext());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            Rv_servicos.setLayoutManager(layoutManager);
            Rv_servicos.setHasFixedSize(true);
            Rv_servicos.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayout.VERTICAL));
            Rv_servicos.setAdapter(trabalhosClientesAdapter);

            DatabaseReference TrabalhosRef = database.child("Saidas");

            Query query = TrabalhosRef.orderByChild("Nome_cliente")
                    .startAt(clientesAtual.getNome())
                    .endAt(clientesAtual.getNome() + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){

                        String data = ds.child("Data_saida").getValue().toString();
                        AUX = "";
                        AUX = data.substring(3,10);
                        if(mesAnoSC.length() == 6){
                            mesAnoSC = "0"+mesAnoSC;
                        }
                        if(mesAnoSC.equals(AUX)){
                            NOME.add(clientesAtual.getNome());
                            DATA.add(ds.child("Data_saida").getValue());
                            VALOR.add(ds.child("Total").getValue());
                            ID.add(ds.child("ID_trabalho").getValue().toString());

                            totalAPagar = totalAPagar + Integer.parseInt(ds.child("Total").getValue().toString());

                        }

                        /*NOME.add(clientesAtual.getNome());
                        DATA.add(ds.child("Data_entrada").getValue());
                        VALOR.add(ds.child("Total").getValue());
                        ID.add(ds.child("ID_trabalho").getValue().toString());*/
                    }

                    totalAPagarText.setTextColor(getResources().getColor(R.color.Negativo));
                    totalAPagarText.setText("R$ "+totalAPagar+",00");

                    trabalhosClientesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    class Runnable2 implements Runnable{
        @Override
        public void run() {

            DatabaseReference clienteRef = database.child("Clientes");

            Query query = clienteRef.orderByChild("Nome")
                    .startAt(clientesAtual.getNome())
                    .endAt(clientesAtual.getNome() + "\uf88f");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        nome_cliente_cadastro.setText(ds.child("Nome").getValue().toString());
                        email_cliente_cadastro.setText(ds.child("CPF").getValue().toString());
                        telefone_cliente_cadastro.setText(ds.child("Telefone").getValue().toString());
                        endereco_cliente_cadastro.setText(ds.child("Endereco").getValue().toString());
                        cep_cliente_cadastro.setText(ds.child("CEP").getValue().toString());
                        referencia_cliente_cadastro.setText(ds.child("Referencia").getValue().toString());
                        if (ds.child("Nome_secretaria").getValue().toString().equals("")){
                            secretaria_nome_cadastro.setText("Sem dados");
                        }else{
                            secretaria_nome_cadastro.setText(ds.child("Nome_secretaria").getValue().toString());
                        }
                        if(ds.child("Telefone_secretaria").getValue().toString().equals("")){
                            secretaria_telefone_cadastro.setText("Sem dados");
                        }else{
                            secretaria_telefone_cadastro.setText(ds.child("Telefone_secretaria").getValue().toString());
                        }
                        Area.setText(ds.child("Area").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    class CarregarTabela implements Runnable{
        @Override
        public void run() {
            NOMET.clear();
            PRECOT.clear();

            adapterTabelaAdc = new AdapterTabelaAdc(NOMET,PRECOT,getApplicationContext());

            //Configura o Recycler
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            Rv_Tabela.setLayoutManager(layoutManager);
            Rv_Tabela.setHasFixedSize(true);
            Rv_Tabela.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            Rv_Tabela.setAdapter( adapterTabelaAdc );

            DatabaseReference clienteRef = database.child("Clientes");

            Query query = clienteRef.orderByChild("Nome")
                    .startAt(clientesAtual.getNome())
                    .endAt(clientesAtual.getNome() + "\uf88f");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Query query1 = clienteRef.child(clientesAtual.getNome()).child("Tabela");
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    NOMET.add(ds.child("Nome").getValue());
                                    PRECOT.add(ds.child("Preco").getValue());
                                    Log.i("INFO", "TABELA: " + ds.child("Nome").getValue());
                                }
                                adapterTabelaAdc.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    /*for (DataSnapshot ds : snapshot.getChildren()){
                        NOMET.add(ds.child("Tabela").child("Nome").child("Nome").getValue());
                        PRECOT.add(ds.child("Tabela").child("Nome").child("Preco").getValue());
                        Log.i("INFO","TABELA: "+ds.child("Nome").child("Nome").getValue());
                    }
                    adapterTabelaAdc.notifyDataSetChanged();*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void carregarCalendar(){

        CalendarDay dataAtual = calendar.getCurrentDate();

        CalendarDay dataPagamentoFiltro = calendarExibicaoPagamento.getCurrentDate();

        if(10 > (dataAtual.getMonth() + 1)){

            mesAnoSC = String.valueOf("0"+(dataAtual.getMonth() + 1)+"/"+dataAtual.getYear());

        }else{

            mesAnoSC = String.valueOf((dataAtual.getMonth() + 1)+"/"+dataAtual.getYear());

        }

        if(10 > (dataPagamentoFiltro.getMonth() + 1)){

            dataSelecionadaPagamentos = String.valueOf("0"+(dataPagamentoFiltro.getMonth() + 1)+"/"+dataPagamentoFiltro.getYear());
            carregarPagamentos(dataSelecionadaPagamentos);

        }else{

            dataSelecionadaPagamentos = String.valueOf((dataPagamentoFiltro.getMonth() + 1)+"/"+dataPagamentoFiltro.getYear());
            carregarPagamentos(dataSelecionadaPagamentos);

        }


        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mesAnoSC = String.valueOf((date.getMonth() + 1) + "/" + date.getYear());
                MyRunnable runnable = new MyRunnable();
                new Thread(runnable).start();
            }
        });

        calendarDataPagamento.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(date.getDay() < 10){
                    diaAuxPay = String.valueOf("0"+date.getDay());
                }
                if(date.getMonth() < 10){
                    mesAuxPay = String.valueOf("0"+(date.getMonth()+1));
                }

                dataPagamento = String.valueOf(diaAuxPay +"/"+ mesAuxPay + "/" + date.getYear());

            }
        });

        calendarExibicaoPagamento.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                if(10 > (date.getMonth() + 1)){

                    dataSelecionadaPagamentos = String.valueOf("0"+(date.getMonth() + 1)+"/"+date.getYear());
                    carregarPagamentos(dataSelecionadaPagamentos);

                }else{

                    dataSelecionadaPagamentos = String.valueOf((date.getMonth() + 1)+"/"+date.getYear());
                    carregarPagamentos(dataSelecionadaPagamentos);

                }
            }
        });

    }

    public void SalvarAlteracoes(){

            nome = nome_cliente_cadastro.getText().toString();
            email = email_cliente_cadastro.getText().toString();
            telefone = telefone_cliente_cadastro.getText().toString();
            endereco = endereco_cliente_cadastro.getText().toString();
            cep = cep_cliente_cadastro.getText().toString();
            referencia = referencia_cliente_cadastro.getText().toString();
            nome_secretaria = secretaria_nome_cadastro.getText().toString();
            telefone_secretaria = secretaria_telefone_cadastro.getText().toString();
            area = Area.getText().toString();



            if(!nome.isEmpty() && !email.isEmpty() && !telefone.isEmpty() && !endereco.isEmpty() && !cep.isEmpty() && !referencia.isEmpty()) {

                Clientes clientes = new Clientes();
                ClientesDAO clientesDAO = new ClientesDAO();

                clientes.setNome(nome);
                clientes.setCPF(email);
                clientes.setTelefone(telefone);
                clientes.setEndereco(endereco);
                clientes.setCEP(cep);
                clientes.setReferencia(referencia);
                clientes.setSecretaria_nome(nome_secretaria);
                clientes.setSecretaria_telefone(telefone_secretaria);
                clientes.setArea(area);
                clientes.setNomeT(NOMET);
                clientes.setPrecoT(PRECOT);

                DatabaseReference data = database.child("Clientes").child(clientesAtual.getNome());
                data.removeValue();

                DatabaseReference userRef3 = database.child("Clientes").child(nome).child("Tabela");

                if(clientesDAO.salvar(clientes)){

                    for(int i=0;i<clientes.getNomeT().size();i++){
                        DatabaseReference userRef4 =  database.child("Clientes").child(nome).child("Tabela").child(NOMET.get(i).toString());
                        userRef4.child("Nome").setValue(NOMET.get(i).toString());
                        userRef4.child("Nome_pesquisa").setValue(NOMET.get(i).toString().toUpperCase());
                        userRef4.child("Preco").setValue(PRECOT.get(i).toString());
                    }

                    Toast.makeText(getApplicationContext(),
                            "Sucesso ao alterar",
                            Toast.LENGTH_LONG).show();

                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),
                            "Erro não reconhecido",
                            Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(getApplicationContext(),
                        "Complete todos os campos",
                        Toast.LENGTH_LONG).show();
            }

    }

    private void carregarPagamentos(String dataPay){

        DATAP = new ArrayList();
        VALORP = new ArrayList();
        IDP = new ArrayList();

        String mes,ano;

        mes = dataPay.substring(0,2);
        ano = dataPay.substring(3,7);

        totalPagamentos = 0;

        pagamentoTabela = new AdapterTabelaAdc(DATAP,VALORP,getApplicationContext());

        //Configura o Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_pagamento.setLayoutManager(layoutManager);
        Rv_pagamento.setHasFixedSize(true);
        Rv_pagamento.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        Rv_pagamento.setAdapter( pagamentoTabela );

        DatabaseReference data = database.child("Clientes").child(clientesAtual.getNome()).child("Pagamentos");

        Query query = data.orderByChild("Id");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                    Log.i("INFO","------ Teste datas pagamento ------------");
                    Log.i("INFO","DataPay: "+dataPay);
                    Log.i("INFO","Ano: "+ano+" Ano Data: "+ds.child("Data_pagamento").getValue().toString().substring(6,10));
                    Log.i("INFO","Mes: "+mes+" Mes Data: "+ds.child("Data_pagamento").getValue().toString().substring(3,5));

                    if(ano.equals(ds.child("Data_pagamento").getValue().toString().substring(6,10)) && mes.equals(ds.child("Data_pagamento").getValue().toString().substring(3,5))){

                        DATAP.add(ds.child("Data_pagamento").getValue());
                        VALORP.add(ds.child("Valor_pagamento").getValue());
                        IDP.add(ds.child("Id").getValue().toString());

                        totalPagamentos = (totalPagamentos + Integer.parseInt(ds.child("Valor_pagamento").getValue().toString()));

                    }

                    TotalPagamentosMes.setTextColor(getResources().getColor(R.color.Positivo));
                    TotalPagamentosMes.setText("R$ "+totalPagamentos+",00");

                }
                pagamentoTabela.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
