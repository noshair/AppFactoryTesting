package com.appfactorycoding.section.search.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appfactorycoding.R
import com.appfactorycoding.databinding.FragmentSearchBinding
import com.appfactorycoding.section.search.ui.adapter.SearchAdapter
import com.appfactorycoding.service.extension.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.SearchItemClickListener {
    private lateinit var searchBinding: FragmentSearchBinding
    private var factory: SearchAdapter? = null

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerview()
        setHasOptionsMenu(true)
        lifecycleScope.launchWhenStarted {
            searchViewModel.searchList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is Resource.OnFailure -> {
                            searchBinding.progressBar.visibility = View.GONE
                            searchBinding.recyclerList.visibility = View.GONE
                            searchBinding.hint.visibility = View.GONE
                            if (it.error != null) {
                                Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                            }
                        }
                        is Resource.OnSuccess -> {
                            searchBinding.recyclerList.visibility = View.VISIBLE
                            searchBinding.progressBar.visibility = View.GONE
                            searchBinding.hint.visibility = View.GONE
                            if (it.data != null) {
                                if (it.data.total > 0)
                                    factory?.update(it.data.objectIDs)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Sorry,No Object Related to this Key",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        is Resource.OnLoading -> {
                            searchBinding.progressBar.visibility = View.VISIBLE
                            searchBinding.hint.visibility = View.GONE
                            searchBinding.recyclerList.visibility = View.GONE
                        }
                        is Resource.OnNothing -> {}
                    }
                }
        }
    }

    private fun initRecyclerview() {
        searchBinding.recyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            this.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            factory = SearchAdapter(this@SearchFragment,context)
            this.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            adapter = factory
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchViewModel.getSearch(query)
                    searchBinding.progressBar.visibility = View.VISIBLE
                    searchView.clearFocus()

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun itemClicked(id: Int) {
        val bundle = bundleOf("id" to id)
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(
            R.id.action_searchFragment_to_searchDetailFragment,
            bundle
        )
    }

}