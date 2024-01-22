package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageActivity : AppCompatActivity() {

    private lateinit var petApiService: ApiService.PetApiService
    private lateinit var breedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        breedImageView = findViewById(R.id.breedImageView) // Asegúrate de que el ID sea correcto

        val breedId = intent.getStringExtra("BREED_ID") ?: ""
        // Configurar Retrofit y la interfaz de la API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thedogapi.com/") // Reemplaza con las URLs reales
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        petApiService = retrofit.create(ApiService.PetApiService::class.java)

        // Obtener las imágenes de la raza seleccionada y mostrarla en la UI
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val breedImages = petApiService.getBreedImages(breedId)
                // Actualizar la UI con las imágenes de la raza seleccionada
                updateBreedImages(breedImages)
            } catch (e: Exception) {
                // Manejar errores de red u otras excepciones
                e.printStackTrace()
                runOnUiThread {
                    // Muestra un mensaje de error al usuario
                    // Por ejemplo, Toast.makeText(applicationContext, "Error al cargar las imágenes", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateBreedImages(breedImages: List<BreedImage>) {
        runOnUiThread {
            // Implementa la lógica para mostrar las imágenes en la UI
            // Puedes usar un ImageView o RecyclerView según tus necesidades
            // Aquí, simplemente mostramos la primera imagen en un ImageView
            if (breedImages.isNotEmpty()) {
                val imageUrl = breedImages[0].url
                // Utiliza Picasso para cargar y mostrar la imagen desde la URL en breedImageView
                Picasso.get().load(imageUrl).into(breedImageView)
            } else {
                // Manejar el caso de lista vacía
                // Por ejemplo, mostrar un mensaje indicando que no hay imágenes disponibles
            }
        }
    }
}
