package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetApiClient {
    private val petApiService: ApiService.PetApiService

    init {
        // Configurar Retrofit y la interfaz de la API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        petApiService = retrofit.create(ApiService.PetApiService::class.java)
    }

    // Obtener lista de razas de perros o gatos
    suspend fun getBreeds(): List<Breed> {
        return try {
            petApiService.getBreeds()
        } catch (e: Exception) {
            // Manejar errores de red u otras excepciones
            emptyList()
        }
    }

    // Obtener imágenes de una raza específica
    suspend fun getBreedImages(breedId: String): List<BreedImage> {
        return try {
            petApiService.getBreedImages(breedId)
        } catch (e: Exception) {
            // Manejar errores de red u otras excepciones
            emptyList()
        }
    }

    // Enviar un voto para una raza específica
    suspend fun voteForBreed(breedId: String, score: Int): Vote? {
        return try {
            petApiService.voteForBreed(breedId, score)
        } catch (e: Exception) {
            // Manejar errores de red u otras excepciones
            null
        }
    }
}