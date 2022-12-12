package com.example.aluguejaapp;

import static com.example.aluguejaapp.transactions.Constants.CHANNEL_ID;
import static com.example.aluguejaapp.transactions.Constants.NOTIFICATION_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluguejaapp.imovel.AddImoveis;
import com.example.aluguejaapp.imovel.DetailImoveis;
import com.example.aluguejaapp.imovel.MeusImoveis;
import com.example.aluguejaapp.transactions.Constants;
import com.example.aluguejaapp.model.Imoveis;
import com.example.aluguejaapp.user.PerfilUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TelaInicial<childEventListener> extends AppCompatActivity {
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<Imoveis> arrayList = new ArrayList<>();
    ArrayAdapter<Imoveis> arrayAdapter;
    int select;
    private NotificationManager mNotifyManager;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_tela_inicial);
        databaseReference = FirebaseDatabase.getInstance().getReference("Imoveis");
        listView = (ListView) findViewById(R.id.listviewtxt);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        select = -1;

        Button alugar = findViewById(R.id.editar);

        listView.setSelector(android.R.color.holo_blue_dark);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(TelaInicial.this, "" + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                select = position;
            }
        });

        alugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imoveis imovel = arrayList.get(select);
                //Imoveis imovel = dataSnapshot.getValue(Imoveis.class);

                if(!imovel.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                    imovel.setInteresse(1);
                    databaseReference.child(imovel.getId()).setValue(imovel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            sendNotification();
                        }
                    });
                }
                else
                    Toast.makeText(TelaInicial.this, "Você não pode alugar seu próprio imóvel", Toast.LENGTH_SHORT).show();
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
        createNotificationChannel();
        getNotificationBuilder();
    }
    public void createNotificationChannel()
    {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Create a NotificationChannel
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                "AlugaJá Notificações", NotificationManager
                .IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notificação AlugaJá");
        mNotifyManager.createNotificationChannel(notificationChannel);
    }

    private NotificationCompat.Builder getNotificationBuilder(){

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alguém se interessou pelo seu imóvel")
                .setContentText("Permita que ele possa entrar em contato")
                .setSmallIcon(R.drawable.ic_launcher_background);
        return notifyBuilder;
    }
    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
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
        Toast.makeText(TelaInicial.this, "", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.profile:
                perfilUsuario(mAuth.getCurrentUser());
                break;
            case R.id.properties:
                addImovel();
                break;
            case R.id.btnMeusImoveis:
                meusImoveis();
                break;
            case R.id.settings:
                configuracoes();
                break;
            case R.id.logout:
                sair();
                break;
        }
        return true;
    }

    public void addImovel() {
        Intent intent = new Intent(this, AddImoveis.class);
        startActivityForResult(intent, Constants.REQUEST_ADD_IMOVEL);
    }
    public void meusImoveis(){
        Intent intent = new Intent(this, MeusImoveis.class);
        startActivity(intent);
    }

    public void sair() {
        mAuth.signOut();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void configuracoes() {
        Intent intent = new Intent(this, Configuracoes.class);
        startActivity(intent);
    }

    public void perfilUsuario(FirebaseUser currentUser) {
        Intent intent = new Intent(this, PerfilUser.class);
        intent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(intent);
    }

}