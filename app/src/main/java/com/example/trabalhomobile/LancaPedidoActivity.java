package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import android.widget.Toast;
public class LancaPedidoActivity extends AppCompatActivity {


    private Spinner spCliente;
    private Spinner spItem;
    private EditText etQuantidade;
    private Button btAdicionar;
    private TextView tvMostrarPedido;
    private TextView tvErroCliente;
    private TextView tvErroItem;
    private ArrayList<Cliente> cliente;
    private ArrayList<Item> item;
    private ArrayList<Pedido> pedidos;
    private String[] vetorCliente;
    private int posicaoSelecionadoCliente = 0;
    private int posicaoSelecionadoItem = 0;
    private RadioButton rbVista;
    private RadioButton rbPrazo;
    private RadioGroup rgPagamento;
    private TextView tvMostrar;
    private double valorFinal;
    private EditText etParcela;
    private ListView lvParcela;
    private TextView tvPedido;
    private Button btPedido;
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanca_pedido);

        spCliente = findViewById(R.id.spCliente);
        spItem = findViewById(R.id.spItem);
        etQuantidade = findViewById(R.id.etQuantidade);
        btAdicionar = findViewById(R.id.btAdicionar);
        tvMostrarPedido = findViewById(R.id.tvMostrarPedido);
        tvErroCliente = findViewById(R.id.tvErroCliente);
        tvErroItem = findViewById(R.id.tvErroItem);
        tvMostrar = findViewById(R.id.tvMostrar);
        rgPagamento = findViewById(R.id.rgPagamento);
        rbPrazo = findViewById(R.id.rbAPrazo);
        rbVista = findViewById(R.id.rbAVista);
        etParcela = findViewById(R.id.etParcela);
        lvParcela = findViewById(R.id.lvParcela);
        tvPedido = findViewById(R.id.tvPedido);
        btPedido = findViewById(R.id.btPedido);

        rgPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                pagamento();
            }
        });

        spCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if(posicao > 0) {
                    posicaoSelecionadoCliente = posicao;
                    tvErroCliente.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if(posicao > 0) {
                    posicaoSelecionadoItem = posicao;
                    tvErroItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etParcela.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int quantidadeParcelas = Integer.parseInt(s.toString());
                    atualizarListaParcelas(quantidadeParcelas);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarPedido();
            }
        });
        popularListaCliente();
        popularListaItem();

        btPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarPedido();
            }
        });
        mostrarPedido();
    }



    DecimalFormat formato = new DecimalFormat();
    private void pagamento() {
        if (rbVista.isChecked()) {
            etParcela.setVisibility(View.GONE);
            lvParcela.setVisibility(View.GONE);

            double precoFinal = valorFinal * 0.95;
            tvMostrar.setText("Valor Total: R$" + formato.format(precoFinal));
        } else if (rbPrazo.isChecked()) {
            etParcela.setVisibility(View.VISIBLE);
            lvParcela.setVisibility(View.VISIBLE);

            if (!etParcela.getText().toString().isEmpty()) {
                int quantidadeParcelas = Integer.parseInt(etParcela.getText().toString());
                atualizarListaParcelas(quantidadeParcelas);
                double valorParcela = valorFinal * 1.05;
                tvMostrar.setText("Valor Total: R$" + formato.format(valorParcela));
            } else {
                Toast.makeText(this, "Digite a quantidade de parcelas", Toast.LENGTH_SHORT).show();
            }
        }
        rgPagamento.setEnabled(rbVista.isChecked() || rbPrazo.isChecked());
    }
    private void atualizarListaParcelas(int quantidadeParcelas) {
        ArrayList<String> parcelas = new ArrayList<>();
        valorFinal=valorFinal*1.05;
        double valorParcela = valorFinal / quantidadeParcelas;
        String texto="";
        for (int i = 1; i <= quantidadeParcelas; i++) {
            texto+=("Parcela "+ i +": R$" + formato.format(valorParcela)+"\n");
        }
        parcelas.add(texto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parcelas);
        lvParcela.setAdapter(adapter);
        double valorTotalParcelado = valorFinal;
        tvMostrar.setText("Valor Total Parcelado: R$" + formato.format(valorTotalParcelado));
    }
    private void popularListaItem() {
        item = Controller.getInstancia().retornarItem();
        String[]vetorItem = new  String[item.size()+1];
        vetorItem[0]= "Selecione um Item";
        for (int i = 0;i < item.size();i++){
            Item item1= item.get(i);
            vetorItem[i+1] = item1.getDescricao();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(
                LancaPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,vetorItem
        );
        spItem.setAdapter(adapter);
    }
    private void popularListaCliente() {
        cliente = Controller.getInstancia().retornarCliente();
        String[]vetorCliente = new String[cliente.size()+1];
        vetorCliente[0] = "Selecione um Cliente";
        for (int i = 0;i < cliente.size();i++){
            Cliente cliente1 = cliente.get(i);
            vetorCliente[i+1] = cliente1.getNome();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(
                LancaPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,vetorCliente);
        spCliente.setAdapter(adapter);
    }
    private void adicionarPedido() {
        int posicaoCliente = spCliente.getSelectedItemPosition();
        int posicaoItem = spItem.getSelectedItemPosition();
        int quantidade = Integer.parseInt(etQuantidade.getText().toString());

        if (posicaoCliente > 0 && posicaoItem > 0 && quantidade > 0) {

            Cliente clienteSelecionado = cliente.get(posicaoCliente - 1);
            Item itemSelecionado = item.get(posicaoItem - 1);

            Pedido pedido = new Pedido();
            pedido.setCliente(clienteSelecionado);
            pedido.setItem(itemSelecionado);
            pedido.setQuantidade(quantidade);
            pedido.setValorTotal(itemSelecionado.getValor() * quantidade);
            valorFinal+=itemSelecionado.getValor() * quantidade;
            Controller.getInstancia().adicionarPedido(pedido);
            atualizarPedido();
        } else {
            Toast.makeText(this, "Selecione todos os campos e insira uma quantidade válida !!!", Toast.LENGTH_SHORT).show();
        }
    }
    private void atualizarPedido() {
        String texto="";
        String nome="";
        int quant;
        Random randon = new Random();
        int codigo = randon.nextInt(100);
        ArrayList<Pedido> pedidos = Controller.getInstancia().retornarPedido();
        for (Pedido pedido : pedidos) {
            nome=pedido.getCliente().getNome();
            quant=pedido.getQuantidade();
            texto += "Item: " + pedido.getItem().getDescricao()+"\n"+
            "Quantidade: "+quant+"\nValor Unitario: "+pedido.getItem().getValor()+"\n";
        }
        tvMostrarPedido.setText("Cliente: "+nome+"\nCodigo do Pedido: "+codigo+"\n"+texto+ "\n"+
                "Valor Total: "+valorFinal+"\n");
    }

    private void finalizarPedido(){
        String texto="";
        String nome="";
        int quant;
        Random randon = new Random();
        int codigo = randon.nextInt(100);
        ArrayList<Pedido> pedidos = Controller.getInstancia().retornarPedido();
        for (Pedido pedido : pedidos) {
            nome=pedido.getCliente().getNome();
            quant=pedido.getQuantidade();
            texto += "Item: " + pedido.getItem().getDescricao()+"\n"+
                    "Quantidade: "+quant+"\nValor Unitario: "+pedido.getItem().getValor()+"\n";

            Toast.makeText(this, "Pedido concluido com sucesso !!!", Toast.LENGTH_SHORT).show();
            finish();

        }
        tvPedido.setText("Cliente: "+nome+"\nCodigo do Pedido: "+codigo+"\n"+texto+ "\n"+
                "Valor Total: "+valorFinal+"\n");
    }
    private void mostrarPedido() {

    }
}