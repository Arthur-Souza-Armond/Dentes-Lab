package com.example.denteslabv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainApresentacao extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen(true);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro1)
                .background(android.R.color.white)
                .canGoBackward(false)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro2)
                .background(android.R.color.white)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro3)
                .background(android.R.color.white)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro4)
                .background(android.R.color.white)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro5)
                .background(android.R.color.white)
                .canGoForward(false)
                .build()
        );

    }

    public void irMenu(View view){

        Intent intent = getIntent();
        String teste = (String) intent.getSerializableExtra("Email");
        Log.i("INFO","t: "+teste);

        Intent intent2 = new Intent(MainApresentacao.this,PrincipalActivity.class);
        intent2.putExtra("Email",teste);
        startActivity(intent2);
    }

}