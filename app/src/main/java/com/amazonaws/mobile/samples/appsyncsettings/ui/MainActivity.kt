/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.amazonaws.mobile.samples.appsyncsettings.R
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme
import com.amazonaws.mobile.samples.appsyncsettings.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        main_navigationview.setNavigationItemSelectedListener {
            item -> when(item.itemId) {
                R.id.nav_preferences -> consume {
                    main_drawerlayout.closeDrawer(GravityCompat.START)
                    startActivity(Intent(this, PreferencesActivity::class.java))
                }
                else -> false
            }
        }

        viewModel.theme.observe(this, Observer { updateTheme(it) })
        updateTheme(viewModel.theme.value)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        android.R.id.home -> consume { main_drawerlayout.openDrawer(GravityCompat.START) }
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
     * Updates the theme based on the theme stored in preferences
     */
    private fun updateTheme(theme: Theme?) {
        theme?.let { supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(theme.color))) }
    }
}
