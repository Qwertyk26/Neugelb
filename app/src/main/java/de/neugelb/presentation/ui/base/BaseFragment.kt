package de.neugelb.presentation.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.neugelb.R
import de.neugelb.domain.error.ErrorType
import de.neugelb.domain.usecase.Error
import de.neugelb.presentation.ui.extensions.observe

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel
    protected val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            observe(errorLiveEvent, ::handleError)
        }
    }

    protected open fun handleError(error: Error<*>): Unit = when (error.type) {
        is ErrorType.ServerError -> {
            Toast.makeText(requireContext(), "${error.type.code}", Toast.LENGTH_SHORT).show()
        }
        is ErrorType.TimeoutError -> {
            Toast.makeText(requireContext(), requireContext().getString(R.string.general_network_error), Toast.LENGTH_SHORT).show()
        }
        else -> {

        }
    }
}