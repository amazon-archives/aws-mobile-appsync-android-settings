/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
 */
package com.amazonaws.mobile.samples.appsyncsettings.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.mobile.auth.ui.SignInUI
import com.amazonaws.mobile.client.AWSMobileClient

class AuthenticatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AWSMobileClient, then transfer to SignInUI
        // once signed-in, transfer to MainActivity
        AWSMobileClient.getInstance().initialize(this) {
            val signInUI = AWSMobileClient.getInstance().getClient(this@AuthenticatorActivity, SignInUI::class.java) as SignInUI
            signInUI.login(this@AuthenticatorActivity, MainActivity::class.java).execute()
        }.execute()
    }
}
