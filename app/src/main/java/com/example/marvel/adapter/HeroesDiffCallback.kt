package com.example.marvel.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.marvel.model.Hero

class HeroesDiffCallback : DiffUtil.ItemCallback<Hero>() {
    override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem.id == newItem.id
    }
}