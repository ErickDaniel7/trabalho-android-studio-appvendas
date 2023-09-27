package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaCliente extends AppCompatActivity {

    private TextView tvListaCliente;

    private Button btVoltarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        tvListaCliente = findViewById(R.id.tvListaCliente);
        btVoltarList = findViewById(R.id.btVoltarList);

        btVoltarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarPaginaInicial();
            }
        });

        atualizarLista();

    }

    private void voltarPaginaInicial() {
        Intent voltarParaMain = new Intent( ListaCliente.this, MainActivity.class);

        startActivity(voltarParaMain);

    }

    private void atualizarLista() {
        String texto = "";
        ArrayList<Cliente> lista = Controller.getInstance().retornarCliente();
        for (Cliente cliente : lista) {
            texto += "CPF: " + cliente.getCPF() + " Nome: " + cliente.getNome() + "\n";
        }
        tvListaCliente.setText(texto);

    }
}