package com.example.modul5.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modul5.R
import com.example.modul5.data.model.Gunung
import com.example.modul5.databinding.ItemGunungBinding

class GunungAdapter(
    private val onLinkClick: (Gunung) -> Unit,
    private val onDetailClick: (Gunung) -> Unit,
    private val onFavoriteClick: (Gunung) -> Unit
) : ListAdapter<Gunung, GunungAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: ItemGunungBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gunung: Gunung) {
            binding.tvGunungName.text = gunung.name
            binding.tvGunungLokasi.text = gunung.lokasi
            binding.tvGunungDeskripsi.text = gunung.deskripsi
            binding.imgGunung.load(gunung.image)

            val iconRes = if (gunung.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_border
            binding.btnFavorite.setImageResource(iconRes)

            binding.btnLink.setOnClickListener {
                onLinkClick(gunung)
            }
            binding.btnDetail.setOnClickListener {
                onDetailClick(gunung)
            }
            binding.btnFavorite.setOnClickListener {
                onFavoriteClick(gunung)
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
