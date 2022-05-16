package com.example.marvel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.model.Hero
import com.example.marvel.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(private val repository: MarvelRepository) : ViewModel() {

    private val _heroes = MutableLiveData<List<Hero>>()
    val heroes: LiveData<List<Hero>> = _heroes

    private val _offset = MutableLiveData<Int>()
    val offset: LiveData<Int> = _offset

    private val _heroesSearch = MutableLiveData<List<Hero>>()
    val heroesSearch: LiveData<List<Hero>> = _heroesSearch

    fun fetchHeroes(offset: Int, checkInternet: Boolean) {
        viewModelScope.launch {
            val returnedHeroes =
                repository.fetchHeroes(offset = offset, checkInternet = checkInternet)
            returnedHeroes?.let {
                _heroes.value = it
            }
        }
    }

    fun fetchHeroesByName(offset: Int, name: String, checkInternet: Boolean) {
        viewModelScope.launch {
            val returnedHeroes =
                repository.fetchHeroesByName(
                    offset = offset,
                    name = name,
                    checkInternet = checkInternet
                )
            returnedHeroes?.let {
                _heroesSearch.value = it
            }
        }
    }

    fun nextPage() {
        _offset.value = (_offset.value ?: 0) + 20
    }
}