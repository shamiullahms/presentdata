package com.app4free.presentdata.data

import android.content.Context
import android.util.Log
import com.app4free.presentdata.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json

class StockRepository(context: Context) {

    val TAG = "StockRepo"
    private var jsonMoshiFlag: Boolean = false
    private var stocks = listOf<Stock>()

    init {
        Log.i(TAG, "MoshiFlag: $jsonMoshiFlag")
        stocks = getStocks(context) ?: emptyList()
        stocks.forEach {
            Log.i(TAG, "$it")
        }
    }

    private fun getStockText(context: Context): String {
        return context.resources.openRawResource(R.raw.stock_data)
            .bufferedReader()
            .use { it.readText() }
    }

    private fun getStocks(context: Context): List<Stock>? {
        val stockData = getStockText(context)

        return if (jsonMoshiFlag) {
            Log.i(TAG, "Using Moshi JSON")

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val listType = Types.newParameterizedType(
                List::class.java, Stock::class.java
            )
            val adapter: JsonAdapter<List<Stock>> = moshi.adapter(listType)
            adapter.fromJson(stockData)
        } else {
            Log.i(TAG, "Using Kotlin JSON")
            val stock: List<Stock> = Json.decodeFromString(stockData)
            Json.decodeFromString<List<Stock>>(stockData)
        }
    }

    fun getStockDataForFirm(firmId: Int, _moshiFlag: Boolean): Stock? {
        Log.i(TAG, "value of _moshiFlag = $_moshiFlag")
        jsonMoshiFlag = _moshiFlag
        Log.i(TAG, "value of stocks = $stocks")
        return stocks.firstOrNull { it.firm_id == firmId }
    }

}