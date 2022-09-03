package com.example.aluguejaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluguejaapp.transactions.Constants;
import com.example.aluguejaapp.transactions.Imoveis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelaInicial<childEventListener> extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<Imoveis> arrayList = new ArrayList<>();
    ArrayAdapter<Imoveis> arrayAdapter;
    int select;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_inicial);
        databaseReference = FirebaseDatabase.getInstance().getReference("Imoveis");
        listView = (ListView) findViewById(R.id.listviewtxt);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        select = -1;

        listView.setSelector(android.R.color.holo_blue_dark);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Toast.makeText(TelaInicial.this, "" + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                select = position;
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Imoveis imovel = dataSnapshot.getValue(Imoveis.class);
                arrayList.add(imovel);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tela_inicial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickDetails (View view) {
        Intent intent = new Intent(this, DetailImoveis.class);

        Imoveis imovel = arrayList.get(select);
        intent.putExtra("id", imovel.getId());
        intent.putExtra("bairro", imovel.getBairro());
        intent.putExtra("banheiros", imovel.getBanheiros());
        intent.putExtra("cidade", imovel.getCidade());
        intent.putExtra("contato", imovel.getContato());
        intent.putExtra("mensalidade", imovel.getMensalidade());
        intent.putExtra("numero", imovel.getNumero());
        intent.putExtra("quartos", imovel.getQuartos());
        intent.putExtra("rua", imovel.getRua());
        intent.putExtra("uf", imovel.getUf());

        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(TelaInicial.this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.properties:
                addImovel();
                break;
        }
        return true;
    }

    public void addImovel() {
        Intent intent = new Intent(this, ListImoveis.class);
        startActivityForResult(intent, Constants.REQUEST_ADD_IMOVEL);
    }
}