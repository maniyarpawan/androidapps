package com.pawan.propertyapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pawan.propertyapplication.adapter.MainAdapter
import com.pawan.propertyapplication.contracts.MainActivityContract
import com.pawan.propertyapplication.databinding.ActivityMainBinding
import com.pawan.propertyapplication.model.MainModel
import com.pawan.propertyapplication.network.api.ApiService
import com.pawan.propertyapplication.network.model.Exclusion
import com.pawan.propertyapplication.network.model.PropertyDTO
import com.pawan.propertyapplication.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var presenter: MainPresenter

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
    private val mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, MainModel(apiService))

        initView()

        presenter.getPropertyDetails()

    }

    private fun initView() {
        binding.rvPropertyDetails.adapter = mainAdapter

    }


    override fun onLoading() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun onSuccess(list: PropertyDTO) {
        binding.progress.visibility = View.GONE
        mainAdapter.setData(
            ArrayList(list.facilities),
            list.exclusions as ArrayList<java.util.ArrayList<Exclusion>>
        )
    }

    override fun onError(message: String) {
        binding.progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}