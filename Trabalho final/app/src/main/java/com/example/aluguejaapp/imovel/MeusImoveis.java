package com.example.aluguejaapp.imovel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluguejaapp.R;
import com.example.aluguejaapp.TelaInicial;
import com.example.aluguejaapp.model.Imoveis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MeusImoveis extends AppCompatActivity {
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<Imoveis> arrayList = new ArrayList<>();
    ArrayAdapter<Imoveis> arrayAdapter;
    int select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_meus_imoveis);
        databaseReference = FirebaseDatabase.getInstance().getReference("Imoveis");
        listView = (ListView) findViewById(R.id.listviewtxt);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        select = -1;

        listView.setSelector(android.R.color.holo_blue_dark);

        listView.setOnItemClickListener((arg0, arg1, position, arg3) -> {
            Toast.makeText(MeusImoveis.this, "" + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
            select = position;
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Imoveis imovel = dataSnapshot.getValue(Imoveis.class);
                if(imovel.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                    arrayList.add(imovel);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void onClickDelete (View view) {
        Imoveis imovel = arrayList.get(select);
        databaseReference.child(imovel.getId()).removeValue();
        arrayList.remove(imovel);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onClickEditar (View view){
        Imoveis imovel = arrayList.get(select);
        Intent intent = new Intent(this, EditarImoveis.class);
        intent.putExtra("id", imovel.getId());
        intent.putExtra("rua", imovel.getRua());
        intent.putExtra("numero", imovel.getNumero());
        intent.putExtra("bairro", imovel.getBairro());
        intent.putExtra("cidade", imovel.getCidade());
        intent.putExtra("uf", imovel.getUf());
        intent.putExtra("mensalidade", imovel.getMensalidade());
        intent.putExtra("quartos", imovel.getQuartos());
        intent.putExtra("banheiros", imovel.getBanheiros());
        intent.putExtra("contato", imovel.getContato());
        startActivity(intent);

    }

    public void onClickVoltar (View view){
        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
    }


}
