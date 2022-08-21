package com.example.denteslabv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.denteslabv2.ui.ADM1.ADMmensagem.AdapterMensagens;
import com.example.denteslabv2.ui.ADM1.ADMmensagem.Mensagem;
import com.example.denteslabv2.ui.Principal.PrincipalFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView email;
    private RecyclerView recyclerView,Rv_Mensagem;
    private TextView Nome, CNome;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public static String USUARIO;
    public int NIVEL, AuxSoma, Aux;
    private ProgressBar CarregarName,CarregarPB;
    private AdapterMensagens adapterMensagens;
    private List Titulo,Corpo,IdICon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().isHideOnContentScrollEnabled();

        Intent intent = getIntent();
        USUARIO = (String) intent.getSerializableExtra("Email");

        //carregarNome();
        carregarAsync();
        carregarAsync2();
        deixarGlobal(USUARIO);
        PrincipalFragment principalFragment = new PrincipalFragment();
        principalFragment.carregarNome();

        CarregarName = findViewById(R.id.pb_carregarNome);
        CarregarName.setVisibility(View.GONE);
        email = findViewById(R.id.TextoNome);
        CNome = findViewById(R.id.TextoNome);

        /*email = findViewById(R.id.txt_nome_colab);
        //Nome = findViewById(R.id.txt_nome_app);

        Intent intent = getIntent();
        USUARIO = (String) intent.getSerializableExtra("Email");
        Log.i("INFO","Email: "+USUARIO);

        Query query = database.child("Usuarios").orderByChild("NivelPermissao");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.i("INFO","Putaria: "+ds.child("E-mail").getValue().toString());
                    if(USUARIO.equals(ds.child("E-mail").getValue().toString())){
                        CNome = findViewById(R.id.txt_CNome);
                        USUARIO = ds.child("Nome").getValue().toString();
                        NIVEL = Integer.parseInt(ds.child("NivelPermissao").getValue().toString());
                        CNome.setText("Olá, "+ds.child("Nome").getValue().toString());
                        Log.i("INFO","Nivel: "+NIVEL);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        /*if (NIVEL >= 3 ){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(PrincipalActivity.this,)
                }
            });
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_principal, R.id.nav_trabalhos, R.id.nav_relatorios, R.id.nav_colaboradores,R.id.nav_adm,R.id.nav_notas)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nav_principal:
                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_LONG).show();
                        NavigationUI.setupWithNavController(navigationView, navController);
                        break;
                    case R.id.nav_colaboradores:
                        Toast.makeText(getApplicationContext(), "COLAB", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_cliente:
                        Toast.makeText(getApplicationContext(), "Clientes", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_servicos:
                        Toast.makeText(getApplicationContext(), "Servicos", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_trabalhos:
                        Toast.makeText(getApplicationContext(), "Trabalhos", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_relatorios:
                        Toast.makeText(getApplicationContext(), "Relatórios", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_principal:
                        Toast.makeText(getApplicationContext(),"HOME",Toast.LENGTH_LONG).show();

                        break;
                    case  R.id.nav_colaboradores:
                        Toast.makeText(getApplicationContext(),"COLAB",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PrincipalActivity.this, TrabalhosFragment.class));
                        break;
                }
                return false;
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                auth.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private  void carregarAsync(){

        AsyncTask task = new carregarNomeAsync();
        task.execute();

    }

    private class carregarNomeAsync extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CNome = findViewById(R.id.TextoNome);

                    CarregarName = findViewById(R.id.pb_carregarNome);
                    CNome.setVisibility(View.GONE);
                    CarregarName.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CNome = findViewById(R.id.TextoNome);

                    CarregarName = findViewById(R.id.pb_carregarNome);
                    CNome.setVisibility(View.GONE);
                    CarregarName.setVisibility(View.VISIBLE);

                }
            });

            Intent intent = getIntent();
            USUARIO = (String) intent.getSerializableExtra("Email");

            Query query = database.child("Usuarios").orderByChild("NivelPermissao");

            Aux = 0;
            AuxSoma = 0;

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.i("INFO", "Putaria: " + ds.child("E-mail").getValue().toString());
                        if (USUARIO.equals(ds.child("E-mail").getValue().toString())) {
                            String nome = ds.child("Nome").getValue().toString();
                            USUARIO = ds.child("Nome").getValue().toString();
                            NIVEL = Integer.parseInt(ds.child("NivelPermissao").getValue().toString());



                            for (int i = 1; i <= nome.length(); i++) {
                                if (nome.substring(Aux, i).equals(" ")) {
                                    AuxSoma = AuxSoma + 1;
                                    Log.i("INFO", "SOMA: " + AuxSoma);
                                    if (AuxSoma == 2) {
                                        String auxname = nome.substring(0, i);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                CarregarName.setVisibility(View.GONE);
                                                email.setVisibility(View.VISIBLE);
                                                CNome.setText("Olá, "+auxname);
                                            }
                                        });
                                    }
                                }
                                Aux = Aux + 1;
                            }
                            Log.i("INFO", "Nivel: " + NIVEL);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }

    }

    private void carregarAsync2(){

        AsyncTask task = new carregarMensagensAsync();
        task.execute();

    }

    private class carregarMensagensAsync extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            CarregarPB = findViewById(R.id.pb_carregar_mensagens);
            Rv_Mensagem = findViewById(R.id.rv_mensagens);

            Rv_Mensagem.setVisibility(View.GONE);
            CarregarPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            USUARIO = "";

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CarregarPB = findViewById(R.id.pb_carregar_mensagens);
                    Rv_Mensagem = findViewById(R.id.rv_mensagens);
                    Rv_Mensagem.setVisibility(View.GONE);
                    CarregarPB.setVisibility(View.VISIBLE);



                }
            });

            Intent intent = getIntent();
            USUARIO = (String) intent.getSerializableExtra("Email");

            Query query = database.child("Usuarios").orderByChild("NivelPermissao");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){

                        Log.i("INFO","TESTEE: "+USUARIO);

                        if(USUARIO.equals(ds.child("Nome").getValue().toString())){

                            Log.i("INFO","ATÈ AQ");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Titulo = new ArrayList();
                                    Corpo = new ArrayList();
                                    IdICon = new ArrayList();

                                    String nome = ds.child("Nome").getValue().toString();

                                    adapterMensagens = new AdapterMensagens(Titulo,Corpo,IdICon,getApplicationContext());

                                    //configurar RecyclerView
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    Rv_Mensagem.setLayoutManager(layoutManager);
                                    Rv_Mensagem.setHasFixedSize(true);
                                    Rv_Mensagem.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                                    Rv_Mensagem.setAdapter( adapterMensagens );

                                    DatabaseReference data = database.child("Usuarios").child(nome).child("Mensagens");

                                    Query query1 = data.orderByChild("ID_Mensagem");

                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                Titulo.add(ds.child("Titulo").getValue().toString());
                                                Corpo.add(ds.child("Corpo").getValue().toString());
                                                IdICon.add(Integer.parseInt(ds.child("Id_Icone").getValue().toString()));
                                            }
                                            adapterMensagens.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    CarregarPB.setVisibility(View.GONE);

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return null;
        }
    }

    private void deixarGlobal(String nome){

        Mensagem mensagem = new Mensagem();
        mensagem.setNome(nome);

    }

}

