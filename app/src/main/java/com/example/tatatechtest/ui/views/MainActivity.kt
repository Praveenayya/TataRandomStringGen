package com.example.tatatechtest.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tatatechtest.adapter.StringAdapter
import com.example.tatatechtest.data.repository.StringRepository
import com.example.tatatechtest.databinding.ActivityMainBinding
import com.example.tatatechtest.ui.viewmodel.MainViewModel
import com.example.tatatechtest.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Generated binding class
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: StringAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate layout with View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val repository = StringRepository(applicationContext)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        // Setup RecyclerView
        adapter = StringAdapter { position -> viewModel.deleteString(position) }
        binding.stringList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        // Observe LiveData
        viewModel.strings.observe(this) { strings ->
            adapter.updateStrings(strings)
        }
        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel._error.value = null // Clear error after showing
            }
        }

        // Button listeners
        binding.generateButton.setOnClickListener {
            val maxLength = binding.lengthInput.text.toString().toIntOrNull()
            if (maxLength != null && maxLength > 0) {
                viewModel.generateString(maxLength)
            } else {
                Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.clearButton.setOnClickListener {
            viewModel.clearAllStrings() // IAV-5
        }
    }
}