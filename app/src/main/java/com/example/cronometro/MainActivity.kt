package com.example.cronometro

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var num1: EditText
    lateinit var num2: EditText
    lateinit var resp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        num1=findViewById(R.id.num1)
        num2=findViewById(R.id.num2)
        resp=findViewById(R.id.resp)
    }

    fun Calcular(v: View){
        try{
            when (v.id){
                R.id.suma->resp.text=
                        (num1.text.toString().toDouble()+num2.text.toString().toDouble()).toString()
                R.id.resta->resp.text=
                        (num1.text.toString().toDouble()-num2.text.toString().toDouble()).toString()
                R.id.mult->resp.text=
                        (num1.text.toString().toDouble()*num2.text.toString().toDouble()).toString()
                R.id.div->resp.text=
                        (num1.text.toString().toDouble()/num2.text.toString().toDouble()).toString()
            }
        } catch(e: Exception){
            Toast.makeText(this,"Ingrese numeros validos",Toast.LENGTH_SHORT)
        }
    }

    fun CambiarActividad(v: View){
        var i= Intent(this, CRONOMETRO::class.java)
        startActivity(i)
    }
}