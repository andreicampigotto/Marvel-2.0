package com.example.marvel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.databinding.ItemAboveBinding
import com.example.marvel.model.Hero
import com.example.marvel.view.HeroesFragmentDirections

class AboveAdapter(val onTap: (Hero) -> Unit) : RecyclerView.Adapter<AboveHeroesViewHolder>() {

    private var heroes = mutableListOf<Hero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboveHeroesViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_above, parent, false)
            .apply {
                return AboveHeroesViewHolder(this)
            }
    }

    override fun onBindViewHolder(holder: AboveHeroesViewHolder, position: Int) {
        heroes[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                it.findNavController()
                    .navigate(HeroesFragmentDirections.actionHeroesFragmentToHeroDetailFragment(this))
            }
        }
    }

    override fun getItemCount(): Int = heroes.size

    fun update(newlist: List<Hero>) {
        heroes.clear()
        heroes.addAll(newlist)
        notifyDataSetChanged()
    }
}

class AboveHeroesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemAboveBinding = ItemAboveBinding.bind(itemView)

    fun bind(hero: Hero) {
//        binding.contentAbove.bind(hero.name, hero.thumbnail.concatThumbnail())
//        binding.contentAbove.bind(hero.name, hero.thumbnail)
          binding.contentAbove.bind(hero.name, R.drawable.ic_baseline_search_24)
    }
}