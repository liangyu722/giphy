package com.android.giphy.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.giphy.Application
import com.android.giphy.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var viewDataBinding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as Application).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupNavigation()
        setupObservable()
    }

    private fun setupListAdapter() {
        adapter = HomeAdapter(viewModel)
        viewDataBinding.gifRecyclerView.adapter = adapter
        viewDataBinding.gifRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun setupNavigation() {
        viewModel.clickEvent.observe(this.viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { gif ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(gif.id)
                findNavController().navigate(action)
            }
        })
    }

    private fun setupObservable() {
        lifecycleScope.launchWhenStarted {
            viewModel.snackbarMessage.collectLatest {
                Snackbar.make(viewDataBinding.root, getText(it), Snackbar.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.pageGifs.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}