package com.example.navegacaoactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navegacaoactivity.transactions.Constantes;
import com.example.navegacaoactivity.transactions.Produto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int selected;
    ArrayList<Produto> listItens;
    ArrayAdapter adapter;
    ListView listCompras;
    EditText edtConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtConteudo = findViewById(R.id.edtTextConteudo);

        selected = -1;

        listItens = new ArrayList<Produto>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listItens);
        listCompras = (ListView)findViewById(R.id.idListaCompras);
        listCompras.setAdapter(adapter);
        listCompras.setSelector(android.R.color.holo_blue_dark);

        listCompras.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                Toast.makeText(MainActivity.this, ""+listItens.get(position).toString(),
                        Toast.LENGTH_SHORT).show();
                selected = position;
            }
        });

        //listItens = findViewById(R.id.listCompras);

    }
    public void clicarAdicionar(View view){
        Log.d("MainActivity", "Clique");
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, Constantes.REQUEST_ADD);
    }

    public void clilcarEditar(View view){
        if(selected > -1){
            Log.d("MainActivity", "Editar");
            Intent intent = new Intent(this, ListActivity.class);
            Produto produto = listItens.get(selected);
            intent.putExtra("id", produto.getId());
            intent.putExtra("nome", produto.getNome());
            intent.putExtra("preco", produto.getPreco());
            intent.putExtra("quantidade", produto.getQuantidade());

            startActivityForResult(intent, Constantes.REQUEST_EDIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constantes.REQUEST_ADD && resultCode == Constantes.RES_ADD) {
            String nome = (String)data.getExtras().get("nome");
            String preco = (String)data.getExtras().get("preco");
            String quantidade = (String)data.getExtras().get("quantidade");
            Produto produto = new Produto(nome, preco, quantidade);
            listItens.add(produto);
            adapter.notifyDataSetChanged();

        }else if(requestCode == Constantes.REQUEST_EDIT && resultCode == Constantes.RES_ADD){
            String nome = (String)data.getExtras().get("nome");
            String preco = (String)data.getExtras().get("preco");
            String quantidade = (String)data.getExtras().get("quantidade");
            int idEdit = (int)data.getExtras().get("id");

            for(Produto produto: listItens){
                if(produto.getId() == idEdit){
                    produto.setNome(nome);
                    produto.setPreco(preco);
                    produto.setQuantidade(quantidade);
                }
            }

            adapter.notifyDataSetChanged();
        }

        if(resultCode == Constantes.RES_ADD){
            String resultado = "Detalhes";

            if(data.getExtras() != null){
                String nome = (String) data.getExtras().get("nome");
                String preco = (String)data.getExtras().get("preco");
                String quantidade = (String)data.getExtras().get("quantidade");
                resultado += " - " + nome + " - " + preco + " - " + quantidade;
            }
            String conteudo = edtConteudo.getText().toString();
            conteudo += resultado + "\n";
            edtConteudo.setText(conteudo);
        }
    }
}