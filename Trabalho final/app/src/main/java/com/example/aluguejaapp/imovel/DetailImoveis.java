package com.example.aluguejaapp.imovel;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aluguejaapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DetailImoveis extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SearchView searchView;

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

        if (getIntent().getExtras() != null) {
            String rua = (String) getIntent().getExtras().get("rua");
            String num = (String) getIntent().getExtras().get("numero");
            String bairro = (String) getIntent().getExtras().get("bairro");
            String cidade = (String) getIntent().getExtras().get("cidade");
            String uf = (String) getIntent().getExtras().get("uf");
            String mensal = (String) getIntent().getExtras().get("mensalidade");
            String quartos = (String) getIntent().getExtras().get("quartos");
            String banhe = (String) getIntent().getExtras().get("banheiros");
            String contato = (String) getIntent().getExtras().get("contato");

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

        searchView = findViewById(R.id.idSearchView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = searchView.getQuery().toString();
                List<Address> addressList = null;


                Geocoder geocoder = new Geocoder(DetailImoveis.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = addressList.get(0);

                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title(location));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng Ceara = new LatLng(-5.489161114975658, -39.31813813058585);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Ceara, 7f));
    }
}