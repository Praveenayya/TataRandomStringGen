package com.example.tatatechtest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatatechtest.data.models.RandomText
import com.example.tatatechtest.data.repository.StringRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StringRepository) : ViewModel() {
    private val _strings = MutableLiveData<List<RandomText>>(emptyList())
    val strings: LiveData<List<RandomText>> get() = _strings

    val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun generateString(maxLength: Int) {
        viewModelScope.launch {
            try {
                val newString = repository.fetchRandomString(maxLength)
                newString?.let {
                    val currentList = _strings.value.orEmpty().toMutableList()
                    currentList.add(it)
                    _strings.postValue(currentList)
                } ?: run {
                    _error.postValue("No string returned")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }

    fun deleteString(position: Int) {
        val currentList = _strings.value.orEmpty().toMutableList()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _strings.value = currentList
        }
    }

    fun clearAllStrings() {
        _strings.value = emptyList()
    }
}