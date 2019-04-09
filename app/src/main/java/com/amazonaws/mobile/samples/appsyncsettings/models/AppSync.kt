/*
// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0.
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
