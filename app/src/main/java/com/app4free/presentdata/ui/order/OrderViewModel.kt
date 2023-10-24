package com.app4free.presentdata.ui.order

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.app4free.presentdata.data.Product
import com.app4free.presentdata.data.ProductRepository

const val TAG = "Order"
const val CLASS = "ViewModel"
class OrderViewModel (app: Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private var productRepository: ProductRepository = ProductRepository()

    val products: LiveData<List<Product>> = liveData {
        val data = productRepository.getProducts()
        //Log.i(TAG, "Class $CLASS - Data from suspend $data")
        emit(data)
    }
/*
    val products: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        val data = productRepository.getProducts(app)
        data?.let {
            products.value = it
        }
    }
*/
}