package com.app4free.presentdata.ui.stock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app4free.presentdata.R
import com.app4free.presentdata.data.Stock
import com.app4free.presentdata.databinding.FragmentStockBinding

class StockFragment : Fragment() {

    private var _binding: FragmentStockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val TAG = "Dashboard"
    val CLASS = "Fragement"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(StockViewModel::class.java)

        _binding = FragmentStockBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.stockInfo.observe(viewLifecycleOwner) {
            updateStockInfo(it)
        }
        binding.btnStock1.setOnClickListener {
            Log.i(TAG, "from $CLASS calling getStockData with 1")
            dashboardViewModel.getStockData(firmId = 1)
        }
        binding.btnStock2.setOnClickListener {
            dashboardViewModel.getStockData(firmId = 2)
        }
        binding.btnStock3.setOnClickListener {
            dashboardViewModel.getStockData(firmId = 3)
        }
        binding.switchMoshi.setOnCheckedChangeListener {
                _,
                isChecked -> dashboardViewModel.setJsonToMoshi(isChecked)
        }

        return root
    }

    private fun updateStockInfo(stock: Stock) {
        binding.firmLabelText.text = when (stock.firm_id) {
            1 -> getString(R.string.firm_1_btn_label)
            2 -> getString(R.string.firm_2_btn_label)
            3 -> getString(R.string.firm_3_btn_label)
            else -> ""
        }
        binding.stockInfoText.text = getString(
            R.string.stock_info,
            stock.open,
            stock.close,
            stock.change
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}