package com.app4free.presentdata.ui.about

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app4free.presentdata.data.ProductRepository

class AboutViewModel (app: Application): AndroidViewModel(app) {
    val TAG = "About"
    val CLASS = "ViewModel"

    private var productRepository: ProductRepository = ProductRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "About this item\n\n" +
                "Better for your health: Organic natural pillows are made from materials that are free of harmful chemicals and synthetic materials, which can irritate your skin, eyes, and respiratory system. They are also hypoallergenic, making them a good choice for people with allergies.\n\n" +
                "organic natural pillows can offer different levels of support for your head and neck, which can help reduce neck and shoulder pain and improve your sleep quality.\n\n" +
                "Lumara Original Natural 100% Organic Silk Cotton Kapok Ilavam Panju Semal Cotton 16×25in Sleeping Pilllow\n\n" +
                "Silk cotton pillow Kapok pillow Organic pillow Natural pillow Organic silk cotton pillow Silk cotton pillow Kapok pillow Pillow for neck support C ooling pillows Silk cotton filled pillows Kapok fiber pillows Natural fiber pillows\n\n" +
                "Better for your health: Organic natural pillows are made from materials that are free of harmful chemicals and synthetic materials, which can irritate your skin, eyes, and respiratory system. They are also hypoallergenic, making them a good choice for people with allergies.\n\n" +
                "organic natural pillows can offer different levels of support for your head and neck, which can help reduce neck and shoulder pain and improve your sleep quality.\n\n" +
                "Lumara Original Natural 100% Organic Silk Cotton Kapok Ilavam Panju Semal Cotton 16×25in Sleeping Pilllow\n\n" +
                "Silk cotton pillow Kapok pillow Organic pillow Natural pillow Organic silk cotton pillow Silk cotton pillow Kapok pillow Pillow for neck support C ooling pillows Silk cotton filled pillows Kapok fiber pillows Natural fiber pillows"
    }
    val text: LiveData<String> = _text

    init {
        var jsonData = productRepository.getProducts(app,ProductRepository.LibraryType.Kotlin)
        jsonData?.forEach {
            Log.i(TAG, "Product list from $CLASS using ${ProductRepository.LibraryType.Kotlin} Object: ${it.name}")
        }

        jsonData = productRepository.getProducts(app, ProductRepository.LibraryType.Moshi)
        jsonData?.forEach {
            Log.i(TAG, "Product list from $CLASS using ${ProductRepository.LibraryType.Moshi} Kotlin Object: ${it.name}")
        }
    }
}