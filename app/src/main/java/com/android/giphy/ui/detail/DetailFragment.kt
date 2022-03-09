package com.android.giphy.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.android.giphy.Application
import com.android.giphy.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: DetailViewModel
    private lateinit var viewDataBinding: FragmentDetailBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as Application).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentDetailBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel.getGif(args.gifId)
        setupObservable()
        return viewDataBinding.root
    }

    private fun setupObservable() {
        lifecycleScope.launchWhenStarted {
            viewModel.snackbarMessage.collectLatest {
                Snackbar.make(viewDataBinding.root, getText(it), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}