/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme
import com.amazonaws.mobile.samples.appsyncsettings.services.interfaces.PreferencesRepository

class MainActivityViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    val theme: LiveData<Theme>
        get() = preferencesRepository.theme

    fun updateTheme(theme: Theme) = preferencesRepository.updateTheme(theme)
}
