package com.example.foodinfo.ui.fragment

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchQueryBinding
import com.example.foodinfo.ui.adapter.SearchRecipeAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.GridItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observePages
import com.example.foodinfo.view_model.SearchQueryViewModel


class SearchQueryFragment : BaseFragment<FragmentSearchQueryBinding>(
    FragmentSearchQueryBinding::inflate
) {

    private val args: SearchQueryFragmentArgs by navArgs()

    private val viewModel: SearchQueryViewModel by appViewModels()

    private lateinit var recyclerAdapter: SearchRecipeAdapter

    private val onBackClickListener: () -> Unit = {
        findNavController().popBackStack(R.id.f_search_input, true)
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            SearchQueryFragmentDirections.actionFSearchQueryToFSearchInput()
        )
    }

    private val onItemClickListener: (String) -> Unit = { id ->
        findNavController().navigate(
            SearchQueryFragmentDirections.actionFSearchQueryToFRecipeExtended(id)
        )
    }

    private val onFavoriteClickListener: (String) -> Unit = { id ->
        viewModel.invertFavoriteStatus(id)
    }

    private val navigateBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack(R.id.f_search_input, true)
        }
    }


    override fun onStop() {
        navigateBackCallback.remove()
        super.onStop()
    }


    override fun initUI() {
        viewModel.inputText = args.query

        binding.tvQuery.text = args.query
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }

        requireActivity().onBackPressedDispatcher.addCallback(navigateBackCallback)

        recyclerAdapter = SearchRecipeAdapter(
            onItemClickListener,
            onFavoriteClickListener
        )

        with(binding.rvRecipes) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                GridItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.search_recipes_item_horizontal),
                    resources.getDimensionPixelSize(R.dimen.search_recipes_item_vertical),
                    2,
                    RecyclerView.VERTICAL
                )
            )
            itemAnimator = null
        }
    }

    override fun subscribeUI() {
        observePages(
            useLoadingData = true,
            dataFlow = viewModel.pagingHelper,
            pageFlow = viewModel.recipes,
            onSuccess = viewModel::setHelper,
            onPageCollected = recyclerAdapter::submitData
        )
    }
}