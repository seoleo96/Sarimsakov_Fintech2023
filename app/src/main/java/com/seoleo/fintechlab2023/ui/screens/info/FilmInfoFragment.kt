package com.seoleo.fintechlab2023.ui.screens.info

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.seoleo.fintechlab2023.R
import com.seoleo.fintechlab2023.databinding.FragmentFilmInfoBinding
import com.seoleo.fintechlab2023.ui.adapter.FilmsAdapter
import com.seoleo.fintechlab2023.ui.model.FilmInfoUIModel
import com.seoleo.fintechlab2023.ui.model.Message
import org.koin.android.ext.android.inject

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmInfoViewModel by inject()
    private var filmDefaultId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filmDefaultId = arguments?.getInt("filmId") ?: -1

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.retry.setOnClickListener {
            viewModel.getInfoFilm(filmDefaultId)
        }

        if (filmDefaultId != -1){
            viewModel.getInfoFilm(filmDefaultId)
        }

        observeUI()
    }

    private fun observeUI() {
        viewModel.content.observe(viewLifecycleOwner) { uiModel ->
            uiModel?.let {
                showContent(it)
            }
        }

        viewModel.progress().observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
            binding.content.isVisible = !it
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
                    binding.content.isGone = true
                    binding.noInternetContent.isVisible = true
                }
                Message.Success -> {
                    binding.progress.isGone = true
                    binding.content.isVisible = true
                    binding.noInternetContent.isGone = true
                }
                Message.TokenNotFound -> {
                    showMessage(getString(R.string.token_not_found))
                }
            }
        }
    }

    private fun showContent(uiModel: FilmInfoUIModel) {
        binding.title.text = uiModel.title
        binding.description.text = uiModel.description
        binding.genres.text = uiModel.genres
        binding.countries.text = uiModel.countries
        Glide
            .with(binding.root)
            .load(uiModel.posterUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    binding.progressPoster.isGone = true
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    binding.progressPoster.isGone = true
                    binding.poster.isVisible = true
                    return false
                }
            })
            .into(binding.poster)
    }

    private fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}