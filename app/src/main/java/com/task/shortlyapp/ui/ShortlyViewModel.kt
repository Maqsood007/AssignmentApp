package com.task.shortlyapp.ui

import androidx.lifecycle.ViewModel
import com.task.shortlyapp.repository.ShortlyAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShortlyViewModel @Inject constructor(val shortlyAppRepository: ShortlyAppRepository) :
    ViewModel()
