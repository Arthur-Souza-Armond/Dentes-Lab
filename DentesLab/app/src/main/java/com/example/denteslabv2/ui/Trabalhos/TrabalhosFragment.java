package com.example.denteslabv2.ui.Trabalhos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.denteslabv2.R;
import com.example.denteslabv2.ui.Trabalhos.Entrada.EntradaFragment;
import com.example.denteslabv2.ui.Trabalhos.saida.SaidaFragment;
import com.example.denteslabv2.ui.Trabalhos.lab.TrabalhosLabFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class TrabalhosFragment extends Fragment {

    private BottomNavigationViewEx bottomNavigationViewEx;
    private Button entradas,lab,saidas;
    private CardView Centrada,Clab,Csaida;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trabalhos, container, false);

        entradas = view.findViewById(R.id.btn_entrada_navi);
        lab = view.findViewById(R.id.btn_lab_navi);
        saidas = view.findViewById(R.id.btn_saida_navi);
        Centrada = view.findViewById(R.id.cv_entrada);
        Clab = view.findViewById(R.id.cv_lab);
        Csaida = view.findViewById(R.id.cv_saida);

        Centrada.setVisibility(View.INVISIBLE);
        Clab.setVisibility(View.VISIBLE);
        Csaida.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewpager, new TrabalhosLabFragment()).commit();

        entradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entradaSel();

            }
        });

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                labSel();

            }
        });

        saidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saidaSel();

            }
        });

        return view;
    }

    public void entradaSel(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewpager, new EntradaFragment()).commit();

        lab.setTextSize(16);
        lab.setTextColor(getResources().getColor(R.color.Cor1));
        entradas.setTextSize(18);
        entradas.setTextColor(getResources().getColor(R.color.textInput));
        saidas.setTextSize(16);
        saidas.setTextColor(getResources().getColor(R.color.Cor1));

        Centrada.setVisibility(View.VISIBLE);
        Clab.setVisibility(View.INVISIBLE);
        Csaida.setVisibility(View.INVISIBLE);

    }

    public void labSel(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewpager, new TrabalhosLabFragment()).commit();

        lab.setTextSize(18);
        lab.setTextColor(getResources().getColor(R.color.textInput));
        entradas.setTextSize(16);
        entradas.setTextColor(getResources().getColor(R.color.Cor1));
        saidas.setTextSize(16);
        saidas.setTextColor(getResources().getColor(R.color.Cor1));

        Centrada.setVisibility(View.INVISIBLE);
        Clab.setVisibility(View.VISIBLE);
        Csaida.setVisibility(View.INVISIBLE);

    }

    public void saidaSel(){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewpager, new SaidaFragment()).commit();

        lab.setTextSize(16);
        lab.setTextColor(getResources().getColor(R.color.Cor1));
        entradas.setTextSize(16);
        entradas.setTextColor(getResources().getColor(R.color.Cor1));
        saidas.setTextSize(18);
        saidas.setTextColor(getResources().getColor(R.color.textInput));

        Centrada.setVisibility(View.INVISIBLE);
        Clab.setVisibility(View.INVISIBLE);
        Csaida.setVisibility(View.VISIBLE);

    }

}