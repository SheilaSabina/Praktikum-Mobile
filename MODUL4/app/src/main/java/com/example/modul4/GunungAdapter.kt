package com.example.modul4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.modul4.databinding.ItemGunungBinding

class GunungAdapter(
    private val onLinkClick: (Gunung) -> Unit,
    private val onDetailClick: (Gunung) -> Unit
) : ListAdapter<Gunung, GunungAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: ItemGunungBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gunung: Gunung) {
            binding.tvGunungName.text = gunung.name
            binding.tvGunungLokasi.text = gunung.lokasi
            binding.tvGunungDeskripsi.text = gunung.deskripsi
            binding.imgGunung.setImageResource(gunung.image)

            binding.btnLink.setOnClickListener {
                onLinkClick(gunung)
            }
            binding.btnDetail.setOnClickListener {
                onDetailClick(gunung)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemGunungBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Gunung>() {
            override fun areItemsTheSame(oldItem: Gunung, newItem: Gunung) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Gunung, newItem: Gunung) =
                oldItem == newItem
        }
    }
}