package com.task.shortlyapp.ui.shorteninglink

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.shortlyapp.R
import com.task.shortlyapp.databinding.FragmentShortyBinding
import com.task.shortlyapp.repository.locale.entity.ShortlyLink
import com.task.shortlyapp.ui.shorteninglink.adapter.ShortlyLinksListAdapter
import com.task.shortlyapp.utils.NetworkState
import com.task.shortlyapp.utils.NetworkStatusListener
import com.task.shortlyapp.utils.getColor
import com.task.shortlyapp.utils.getDrawable
import com.task.shortlyapp.utils.hide
import com.task.shortlyapp.utils.hideKeyboard
import com.task.shortlyapp.utils.show
import com.task.shortlyapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShortyLinkFragment : Fragment(), ShortyLinkView, View.OnClickListener {

    private var fragmentShortyBinding: FragmentShortyBinding? = null
    private val shortlyViewModel: ShortlyViewModel by viewModels()
    private val shortlyLinksListAdapter = ShortlyLinksListAdapter({
        onDelete(shortlyLink = it)
    }, {
        onCopy(shortlyLink = it)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShortyBinding = FragmentShortyBinding.inflate(inflater, container, false)
        return fragmentShortyBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addListeners()
        shortlyViewModel.initialViewToggle()
        initAdapter()
    }

    private fun initAdapter() {
        fragmentShortyBinding?.contentLinkHistory?.recycleViewLinks?.apply {
            adapter = shortlyLinksListAdapter
        }
        if (shortlyLinksListAdapter.shortlyLinks.isEmpty())
            shortlyViewModel.getShortenLinksFromDb()
    }

    //region LISTENERS
    private fun addListeners() {

        shortlyViewModel.initialViewToggle.observe(viewLifecycleOwner) {
            fragmentShortyBinding?.viewSwitcher?.displayedChild = it.position
        }

        fragmentShortyBinding?.layoutShortenUrlForm?.buttonShortenIt?.setOnClickListener(this)
        editTextChangeListener()
        addShorteningLinkStateObserver()
    }

    private fun addShorteningLinkStateObserver() {
        shortlyViewModel.shorteningLinkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.Success -> {
                    (state.data as? List<ShortlyLink>)?.forEach {
                        if (shortlyLinksListAdapter.shortlyLinks.contains(it).not()) {
                            shortlyLinksListAdapter.add(it)
                        }
                    }
                    enableDisableShortenItButton(enable = true)
                    fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.setText(getString(R.string.empty))
                }
                is NetworkState.Failure -> {
                    onShortlyError(state.error as? String)
                    enableDisableShortenItButton(enable = true)
                }
                is NetworkState.Loading -> {
                    enableDisableShortenItButton(enable = false)
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
                triggerShortly()
            }
        }
    }
    //endregion

    //region SHORTY LINK VIEW

    override fun getEnteredLink(): String {
        return fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.text.toString()
    }

    override fun triggerShortly() {
        fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.text.toString()
            .takeIf { it.isNotEmpty() }?.let {
                hideKeyboard()
                if (NetworkStatusListener.isOnline(requireContext())) {
                    shortlyViewModel.shortenLink(link = getEnteredLink())
                } else {
                    onShortlyError(getString(R.string.no_internet_error))
                }
            } ?: kotlin.run {
            emptyURLError()
        }
    }

    override fun onShortlyError(message: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message ?: getString(R.string.error_received_custom))
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    override fun emptyURLError() {
        handleEditTextErrorView(
            R.drawable.edit_text_border_error,
            R.color.red,
            getString(R.string.please_add_a_link_here)
        )
    }

    override fun onDelete(shortlyLink: ShortlyLink) {
        shortlyLinksListAdapter.remove(shortlyLink)
        shortlyViewModel.initialViewToggle.value =
            if (shortlyLinksListAdapter.shortlyLinks.isEmpty()) ViewType.ILLUSTRATION else ViewType.DATA
        shortlyViewModel.deleteShortlyLink(shortlyLink)
    }

    override fun onCopy(shortlyLink: ShortlyLink) {
        val clipboard: ClipboardManager? =
            requireActivity().getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText(shortlyLink.short_link, shortlyLink.short_link)
        clipboard?.setPrimaryClip(clip)
        showToast(getString(R.string.copy_to_clip_board))
    }

    //endregion

    //region UTILS
    private fun handleEditTextErrorView(bgDrawable: Int, hintColor: Int, hintText: String) {
        fragmentShortyBinding?.layoutShortenUrlForm?.editTextLink?.apply {
            setBackgroundDrawable(
                getDrawable(requireContext(), bgDrawable)
            )
            hint = hintText
            setHintTextColor(getColor(requireContext(), hintColor))
        }
    }

    private fun enableDisableShortenItButton(enable: Boolean) {
        fragmentShortyBinding?.layoutShortenUrlForm?.apply {
            buttonShortenIt.isEnabled = enable
            if (enable) myProgressBar.hide() else myProgressBar.show()
        }
    }
}
