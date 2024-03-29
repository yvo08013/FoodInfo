package com.example.foodinfo.features.recipe.adapter

import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.databinding.RvItemNutrientOfRecipeBinding
import com.example.foodinfo.features.recipe.model.NutrientVHModel


fun nutrientAdapterDelegate(
    onNutrientClickListener: (NutrientVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemNutrientOfRecipeBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.clNutrient.setOnClickListener { onNutrientClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.tvName.text = item.name
        binding.tvWeight.text = item.weight
        binding.tvPercent.text = item.dailyPercent
        binding.progressBar.progress = item.dailyPercentValue
    }
)