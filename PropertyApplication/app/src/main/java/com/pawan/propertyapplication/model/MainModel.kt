package com.pawan.propertyapplication.model

import com.pawan.propertyapplication.contracts.MainActivityContract
import com.pawan.propertyapplication.network.api.ApiService

class MainModel(private val apiService: ApiService) : MainActivityContract.Model {
    override suspend fun fetchUniversity(
        onFinishListener: MainActivityContract.Model.OnFinishListener
    ) {
        onFinishListener.onLoading()
        try{
            val response = apiService.getProperty()
            if(response.isSuccessful){
                response.body()?.let {
                    onFinishListener.onSuccess(it)
                }
            }else{
                onFinishListener.onError(response.errorBody().toString())
            }
        }catch (e:Exception){
            onFinishListener.onError(message = e.message.toString())
        }
    }
}