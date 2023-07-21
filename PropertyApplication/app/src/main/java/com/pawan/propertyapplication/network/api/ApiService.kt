package com.pawan.propertyapplication.network.api

import com.pawan.propertyapplication.network.model.PropertyDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/iranjith4/ad-assignment/db")
    suspend fun getProperty():Response<PropertyDTO>

}