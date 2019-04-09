/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.lifecycle

import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobile.samples.appsyncsettings.services.aws.AWSPreferencesRepository
import com.amazonaws.mobile.samples.appsyncsettings.services.interfaces.PreferencesRepository
import com.amazonaws.mobile.samples.appsyncsettings.viewmodels.MainActivityViewModel
import com.amazonaws.mobile.samples.appsyncsettings.viewmodels.PreferencesActivityViewModel
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * List of services - we only have one - our preferences repository
 */
val servicesModule : Module = applicationContext {
    bean { AWSPreferencesRepository(get()) as PreferencesRepository }
}

/**
 * List of view-models available for dependency injection
 */
val viewModelModule : Module = applicationContext {
    viewModel { MainActivityViewModel(get()) }
    viewModel { PreferencesActivityViewModel(get()) }
}

/**
 * The Application class in Android is the base class within an Android app that contains all
 * other components such as activities and services.  The Application class, or any subclass of
 * the Application class, is instantiated before any other class when the process for your
 * application or package is created.
 */
class Application : android.app.Application() {
    /**
     * Called when the application is starting, before any activity, service, or receiver
     * object (excluding content providers) have been created.
     *
     * Implementations should be as quick as possible (for example, using lazy initialization
     * of state) since the time spent in this function directly impacts the performance of starting
     * the first activity, service, or receiver in a process.
     */
    override fun onCreate() {
        super.onCreate()

        val configuration = AWSConfiguration(applicationContext)
        val credentialsProvider = CognitoCachingCredentialsProvider(applicationContext, configuration)
        val pinpointManager = PinpointManager(PinpointConfiguration(applicationContext, credentialsProvider, configuration))
        pinpointManager.sessionClient?.startSession()
        pinpointManager.analyticsClient?.submitEvents()

        // Initialize Koin dependency injection
        startKoin(this, listOf(servicesModule, viewModelModule))
    }

}

