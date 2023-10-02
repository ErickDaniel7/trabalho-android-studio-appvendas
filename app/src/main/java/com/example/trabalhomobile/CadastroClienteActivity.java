package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CadastroClienteActivity extends AppCompatActivity {

    private EditText etNomeCliente;
    private EditText etCpfCliente;
    private Button btSalvar;
    private TextView tvMostrarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        etNomeCliente = findViewById(R.id.etNomeCliente);
        etCpfCliente = findViewById(R.id.etCpfCliente);
        btSalvar = findViewById(R.id.btSalvar);
        tvMostrarCliente = findViewById(R.id.tvMostrarCliente);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
        atualizarCliente();
    }
    private void salvarCliente() {
        int cpf;
        if(etNomeCliente.getText().toString().isEmpty()){
            etNomeCliente.setError("O Nome do Cliente deve ser informado!");
            return;
        }

        if(etCpfCliente.getText().toString().isEmpty()){
            etCpfCliente.setError("O CPF do Cliente deve ser informado!!");
            return;
        }else{
            try {
                cpf= Integer.parseInt(etCpfCliente.getText().toString());
            }catch (Exception ex){
                etCpfCliente.setError("Informe um CPF válido (somente números)!");
                return;
            }
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(String.valueOf(cpf));
        cliente.setNome(etNomeCliente.getText().toString());
        Controller.getInstancia().salvarCliente(cliente);
        Toast.makeText(CadastroClienteActivity.this,
                "Cliente Cadastrado com Sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }
    private void atualizarCliente() {
        String texto ="";
        ArrayList<Cliente> lista = Controller.getInstancia().retornarCliente();
        for (Cliente cliente: lista){
texto +="Nome: "+cliente.getNome()+"\nCPF: "+cliente.getCpf()+
        "\n-----------------------------\n";
    }
        tvMostrarCliente.setText(texto);
        }
}