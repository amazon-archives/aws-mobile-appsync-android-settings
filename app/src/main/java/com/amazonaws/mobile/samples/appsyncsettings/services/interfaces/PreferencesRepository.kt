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