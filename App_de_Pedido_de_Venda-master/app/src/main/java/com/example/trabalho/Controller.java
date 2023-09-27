package com.example.trabalho;

import java.util.ArrayList;

public class Controller {
    private static Controller instancia;
    private ArrayList<Cliente> listaCliente;
    private ArrayList<Produto> listaProduto;

    public static Controller getInstance(){
        if(instancia == null){
            return instancia = new Controller();
        }else{
            return instancia;
        }
    }

    private Controller(){
        listaCliente = new ArrayList<>();
        listaProduto = new ArrayList<>();
    }

    public void salvarCliente(Cliente cliente){
        listaCliente.add(cliente);
    }
    public ArrayList<Cliente> retornarCliente(){
        return listaCliente;
    }

    public void salvarProduto (Produto produto){
        listaProduto.add(produto);
    }

    public ArrayList<Produto> retornarProduto(){
        return listaProduto;
    }
}
