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
