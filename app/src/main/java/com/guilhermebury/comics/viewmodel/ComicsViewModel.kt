package com.guilhermebury.comics.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermebury.comics.extension.toLiveData
import com.guilhermebury.comics.domain.model.ItemModel
import com.guilhermebury.comics.error.ComicsError
import com.guilhermebury.comics.repository.ComicsRepository
import com.guilhermebury.comics.ui.Item
import kotlinx.coroutines.launch

const val DESCRIPTION = "No description"

class ComicsViewModel(
    private val repository: ComicsRepository
) : ViewModel() {

    private val _comicsList = MutableLiveData<List<Item>>()
    val comicsList = _comicsList.toLiveData()

    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility = _errorVisibility.toLiveData()

    private val _errorText = MutableLiveData<String>()
    val errorText = _errorText.toLiveData()

    private val _comicsVisibility = MutableLiveData(false)
    val comicsVisibility = _comicsVisibility.toLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.toLiveData()

    fun getComics() {
        viewModelScope.launch {
            _comicsVisibility.postValue(false)
            _isLoading.postValue(true)
            repository.getComics()
                .onSuccess { item ->
                    onGetComicsSuccess(item)
                }
                .onFailure { error ->
                    onGetComicsFailure(error)
                }
                .onAny {
                    _isLoading.postValue(false)
                }
        }
    }

    private fun onGetComicsSuccess(item: ItemModel) {
        _comicsList.value = item.data.results.map { result ->
            Item(
                image = "${result.thumbnail.path}.${result.thumbnail.extension}",
                title = result.title,
                description = result.description ?: DESCRIPTION
            )
        }
        _errorVisibility.postValue(false)
        _comicsVisibility.postValue(true)
    }

    private fun onGetComicsFailure(error: ComicsError) {
        _errorText.postValue(error.message)
        _errorVisibility.postValue(true)
        _comicsVisibility.postValue(false)
    }
}