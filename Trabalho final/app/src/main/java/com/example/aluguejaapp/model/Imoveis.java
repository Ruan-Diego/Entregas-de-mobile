package com.example.aluguejaapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Imoveis {
    String id;
    String email;
    String rua;
    String numero;
    String bairro;
    String cidade;
    String uf;
    String mensalidade;
    String quartos;
    String banheiros;
    String contato;
    int interesse;



    public Imoveis(int interesse, String email, String rua, String numero, String bairro, String cidade, String uf, String mensalidade, String quartos, String banheiros, String contato) {
        this.interesse = interesse;
        this.email = email;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.mensalidade = mensalidade;
        this.quartos = quartos;
        this.banheiros = banheiros;
        this.contato = contato;
    }

    public Imoveis(){

    }

    public int getInteresse() {
        return interesse;
    }

    public void setInteresse(int interesse) {
        this.interesse += interesse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(String mensalidade) {
        this.mensalidade = mensalidade;
    }

    public String getQuartos() {
        return quartos;
    }

    public void setQuartos(String quartos) {
        this.quartos = quartos;
    }

    public String getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(String banheiros) {
        this.banheiros = banheiros;
    }

    @Override
    public String toString() {
        return  cidade +"na rua: "+ rua + "\n" + quartos +
                " Quartos " + banheiros + " Banheiros "+ mensalidade;
    }
}
