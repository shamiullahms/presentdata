package com.app4free.presentdata.ui.stock

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app4free.presentdata.data.Stock
import com.app4free.presentdata.data.StockRepository

class StockViewModel(app: Application) : AndroidViewModel(app) {

    val TAG = "Dashboard"
    val CLASS = "ViewModel"

    private var stockRepository: StockRepository = StockRepository(app)
    private val _stockInfo = MutableLiveData<Stock>()
    val stockInfo: LiveData<Stock> = _stockInfo

    private val moshiFlag = MutableLiveData<Boolean>().apply { value = false }

    fun getStockData(firmId: Int) {
        Log.i(TAG, "from $CLASS calling getStockDataForFirm with 1 and value of _moshiFlag is $moshiFlag")

        val stock = moshiFlag.value?.let { stockRepository.getStockDataForFirm(firmId, it) }
        stock?.let { _stockInfo.value = it }
    }

    fun setJsonToMoshi(jsonMoshiFlag: Boolean) {
        moshiFlag.value = jsonMoshiFlag
    }
}