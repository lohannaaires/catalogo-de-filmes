package com.lohanna.catalogodefilmes.data.movies.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Response") val response: String,
    @SerializedName("Error")val error: String
)
