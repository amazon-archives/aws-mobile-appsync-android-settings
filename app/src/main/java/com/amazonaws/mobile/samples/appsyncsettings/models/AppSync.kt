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
package com.amazonaws.mobile.samples.appsyncsettings.models

import com.amazonaws.regions.Regions
import org.json.JSONObject

/**
 * Represents the JSON File res/raw/appsync.json
 */
class AppSync(val jsonObject: JSONObject) {
    /**
     * The GraphQL endpoint
     */
    var graphqlEndpoint: String = ""

    /**
     * The configured authentication
     */
    var authType: AppSyncAuthType = AppSyncAuthType.API_KEY

    /**
     * The region the endpoint is located in
     */
    var region: Regions = Regions.DEFAULT_REGION

    /**
     * The API Key (if any)
     */
    var apiKey: String = ""

    /**
     * Take a string instead of a JSONObject
     */
    constructor(json: String) : this(JSONObject(json))

    /**
     * initialize
     */
    init {
        graphqlEndpoint = jsonObject.getString("graphqlEndpoint")
        region = Regions.fromName(jsonObject.getString("region"))
        apiKey = jsonObject.getString("apiKey")
        authType = AppSyncAuthType.valueOf(jsonObject.getString("authenticationType"))
    }
}