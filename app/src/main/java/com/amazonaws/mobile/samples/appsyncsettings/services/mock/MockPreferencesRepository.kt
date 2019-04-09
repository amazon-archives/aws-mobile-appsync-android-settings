/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.services.mock

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme
import com.amazonaws.mobile.samples.appsyncsettings.models.Themes
import com.amazonaws.mobile.samples.appsyncsettings.services.interfaces.PreferencesRepository

/**
 * Mock implementation of the Preferences Repository
 */
class MockPreferencesRepository : PreferencesRepository {
    private val mutableTheme: MutableLiveData<Theme> = MutableLiveData()

    init {
        mutableTheme.value = Themes.instance.getThemeByName("Default")
    }

    /**
     * Observable version of the theme
     */
    override val theme: LiveData<Theme>
        get() = mutableTheme

    /**
     * Method to update the theme from the UI
     */
    override fun updateTheme(theme: Theme) {
        val storedTheme = Themes.instance.getAllThemes().find { it.value.shortName == theme.shortName }?.value
        if (storedTheme != null)
            mutableTheme.value = storedTheme
        else
            throw IllegalArgumentException("Invalid theme suggested")
    }
}
