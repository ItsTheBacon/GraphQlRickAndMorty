package com.bacon.graphqlrickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bacon.graphqlrickandmorty.CharactersListQuery
import com.bacon.graphqlrickandmorty.common.extension.setOnSingleClickListener
import com.bacon.graphqlrickandmorty.databinding.ItemCharacterBinding


class CharactersAdapter(
    val onItemLongClick: (photo: String) -> Unit,
    val onItemClick: (id: String) -> Unit,
) :
    ListAdapter<CharactersListQuery.Result, CharactersAdapter.CharacterViewHolder>(CharacterDiffUtil()) {
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnSingleClickListener {
                getItem(adapterPosition)?.let {
                    onItemClick(it.id.toString())
                }
            }
        }

        fun onBind(item: CharactersListQuery.Result) = with(binding) {
            itemName.text = item.name
            itemImage.load(item.image)
            itemStatus.text = item.status
            itemImage.setOnLongClickListener {
                getItem(adapterPosition)?.let {
                    onItemLongClick(it.image.toString())
                }
                true
            }
        }
    }

}


class CharacterDiffUtil : DiffUtil.ItemCallback<CharactersListQuery.Result>() {

    override fun areItemsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result,
    ): Boolean {
        return oldItem == newItem
    }

}

