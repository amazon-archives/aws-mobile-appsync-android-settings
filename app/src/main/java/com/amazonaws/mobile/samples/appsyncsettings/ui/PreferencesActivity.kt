/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.ui

import android.arch.lifecycle.Observer
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.amazonaws.mobile.samples.appsyncsettings.R
import com.amazonaws.mobile.samples.appsyncsettings.R.id.prefs_theme_spinner
import com.amazonaws.mobile.samples.appsyncsettings.R.id.prefs_toolbar
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme
import com.amazonaws.mobile.samples.appsyncsettings.models.Themes
import com.amazonaws.mobile.samples.appsyncsettings.viewmodels.PreferencesActivityViewModel
import kotlinx.android.synthetic.main.activity_preferences.*
import org.jetbrains.anko.toast
import org.koin.android.architecture.ext.viewModel

class PreferencesActivity : AppCompatActivity() {
    private val viewModel by viewModel<PreferencesActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        setSupportActionBar(prefs_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Populate the spinner
        val themes = Themes.instance.getAllThemes().map { entry -> entry.value.displayName }
        val themeAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prefs_theme_spinner.adapter = themeAdapter

        // Set the initial position of the spinner
        prefs_theme_spinner.setSelection(themeAdapter.getPosition(viewModel.theme.value?.displayName ?: Themes.defaultTheme))

        // Listen for changes to the theme
        prefs_theme_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { /* Do Nothing */ }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val displayName: String = parent?.getItemAtPosition(position).toString()
                val theme = Themes.instance.getThemeByDisplayName(displayName)
                viewModel.updateTheme(theme)
                toast("Theme updated")
            }
        }

        viewModel.theme.observe(this, Observer { updateTheme(it) })
        updateTheme(viewModel.theme.value)
    }

    /**
     * This hook is called whenever an item in the options menu is selected.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item!!.itemId) {
        android.R.id.home -> consume { finish() }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Used by the menu callbacks to consume an event - this is just shorthand
     */
    private fun consume(lambda: () -> Unit): Boolean {
        lambda()
        return true
    }

    /**
     * Update the header tint based on the theme
     */
    private fun updateTheme(theme: Theme?) {
        theme?.let { supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(theme.color))) }
    }
}
