package com.seoleo.fintechlab2023.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.seoleo.fintechlab2023.databinding.ItemFilmBinding
import com.seoleo.fintechlab2023.ui.model.FilmUiModel

class FilmsAdapter(
    private val onClick : (Int) -> Unit,
    private val onLongClick : (FilmUiModel) -> Unit
):  RecyclerView.Adapter<FilmsAdapter.ViewHolder>(){

    private var films =  ArrayList<FilmUiModel>()

    fun updateItems(newItems: List<FilmUiModel>){
        val diffCallback = FilmsDiffUtil(films, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        films.clear()
        films.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(films[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onLongClick, onClick)
    }

    override fun getItemCount(): Int {
        return films.size
    }


    class ViewHolder(
        private val binding: ItemFilmBinding,
        private val onLongClick: (FilmUiModel) -> Unit,
        private val onClick: (Int) -> Unit,
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(filmUiModel: FilmUiModel) {
            binding.name.text = filmUiModel.name
            binding.genre.text = filmUiModel.genre.plus(" ").plus(filmUiModel.year)
            binding.favourite.isVisible = filmUiModel.isFav
            Glide
                .with(binding.root)
                .load(filmUiModel.posterUrl)
                .centerCrop()
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        binding.progress.isGone = true
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        binding.progress.isGone = true
                        binding.poster.isVisible = true
                        return false
                    }

                })

                .into(binding.poster)

            binding.root.setOnLongClickListener {
                onLongClick.invoke(filmUiModel)
                true
            }

            binding.root.setOnClickListener {
                onClick.invoke(filmUiModel.filmId)
            }
        }
    }
}