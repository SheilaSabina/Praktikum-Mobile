package com.example.modul5.ui

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul5.R
import com.example.modul5.databinding.FragmentFavoritGunungBinding
import com.example.modul5.viewmodel.GunungViewModel
import com.example.modul5.viewmodel.GunungViewModelFactory
import com.example.modul5.data.repository.RepositoryInstance
import kotlinx.coroutines.launch

class FavoritGunungFragment : Fragment() {

    private var _binding: FragmentFavoritGunungBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GunungAdapter

    private val viewModel: GunungViewModel by viewModels {
        GunungViewModelFactory(RepositoryInstance.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritGunungBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GunungAdapter(
            onLinkClick = { gunung ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gunung.link))
                startActivity(intent)
            },
            onDetailClick = { gunung ->
                val detail = GunungDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("EXTRA_NAME", gunung.name)
                        putString("EXTRA_LOKASI", gunung.lokasi)
                        putString("EXTRA_DESKRIPSI", gunung.deskripsi)
                        putString("EXTRA_PHOTO", gunung.image)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerGunung, detail)
                    .addToBackStack(null)
                    .commit()

                requireActivity().findViewById<View>(R.id.fragmentContainerGunung).visibility = View.VISIBLE
            }
            ,
            onFavoriteClick = { gunung ->
                val updatedGunung = gunung.copy(isFavorite = !gunung.isFavorite)
                viewModel.updateGunung(updatedGunung)
            }
        )

        binding.recyclerViewFavorit.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorit.adapter = adapter

        lifecycleScope.launch {
            viewModel.favoriteList.collect { favorites ->
                adapter.submitList(favorites)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}