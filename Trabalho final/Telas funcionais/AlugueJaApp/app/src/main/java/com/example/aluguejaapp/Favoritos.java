package com.example.aluguejaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluguejaapp.transactions.Constants;
import com.example.aluguejaapp.transactions.Imoveis;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {

    int selected;
    ArrayList<Imoveis> listItem;
    ArrayAdapter adapter;
    ListView listaDeFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        selected = -1;
        listItem = new ArrayList<Imoveis>();

        Imoveis imovel_1 = new Imoveis("Rua A", "10", "Centro", "Quixadá", "CE", "850", "3", "2","(88) 987654321");
        Imoveis imovel_2 = new Imoveis("Rua B", "15", "Centro", "Quixadá", "CE", "650", "2", "1","(88) 985487345");
        Imoveis imovel_3 = new Imoveis("Rua B", "12", "Centro", "Quixadá", "CE", "800", "2", "2","(88) 987654321");

        Imoveis[] imoveisAdd = {imovel_1, imovel_2, imovel_3};

        for (int i = 0; i < imoveisAdd.length; i++){
            listItem.add(imoveisAdd[i]);
        }


        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listItem);
        listaDeFavoritos = (ListView) findViewById(R.id.listaFavoritos);
        listaDeFavoritos.setAdapter(adapter);

        listaDeFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(Favoritos.this, "" + listItem.get(position).toString(),
                        Toast.LENGTH_SHORT).show();
                selected = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_ADD_IMOVEL && resultCode == Constants.RESULT_ADD_IMOVEL) {
            String rua = (String) data.getExtras().get("rua");
            String numero = (String) data.getExtras().get("numero");
            String bairro = (String) data.getExtras().get("bairro");
            String cidade = (String) data.getExtras().get("cidade");
            String uf = (String) data.getExtras().get("uf");
            String mensalidade = (String) data.getExtras().get("mensalidade");
            String quartos = (String) data.getExtras().get("quartos");
            String banheiros = (String) data.getExtras().get("banheiros");
            String contato = (String) data.getExtras().get("contato");
            Imoveis imovel = new Imoveis(rua, numero, bairro, cidade, uf, mensalidade, quartos, banheiros, contato);
            listItem.add(imovel);
            adapter.notifyDataSetChanged();

        } else if (requestCode == Constants.REQUEST_EDT_IMOVEL && resultCode == Constants.RESULT_ADD_IMOVEL) {
            String rua = (String) data.getExtras().get("rua");
            String numero = (String) data.getExtras().get("numero");
            String bairro = (String) data.getExtras().get("bairro");
            String cidade = (String) data.getExtras().get("cidade");
            String uf = (String) data.getExtras().get("uf");
            String mensalidade = (String) data.getExtras().get("mensalidade");
            String quartos = (String) data.getExtras().get("quartos");
            String banheiros = (String) data.getExtras().get("banheiros");
            String contato = (String) data.getExtras().get("contato");
            int idEdit = (int) data.getExtras().get("id");

            for (Imoveis imovel : listItem) {
                if (imovel.getId() == idEdit) {
                    imovel.setRua(rua);
                    imovel.setNumero(numero);
                    imovel.setBairro(bairro);
                    imovel.setCidade(cidade);
                    imovel.setUf(uf);
                    imovel.setMensalidade(mensalidade);
                    imovel.setQuartos(quartos);
                    imovel.setBanheiros(banheiros);
                    imovel.setContato(contato);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

}