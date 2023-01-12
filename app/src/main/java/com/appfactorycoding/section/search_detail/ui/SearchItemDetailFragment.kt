package com.appfactorycoding.section.search_detail.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appfactorycoding.R
import com.appfactorycoding.databinding.FragmentSearchDetailBinding
import com.appfactorycoding.section.search_detail.ui.adapter.SearchDetailAdapter
import com.appfactorycoding.service.extension.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailBinding
    private var factory: SearchDetailAdapter? = null
    private val searchViewModel: SearchItemViewModel by lazy {
        ViewModelProvider(this)[SearchItemViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("id")?.let { searchViewModel.getSelectedItem(it) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_detail, container, false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerview()
        lifecycleScope.launchWhenStarted {

            searchViewModel.searchDetailItem.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is Resource.OnFailure -> {
                            if (it.error != null) {
                                Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                            }
                        }
                        is Resource.OnSuccess -> {
                            if (it.data != null) {
                                if (it.data.primaryImage != "")
                                    Glide.with(this@SearchDetailFragment)
                                        .load(it.data.primaryImage)
                                        .placeholder(R.drawable.loadingif)
                                        .into(binding.primaryImage)
                                else{
                                    binding.primaryImage.setImageResource(R.drawable.nophoto)
                                }

                                binding.title.text =
                                    context?.resources?.getString(R.string.title, it.data.title)
                                        ?: ""
                                binding.objectName.text = context?.resources?.getString(
                                    R.string.objectName,
                                    it.data.objectName
                                ) ?: ""
                                binding.department.text = context?.resources?.getString(
                                    R.string.department,
                                    it.data.department
                                ) ?: ""
                                if (it.data.additionalImages.isNotEmpty()){
                                    factory?.update(it.data.additionalImages)
                                }
                            }
                        }
                        is Resource.OnLoading -> {
                        }
                        else -> {}
                    }

                }
        }

    }
    private fun initRecyclerview() {
        binding.recyclerDetailList.apply {
            layoutManager = LinearLayoutManager(context)
            this.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            factory = SearchDetailAdapter(context)
            this.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            adapter = factory
        }
    }

}