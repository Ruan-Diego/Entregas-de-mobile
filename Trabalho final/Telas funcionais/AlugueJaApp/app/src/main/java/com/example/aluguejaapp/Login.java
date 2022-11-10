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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnCadastrar;
    private FirebaseAuth mAuth;
    private ProgressBar prgBar;
    private CheckBox chkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.editTextEmail);
        edtSenha = findViewById(R.id.editTextSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastroUser);
        prgBar = findViewById(R.id.loginProgressBarCad);
        chkBox = findViewById(R.id.ckbMostrarSenhaCad);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = edtEmail.getText().toString();
                String loginSenha = edtSenha.getText().toString();

                //validar se o usuario está passando email e senha
                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginSenha)){
                    prgBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail, loginSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                abrirTelaInicial();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();
                                prgBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CadastroUser.class);
                startActivity(intent);
            }
        });

        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    edtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void abrirTelaInicial(){
        Intent intent = new Intent(Login.this, TelaInicial.class);
        startActivity(intent);
        finish();
    }
}