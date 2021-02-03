package com.example.cronometro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import java.lang.Exception

class CRONOMETRO : AppCompatActivity() {
    var mili=0;
    var seg=0;
    var min=0;
    var mi="00"
    var s="00"
    var m="00"
    var encendido=false
    var crono=true
    var tempo=false
    lateinit var textotempo: LinearLayout
    lateinit var cambio: ToggleButton
    lateinit var progress: ProgressBar
    lateinit var mint:EditText
    lateinit var segt:EditText
    lateinit var tiempo: TextView
    lateinit var cronometro: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)
        tiempo=findViewById(R.id.tiemporec)
        progress=findViewById(R.id.tempo)
        cambio=findViewById(R.id.cambio)
        mint=findViewById(R.id.mintempo)
        segt=findViewById(R.id.segtempo)
        textotempo=findViewById(R.id.ajustart)
        cronometro=Thread(Runnable {
            while(true) {
                if(crono) {
                    if (encendido) {
                        Thread.sleep(10)
                        mili += 10
                        if (mili == 1000) {mili = 0;seg++}
                        if (seg == 60) {seg = 0;min++}

                        runOnUiThread(Runnable {
                            mi = when (mili) {
                                in 0..99 -> "0${mili / 10}"
                                else -> "${(mili / 10).toInt()}"
                            }

                            s = when (seg) {
                                in 0..9 -> "0$seg"
                                else -> "$seg"
                            }

                            m = when (min) {
                                in 0..9 -> "0$min"
                                else -> "$min"
                            }

                            tiempo.text = "$m:$s:$mi"
                        })
                    }
                }

                else if(tempo) {
                    if(encendido) {
                        Thread.sleep(1000)
                        seg--
                        if (seg == -1 && min>0) {seg = 59;min--}
                        else if(seg == -1 && min==0){
                            seg=0
                            min=0
                            encendido = false
                            tiempo.text = getString(R.string.tempodefault)
                            continue
                        }

                        runOnUiThread(Runnable {
                            s= when(seg) {
                                in 0..9 -> "0$seg"
                                else -> "$seg"
                            }

                            m= when(min) {
                                in 0..9 -> "0$min"
                                else -> "$min"
                            }

                            if(progress.progress>0) progress.progress--
                            tiempo.text="$m:$s"
                        })
                    }
                }
            }
        })

        cronometro.start()
    }

    fun CambiarModo(v: View){
        mili=0
        seg=0
        min=0

        when(cambio.isChecked){
            true -> {
                textotempo.visibility=LinearLayout.VISIBLE
                textotempo.isEnabled=true
                crono=false
                tempo=true
                tiempo.text=getString(R.string.tempodefault)
            }

            false -> {
                textotempo.visibility=LinearLayout.GONE
                textotempo.isEnabled=false
                crono=true
                tempo=false
                tiempo.text=getString(R.string.cronodefault)
            }
        }
    }

    fun ModificarCrono(v: View){
        when(v.id){
            R.id.START -> {

                if(tempo){
                    if(!mint.text.isEmpty()) min=mint.text.toString().toInt()

                    if(!segt.text.isEmpty()) seg=segt.text.toString().toInt()

                    if(min!=0 || seg!=0) progress.max=min*60+seg
                    else progress.max=100

                    progress.progress=progress.max

                    s= when(seg) {
                        in 0..9 -> "0$seg"
                        else -> "$seg"
                    }

                    m= when(min) {
                        in 0..9 -> "0$min"
                        else -> "$min"
                    }

                    tiempo.text="$m:$s"
                }

                encendido=true
            }
            R.id.PAUSE -> encendido=false
            R.id.STOP -> {
                mili=0
                seg=0
                min=0
                tiempo.text= when{
                    crono -> getString(R.string.cronodefault)
                    else -> getString(R.string.tempodefault)
                }

                encendido=false
            }
        }
    }
}