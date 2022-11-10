package com.example.aluguejaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aluguejaapp.transactions.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtEmailCad;
    private EditText edtSenhaCad01;
    private EditText edtSenhaCad02;
    private EditText edtNomeCad;
    private EditText edtCpfCad;
    private EditText edtCidadeCad;
    private EditText edtUfCad;
    private CheckBox ckbSenhaCad;
    private Button btnCadastrar;
    private Button btnLogar;
    private ProgressBar prgsBarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        mAuth = FirebaseAuth.getInstance();
        edtEmailCad = findViewById(R.id.editTextEmailCad);
        edtSenhaCad01 = findViewById(R.id.editTextSenhaCad01);
        edtSenhaCad02 = findViewById(R.id.editTextSenhaCad02);
        edtNomeCad = findViewById(R.id.editTextNomeCad);
        edtCpfCad = findViewById(R.id.editTextCpfCad);
        edtCidadeCad = findViewById(R.id.editTextCidadeCad);
        ckbSenhaCad = findViewById(R.id.ckbMostrarSenhaCad);
        edtUfCad = findViewById(R.id.editTextUfCad);
        btnCadastrar = findViewById(R.id.btnEntrar);
        btnLogar = findViewById(R.id.btnLogin);
        prgsBarCad = findViewById(R.id.loginProgressBarCad);


        ckbSenhaCad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    edtSenhaCad01.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtSenhaCad02.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edtSenhaCad01.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtSenhaCad02.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario usuario = new Usuario();

                usuario.setEmail(edtEmailCad.getText().toString());
                usuario.setNome(edtNomeCad.getText().toString());
                usuario.setCpf(edtCpfCad.getText().toString());
                usuario.setCidade(edtCidadeCad.getText().toString());
                usuario.setUf(edtUfCad.getText().toString());
                String cadSenha01 = edtSenhaCad01.getText().toString();
                String cadSenha02 = edtSenhaCad02.getText().toString();

                if(TextUtils.isEmpty(usuario.getEmail()) || TextUtils.isEmpty(cadSenha01) || TextUtils.isEmpty(cadSenha02) ||
                        TextUtils.isEmpty(usuario.getNome()) || TextUtils.isEmpty(usuario.getCpf()) || TextUtils.isEmpty(usuario.getCidade()) ||
                        TextUtils.isEmpty(usuario.getCidade()) || TextUtils.isEmpty(usuario.getUf())){
                    Toast.makeText(CadastroUser.this, "ERRO", Toast.LENGTH_SHORT).show();
                } else {
                    if (cadSenha01.equals(cadSenha02)){
                        prgsBarCad.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(usuario.getEmail(), cadSenha01).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    usuario.setId(mAuth.getUid());
                                    usuario.salvar();
                                    abrirTelaInicial();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(CadastroUser.this, ""+error, Toast.LENGTH_SHORT).show();
                                }
                                prgsBarCad.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else { //Senhas Diferentes
                        Toast.makeText(CadastroUser.this, "Senhas devem ser iguais!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroUser.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void abrirTelaInicial(){
        Intent intent = new Intent(CadastroUser.this, TelaInicial.class);
        startActivity(intent);
        finish();
    }
}