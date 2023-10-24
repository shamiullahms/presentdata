package com.app4free.presentdata.ui.about

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app4free.presentdata.databinding.FragmentAboutBinding

const val TAG = "About"
const val CLASS = "Fragement"

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("$TAG", "$CLASS onCreate In")
        val aboutViewModel =
            ViewModelProvider(this).get(AboutViewModel::class.java)
        Log.i("$TAG", "$CLASS onCreate Mid")

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAbout
        aboutViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        Log.i("$TAG", "$CLASS onCreate Out")
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
/*
        super.onViewCreated(view, savedInstanceState)

        val sharedViewModel = ViewModelProvider (this).get(SharedViewModel::class.java)
*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}