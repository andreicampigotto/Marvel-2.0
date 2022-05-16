package com.example.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.lib.databinding.ContentDetailBinding

class ContentDetail @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: ContentDetailBinding = ContentDetailBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun bind(name: String, thumbnail: String) {
        binding.tvHeroNameDetail.text = name
        Glide.with(context)
            .load(thumbnail)
            .placeholder(R.drawable.ic_baseline_download_24)
            .into(binding.imHeroDetail)
    }
}