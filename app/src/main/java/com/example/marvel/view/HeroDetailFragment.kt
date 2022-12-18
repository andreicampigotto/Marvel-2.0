package com.example.marvel.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.marvel.R
import com.example.marvel.databinding.HeroDetailFragmentBinding
import com.example.marvel.model.Hero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment() : Fragment(R.layout.hero_detail_fragment) {

    private lateinit var binding: HeroDetailFragmentBinding
    private val args by navArgs<HeroDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HeroDetailFragmentBinding.bind(view)

        args.hero?.let { bind(it) }
    }

    private fun bind(hero: Hero) {
//        binding.contentCardDetail.bind(hero.name, hero.thumbnail.concatThumbnail())
//        binding.contentCardDetail.bind(hero.name, hero.thumbnail)
        binding.tvDescription.text = hero.description
    }

}