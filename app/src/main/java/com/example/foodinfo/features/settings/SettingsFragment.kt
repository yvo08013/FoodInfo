package com.example.foodinfo.features.settings

import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.databinding.FragmentSettingsBinding


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private val viewModel: SettingsViewModel by appViewModels()


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}