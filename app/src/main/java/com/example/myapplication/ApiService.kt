package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    val catApiService: PetApiService by lazy {
        createPetApiService("https://api.thecatapi.com/")
    }

    val dogApiService: PetApiService by lazy {
        createPetApiService("https://api.thedogapi.com/")
    }

    private fun createPetApiService(baseUrl: String): PetApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PetApiService::class.java)
    }

    interface PetApiService {

        // Obtener lista de razas de perros o gatos
        @GET("breeds")
        suspend fun getBreeds(): List<Breed>

        // Obtener imágenes de una raza específica
        @GET("images/{breedId}/images")
        suspend fun getBreedImages(@Path("breedId") breedId: String): List<BreedImage>

        // Enviar un voto para una raza específica
        @GET("vote/{breedId}/{score}")
        suspend fun voteForBreed(@Path("breedId") breedId: String, @Path("score") score: Int): Vote
    }
}
