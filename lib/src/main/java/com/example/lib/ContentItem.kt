package com.example.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.lib.databinding.ContentItemBinding

class ContentItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: ContentItemBinding = ContentItemBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun bind(name: String, thumbnail: Int) {
        binding.tvHeroName.text = name
        Glide.with(context)
            .load(thumbnail)
            .placeholder(R.drawable.ic_baseline_download_24)
            .into(binding.imHero)
    }
}