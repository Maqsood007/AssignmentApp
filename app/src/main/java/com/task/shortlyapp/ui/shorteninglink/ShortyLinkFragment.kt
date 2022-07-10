package com.task.shortlyapp.ui.shorteninglink

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.shortlyapp.R
import com.task.shortlyapp.databinding.FragmentShortyBinding
import com.task.shortlyapp.utils.NetworkState
import com.task.shortlyapp.utils.getColor
import com.task.shortlyapp.utils.getDrawable
import com.task.shortlyapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShortyLinkFragment : Fragment(), ShortyLinkView, View.OnClickListener {

    private var fragmentShortyBinding: FragmentShortyBinding? = null
    private var shortlyViewModel: ShortlyViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShortyBinding = FragmentShortyBinding.inflate(inflater, container, false)
        return fragmentShortyBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shortlyViewModel = ViewModelProvider(this)[ShortlyViewModel::class.java]
        addListeners()
    }

    //region LISTENERS
    private fun addListeners() {
        fragmentShortyBinding?.layoutShortenUrlForm?.buttonShortenIt?.setOnClickListener(this)
        editTextChangeListener()
        addShorteningLinkStateObserver()
    }

    private fun addShorteningLinkStateObserver() {
        shortlyViewModel?.shorteningLinkState?.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Success -> {
                    Log.d("", "")
                }
                is NetworkState.Failure -> {
                    onShortyError(it.error as? String)
                }
                is NetworkState.Loading -> {
                    Log.d("", "")
                }
            }
        }
    }

    private fun editTextChangeListener() {
        fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                handleEditTextErrorView(
                    R.drawable.edit_text_border,
                    R.color.gray_dark,
                    getString(R.string.shorten_a_link_here)
                )
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonShortenIt -> {
                triggerShorty()
            }
        }
    }
    //endregion

    //region SHORTY LINK VIEW

    override fun getEnteredLink(): String {
        return fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.text.toString()
    }

    override fun triggerShorty() {
        fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.text.toString()
            .takeIf { it.isNotEmpty() }?.let {
                hideKeyboard()
                shortlyViewModel?.shortenLink(link = getEnteredLink())
            } ?: kotlin.run {
            emptyURLError()
        }
    }

    override fun onShortyError(message: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message ?: getString(R.string.error_received_custom))
            .show()
    }

    override fun emptyURLError() {
        handleEditTextErrorView(
            R.drawable.edit_text_border_error,
            R.color.red,
            getString(R.string.please_add_a_link_here)
        )
    }

    //endregion

    //region UTILS
    private fun handleEditTextErrorView(bgDrawable: Int, hintColor: Int, hintText: String) {
        fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.apply {
            setBackgroundDrawable(
                getDrawable(requireContext(), bgDrawable)
            )
            hint = hintText
            getColor(requireContext(), hintColor)?.let { setHintTextColor(it) }
        }
    }
}
