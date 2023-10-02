package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastrarItensActivity extends AppCompatActivity {

    private EditText etCodigo;
    private EditText etDescricao;
    private EditText etValor;
    private Button btSalvar;
    private TextView tvMostrarItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_itens);
        etCodigo = findViewById(R.id.etCodigo);
        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        btSalvar = findViewById(R.id.btSalvar);
        tvMostrarItem = findViewById(R.id.tvMostrarItem);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItem();
            }
        });
        atualizarItem();
    }
    private void salvarItem() {
        int codigo;
        double valor;
        if(etCodigo.getText().toString().isEmpty()){
            etCodigo.setError("O Codigo do Item deve ser informado!!");
            return;
        }else{
            try {
                codigo= Integer.parseInt(etCodigo.getText().toString());
            }catch (Exception ex){
                etCodigo.setError("Informe um Codigo válido (somente números)!");
                return;
            }
        }
        if(etDescricao.getText().toString().isEmpty()){
            etDescricao.setError("O Nome do Item deve ser informado!");
            return;
        }
        if(etValor.getText().toString().isEmpty()){
            etValor.setError("O Valor do Item deve ser informado!!");
            return;
        }else{
            try {
                valor= Double.parseDouble(etValor.getText().toString());
            }catch (Exception ex){
                etValor.setError("Informe um Valor válido (somente números)!");
                return;
            }
        }

        Item item = new Item();
        item.setCodigo(Integer.valueOf(codigo));
        item.setValor(Double.valueOf(valor));
        item.setDescricao(etDescricao.getText().toString());
        Controller.getInstancia().salvarItem(item);
        Toast.makeText(CadastrarItensActivity.this,
                "Item Cadastrado com Sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }
    private void atualizarItem() {
        String texto="";
        ArrayList<Item> lista = Controller.getInstancia().retornarItem();
        for (Item item: lista){
            texto +="Código: "+item.getCodigo()+"\nNome do Item: "+item.getDescricao()+"\nValor: "+item.getValor()+
                    "\n---------------------------------------------\n";
        }
        tvMostrarItem.setText(texto);
    }
}