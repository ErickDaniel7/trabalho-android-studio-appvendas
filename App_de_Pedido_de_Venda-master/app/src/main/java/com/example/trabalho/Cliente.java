package com.example.trabalho;

public class Cliente {

    private int CPF;
    private String nome;

    public Cliente(){

    }

    public int getCPF(){
        return CPF;
    }

    public void setCPF(int CPF){
        this.CPF = CPF;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
