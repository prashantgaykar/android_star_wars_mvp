package io.prashant.starwars.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.prashant.starwars.R
import io.prashant.starwars.data.local.Character

class CharacterPagingAdapter(private val onCharacterClick: (Character?) -> Unit) :
    PagingDataAdapter<Character, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val characterViewHolder = (holder as? CharacterViewHolder)
        val character = getItem(position)
        characterViewHolder?.itemView?.setOnClickListener {
            onCharacterClick(character)
        }
        characterViewHolder?.bind(item = character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.getInstance(parent)
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup): CharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_character, parent, false)
                return CharacterViewHolder(view)
            }
        }

        var tvName: TextView = view.findViewById(R.id.tv_name)

        fun bind(item: Character?) {
            tvName.text = item?.name
        }

    }


}