package com.sunnyoaklabs.manodienynas.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    repository: Repository
): ViewModel()