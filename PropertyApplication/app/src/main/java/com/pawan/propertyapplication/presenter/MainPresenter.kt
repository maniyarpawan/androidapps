package com.pawan.propertyapplication.presenter

import com.pawan.propertyapplication.contracts.MainActivityContract
import com.pawan.propertyapplication.launchOnMain
import com.pawan.propertyapplication.network.model.PropertyDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainActivityContract.View,
    private val model: MainActivityContract.Model
) : MainActivityContract.Presenter, MainActivityContract.Model.OnFinishListener {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun getPropertyDetails() {
        scope.launch {
            model.fetchUniversity(onFinishListener = this@MainPresenter)
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }


    override fun onLoading() {
        scope.launchOnMain { view.onLoading() }

    }

    override fun onError(message: String) {
        scope.launchOnMain { view.onError(message) }
    }

    override fun onSuccess(list: PropertyDTO) {
        scope.launchOnMain { view.onSuccess(list) }
    }
}