package com.example.marvel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.databinding.ItemHeroBinding
import com.example.marvel.model.Hero
import com.example.marvel.view.HeroesFragmentDirections

class HeroesAdapter() :
    ListAdapter<Hero, HeroesViewHolder>(HeroesDiffCallback()) {

    private val heroes = mutableListOf<Hero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false).apply {
            return HeroesViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        getItem(position).let { hero ->
            holder.bind(hero)
            holder.itemView.setOnClickListener {
                it.findNavController().navigate(HeroesFragmentDirections.actionHeroesFragmentToHeroDetailFragment(hero))
            }
        }
    }

    fun update(newlist: MutableList<Hero>) {
        heroes.addAll(newlist)
        submitList(heroes)
    }

    fun clearList(newlist: MutableList<Hero>) {
        heroes.clear()
        heroes.addAll(newlist)
        submitList(heroes)
    }
}

class HeroesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemHeroBinding = ItemHeroBinding.bind(itemView)

    fun bind(hero: Hero) {
        binding.contentItem.bind(hero.name, hero.thumbnail.concatThumbnail())
    }
}