package com.guilhermebury.comics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilhermebury.comics.databinding.ActivityComicsBinding
import com.guilhermebury.comics.viewmodel.ComicsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComicsBinding
    private val viewModel: ComicsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComicsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupSwipeRefreshListener()
        viewModel.getComics()
    }

    private fun setupObservers() {
        viewModel.comicsList.observe(this) { comics ->
            binding.comicsList.adapter = Adapter(comics)
            binding.comicsList.layoutManager = LinearLayoutManager(this@ComicsActivity)
        }

        viewModel.errorVisibility.observe(this) { isVisible ->
            binding.comicsErrorText.isVisible = isVisible
        }

        viewModel.comicsVisibility.observe(this) { isVisible ->
            binding.comicsList.isVisible = isVisible
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.comicsSwipeRefresh.isRefreshing = isLoading
        }

        viewModel.errorText.observe(this) { error ->
            binding.comicsErrorText.text = error
        }
    }

    private fun setupSwipeRefreshListener() {
        binding.comicsSwipeRefresh.setOnRefreshListener {
            viewModel.getComics()
        }
    }
}