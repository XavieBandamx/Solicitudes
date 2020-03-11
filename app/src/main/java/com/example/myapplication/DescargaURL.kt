package com.example.myapplication

import android.os.AsyncTask
import android.os.StrictMode
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DescargaURL(var completadoListener: CompletadoListener?):AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String): String? {
        return try {
            descargarDatos(params[0])
        } catch (e: IOException) {
            return null
        }
    }

    override fun onPostExecute(result: String) {
        try{
            completadoListener?.descargaCompleta(result)
        }catch (e:IOException){

        }
    }
    @Throws(IOException::class)
    private fun descargarDatos(url:String):String{
        //val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        //StrictMode.setThreadPolicy(policy)
        var inputStream: InputStream? = null
        try {
            val urlcon = URL(url)
            val conn = urlcon.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()

            inputStream = conn.inputStream
            return inputStream.bufferedReader().use {
                it.readText()
            }
        }finally{
            if(inputStream != null){
                inputStream.close()
            }
        }
    }
}