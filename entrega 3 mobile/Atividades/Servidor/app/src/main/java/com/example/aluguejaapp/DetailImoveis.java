package com.example.aluguejaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DetailImoveis extends FragmentActivity {


    TextView ruaD, numD, bairroD, cidadeD, ufD, mensalD, quartosD, banheD, contatoD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_imoveis);

        ruaD = findViewById(R.id.txtRuaD);
        numD = findViewById(R.id.txtNumD);
        bairroD = findViewById(R.id.txtBairroD);
        cidadeD = findViewById(R.id.txtCidadeD);
        ufD = findViewById(R.id.txtUFD);
        mensalD = findViewById(R.id.txtMensalD);
        quartosD = findViewById(R.id.txtQuartosD);
        banheD = findViewById(R.id.txtBanheD);
        contatoD = findViewById(R.id.txtContD);

        if (getIntent().getExtras() != null){
            String rua = (String)getIntent().getExtras().get("rua");
            String num = (String)getIntent().getExtras().get("numero");
            String bairro = (String)getIntent().getExtras().get("bairro");
            String cidade = (String)getIntent().getExtras().get("cidade");
            String uf = (String)getIntent().getExtras().get("uf");
            String mensal = (String)getIntent().getExtras().get("mensalidade");
            String quartos = (String)getIntent().getExtras().get("quartos");
            String banhe = (String)getIntent().getExtras().get("banheiros");
            String contato = (String)getIntent().getExtras().get("contato");

            ruaD.setText(rua);
            numD.setText(num);
            bairroD.setText(bairro);
            cidadeD.setText(cidade);
            ufD.setText(uf);
            mensalD.setText(mensal);
            quartosD.setText(quartos);
            banheD.setText(banhe);
            contatoD.setText(contato);
        }

    }

}