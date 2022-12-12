package com.example.foodinfo.ui.view_holder

import android.view.LayoutInflater
import com.example.foodinfo.databinding.ItemRecipeCategoryBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.repository.model.CategoryLabelsModel


class RecipeCategoryViewHolder(
    private val inflater: LayoutInflater,
    private val binding: ItemRecipeCategoryBinding,
    private val onLabelClickListener: (String, String) -> Unit
) : BaseViewHolder<ItemRecipeCategoryBinding, CategoryLabelsModel>(binding) {

    override fun bind(newItem: CategoryLabelsModel) {
        super.bind(newItem)
        binding.tvTitle.text = item.name
        for (label in item.labels) {
            binding.cgLabels.addView(
                TvChipBinding.inflate(inflater, null, false).apply {
                    this.textView.text = label
                }.root.apply {
                    setOnClickListener { onLabelClickListener(item.name, label) }
                }
            )
        }
    }
}