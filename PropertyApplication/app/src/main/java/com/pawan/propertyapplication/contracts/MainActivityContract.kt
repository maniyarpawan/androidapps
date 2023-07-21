package com.pawan.propertyapplication.contracts

import com.pawan.propertyapplication.network.model.PropertyDTO

interface MainActivityContract {

    interface View{
        fun onLoading()
        fun onSuccess(list:PropertyDTO)
        fun onError(message:String)
    }

    interface Presenter{
        fun getPropertyDetails()
        fun onDestroy()
    }


    interface Model{
        interface OnFinishListener{
            fun onLoading()
            fun onError(message:String)
            fun onSuccess(list:PropertyDTO)
        }
        suspend fun fetchUniversity(onFinishListener: OnFinishListener)
    }
}