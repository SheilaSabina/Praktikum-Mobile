package com.example.modul5.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.modul5.R
import com.example.modul5.databinding.DetailFragmentBinding

class GunungDetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        val name = arguments?.getString("EXTRA_NAME") ?: "Tidak ada nama"
        val lokasi = arguments?.getString("EXTRA_LOKASI") ?: "Tidak ada lokasi"
        val deskripsi = arguments?.getString("EXTRA_DESKRIPSI") ?: "Deskripsi belum tersedia"
        val image = arguments?.getString("EXTRA_PHOTO")

        binding.tvName.text = name
        binding.tvLokasi.text = lokasi
        binding.tvDeskripsi.text = deskripsi

        image?.let {
            binding.imgPoster.load(it) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            activity?.findViewById<View>(R.id.fragmentContainerGunung)?.visibility = View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}