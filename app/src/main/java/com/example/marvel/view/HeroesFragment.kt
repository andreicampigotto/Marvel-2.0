package com.example.marvel.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.adapter.AboveAdapter
import com.example.marvel.adapter.HeroesAdapter
import com.example.marvel.databinding.HeroesFragmentBinding
import com.example.marvel.model.Hero
import com.example.marvel.utils.checkConnection
import com.example.marvel.viewModel.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesFragment : Fragment(R.layout.heroes_fragment) {

    private lateinit var viewModel: HeroesViewModel
    private lateinit var binding: HeroesFragmentBinding
    private val heroesAdapter = HeroesAdapter {}

    private val aboveAdapter = AboveAdapter {}

    private val observerHero = Observer<List<Hero>> {
        heroesAdapter.update(it.toMutableList())
        binding.progressBar.visibility = View.GONE
        binding.heroesRecyclerView.visibility = View.VISIBLE
    }

    private val observerClearHero = Observer<List<Hero>> {
        heroesAdapter.clearList(it.toMutableList())
    }

    private val observerAbove = Observer<List<Hero>> { aboveAdapter.update(it.subList(0, 5)) }
    private val observerOffset = Observer<Int> {
        viewModel.fetchHeroes(offset = it, (requireActivity() as MainActivity).checkConnection())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HeroesViewModel::class.java]

        viewModel.heroes.observe(viewLifecycleOwner, observerHero)
        viewModel.heroes.observe(viewLifecycleOwner, observerAbove)
        viewModel.offset.observe(viewLifecycleOwner, observerOffset)
        viewModel.heroesSearch.observe(viewLifecycleOwner, observerClearHero)

        binding = HeroesFragmentBinding.bind(view)

        setupRecyclerViewTop()
        setupRecyclerView()
        searchHero()
    }

    private fun setupRecyclerView() = with(binding.heroesRecyclerView) {
        adapter = heroesAdapter
        layoutManager = GridLayoutManager(requireContext(), 2)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.nextPage()
                }
                hideSoftInput()
            }
        })
    }

    private fun setupRecyclerViewTop() = with(binding.include.recyclerViewTopHeroes) {
        binding.include.recyclerViewTopHeroes.adapter = aboveAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideSoftInput()
            }
        })
        viewModel.fetchHeroes(0, (requireActivity() as MainActivity).checkConnection())
    }

    fun View.hideSoftInput() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun searchHero() {
        binding.include.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.let {
                    if (it?.length!! >= 2) {
                        viewModel.fetchHeroesByName(
                            0,
                            it.toString(),
                            (requireActivity() as MainActivity).checkConnection()
                        )
                    } else
                        viewModel.fetchHeroes(
                            0,
                            (requireActivity() as MainActivity).checkConnection()
                        )
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}

