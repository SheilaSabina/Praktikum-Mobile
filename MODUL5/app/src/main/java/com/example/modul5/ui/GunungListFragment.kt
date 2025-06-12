package com.example.modul5.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul5.R
import com.example.modul5.data.repository.GunungRepository
import com.example.modul5.databinding.ListFragmentBinding
import com.example.modul5.viewmodel.GunungViewModel
import com.example.modul5.viewmodel.GunungViewModelFactory
import kotlinx.coroutines.flow.collect

class GunungListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GunungViewModel
    private lateinit var adapter: GunungAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = GunungRepository(requireContext())
        val factory = GunungViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[GunungViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
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
                viewModel.selectGunung(gunung)
            },
            onFavoriteClick = { gunung ->
                val updatedGunung = gunung.copy(isFavorite = !gunung.isFavorite)
                viewModel.updateGunung(updatedGunung)
            }
        )

        binding.rvGunung.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGunung.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.gunungList.collect { listGunung ->
                adapter.submitList(listGunung)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorMessage.collect { error ->
                error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.selectedGunung.collect { selected ->
                selected?.let {
                    val detailFragment = GunungDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("EXTRA_NAME", it.name)
                            putString("EXTRA_LOKASI", it.lokasi)
                            putString("EXTRA_DESKRIPSI", it.deskripsi)
                            putString("EXTRA_PHOTO", it.image)
                        }
                    }

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerGunung, detailFragment)
                        .addToBackStack(null)
                        .commit()

                    requireActivity().findViewById<View>(R.id.fragmentContainerGunung).visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
