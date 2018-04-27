/*
 * Copyright 2018 Amazon.com, Inc. and its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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