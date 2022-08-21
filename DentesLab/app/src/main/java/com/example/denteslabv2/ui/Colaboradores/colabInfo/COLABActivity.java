package com.example.denteslabv2.ui.Colaboradores.colabInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Colaboradores.Colaborador;
import com.example.denteslabv2.ui.Colaboradores.Colaboradores;
import com.example.denteslabv2.ui.Colaboradores.colabInfo.adapter.AdapterFeitosColab;
import com.example.denteslabv2.ui.Colaboradores.colabInfo.adapter.AdapterPrecosColab;
import com.example.denteslabv2.ui.helper.RecyclerItemClickListnner;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.w3c.dom.ls.LSInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class COLABActivity extends AppCompatActivity {

    private TextInputEditText nome,email,telefone,Nivel;
    private RadioGroup radioGroup;
    private RadioButton financeiro,marketing,protetico;
    private Button cadastrar;
    private String Setor;
    private TextView AdcEdit;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String colabAtual,UserAtual,NivelAtual;
    private int NivelU;
    private String Usuario,Senha;

    //Parte dos servicos
    private RecyclerView Rv_trabalhosFeitos,Rv_servicosPrecos;
    private Button adcionar;
    private List NOME,PRECO,TIPO,DATA;
    private AdapterFeitosColab adapterFeitosColab;
    private AdapterPrecosColab adapterPrecosColab;
    private MaterialCalendarView calendar;
    private String dataSelecionada;

    //Adcionar novo servico
    private LinearLayout linearAdcionar;
    private TextInputEditText nomeServico,precoServico;
    private Button salvar;
    private int AuxId;
    private RecyclerView Rv_servicosDisp;
    private List ServicoNome,AUX;
    private String NomeS;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colab);

        getSupportActionBar().hide();

        nome = findViewById(R.id.txt_nome_colab);
        email = findViewById(R.id.txt_email_colab);
        telefone = findViewById(R.id.txt_telefone_colab);
        Nivel = findViewById(R.id.txt_nivel_permissao);
        AdcEdit = findViewById(R.id.textadcEdit);


        //Parte de servicos ------------------------------------------------------------------------
        Rv_trabalhosFeitos = findViewById(R.id.rv_trabalhosRealizadosColab);
        Rv_servicosPrecos = findViewById(R.id.rv_precosServicosColab);
        adcionar = findViewById(R.id.btn_adcionarServicoColab);
        calendar = findViewById(R.id.calendarDataFeitoColab);

        //Parte de adcionar ------------------------------------------------------------------------
        linearAdcionar = findViewById(R.id.linearAdcionarServicoColab);
        //nomeServico = findViewById(R.id.txt_nomeServicoColab);
        precoServico = findViewById(R.id.txt_precoServicoColab);
        salvar = findViewById(R.id.btn_salvarServicoColab);
        Rv_servicosDisp = findViewById(R.id.rv_servicosColabs);

        linearAdcionar.setVisibility(View.GONE);

        colabAtual = String.valueOf(getIntent().getSerializableExtra("Nome"));

        Intent intent = new Intent();
        UserAtual = (String) intent.getSerializableExtra("User");
        NivelAtual = (String) intent.getSerializableExtra("Nivel");
        Log.i("INFO",UserAtual+" / "+NivelAtual);

        /*PrincipalActivity principalActivity = new PrincipalActivity();
        NivelU = principalActivity.getNivel();
        Usuario = principalActivity.getUsuario();
        Log.i("INFO",Usuario+"/"+NivelU);*/

        //Autocompletar os campos
        if(colabAtual != null){

            AdcEdit.setText("Edite o colaborador");

            DatabaseReference userRef = database.child("Usuarios");//.child(colabAtual);

            Query query = userRef.orderByChild("Nome").startAt(colabAtual).endAt(colabAtual + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        nome.setText(ds.child("Nome").getValue().toString());
                        email.setText(ds.child("E-mail").getValue().toString());
                        telefone.setText(ds.child("Telefone").getValue().toString());
                        Nivel.setText(ds.child("NivelPermissao").getValue().toString());
                        Senha = ds.child("Senha").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference data = database.child("Usuarios");

            Query query1 = data.orderByChild("E-mail")
                    .startAt(auth.getCurrentUser().getEmail())
                    .endAt(auth.getCurrentUser().getEmail() + "\uf88f");

            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        if(Integer.parseInt(ds.child("NivelPermissao").getValue().toString()) >= 3){

                            CalendarDay calendarDay = calendar.getCurrentDate();

                            carregarFeitos((calendarDay.getMonth() + 1),calendarDay.getYear());
                            carregarPrecos();
                            carregarDataAtual();

                            adcionar.setVisibility(View.VISIBLE);
                            adcionar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    linearAdcionar.setVisibility(View.VISIBLE);

                                    ServicoNome = new ArrayList();
                                    AUX = new ArrayList();

                                    adapterPrecosColab = new AdapterPrecosColab(ServicoNome,AUX,getApplicationContext());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    Rv_servicosDisp.setLayoutManager(layoutManager);
                                    Rv_servicosDisp.setHasFixedSize(true);
                                    Rv_servicosDisp.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                                    Rv_servicosDisp.setAdapter( adapterPrecosColab );

                                    DatabaseReference dataAux = database.child("Servicos");

                                    Query query = dataAux.orderByChild("ID_servico");

                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds : snapshot.getChildren()){

                                                ServicoNome.add(ds.child("nome_servico").getValue());
                                                AUX.add("0");

                                            }
                                            adapterPrecosColab.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Rv_servicosDisp.addOnItemTouchListener(new RecyclerItemClickListnner(
                                            getApplicationContext(),
                                            Rv_servicosDisp,
                                            new RecyclerItemClickListnner.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, int position) {
                                                    NomeS = ServicoNome.get(position).toString();
                                                }

                                                @Override
                                                public void onLongItemClick(View view, int position) {

                                                }

                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                }
                                            }
                                    ));

                                    salvar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String nome,preco;

                                            //nome = nomeServico.getText().toString();
                                            preco = precoServico.getText().toString();

                                            if(!NomeS.equals("") &&  !preco.equals("")){

                                                AuxId = 0;

                                                DatabaseReference data = database.child("Usuarios").child(colabAtual).child("Valores");

                                                Query query2 = data.orderByChild("Id_servico");

                                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot ds : snapshot.getChildren()){
                                                            AuxId = AuxId + 1;
                                                        }

                                                        String auxliarId = String.valueOf(AuxId + 1);

                                                        DatabaseReference data2 = data.child(auxliarId);

                                                        data2.child("nome_servico").setValue(NomeS);
                                                        data2.child("preco_servico").setValue(preco);
                                                        data2.child("Id_servico").setValue(auxliarId);

                                                        Toast.makeText(
                                                                getApplicationContext(),
                                                                "Sucesso ao salvar",
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        linearAdcionar.setVisibility(View.GONE);
                                                        carregarPrecos();

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }else{

                                                Toast.makeText(
                                                        getApplicationContext(),
                                                        "Complete todos os campos",
                                                        Toast.LENGTH_LONG
                                                ).show();

                                            }

                                        }
                                    });

                                }
                            });

                            nome.setEnabled(true);
                            email.setEnabled(true);
                            telefone.setEnabled(true);
                            Nivel.setEnabled(true);

                        }else{

                            adcionar.setVisibility(View.GONE);


                            nome.setEnabled(false);
                            email.setEnabled(false);
                            telefone.setEnabled(false);
                            Nivel.setEnabled(false);
                            //cadastrar.setVisibility(View.INVISIBLE);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /*if(NivelU < 3) {

                nome.setEnabled(false);
                email.setEnabled(false);
                telefone.setEnabled(false);
                Nivel.setEnabled(false);
                //cadastrar.setVisibility(View.INVISIBLE);

            }else if(NivelU == 3){

                nome.setEnabled(true);
                email.setEnabled(true);
                telefone.setEnabled(true);
                Nivel.setEnabled(true);*/
                //cadastrar.setVisibility(View.VISIBLE);

                /*cadastrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nomeC = nome.getText().toString();
                        String emailC = email.getText().toString();
                        String telefoneC = telefone.getText().toString();
                        String NivelC = Nivel.getText().toString();


                        if((!nomeC.isEmpty()) && (!emailC.isEmpty()) && (!telefoneC.isEmpty()) && (!NivelC.isEmpty())){

                            if(Salvar(nomeC,telefoneC,emailC,Senha,NivelC)){
                                Toast.makeText(getApplicationContext(),"Sucesso ao atualizar",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Erro ao atualizar",Toast.LENGTH_LONG).show();
                            }

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

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Você deve completar todos os campos",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });*/

            //}
        }

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
                        Toast.makeText(getApplicationContext(),
                                "Sucesso ao salvar colaborador",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "ERRO ao cadastrar novo colaborador",
                                Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Você deve completar todos os campos",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

         */

    }

    private void carregarFeitos(int mes,int ano){

        TIPO = new ArrayList();
        DATA = new ArrayList();

        adapterFeitosColab = new AdapterFeitosColab(TIPO,DATA,getApplicationContext());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_trabalhosFeitos.setLayoutManager(layoutManager);
        Rv_trabalhosFeitos.setHasFixedSize(true);
        Rv_trabalhosFeitos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        Rv_trabalhosFeitos.setAdapter( adapterFeitosColab );

        DatabaseReference data = database.child("Usuarios").child(colabAtual).child("Feitos");

        Query query = data.orderByChild("Id_Feito");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    TIPO.add(ds.child("Tipo_feito").getValue());
                    DATA.add(ds.child("data_feito").getValue());
                }
                adapterFeitosColab.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void carregarPrecos(){

        NOME = new ArrayList();
        PRECO = new ArrayList();

        adapterPrecosColab = new AdapterPrecosColab(NOME,PRECO,getApplicationContext());

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_servicosPrecos.setLayoutManager(layoutManager);
        Rv_servicosPrecos.setHasFixedSize(true);
        Rv_servicosPrecos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        Rv_servicosPrecos.setAdapter( adapterPrecosColab );

        DatabaseReference data = database.child("Usuarios").child(colabAtual).child("Valores");

        Query query = data.orderByChild("Id_servico");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    NOME.add(ds.child("nome_servico").getValue());
                    PRECO.add(ds.child("preco_servico").getValue());
                }
                adapterPrecosColab.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public  boolean Salvar(String nome,String telefone,String email,String senha,String nivel){

        DatabaseReference UsuarioRef = database.child("Usuarios").child(nome);

        UsuarioRef.child("Nome").setValue(telefone);
        UsuarioRef.child("NivelPermissao").setValue(nivel);
        UsuarioRef.child("Senha").setValue(senha);
        UsuarioRef.child("E-mail").setValue(email);
        UsuarioRef.child("Telefone").setValue(telefone);

        return true;
    }

    private void carregarDataAtual(){

        CalendarDay calendarDay = calendar.getCurrentDate();

        dataSelecionada = String.valueOf((calendarDay.getMonth() + 1)+"/"+(calendarDay.getYear()));

        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                dataSelecionada = String.valueOf((date.getMonth() + 1)+"/"+(date.getYear()));

                int mes = date.getMonth();
                int ano = date.getYear();

                carregarFeitos(mes,ano);

            }
        });

    }

}
