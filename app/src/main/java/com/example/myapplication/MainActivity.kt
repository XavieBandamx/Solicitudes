package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(),CompletadoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnConexion = findViewById<Button>(R.id.btnConexion)
        val btnSolicitudHttp = findViewById<Button>(R.id.btnSolicitudHttp)
        val btnSolicitudVolley = findViewById<Button>(R.id.btnSolicitudVolley)
        val btnSolicitudOkHTTP = findViewById<Button>(R.id.btnSolicitudOkHTTP)
        btnConexion.setOnClickListener {
            if(Network.hayRed(this)){
                Toast.makeText(this,"Si hay red",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"No hay red",Toast.LENGTH_LONG).show()
            }
        }
        btnSolicitudHttp.setOnClickListener {
            if(Network.hayRed(this)){
              //  Log.d("btnSolicitudHttp",descargarDatos("https://www.facebook.com"))
                DescargaURL(this).execute("https://www.google.com")
            }else{
                Toast.makeText(this,"No hay red",Toast.LENGTH_LONG).show()
            }
        }
        btnSolicitudVolley.setOnClickListener {
            if(Network.hayRed(this)){
                solicitudVolley("https://www.youtube.com")
            }else{
                Toast.makeText(this,"No hay red",Toast.LENGTH_LONG).show()
            }
        }
        btnSolicitudOkHTTP.setOnClickListener {
            if(Network.hayRed(this)){
                solicitudOkHTTP("https://www.instagram.com")
            }else{
                Toast.makeText(this,"No hay red",Toast.LENGTH_LONG).show()
            }
        }
    }
    //Metodo Volley
    private fun solicitudVolley(url:String){
        val queue = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            try{
                Log.d("btnSolicitudVolley", response)
            }catch (e:Exception){

            }
        }, Response.ErrorListener {  })
        queue.add(solicitud)
    }
    //Metodo OKHTTP
    private fun solicitudOkHTTP(url: String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object : okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                //error
            }
            override fun onResponse(call: Call?, response: okhttp3.Response?) {
                val result = response?.body()?.string()

                this@MainActivity.runOnUiThread{
                    try {
                        Log.d("solicitudOkHTTP", result)
                    }catch (e: Exception){

                    }
                }
            }
        })
    }



    override fun descargaCompleta(res: String) {
        Log.d("Descarga completa",res)
    }
}
