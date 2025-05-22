package com.example.modul4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.modul4.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        val image = arguments?.getInt("EXTRA_PHOTO")
        val name = arguments?.getString("EXTRA_NAME")
        val lokasi = arguments?.getString("EXTRA_LOKASI")
        val deskripsi = arguments?.getString("EXTRA_DESKRIPSI")


        binding.tvName.text = name
        binding.tvLokasi.text = lokasi
        binding.tvDeskripsi.text = deskripsi
        image?.let {
            binding.imgPoster.setImageResource(it)
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}