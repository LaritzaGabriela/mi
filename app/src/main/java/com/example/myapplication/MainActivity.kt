import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.Breed
import com.example.myapplication.ImageActivity
import com.example.myapplication.PetViewModel
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var petViewModel: PetViewModel
    private lateinit var breedListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        breedListView = findViewById(R.id.breedListView)

        petViewModel = ViewModelProvider(this).get(PetViewModel::class.java)

        lifecycleScope.launchWhenCreated {
            try {
                val breeds = petViewModel.getBreeds()
                updateBreedList(breeds)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        breedListView.setOnItemClickListener { _, _, position, _ ->
            val selectedBreed = breedListView.adapter.getItem(position) as Breed
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("BREED_ID", selectedBreed.id)
            startActivity(intent)
        }
    }

    private fun updateBreedList(breeds: List<Breed>) {
        runOnUiThread {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breeds)
            breedListView.adapter = adapter
        }
    }
}
