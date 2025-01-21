package com.lohanna.catalogodefilmes.data.common.remote

import com.lohanna.catalogodefilmes.BuildConfig

class RemoteService(baseUrl: String = BuildConfig.BASE_URL) :
    BaseRemoteService(baseUrl)