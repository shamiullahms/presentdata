package com.app4free.presentdata.data

import android.content.Context
import android.text.Spannable.Factory
import android.util.Log
import com.app4free.presentdata.R
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URI

const val BASE_ENDPOINT_URL = "https://2873199.youcanlearnit.net/"
const val TAG = "Data"
const val CLASS = "ProductRepo"

class ProductRepository {

    enum class LibraryType {
        Moshi, Kotlin
    }

    enum class DataType {
        Resource, Asset
    }

    //LibraryType.Moshi
    //2023-10-23 22:20:52.206         Start      2023-10-23 22:24:47.346
    //2023-10-23 22:20:52.262 (56)    Call API   2023-10-23 22:24:47.432 (86)
    //2023-10-23 22:20:54.989 (2.727) Pull Data  2023-10-23 22:24:50.171 (2.739)

    //LibraryType.Kotlin (Faster)
    //2023-10-23 22:23:05.010           2023-10-23 22:30:04.925
    //2023-10-23 22:23:05.074 (64)      2023-10-23 22:30:04.973 (48)
    //2023-10-23 22:23:06.794 (1.720)   2023-10-23 22:30:06.080 (1.107)

    private var libraryType = LibraryType.Kotlin

    private val retroFit: Retrofit by lazy {
        Log.i(TAG, "From $CLASS, value of libraryType is $libraryType")

        val factory = if (libraryType == LibraryType.Moshi) {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            MoshiConverterFactory.create(moshi)
        } else {
            val contentType =
                "application/json; charset=utf-8".toMediaType()  // MediaType("application/json")
            Json.asConverterFactory(contentType)
        }

        Retrofit.Builder()
            .baseUrl(BASE_ENDPOINT_URL)
            .addConverterFactory(factory)
            .build()
    }

    fun getTextFromResource(context: Context, resourseId: Int): String {
        return context.resources.openRawResource(resourseId)
            .bufferedReader()
            .use { it.readText() }
    }

    fun getTextFromAsset(context: Context, fileName: String): String {
        return context.resources.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

    private val productApi: ProductApi by lazy {
        retroFit.create(ProductApi::class.java)
    }

    suspend fun getProducts() : List<Product> {
        //Log.i(TAG, "From $CLASS, in suspend getProducts")
        val resp = productApi.getProducts()
        return if (resp.isSuccessful)
            resp.body() ?: emptyList()
        else
            emptyList()
    }

    fun getProducts(
        context: Context,
        libType: LibraryType = LibraryType.Kotlin,
        dataType: DataType = DataType.Asset
    ): List<Product>? {
        var productData = when (dataType) {
            DataType.Asset -> getTextFromAsset(context, "olive_oils_data.json")
            DataType.Resource -> getTextFromResource(context, R.raw.olive_oils_data)
        }

        return when (libType) {
            LibraryType.Moshi -> {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val listType = Types.newParameterizedType(
                    List::class.java, Product::class.java
                )
                val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
                return adapter.fromJson(productData)
            }

            LibraryType.Kotlin -> {
                //val products: List<Product> = Json.decodeFromString(productData)
                return Json.decodeFromString<List<Product>>(productData)
            }
        }
    }

}