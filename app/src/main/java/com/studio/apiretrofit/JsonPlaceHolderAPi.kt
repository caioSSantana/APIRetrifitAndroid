package com.studio.apiretrofit

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderAPi {

    @GET("carros/")
    fun getCarros(): Call<List<Carro>>

    @POST("carros/")
    fun addCarro(@Body carro:Carro): Call<Void>

    @PUT("carros/{document}")
    fun updateCarro(@Body carro: Carro, @Path("document") document: String): Call<Void>

    @DELETE("carros/{document}")
    fun deleteCarro(@Path("document") document: String): Call<Void>

}