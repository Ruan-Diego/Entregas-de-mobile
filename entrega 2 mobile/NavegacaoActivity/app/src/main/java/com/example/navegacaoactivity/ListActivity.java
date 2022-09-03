package com.example.navegacaoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.navegacaoactivity.transactions.Constantes;

public class ListActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtPreco;
    EditText edtQuant;
    boolean edit;
    int idEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        edtNome = findViewById(R.id.editTextNome);
        edtPreco = findViewById(R.id.editTextPreco);
        edtQuant = findViewById(R.id.editTextQuant);

        edit = false;

        if(getIntent().getExtras() != null){
            String nome = (String)getIntent().getExtras().get("nome");
            String preco = (String)getIntent().getExtras().get("preco");
            String quantidade = (String)getIntent().getExtras().get("quantidade");
            idEditar = (int)getIntent().getExtras().get("id");

            edtNome.setText(nome);
            edtPreco.setText(preco);
            edtQuant.setText(quantidade);

            edit = true;
        }

    }

    public void cancelarBtn(View view){
        setResult(Constantes.RES_CANCEL);
        finish();
    }

    public void adicionarBtn(View view){
        Intent intent = new Intent();

        String nome = edtNome.getText().toString();
        String preco = edtPreco.getText().toString();
        String quantidade = edtQuant.getText().toString();

        intent.putExtra("nome", nome);
        intent.putExtra("preco",preco);
        intent.putExtra("quantidade", quantidade);

        if(edit){
            intent.putExtra("id", idEditar);
        }

        setResult(Constantes.RES_ADD, intent);
        finish();
    }
}