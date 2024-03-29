package com.example.foodinfo.features.recipe.adapter

import android.view.LayoutInflater
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.databinding.ItemCategoryOfRecipeBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.features.recipe.model.CategoryVHModel


fun categoryAdapterDelegate(
    onLabelClickListener: (Int) -> Unit
) = appAdapterDelegate<CategoryVHModel, ItemCategoryOfRecipeBinding>(
    inflate = ItemCategoryOfRecipeBinding::inflate,
    onBind = { binding, item, _ ->
        binding.tvTitle.text = item.name
        LayoutInflater.from(binding.root.context).also { inflater ->
            for (label in item.labels) {
                binding.cgLabels.addView(
                    TvChipBinding.inflate(inflater, null, false).apply {
                        this.textView.text = label.name
                    }.root.apply {
                        setOnClickListener { onLabelClickListener(label.infoID) }
                    }
                )
            }
        }
    }
)