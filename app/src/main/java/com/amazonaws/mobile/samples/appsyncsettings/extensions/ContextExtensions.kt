/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.extensions

import android.content.Context

fun Context.readResourceFile(resourceId: Int)
        = resources.openRawResource(resourceId).bufferedReader().use { it.readText() } // Defaults to UTF-8
