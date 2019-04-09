/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.models

import java.util.*

data class Theme(val shortName: String, val displayName: String, val color: Int) {
    val id: String = UUID.randomUUID().toString()
}
