package com.seoleo.fintechlab2023.ui.screens.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.snackbar.Snackbar
import com.seoleo.fintechlab2023.R
import com.seoleo.fintechlab2023.databinding.FragmentMainBinding
import com.seoleo.fintechlab2023.ui.adapter.FilmsAdapter
import com.seoleo.fintechlab2023.ui.adapter.MarginItemDecoration
import com.seoleo.fintechlab2023.ui.model.Message
import com.seoleo.fintechlab2023.ui.screens.info.FilmInfoFragment
import org.koin.android.ext.android.inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FilmsAdapter
    private val viewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        listeners()
        observeContent()
    }

    private fun listeners() {
        binding.retry.setOnClickListener {
            viewModel.fetchFilms()
        }

        binding.searchView.setOnSearchClickListener {
            binding.title.isVisible = false
        }

        binding.searchView.setOnCloseListener {
            binding.title.isVisible = true
            viewModel.fetchFilmsFlow()
            false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?) = true
            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    viewModel.fetchFilmsFlow(it)
                }
                return true
            }
        })
    }

    private fun observeContent() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }
        viewModel.progress().observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
            binding.recycler.isVisible = !it
        }
        viewModel.emptySearchResponse().observe(viewLifecycleOwner) {
            binding.recycler.isVisible = !it
            binding.emptySearchContent.isVisible = it
            setFragmentResult("hide", bundleOf("hide" to it))
        }
        viewModel.message().observe(viewLifecycleOwner) { message ->
            when (message) {
                Message.LargeRequestLimit -> {
                    showMessage(getString(R.string.large_request_limit))
                }
                Message.ManyRequests -> {
                    showMessage(getString(R.string.many_requests))
                }
                Message.NoInternet -> {
                    binding.progress.isGone = true
                    binding.recycler.isGone = true
                    binding.noInternetContent.isVisible = true
                    setFragmentResult("hide", bundleOf("hide" to true))
                }
                Message.Success -> {
                    binding.progress.isGone = true
                    binding.recycler.isVisible = true
                    binding.noInternetContent.isGone = true
                    setFragmentResult("hide", bundleOf("hide" to false))
                }
                Message.TokenNotFound -> {
                    showMessage(getString(R.string.token_not_found))
                }
            }
        }
    }

    private fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
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

