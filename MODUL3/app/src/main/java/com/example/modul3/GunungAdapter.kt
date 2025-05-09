package com.example.modul3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3.databinding.ItemGunungBinding

class GunungAdapter(
    private val listGunung: ArrayList<Gunung>,
    private val onLinkClick: (String) -> Unit,
    private val onDetailClick: (Int, String, String, String) -> Unit
) : RecyclerView.Adapter<GunungAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemGunungBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gunung: Gunung, onLinkClick: (String) -> Unit, onDetailClick: (Int, String, String, String) -> Unit) {
            // Bind data to views using the binding object
            binding.tvGunungName.text = gunung.name
            binding.tvGunungLokasi.text = gunung.lokasi
            binding.tvGunungDeskripsi.text = gunung.deskripsi
            binding.imgGunung.setImageResource(gunung.image)

            binding.btnLink.setOnClickListener { onLinkClick(gunung.link) }

            binding.btnDetail.setOnClickListener {
                onDetailClick(gunung.image, gunung.name, gunung.lokasi, gunung.deskripsi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemGunungBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listGunung.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val gunung = listGunung[position]
        holder.bind(gunung, onLinkClick, onDetailClick)
    }
}