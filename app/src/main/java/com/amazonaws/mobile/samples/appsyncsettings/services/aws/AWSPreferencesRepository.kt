/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.services.aws

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.samples.appsyncsettings.R
import com.amazonaws.mobile.samples.appsyncsettings.extensions.readResourceFile
import com.amazonaws.mobile.samples.appsyncsettings.models.AppSync
import com.amazonaws.mobile.samples.appsyncsettings.models.Theme
import com.amazonaws.mobile.samples.appsyncsettings.models.Themes
import com.amazonaws.mobile.samples.appsyncsettings.services.interfaces.PreferencesRepository
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.api.Response
import type.SettingsInput

class AWSPreferencesRepository(applicationContext: Context) : PreferencesRepository {
    companion object {
        /**
         * For logging
         */
        private val TAG = this::class.java.simpleName
    }

    private val mutableTheme: MutableLiveData<Theme> = MutableLiveData()

    override val theme: LiveData<Theme>
        get() = mutableTheme

    /**
     * The AWS AppSync Configuration (from res/raw/appsync.json)
     */
    private val appSyncConfig = AppSync(applicationContext.readResourceFile(R.raw.appsync))

    /**
     * The client connection to AWS AppSync
     */
    private val appSyncClient: AWSAppSyncClient

    init {
        // Set the default so that we never have an NPE
        mutableTheme.value = Themes.instance.getThemeByName(Themes.defaultTheme)

        // Get a connection to the AppSync client
        appSyncClient = AWSAppSyncClient.builder()
                .context(applicationContext)
                .credentialsProvider(AWSMobileClient.getInstance().credentialsProvider)
                .region(appSyncConfig.region)
                .serverUrl(appSyncConfig.graphqlEndpoint)
                .build()

        // Query the settings
        appSyncClient
                .query(GetSettingsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(object : GraphQLCall.Callback<GetSettingsQuery.Data>() {
                    override fun onResponse(response: Response<GetSettingsQuery.Data>) {
                        Log.d(TAG, "AppSync onResponse(): settings = ${response.data()?.settings ?: "null"}, cached = ${response.fromCache()}")
                        response.data()?.settings?.theme()?.let {
                            Log.d(TAG, "AppSync onResponse(): New Theme Received - $it")
                            runOnUiThread { mutableTheme.value = Themes.instance.getThemeByName(it) }
                        }
                    }

                    override fun onFailure(exception: ApolloException) {
                        Log.e(TAG, "Failed to execute GetSettings: ${exception.message}")
                    }
                })
    }

    override fun updateTheme(theme: Theme) {
        // Store the settings in AWS AppSync
        val settingsInput = SettingsInput.builder()
                .theme(theme.shortName)
                .displayName(theme.displayName)
                .build()
        val storeSettingsMutation = StoreSettingsMutation.builder()
                .settings(settingsInput)
                .build()
        appSyncClient.mutate(storeSettingsMutation).enqueue(object : GraphQLCall.Callback<StoreSettingsMutation.Data>() {
            override fun onResponse(response: Response<StoreSettingsMutation.Data>) {
                Log.d(TAG, "AppSync onResponse(): data = ${response.data()?.storeSettings() ?: "null"}")
                response.data()?.storeSettings()?.theme()?.let {
                    runOnUiThread { mutableTheme.value = Themes.instance.getThemeByName(it) }
                }
            }

            override fun onFailure(exception: ApolloException) {
                Log.e(TAG, "Failed to execute StoreSettings: ${exception.message}")
            }
        })
    }
}
