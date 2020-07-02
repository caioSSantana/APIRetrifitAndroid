package com.studio.apiretrofit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var jsonPlaceHolderApi:JsonPlaceHolderAPi

    private lateinit var tvResult:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = tv_result

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.71:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPi::class.java)

        adicionarCarro(Carro("Fiat UNO", "DCE1234", 12, "8"))
        deletarCarro("7")
        editarCarro(Carro("Jimmy Suzuki","TAL1002", 2000, "1"),"1")

    }
    private fun getCarros(){
        var call: Call<List<Carro>> = jsonPlaceHolderApi.getCarros()

        call.enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(applicationContext,"Sem resposta", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                Toast.makeText(applicationContext, "Resposta Obtida", Toast.LENGTH_LONG).show()
                var carros: List<Carro>? = response.body() // todos carros estão aqui
                var content = ""
                for (carro in carros!!){
                    content +="Carro: "+carro.carro+"\n"
                    content +="Carro: "+carro.placa+"\n"
                    content += "Preço: "+carro.preco+"\n"
                    content += "Document: "+carro.document+"\n\n"

                }
                tvResult.text = content
            }
        })
    }

    private fun adicionarCarro(carro: Carro){
        var call = jsonPlaceHolderApi.addCarro(carro)

        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "Sem resposta", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext, "Resposta Obtida", Toast.LENGTH_LONG).show()
                getCarros()
            }
        })
    }

    private fun deletarCarro(document: String){
        var call = jsonPlaceHolderApi.deleteCarro(document)

        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext,"Sem resposta", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext, "Resposta Obtida", Toast.LENGTH_LONG).show()
                getCarros()
            }
        })
    }

    private fun editarCarro(carro: Carro, document: String){
        var call = jsonPlaceHolderApi.updateCarro(carro, document)

        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext,"Sem resposta", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext, "Resposta Obtida", Toast.LENGTH_LONG).show()
                getCarros()
            }
        })
    }
}
