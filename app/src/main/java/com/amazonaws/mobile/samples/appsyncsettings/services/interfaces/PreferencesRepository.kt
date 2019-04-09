/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.services.interfaces

import android.arch.lifecycle.LiveData
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme

/**
 * Description of the preferences repository.  Right now, we only have one preference -
 * the theme.
 */
interface PreferencesRepository {
    val theme: LiveData<Theme>

    fun updateTheme(theme: Theme)
}
