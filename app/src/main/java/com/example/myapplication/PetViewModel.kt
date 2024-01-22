package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetViewModel : ViewModel() {

    private val _breedsLiveData = MutableLiveData<List<Breed>>()
    val breedsLiveData: LiveData<List<Breed>> get() = _breedsLiveData

    private val _breedImagesLiveData = MutableLiveData<List<BreedImage>>()
    val breedImagesLiveData: LiveData<List<BreedImage>> get() = _breedImagesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val petApiService: ApiService.PetApiService by lazy {
        createPetApiService("https://api.thedogapi.com/")
    }

    // Función para obtener la lista de razas
    fun getBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val breeds = petApiService.getBreeds()
                _breedsLiveData.postValue(breeds)
            } catch (e: Exception) {
                _errorLiveData.postValue("Error al obtener la lista de razas")
            }
        }
    }

    // Función para obtener imágenes de una raza específica
    fun getBreedImages(breedId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val breedImages = petApiService.getBreedImages(breedId)
                _breedImagesLiveData.postValue(breedImages)
            } catch (e: Exception) {
                _errorLiveData.postValue("Error al obtener las imágenes de la raza")
            }
        }
    }

    // Función para enviar un voto para una raza específica
    fun voteForBreed(breedId: String, score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                petApiService.voteForBreed(breedId, score)
            } catch (e: Exception) {
                _errorLiveData.postValue("Error al enviar el voto")
            }
        }
    }

    private fun createPetApiService(baseUrl: String): ApiService.PetApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.PetApiService::class.java)
    }
}