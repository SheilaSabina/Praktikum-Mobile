package com.example.modul3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var gunungAdapter: GunungAdapter
    private val list = ArrayList<Gunung>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)

        // Clear the list and load new data
        list.clear()
        list.addAll(getListGunung())
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        gunungAdapter = GunungAdapter(
            list,
            onLinkClick = { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            },
            onDetailClick = { image, name, lokasi, deskripsi ->
                val detailFragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("EXTRA_PHOTO", image)
                        putString("EXTRA_NAME", name)
                        putString("EXTRA_LOKASI", lokasi)
                        putString("EXTRA_DESKRIPSI", deskripsi)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        // Set up RecyclerView
        binding.rvGunung.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gunungAdapter
            setHasFixedSize(true)
        }
    }

    private fun getListGunung(): ArrayList<Gunung> {
        val dataImage = resources.obtainTypedArray(R.array.gunung_image)
        val dataName = resources.getStringArray(R.array.gunung_name)
        val dataLokasi = resources.getStringArray(R.array.gunung_lokasi)
        val dataDesc = resources.getStringArray(R.array.gunung_deskripsi)
        val dataLink = resources.getStringArray(R.array.gunung_link)


        val listGunung = ArrayList<Gunung>()
        for (i in dataName.indices) {
            val gunung = Gunung(dataImage.getResourceId(i, -1),dataName[i],dataLokasi[i],dataDesc[i], dataLink[i])
            listGunung.add(gunung)
        }
        dataImage.recycle()
        return listGunung
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}