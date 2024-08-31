package com.sun.weather.ui.setting

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.sun.weather.R
import com.sun.weather.base.BaseFragment
import com.sun.weather.databinding.FragmentSettingBinding
import com.sun.weather.ui.SharedViewModel
import com.sun.weather.utils.Constant.LANGUAGE_CODE_ENGLISH
import com.sun.weather.utils.Constant.LANGUAGE_CODE_VIETNAMESE
import com.sun.weather.utils.SharedPrefManager
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class SettingFragment() : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    override val viewModel: SettingViewModel by viewModel()
    override lateinit var  sharedViewModel: SharedViewModel
    override fun initView() {
        sharedViewModel = activityViewModel<SharedViewModel>().value
        binding.languageButton.setOnClickListener {
            showLanguageSelectionDialog()
        }
    }

    override fun initData() {
        viewModel.flagResId.observe(viewLifecycleOwner) { flagResId ->
            binding.flagImageView.setImageResource(flagResId)
        }
    }
    private fun showLanguageSelectionDialog() {
        val languageCode = Locale.getDefault().language
        val languages =
            when (languageCode) {
                LANGUAGE_CODE_VIETNAMESE -> resources.getStringArray(R.array.languages_vietnamese)
                LANGUAGE_CODE_ENGLISH -> resources.getStringArray(R.array.languages_english)
                else -> resources.getStringArray(R.array.languages_english)
            }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_language))
            .setItems(languages) { _, which ->
                when (which) {
                    INDEX_VIETNAMESE -> {
                        viewModel.setLocale(requireContext(),LANGUAGE_CODE_VIETNAMESE)
                        viewModel.setFlagResId(FLAG_RES_ID_VIETNAMESE)
                    }
                    INDEX_ENGLISH -> {
                        viewModel.setLocale(requireContext(), LANGUAGE_CODE_ENGLISH)
                        viewModel.setFlagResId(FLAG_RES_ID_ENGLISH)
                    }
                }
                activity?.recreate()
            }
            .show()
    }

    override fun bindData() {

    }

    companion object {
        const val KEY_FLAG_RES_ID = "flagResId"
        val DEFAULT_FLAG_RES_ID = R.drawable.vn_flag
        val FLAG_RES_ID_VIETNAMESE = R.drawable.vn_flag
        val FLAG_RES_ID_ENGLISH = R.drawable.en_flag
        const val INDEX_VIETNAMESE = 0
        const val INDEX_ENGLISH = 1
        const val KEY_LANGUAGE_CODE = "language_code"
        fun newInstance() = SettingFragment()

    }

}