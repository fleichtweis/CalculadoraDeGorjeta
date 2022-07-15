package com.example.calculadoradegorjeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editConta: EditText
    private lateinit var editPorcentagem: EditText
    private lateinit var editNumeroPessoas: EditText
    private lateinit var btnCalcular: Button
    private lateinit var checkDividirConta: CheckBox
    private lateinit var textGorjeta:TextView
    private lateinit var textTotal:TextView
    private lateinit var textTotalPessoa:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editConta = findViewById(R.id.edit_conta)
        editPorcentagem = findViewById(R.id.edit_porcentagem)
        editNumeroPessoas = findViewById(R.id.edit_numero_pessoas)
        btnCalcular = findViewById(R.id.btn_calcular)
        checkDividirConta = findViewById(R.id.check_dividir_conta)
        textGorjeta = findViewById(R.id.text_gorjeta)
        textTotal = findViewById(R.id.text_total)
        textTotalPessoa = findViewById(R.id.text_total_pessoa)


        btnCalcular.setOnClickListener {
            if (validarCampos()){
                val valorConta = editConta.text.toString().toDouble()
                val porcentagemGorjeta = editPorcentagem.text.toString().toDouble()

                val numeroPessoas = if (checkDividirConta.isChecked) {
                    editNumeroPessoas.text.toString().toInt()
                } else{
                    1
                }

                calcular(valorConta, porcentagemGorjeta, numeroPessoas)
            }
        }

        checkDividirConta.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                editNumeroPessoas.visibility = View.VISIBLE
            } else{
                editNumeroPessoas.visibility = View.INVISIBLE
            }
        }
    }

    // Função com parâmetros para treinar a passagem dos mesmos,
    // não seria necessário, pois temos acesso aos dados nos componentes do projeto.
    private fun calcular(valorConta: Double, porcentagemGorjeta: Double, numeroPessoas: Int){

        val gorjeta = valorConta * (porcentagemGorjeta/100)
        val totalConta = valorConta + gorjeta
        val totalPorPessoa = totalConta / numeroPessoas

        textGorjeta.text = formatarNumero(gorjeta)
        textTotal.text = formatarNumero(totalConta)
        textTotalPessoa.text = formatarNumero(totalPorPessoa)

    }

    private fun validarCampos(): Boolean{

        if (editConta.text.isEmpty()){
            Toast.makeText(this, "Preencha o valor da conta", Toast.LENGTH_SHORT).show()
            return false
        }
        if (editPorcentagem.text.isEmpty()){
            Toast.makeText(this, "Preencha a porcentagem da gorjeta", Toast.LENGTH_SHORT).show()
            return false
        }
        if (checkDividirConta.isChecked && editNumeroPessoas.text.isEmpty()){
            Toast.makeText(this, "Preencha o número de pessoas para dividir a conta", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun formatarNumero(numero: Double): String{

        val decimalFormat = DecimalFormat("R$ 0.00") //Padrão formatação para o número

        return decimalFormat.format(numero)
    }

}