package com.example.modul4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul4.databinding.ListFragmentBinding
import com.example.modul4.viewmodel.GunungViewModelFactory
import com.example.modul4.viewmodel.GunungViewModel


class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GunungViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)

        val listGunung = getListGunung()
        val factory = GunungViewModelFactory(listGunung)
        viewModel = ViewModelProvider(this, factory)[GunungViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = GunungAdapter(
            onLinkClick = { gunung ->
                viewModel.onLinkClicked(gunung)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gunung.link))
                startActivity(intent)
            },
            onDetailClick = { gunung ->
                viewModel.onDetailClicked(gunung)
                val detailFragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("EXTRA_PHOTO", gunung.image)
                        putString("EXTRA_NAME", gunung.name)
                        putString("EXTRA_LOKASI", gunung.lokasi)
                        putString("EXTRA_DESKRIPSI", gunung.deskripsi)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.rvGunung.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGunung.adapter = adapter
        binding.rvGunung.setHasFixedSize(true)

        lifecycleScope.launchWhenStarted {
            viewModel.gunungList.collect { list ->
                adapter.submitList(list)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedGunung.collect { selected ->
                selected?.let {
                    Log.d("ListFragment", "Navigasi ke detail: ${it.name}")
                }
            }
        }
    }

    private fun getListGunung(): List<Gunung> {
        val dataImage = resources.obtainTypedArray(R.array.gunung_image)
        val dataName = resources.getStringArray(R.array.gunung_name)
        val dataLokasi = resources.getStringArray(R.array.gunung_lokasi)
        val dataDesc = resources.getStringArray(R.array.gunung_deskripsi)
        val dataLink = resources.getStringArray(R.array.gunung_link)

        return List(dataName.size) { i ->
            Gunung(
                dataImage.getResourceId(i, -1),
                dataName[i],
                dataLokasi[i],
                dataDesc[i],
                dataLink[i]
            )
        }.also { dataImage.recycle() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
