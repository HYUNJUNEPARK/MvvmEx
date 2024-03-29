package com.study.mvvmbasicex.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.study.mvvmbasicex.data.local.CountLocalDataSource
import com.study.mvvmbasicex.data.local.CountRepository
import com.study.mvvmbasicex.ui.SavableMutableStateFlow.Companion.KEY_COUNT
import kotlinx.coroutines.flow.StateFlow

class CountViewModel(
    application: Application,
    stateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = CountRepository(
        dataSource = CountLocalDataSource.getInstance(
            _context = getApplication<Application>().applicationContext
        )
    )
    private val _count = stateHandle.getMutableStateFlow(KEY_COUNT, 0)
    val count: StateFlow<Int>
        get() = _count.asStateFlow()

    init {
        _count.value = repository.getInt()
    }

    fun increase() {
        _count.value += 1
        repository.putInt(_count.value)
    }

    fun decrease() {
        _count.value -= 1
        repository.putInt(_count.value)
    }
}