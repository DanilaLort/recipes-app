package com.example.recipes_app.presentation.recipes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.databinding.RecipeCardBinding
import com.example.recipes_app.domain.models.Recipe
import com.example.recipes_app.presentation.recipes.adapter.RecipesPagingAdapter.RecipeViewHolder
import com.example.recipes_app.utils.GlideApp

class RecipesPagingAdapter : PagingDataAdapter<Recipe, RecipeViewHolder>(RECIPE_DIFF_CALLBACK) {

    companion object {
        private val RECIPE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class RecipeViewHolder(
        private val binding: RecipeCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe?) {
            Log.d("PagingData", "$recipe")
            recipe?.let {
                binding.apply {
                    recipeTitle.text = it.title

                    binding.recipeTitle.text = it.title

                    GlideApp.with(binding.root)
                        .load(it.image)
                        .into(binding.recipeImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        Log.d("PagingData", "onCreateViewHolder adapter")

        val binding = RecipeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecipeViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        Log.d("PagingData", "$item adapter")
        holder.bind(item)
    }

}