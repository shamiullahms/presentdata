package com.app4free.presentdata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.app4free.presentdata.data.ProductRepository

class SharedViewModel (app: Application): AndroidViewModel(app) {
    var productRepository: ProductRepository = ProductRepository()

    init {
        val jsonDataMoshi = productRepository.getProducts(app)
        jsonDataMoshi?.forEach {
            Log.i("SharedViewModel", "Product list from Moshi Object: ${it.name}")
        }

    }
}