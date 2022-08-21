package com.example.denteslabv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.cloudmessaging.CloudMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth Usuario = FirebaseAuth.getInstance();
    private TextInputEditText email, senha;
    private TextInputLayout email2, senha2;
    private Button Logar;
    private ProgressBar bar;
    private RecyclerView Rv;
    private TextView CriarConta;
    private Dialog mDialog;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        email = findViewById(R.id.txt_email);
        senha = findViewById(R.id.txt_senha);
        Logar = findViewById(R.id.btn_logar);
        bar = findViewById(R.id.pb_log);
        email2 = findViewById(R.id.textInputLayout);
        senha2 = findViewById(R.id.textInputLayout2);
        CriarConta = findViewById(R.id.txt_criar_conta);
        mDialog = new Dialog(this);

        bar.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        email2.setVisibility(View.INVISIBLE);
        senha.setVisibility(View.INVISIBLE);
        senha2.setVisibility(View.INVISIBLE);
        Logar.setVisibility(View.INVISIBLE);

        //Usuario.signOut();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            Log.w("INFO","Fetching");
                            return;
                        }

                        String token = task.getResult();

                        Log.i("INFO","Token: "+token);



                    }
                });

        if (Usuario.getCurrentUser() != null) {

            bar.setVisibility(View.VISIBLE);
            String teste = Usuario.getCurrentUser().getEmail();
            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            intent.putExtra("Email",teste);
            startActivity(intent);

        } else {

            email = findViewById(R.id.txt_email);
            senha = findViewById(R.id.txt_senha);
            Logar = findViewById(R.id.btn_logar);
            bar = findViewById(R.id.pb_log);
            email2 = findViewById(R.id.textInputLayout);
            senha2 = findViewById(R.id.textInputLayout2);

            bar.setVisibility(View.INVISIBLE);
            email.setVisibility(View.VISIBLE);
            email2.setVisibility(View.VISIBLE);
            senha.setVisibility(View.VISIBLE);
            senha2.setVisibility(View.VISIBLE);
            Logar.setVisibility(View.VISIBLE);

            Logar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Email = email.getText().toString();
                    String Senha = senha.getText().toString();
                    final String teste = Email;

                    if (!Email.equals("") && !Senha.equals("")) {

                        Usuario.signInWithEmailAndPassword(Email, Senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    bar.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(MainActivity.this,MainApresentacao.class);
                                    intent.putExtra("Email",Email);
                                    startActivity(intent);
                                    email.setText("");
                                    senha.setText("");

                                } else {
                                    String excessao = "";
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthInvalidCredentialsException e ){
                                        excessao = "E-mail e senha não correspondem";
                                    }catch (FirebaseAuthInvalidUserException e ){
                                        excessao = "Usuario não está cadastrado";
                                    }catch (Exception e ){
                                        excessao = "Erro ao cadastrar usuário: "+e.getMessage();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(getApplicationContext(), excessao, Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    } else {

                        Toast.makeText(getApplicationContext(), "Preencha todos os campos para poder logar", Toast.LENGTH_LONG).show();

                    }

                }
            });

        }

        CriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setContentView(R.layout.popup_criar_conta);

                TextInputEditText telefone,nome,email,senha;
                Button criar;

                telefone = mDialog.findViewById(R.id.txt_telefone_U);
                nome = mDialog.findViewById(R.id.txt_nome_U);
                email = mDialog.findViewById(R.id.txt_Email_U);
                senha = mDialog.findViewById(R.id.txt_senha_U);

                criar = mDialog.findViewById(R.id.btn_criar_conta_U);

                criar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String telefoneU = telefone.getText().toString();
                        String nomeU = nome.getText().toString();
                        String emailU = email.getText().toString();
                        String senhaU = senha.getText().toString();

                        if(!telefoneU.isEmpty() && !nomeU.isEmpty() && !emailU.isEmpty() && !senhaU.isEmpty()){

                            String encodeEmail = Base64.encodeToString(nomeU.getBytes(),Base64.DEFAULT).replace("(\\n|\\r)","").replace("@","a").replace("\n","n");
                            String encodeSenha = Base64.encodeToString(senhaU.getBytes(),Base64.DEFAULT).replace("(\\n|\\r)","");

                            DatabaseReference UserRef = database.child("Usuarios").child(nomeU);

                            UserRef.child("Nome").setValue(nomeU);
                            UserRef.child("Telefone").setValue(telefoneU);
                            UserRef.child("E-mail").setValue(emailU);
                            UserRef.child("Senha").setValue(senhaU);
                            UserRef.child("NivelPermissao").setValue(1);

                            auth.createUserWithEmailAndPassword(emailU,senhaU)
                                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                mDialog.dismiss();

                                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                                Toast.makeText(getApplicationContext(),"Esse usuário já existe",Toast.LENGTH_LONG).show();

                                            }else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                Toast.makeText(getApplicationContext(),"E-mail incorreto",Toast.LENGTH_LONG).show();

                                            }else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                                Toast.makeText(getApplicationContext(),"Essa senha não é forte o suficiente",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(getApplicationContext(),"Complete todos os campos nescessários",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mDialog.show();

            }
        });

    }

}
