package com.sun.weather.ui.search

import android.util.Log
import android.widget.SearchView
import com.google.android.gms.maps.SupportMapFragment
import com.sun.weather.R
import com.sun.weather.base.BaseFragment
import com.sun.weather.databinding.FragmentSearchBinding
import com.sun.weather.ui.SharedViewModel
import com.sun.weather.ui.home.HomeFragment
import com.sun.weather.utils.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override val viewModel: SearchViewModel by viewModel()
    override val sharedViewModel: SharedViewModel by activityViewModel()

    override fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            viewModel.onMapReady(googleMap)
        } ?: Log.d("SearchFragment", "SupportMapFragment not found!")
        binding.mapSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onSearchQuerySubmit(query ?: "")
                    query?.let {
                        sharedViewModel.setSelectedLocation(it)
                    }
                    return false
                }
            },
        )
    }

    override fun initData() {
        viewModel.currentMarkerPosition.observe(viewLifecycleOwner) { position ->
            position?.let {
                viewModel.updateMapMarker(it)
            }
        }
    }

    override fun bindData() {
        viewModel.navigateToHome.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                replaceFragment(R.id.fragment_container, HomeFragment.newInstance(), true)
            }
        }
    }
    companion object {
        fun newInstance() = SearchFragment()
    }
}
