package com.example.travelmemories.ui.home

import androidx.lifecycle.*
import com.example.travelmemories.MemoryDao
import com.example.travelmemories.MemoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val dao: MemoryDao) : ViewModel() {

    val text: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
//    val text: LiveData<String> = _text

    private val _allQueries = MutableLiveData<List<MemoryEntity>>()
    val allQueries: LiveData<List<MemoryEntity>>
        get() = _allQueries

    private val _lastQuery = MutableLiveData<MemoryEntity>()
    val lastQuery: LiveData<MemoryEntity>
        get() = _lastQuery

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _allQueries.postValue(dao.getAll())
        }
    }

    // Presupun ca toate numele sunt unice
    fun getMemoryByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _lastQuery.postValue(dao.getMemoryByName(name))
        }
    }

    fun insert(memory: MemoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(memory)
        }
    }

    fun update(memory: MemoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(memory)
        }
    }

    fun delete(memory: MemoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(memory)
        }
    }

}

class HomeViewModelFactory(private val dao: MemoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}