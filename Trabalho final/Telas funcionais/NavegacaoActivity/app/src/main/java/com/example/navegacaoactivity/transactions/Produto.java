package com.example.navegacaoactivity.transactions;

public class Produto {
    private  static  int contadorId = 0;
    private int id;
    private String nome;
    private String preco;
    private String quantidade;

    public Produto(String nome, String preco, String quantidade) {
        this.id = contadorId++;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto() {

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return id + " " +nome ;
    }
}
