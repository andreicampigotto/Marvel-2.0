package com.example.marvel.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.marvel.R
import com.example.marvel.databinding.HeroDetailFragmentBinding
import com.example.marvel.model.Hero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment(val hero: Hero) : Fragment(R.layout.hero_detail_fragment) {

    private lateinit var binding: HeroDetailFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HeroDetailFragmentBinding.bind(view)

        binding.toolbarDetail.enableBackButton()
        binding.toolbarDetail.clickBackButton {
            (requireActivity() as MainActivity).replaceFrag(HeroesFragment())
        }

        bind(hero)

    }

    private fun bind(hero: Hero) {
        binding.contentCardDetail.bind(hero.name, hero.thumbnail.concatThumbnail())
        binding.tvDescription.text = hero.description
    }

}