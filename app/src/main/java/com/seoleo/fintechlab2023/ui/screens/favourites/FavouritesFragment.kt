package com.seoleo.fintechlab2023.ui.screens.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.seoleo.fintechlab2023.R
import com.seoleo.fintechlab2023.databinding.FragmentFavouritesBinding
import com.seoleo.fintechlab2023.ui.adapter.FilmsAdapter
import com.seoleo.fintechlab2023.ui.adapter.MarginItemDecoration
import com.seoleo.fintechlab2023.ui.screens.info.FilmInfoFragment
import org.koin.android.ext.android.inject

class FavouritesFragment() : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FilmsAdapter
    private val viewModel: FavouritesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        observeContent()
        listeners()
    }

    private fun listeners() {
        binding.searchView.setOnSearchClickListener {
            binding.title.isVisible = false
        }

        binding.searchView.setOnCloseListener {
            binding.title.isVisible = true
            false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?) = true
            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    viewModel.fetchFavFilms(it)
                }

                return true
            }
        })
    }

    private fun observeContent() {
        viewModel.emptySearchResponse().observe(viewLifecycleOwner) {
            binding.recycler.isVisible = !it
            binding.emptySearchContent.isVisible = it
        }
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }
    }

    private fun setUpAdapter() {
        adapter = FilmsAdapter(
            onLongClick = { filmUiModel ->
                viewModel.toFavourite(filmUiModel)
            },
            onClick = { filmId ->
                val bundle = Bundle()
                bundle.putInt("filmId", filmId)
                val fragment = FilmInfoFragment()
                fragment.arguments = bundle
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack("filmInfo")
                    .commit()
            }
        )
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_8))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}