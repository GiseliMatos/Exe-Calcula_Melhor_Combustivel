package br.edu.utfpr.exe02_comparaprecocombustiveis_tratamentoeventos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.compareTo

class MainActivity : AppCompatActivity() {

    private lateinit var et_vlrEtanol : EditText
    private lateinit var et_vlrGasolina : EditText
    private lateinit var et_conEtanol : EditText
    private lateinit var et_conGasolina : EditText
    private lateinit var tv_vlrResultado : TextView
    private lateinit var bt_calcular : Button
    private lateinit var bt_sair : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        et_vlrEtanol = findViewById(R.id.et_vlrEtanol)
        et_vlrGasolina = findViewById(R.id.et_vlrGasolina)
        et_conEtanol = findViewById(R.id.et_conEtanol)
        et_conGasolina = findViewById(R.id.et_conGasolina)
        tv_vlrResultado = findViewById(R.id.tv_vlrResultado)
        bt_calcular = findViewById(R.id.bt_calcular)
        bt_sair = findViewById(R.id.bt_sair)

        bt_calcular.setOnClickListener {
            val resultado = calculaConsumo()

            if (resultado.isNotEmpty()) {
                tv_vlrResultado.text = getString(R.string.msg_melhor_combustivel, resultado)
            } else {
                tv_vlrResultado.text = getString(R.string.erro_geral)
            }
        }

        bt_calcular.setOnLongClickListener {
            Toast.makeText(this, getString(R.string.hint_calculo), Toast.LENGTH_SHORT).show()
            true
        }

        bt_sair.setOnClickListener {
            sair()
        }

        bt_sair.setOnLongClickListener {
            Toast.makeText(this, getString(R.string.hint_sair), Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun sair() {
        finish()
    }

    private fun calculaConsumo(): String {
        val vlrEtanol = et_vlrEtanol.text.toString().toDoubleOrNull()
        val vlrGasolina = et_vlrGasolina.text.toString().toDoubleOrNull()
        val conEtanol = et_conEtanol.text.toString().toDoubleOrNull()
        val conGasolina = et_conGasolina.text.toString().toDoubleOrNull()

        if(vlrEtanol==null || vlrEtanol<=0){
            et_vlrEtanol.error = getString(R.string.erro_validacao)
            return ""
        }

        if(vlrGasolina == null || vlrGasolina <= 0){
            et_vlrGasolina.error = getString(R.string.erro_validacao)
            return ""
        }

        if(conEtanol == null || conEtanol <= 0){
            et_conEtanol.error = getString(R.string.erro_validacao)
            return ""
        }

        if(conGasolina == null || conGasolina <= 0){
            et_conGasolina.error = getString(R.string.erro_validacao)
            return ""
        }

        val custoEtanol : Double
        val custoGasolina : Double
        val result : String

        custoEtanol = vlrEtanol/conEtanol
        custoGasolina = vlrGasolina/conGasolina

        if (custoEtanol < custoGasolina){
            result = getString(R.string.etanol)
        }
        else{
            result = getString(R.string.gasolina)
        }
        return result
    }
}