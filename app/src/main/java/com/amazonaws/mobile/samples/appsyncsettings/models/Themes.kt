/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.models

import com.amazonaws.mobile.samples.appsyncsettings.R

/**
 * A list of valid themes and methods to get the list
 */
class Themes {
    companion object {
        val instance = Themes()

        const val defaultTheme = "Default"
    }

    private val themes: MutableMap<String, Theme> = HashMap()

    init {
        themes.putAll(mapOf(
            "Default" to Theme("Default", "Default", R.color.theme_default_colorPrimary),
            "Theme1" to Theme("Theme1", "Theme 1", R.color.theme_theme1_colorPrimary),
            "Theme2" to Theme("Theme2", "Theme 2", R.color.theme_theme2_colorPrimary),
            "Theme3" to Theme("Theme3", "Theme 3", R.color.theme_theme3_colorPrimary)
        ))
    }

    /**
     * Return the theme, or throw an error
     *
     * @param shortName [String] the name of the theme
     * @throws IllegalArgumentException if the name is not found
     */
    fun getThemeByName(shortName: String): Theme {
        val theme = themes[shortName]
        if (theme != null)
            return theme
        else
            throw IllegalArgumentException("shortName is not found")
    }

    /**
     * Gets the theme based on the display name
     */
    fun getThemeByDisplayName(displayName: String): Theme {
        val themeEntry = themes.filter { entry -> entry.value.displayName == displayName }
        val theme = themeEntry.entries.elementAtOrNull(0)?.value
        if (theme != null)
            return theme
        else
            throw IllegalArgumentException("displayName is not found")
    }

    /**
     * Gets a list of all the themes
     */
    fun getAllThemes() = themes.entries.toList()
}
