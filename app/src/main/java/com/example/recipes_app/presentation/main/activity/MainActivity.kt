package com.example.recipes_app.presentation.main.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes_app.presentation.main.viewmodel.MainActivityViewModel
import com.example.recipes_app.presentation.recipes.adapter.RecipesPagingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipesPagingAdapter
    private var searchDebounceJob: Job? = null
    private val viewModel: MainActivityViewModel by viewModel()
    private var sortBy = ""
    private var sortType = ""
    private var isDescending = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupRecyclerView()

        setupSearch()

        setupObservers()

        setupBottomSheet()

        setupSortDirectionButton()

        binding.toolBar.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "https://spoonacular.com"
                )
            )
            startActivity(intent)
        }
    }

    private fun setupSortDirectionButton() {
        binding.sortDirectionButton.setOnClickListener {
            isDescending = !isDescending

            val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_180)
            binding.sortDirectionButton.startAnimation(rotation)

            binding.sortDirectionButton.isPressed = !isDescending
        }
    }

    private fun setupBottomSheet() {

        binding.sortGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            sortBy = group.findViewById<Chip>(checkedIds.first()).text.toString()
        }

        binding.categoryGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            sortType = group.findViewById<Chip>(checkedIds.first()).text.toString()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.filtersBottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }
                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.alpha = slideOffset + 1
            }
        })

        binding.iconFilter.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        binding.applyButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            viewModel.setFilters(
                sortBy = sortBy,
                sortType = sortType,
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = RecipesPagingAdapter()

        binding.recipeList.visibility = View.VISIBLE

        binding.recipeList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                Log.d("PagingData", "loadState activity")

                binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
                binding.errorMessage.isVisible = loadState.refresh is LoadState.Error

                if (loadState.refresh is LoadState.Error) {
                    val error = (loadState.refresh as LoadState.Error).error
                    binding.errorMessage.text = error.message
                }
            }
        }
    }

    private fun setupSearch() {
        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchDebounceJob?.cancel()
                searchDebounceJob = lifecycleScope.launch {
                    delay(500)
                    viewModel.setRecipesQuery(p0?.toString() ?: "")
                }
            }
        })
    }

    private fun setupObservers() {
        // Собираем пагинированные данные
        lifecycleScope.launch {
            viewModel.recipesPagingData.collectLatest { pagingData ->
                Log.d("PagingData", "setupObservers() activity")
                adapter.submitData(pagingData)
            }
        }
    }
}